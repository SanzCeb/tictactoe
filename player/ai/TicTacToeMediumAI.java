package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.player.Coordinate;

import java.util.Optional;

public class TicTacToeMediumAI extends TicTacToeAI {
    public TicTacToeMediumAI(Board ticTacToeState) {
        super(ticTacToeState);
    }

    @Override
    public Coordinate getCoordinates() {
        Coordinate coordinate;
        var maybeMove = moveToWinInOne();
        if (maybeMove.isEmpty()) {
            maybeMove = moveToNotLoseInOne();
            if (maybeMove.isEmpty()) {
                coordinate = BoardMovesAnalytics.randomMove(TIC_TAC_TOE);
            } else {
                coordinate = maybeMove.get();
            }
        } else {
            coordinate = maybeMove.get();
        }
        return  coordinate;
    }

    private Optional<Coordinate> moveToNotLoseInOne() {
        return BoardMovesAnalytics.movesToNotLoseInOne(TIC_TAC_TOE).findAny();
    }

    private Optional<Coordinate> moveToWinInOne() {
        return BoardMovesAnalytics.movesToWinInOne(TIC_TAC_TOE).findAny();
    }

    @Override
    public String playerMessage() {
        return "Making move level \"medium\"\n";
    }
}
