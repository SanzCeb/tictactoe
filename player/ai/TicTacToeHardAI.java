package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.board.BoardMovesAnalytics;
import tictactoe.player.Coordinate;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class TicTacToeHardAI extends TicTacToeAI {

    public TicTacToeHardAI(Board tic_tac_toe) {
        super(tic_tac_toe);
    }

    @Override
    public Coordinate getCoordinates() {
        return BoardMovesAnalytics.legalMoves(TIC_TAC_TOE)
                .max(Comparator.comparingInt(move -> minimax(newBoard(move), true)))
                .orElseThrow(() -> new NoSuchElementException("No legal moves"));
    }

    //TODO CHECK OPPONENT
    private int minimax(Board tic_tac_toe, boolean opponent) {
        if (!tic_tac_toe.isNotFinished()) {
            if (opponent && BoardMovesAnalytics.isThereWinner(tic_tac_toe)) {
                return 10;
            } else if (BoardMovesAnalytics.isThereWinner(tic_tac_toe)) {
                return -10;
            } else {
                return 0;
            }
        } else if (opponent) {
            return worstScore(tic_tac_toe);
        } else {
            return bestScore(tic_tac_toe);
        }
    }

    private Board newBoard (Coordinate move){
        return BoardMovesAnalytics.simulateMove(TIC_TAC_TOE, move);
    }


    @Override
    public String playerMessage() {
        return "Making move level \"hard\"\n";
    }

    private int bestScore (Board tic_tac_toe){
        return scoreSimulatedMoves(tic_tac_toe, true)
                .max()
                .orElse(0);
    }

    private int worstScore (Board tic_tac_toe){
        return scoreSimulatedMoves(tic_tac_toe, false)
                .min()
                .orElse(0);
    }

    private IntStream scoreSimulatedMoves(Board tic_tac_toe, boolean opponent) {
        return BoardMovesAnalytics.simulateLegalMoves(tic_tac_toe)
                .mapToInt(board -> minimax(board, opponent));
    }
}
