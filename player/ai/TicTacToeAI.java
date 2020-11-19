package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.player.Player;

abstract class TicTacToeAI extends Player {
    protected final Board TIC_TAC_TOE;

    protected TicTacToeAI(Board tic_tac_toe) {
        super(tic_tac_toe.getDimension(), false);
        TIC_TAC_TOE = tic_tac_toe;
    }

}
