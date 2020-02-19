package seaBattle.logic;

import seaBattle.data.Configuration;
import seaBattle.data.Player;

/**
 * @version 0.1
 * @author Andrew Nuzha
 */
public class Application {

    public void run() {
        Configuration.readConfig();
        System.out.println("Welcome to seabattle game ver 0.1\n");

        Player humanPlayer = new Player();
        Player botPlayer = new Player();

        FieldPrinter fieldPrinter = new FieldPrinter();
        fieldPrinter.printField(humanPlayer.getField(),botPlayer.getField());

        Battle seaBattle = new Battle(humanPlayer, botPlayer);
        seaBattle.startBattle();
    }
}