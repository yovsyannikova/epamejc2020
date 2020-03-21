package seabattle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Bot extends Player {
    
    private List<Coordinate> possibleVariants = new ArrayList<>();
    Shot previousHit;
    
    public Bot() {
    }
    
    @Override public void fieldFillStrategy() {
        super.fillListOfShipsRandomly();
    }
    
    public void turn(String s) {
        skip40rows();
        shotMethod();
    }

//    @Override public void shotMethod() {
//        System.out.println("My field");
//        getView().printField(getMyField());
//        System.out.println("Opponents field");
//        getView().printField(getOpponentsField());
//        shotWhileHit();
//    }
    
    @Override public void shotMethod() {
        int fireResult = -1;
        Coordinate shotCoordinate;
        System.out.println("My field");
        view.printField(myField);
        System.out.println("Opponents field");
        view.printField(opponentsField);
        while (fireResult != 0 && opponentsFieldData.getShips()
                                                    .size() > 0 && myField.getShips()
                                                                          .size() > 0) {
            if (possibleVariants.size() == 0) {
                do {
                    shotCoordinate = Coordinate.generateRandomCoordinate();
                } while (!Coordinate.isCoordinatesCorrect(shotCoordinate) || opponentsField.checkShotsCollision(
                        shotCoordinate));
            } else {
                shotCoordinate = possibleVariants.get(new Random().nextInt(possibleVariants.size()));
            }
            Shot shot = new Shot(shotCoordinate);
            opponentsField.getShots()
                          .add(shot);
            possibleVariants.remove(shotCoordinate);
            fireResult = takeAShot(opponentsFieldData, shotCoordinate);
            if (fireResult > 0) {
                addPossibleVariantsOfShots(shotCoordinate);
                previousHit = shot;
            }
            System.out.println("My Field");
            view.printField(myField);
            System.out.println("My view of opponents field");
            view.printField(opponentsField);
        }
    }

//    @Override public int takeAShot(Field field, Coordinate shotCoordinate) {
//        Iterator shipIterator = field.ships.iterator();
//        int result = 0;
//        result = iterateShips(shotCoordinate, shipIterator, result);
//        if (result == 0) {
//            getOpponentsField().misses.add(new Miss(shotCoordinate));
//            getOpponentsFieldData().misses.add(new Miss(shotCoordinate));
//            System.out.println("Miss");
//        }
//        return result;
//    }
    
    
    @Override public int takeAShot(Field field, Coordinate shotCoordinate) {
        Iterator shipIterator = field.getShips()
                                     .iterator();
        int result;
        while (shipIterator.hasNext()) {
            Ship ship = (Ship) shipIterator.next();
            Iterator shipPartsIterator = ship.getShipParts()
                                             .iterator();
            result = iterateShipParts(shotCoordinate, shipIterator, ship, shipPartsIterator);
            if (result > 0) {
                return result;
            }
        }
        opponentsField.getMisses()
                      .add(new Miss(shotCoordinate));
        opponentsFieldData.getMisses()
                          .add(new Miss(shotCoordinate));
        System.out.println("Miss");
        return 0;
    }
    
    private void shotWhileHit() {
        Coordinate shotCoordinate;
        int fireResult = -1;
        while (fireResult != 0 && getOpponentsFieldData().ships.size() > 0 && getMyField().ships.size() > 0) {
            shotCoordinate = getShotCoordinate();
            Shot shot = new Shot(shotCoordinate);
            getOpponentsField().shots.add(shot);
            possibleVariants.remove(shotCoordinate);
            fireResult = takeAShot(getOpponentsFieldData(), shotCoordinate);
            if (fireResult > 0) {
                addPossibleVariantsOfShots(shotCoordinate);
                previousHit = shot;
            } else {
                System.out.println("My Field");
                getView().printField(getMyField());
                System.out.println("My view of opponents field");
                getView().printField(getOpponentsField());
            }
        }
    }
    
    private Coordinate getShotCoordinate() {
        Coordinate shotCoordinate;
        if (possibleVariants.size() == 0) {
            do {
                shotCoordinate = Coordinate.generateRandomCoordinate();
            } while (!Coordinate.isCoordinatesCorrect(shotCoordinate) || getOpponentsField().checkShotsCollision(
                    shotCoordinate));
        } else {
            shotCoordinate = possibleVariants.get(new Random().nextInt(possibleVariants.size()));
        }
        return shotCoordinate;
    }
    
    private int iterateShips(Coordinate shotCoordinate, Iterator shipIterator, int result) {
        while (shipIterator.hasNext()) {
            Ship ship = (Ship) shipIterator.next();
            Iterator shipPartsIterator = ship.getShipParts().iterator();
            result = iterateShipParts(shotCoordinate, shipIterator, ship, shipPartsIterator);
        }
        return result;
    }
    
    private int iterateShipParts(
            Coordinate shotCoordinate, Iterator shipIterator, Ship ship, Iterator shipPartsIterator) {
        while (shipPartsIterator.hasNext()) {
            Coordinate shipCoordinate = (Coordinate) shipPartsIterator.next();
            if (shotCoordinate.equals(shipCoordinate)) {
                return hitOrKill(shotCoordinate, shipIterator, ship, shipPartsIterator, shipCoordinate);
            }
        }
        return 0;
    }
    
    private int hitOrKill(
            Coordinate shotCoordinate, Iterator shipIterator, Ship ship, Iterator shipPartsIterator,
            Coordinate shipCoordinate) {
        if (ship.getShipParts().size() > 1) {
            hit(shotCoordinate, shipPartsIterator);
            return 1;
        } else {
            kill(shotCoordinate, shipIterator, ship, shipPartsIterator, shipCoordinate);
            return 2;
        }
    }
    
    private void kill(
            Coordinate shotCoordinate, Iterator shipIterator, Ship ship, Iterator shipPartsIterator,
            Coordinate shipCoordinate) {
        System.out.println("kill");
        shipPartsIterator.remove();
        getOpponentsField().addAssistPointsAroundKill(shotCoordinate, ship);
        shipIterator.remove();
        opponentsFieldData.getShots()
                          .add(new Shot(shipCoordinate));
        possibleVariants = new ArrayList<>();
    }
    
    private void hit(Coordinate shotCoordinate, Iterator shipPartsIterator) {
        shipPartsIterator.remove();
        System.out.println("hit");
        getOpponentsField().addAssistPointsAroundShot(shotCoordinate);
        Shot shot = new Shot(shotCoordinate);
        getOpponentsFieldData().shots.add(shot);
        removeRedundantPossibleVariants(shotCoordinate, shot);
    }
    
    private void removeRedundantPossibleVariants(Coordinate shotCoordinate, Shot shot) {
        if (possibleVariants.size() > 1 && isNearPrevious(shot)) {
            Ship hypotheticalShip = new Ship(shotCoordinate, previousHit.getCoordinate());
            switch (hypotheticalShip.getDirection()) {
                case 1:
                    removePossibleVerticalPoints();
                    break;
                case 2:
                    removePossibleHorizontalPoints();
                    break;
            }
        }
    }
    
    private List<Coordinate> getPossibleVariants() {
        return possibleVariants;
    }
    
    private void removePossibleVerticalPoints() {
        Iterator possibleVariantsIterator = getPossibleVariants().iterator();
        while (possibleVariantsIterator.hasNext()) {
            Coordinate coordinate = (Coordinate) possibleVariantsIterator.next();
            if (coordinate.getY() > previousHit.getCoordinate().getY() ||
                coordinate.getY() < previousHit.getCoordinate().getY()) {
                possibleVariantsIterator.remove();
            }
        }
    }
    
    private void removePossibleHorizontalPoints() {
        Iterator possibleVariantsIterator = getPossibleVariants().iterator();
        while (possibleVariantsIterator.hasNext()) {
            Coordinate coordinate = (Coordinate) possibleVariantsIterator.next();
            if (coordinate.getX() > previousHit.getCoordinate().getX() ||
                coordinate.getX() < previousHit.getCoordinate().getX()) {
                possibleVariantsIterator.remove();
            }
        }
    }
    
    private boolean isNearPrevious(Shot shot) {
        return (Math.abs(previousHit.getCoordinate().getX() - shot.getCoordinate().getX()) < 2 && Math.abs(
                previousHit.getCoordinate().getY() - shot.getCoordinate().getY()) < 2);
    }
    
    private void addPossibleVariantsOfShots(Coordinate shotCoordinate) {
        addPossibleVariants(shotCoordinate, 0, -1);
        addPossibleVariants(shotCoordinate, 1, 0);
        addPossibleVariants(shotCoordinate, 0, 1);
        addPossibleVariants(shotCoordinate, -1, 0);
    }
    
    private void addPossibleVariants(Coordinate shotCoordinate, int addToX, int addToY) {
        Coordinate possibleCoordinate;
        possibleCoordinate = new Coordinate(shotCoordinate.getX() + addToX, shotCoordinate.getY() + addToY);
        if (Coordinate.isCoordinatesCorrect(possibleCoordinate) && !getOpponentsField().checkShotsCollision(
                possibleCoordinate)) {
            possibleVariants.add(possibleCoordinate);
        }
    }
    
}