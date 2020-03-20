package homeworks.seabattle.initiator;

import homeworks.seabattle.field.FieldPrinter;
import homeworks.seabattle.util.ConsoleReader;
import homeworks.seabattle.util.Positions;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerFieldInitiator extends FieldInitiator {

    @Override
    @SneakyThrows
    public void init() {

        Positions.swapPositions();

        FieldPrinter fieldPrinter = new FieldPrinter();

        String userShip;

        while (Positions.playerShips.size() != 10) {

            fieldPrinter.print();

            System.out.print("\nSelect cells to put your ship in\n" +
                    "for example 'a1 a2 a3 a4'\n" +
                    "dont forget that ship should be a straight line and not diagonal: ");

            userShip = ConsoleReader.reader.readLine();

            if (userShip.equalsIgnoreCase("exit")) {
                break;
            }

            handleUserInput(userShip);
        }
    }

    private void handleUserInput(String userShip) {
        if (userShip.length() != 0) {
            filterAndSet(findCellPositions(getCells(userShip)));
        } else {
            System.out.println("Wrong input");
        }
    }

    private List<Integer> findCellPositions(List<String> shipCells) {
        return shipCells.stream()
                .map(Positions.allCells::indexOf)
                .collect(Collectors.toList());
    }

    private List<String> getCells(String userShip) {
        return Arrays.stream(userShip.split(" "))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

}