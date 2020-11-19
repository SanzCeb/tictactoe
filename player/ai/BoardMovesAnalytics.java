package tictactoe.player.ai;

import tictactoe.board.Board;
import tictactoe.board.GameState;
import tictactoe.player.Coordinate;

import java.util.HashSet;
import java.util.Random;
import java.util.stream.Stream;

public class BoardMovesAnalytics {

    public static Coordinate randomMove(Board b) {
        Random randomizer = new Random();
        var randomMove = randomizer.nextInt((int) (legalMoves(b).count()));
        var legalMoves = legalMoves(b).unordered().iterator();

        while(randomMove > 1) {
            legalMoves.next();
            randomMove--;
        }

        return legalMoves.next();
    }

    public static Stream<Coordinate> movesToWinInOne(Board b) {
        return legalMoves(b).filter(coordinate -> moveWins(b, coordinate));
    }

    public static Stream<Coordinate> movesToNotLoseInOne(Board b) {
        return legalMoves(b).filter(move -> !moveLoses(b, move));
    }

    private static boolean moveWins(Board b, Coordinate move) {
        var result = simulateMove(b, move).getGameState();
        return result == GameState.X_WINS || result == GameState.O_WINS;
    }

    private static boolean moveLoses (Board b, Coordinate move) {
        var futureBoard = simulateMove(b, move);
        return movesToWinInOne(futureBoard)
                .findAny().isPresent();
    }

    private static Board simulateMove(Board b, Coordinate move) {
        var newBoard = new Board(b);
        newBoard.put(move.x, move.y);
        return newBoard;
    }

    private static Stream<Coordinate> legalMoves(Board b) {
        var playableMoves = new HashSet<Coordinate>();

        for (int i = 0; i < b.getDimension(); i++) {
            for (int j = 0; j < b.getDimension(); j++) {
                if (!b.isCellOccupied(i, j)) {
                    playableMoves.add(new Coordinate(i, j));
                }
            }
        }

        return playableMoves.stream();
    }
}
