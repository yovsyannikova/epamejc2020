package seabattle;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    
    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private List<Coordinate> shipParts;
    private int length;
    private int direction;
    
    public Ship(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.length = calculateLength();
        this.direction = calculateDirection();
        this.shipParts = fillShipPartsList();
    }
    
    public Coordinate getFirstCoordinate() {
        return firstCoordinate;
    }
    
    public void setFirstCoordinate(Coordinate firstCoordinate) {
        this.firstCoordinate = firstCoordinate;
    }
    
    public Coordinate getSecondCoordinate() {
        return secondCoordinate;
    }
    
    public void setSecondCoordinate(Coordinate secondCoordinate) {
        this.secondCoordinate = secondCoordinate;
    }
    
    public List<Coordinate> getShipParts() {
        return shipParts;
    }
    
    public void setShipParts(List<Coordinate> shipParts) {
        this.shipParts = shipParts;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int calculateLength() {
        return (firstCoordinate.getX() == secondCoordinate.getX() ?
                Math.abs(firstCoordinate.getY() - secondCoordinate.getY()) :
                Math.abs(firstCoordinate.getX() - secondCoordinate.getX())) + 1;
    }
    
    public int calculateDirection() {
        if (firstCoordinate.getY() - secondCoordinate.getY() < 0) {
            return 2;
        }
        if (firstCoordinate.getX() - secondCoordinate.getX() < 0) {
            return 1;
        }
        return 0;
    }
    
    private List<Coordinate> fillShipPartsList() {
        List<Coordinate> coordinates = new ArrayList<>();
        switch (direction) {
            case 0:
                coordinates.add(firstCoordinate);
                break;
            case 1:
                fillHorizontally(coordinates);
                break;
            case 2:
                fillVertically(coordinates);
                break;
        }
    
        return coordinates;
    }
    
    private void fillHorizontally(List<Coordinate> coordinates) {
        for (int i = 0; i < length; i++) {
            coordinates.add(new Coordinate(firstCoordinate.getX() + i, firstCoordinate.getY()));
        }
    }
    
    private void fillVertically(List<Coordinate> coordinates) {
        for (int i = 0; i < length; i++) {
            coordinates.add(new Coordinate(firstCoordinate.getX(), firstCoordinate.getY() + i));
        }
    }
    
}
