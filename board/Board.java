package tictactoe.board;

import tictactoe.player.Coordinate;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {
    private final char[][] board;
    private GameState gameState;

    public Board(int dimension) {
        this.gameState = GameState.X_TURN;
        this.board = new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = '_';
            }
        }
    }

    public Board (Board b) {
        this(b.board, b.gameState);
    }

    private Board (char[][] board, GameState gameState) {
        this.gameState = gameState;
        this.board = new char[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, board.length);
        }
    }

    public Iterator<Stream<String>> getRows() {
        return Arrays.stream(board)
                .map(CharBuffer::wrap)
                .map(CharBuffer::chars)
                .map(intStream -> intStream.mapToObj(Character::toString))
                .iterator();
    }

    public void put(Coordinate coordinates) {
        var newGameState = simulatePut(coordinates);
        if (newGameState != GameState.IMPOSSIBLE) {
            this.board[coordinates.x][coordinates.y] = getPlayer();
            gameState = newGameState;
        }
    }

    public boolean areCoordinatesInBound(Coordinate coordinates) {
        return IntStream.range(0, board.length).anyMatch(number -> number == coordinates.x) &&
                IntStream.range(0, board.length).anyMatch(number -> number == coordinates.y);
    }

    public int getDimension() {
        return board.length;
    }

    private GameState analyzeBoard() {
        GameState output = gameState;
        if (isNotFinished() || output == GameState.IMPOSSIBLE) {
            if (gameImpossible()) {
                output = GameState.IMPOSSIBLE;
            } else if (playerWins('X')) {
                output = GameState.X_WINS;
            } else if (playerWins('O')) {
                output = GameState.O_WINS;
            } else if (emptyCells()) {
                output = (gameState == GameState.X_TURN) ? GameState.O_TURN : GameState.X_TURN;
            } else {
                output = GameState.DRAW;
            }
        }
        return output;
    }

    private boolean emptyCells() {
        for (char[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (chars[j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean playerWins(char player) {
        var playerWinsLeftDiagonal = true;
        var playerWinsRightDiagonal = true;

        for (int i = 0; i < board.length; i++) {
            var playerWinsRow = true;
            var playerWinsColumn = true;
            for (int j = 0; j < board.length; j++) {
                playerWinsColumn = playerWinsColumn && board[j][i] == player;
                playerWinsRow = playerWinsRow && board[i][j] == player;
            }
            if (playerWinsColumn || playerWinsRow) {
                return true;
            }
            playerWinsLeftDiagonal = playerWinsLeftDiagonal && board[i][i] == player;
            playerWinsRightDiagonal = playerWinsRightDiagonal && board[i][board.length - i - 1] == player;
        }

        return playerWinsLeftDiagonal || playerWinsRightDiagonal;
    }

    private boolean gameImpossible() {
        var numOs = 0;
        var numXs = 0;
        for (char[] chars : board) {
            for (int j = 0; j < board.length; j++) {
                if (chars[j] == 'X') {
                    numXs++;
                } else if (chars[j] == 'O') {
                    numOs++;
                }
            }
        }
        return Math.abs(numOs - numXs) > 1 || playerWins('X') && playerWins('O');
    }

    private GameState simulatePut(Coordinate coordinate) {
        var newBoard = new Board (board, gameState);
        try {
            if (!isCellOccupied(coordinate)) {
                newBoard.board[coordinate.x][coordinate.y] = getPlayer();
            } else{
                return GameState.IMPOSSIBLE;
            }
        } catch (NullPointerException exception) {
            return GameState.IMPOSSIBLE;
        }
        return newBoard.analyzeBoard();
    }

    private char getPlayer() {
        return gameState == GameState.X_TURN ? 'X' : 'O';
    }

    public boolean isCellOccupied(Coordinate coordinate) {
        return this.board[coordinate.x][coordinate.y] != '_';
    }

    public boolean isNotFinished() {
        return gameState == GameState.X_TURN || gameState == GameState.O_TURN;
    }

    public GameState getGameState() {
        return gameState;
    }
}
