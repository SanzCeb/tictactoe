package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.board.BoardMovesAnalytics;
import tictactoe.player.Coordinate;

public class TicTacToeEasyAI extends TicTacToeAI {

    public TicTacToeEasyAI(Board ticTacToe) {
        super(ticTacToe);
    }

    public Coordinate getCoordinates() {
        return  BoardMovesAnalytics.randomMove(TIC_TAC_TOE);
    }

    @Override
    public String playerMessage() {
        return "Making move level \"easy\"\n";
    }
}
