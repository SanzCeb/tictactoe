package tictactoe.board;

import tictactoe.player.Coordinate;

import java.util.ArrayList;
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

    public static Board simulateMove(Board b, Coordinate move) {
        var newBoard = new Board(b);
        newBoard.put(move);
        return newBoard;
    }

    public static Stream<Coordinate> legalMoves(Board b) {
        var playableMoves = new ArrayList<Coordinate>();

        for (int i = 0; i < b.getDimension(); i++) {
            for (int j = 0; j < b.getDimension(); j++) {
                if (!b.isCellOccupied(new Coordinate(i, j))) {
                    playableMoves.add(new Coordinate(i, j));
                }
            }
        }

        return playableMoves.stream();
    }

    public static boolean isThereWinner(Board b) {
        return b.getGameState() == GameState.X_WINS || b.getGameState() == GameState.O_WINS;
    }

    public static Stream<Board> simulateLegalMoves(Board b) {
        return legalMoves(b).map(move -> BoardMovesAnalytics.simulateMove(b, move));
    }
}
