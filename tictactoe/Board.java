package tictactoe;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Board {
    private final char[][] board;
    private GameState gameState;

    Board(int dimension) {
        this.gameState = GameState.X_TURN;
        this.board = new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = '_';
            }
        }
    }

    private Board (char[][] board, GameState gameState) {
        this.gameState = gameState;
        this.board = new char[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, board.length);
        }
    }

    Iterator<Stream<String>> getRows() {
        return Arrays.stream(board)
                .map(CharBuffer::wrap)
                .map(CharBuffer::chars)
                .map(intStream -> intStream.mapToObj(Character::toString))
                .iterator();
    }

    void put(int coordinateX, int coordinateY) {
        var newGameState = simulatePut(coordinateX, coordinateY);
        if (newGameState != GameState.IMPOSSIBLE) {
            this.board[coordinateX][coordinateY] = getPlayer();
            gameState = newGameState;
        }
    }

    boolean areCoordinatesInBound(int coordinateX, int coordinateY) {
        return IntStream.range(0, board.length).anyMatch(number -> number == coordinateX) &&
                IntStream.range(0, board.length).anyMatch(number -> number == coordinateY);
    }

    int getDimension() {
        return board.length;
    }

    private GameState analyzeBoard() {
        GameState output = GameState.IMPOSSIBLE;
        if (isNotFinished() || gameState == GameState.IMPOSSIBLE) {
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

    private GameState simulatePut(int coordinateX, int coordinateY) {
        var newBoard = new Board (board, gameState);
        try {
            if (!isCellOccupied(coordinateX, coordinateY)) {
                newBoard.board[coordinateX][coordinateY] = getPlayer();
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

    public boolean isCellOccupied(int coordinateX, int coordinateY) {
        return this.board[coordinateX][coordinateY] != '_';
    }

    public boolean isNotFinished() {
        return gameState == GameState.X_TURN || gameState == GameState.O_TURN;
    }

    GameState getGameState() {
        return gameState;
    }
}
