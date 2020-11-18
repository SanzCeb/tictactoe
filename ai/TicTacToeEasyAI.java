package tictactoe.ai;

import java.util.Random;

public class TicTacToeEasyAI {
    private static final Random randomizer = new Random();

    public static int [] getCoordinates() {
        return new int [] {randomizer.nextInt(3), randomizer.nextInt(3)};
    }
}
