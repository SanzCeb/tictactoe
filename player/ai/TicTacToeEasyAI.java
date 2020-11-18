package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.player.Coordinate;
import tictactoe.player.Player;

import java.util.Random;

public class TicTacToeEasyAI extends Player {
    private static final Random randomizer = new Random();

    public TicTacToeEasyAI(int dimension) {
        super(dimension);
    }

    public Coordinate getCoordinates() {
        return  new Coordinate(randomizer.nextInt(dimension), randomizer.nextInt(dimension));
    }

    @Override
    public String playerMessage() {
        return "Making move level \"easy\"";
    }
}
