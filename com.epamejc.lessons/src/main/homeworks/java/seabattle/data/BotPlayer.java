package homeworks.java.seabattle.data;

import homeworks.java.seabattle.data.enums.GameState;
import homeworks.java.seabattle.utils.RandomNameGenerator;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class BotPlayer extends Player {

    private AI ai;

    public BotPlayer() {

        super();
        ai = new AI();
        this.setName(RandomNameGenerator.generateName());

    }

    @Override
    @SneakyThrows
    public GameState shoot(Player enemy) {

        TimeUnit.MILLISECONDS.sleep(600);
        moves++;
        return ai.shoot(enemy);

    }

}
