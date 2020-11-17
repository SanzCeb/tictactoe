package tictactoe;

public enum GameState {
    X_TURN("X turn"),
    O_TURN("O turn"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    IMPOSSIBLE("Impossible");

    private String message;

    GameState(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
