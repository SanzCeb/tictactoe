package tictactoe.player;


public abstract class Player {
    protected int dimension;
    private boolean human;
    protected Player(int dimension, boolean human) {
        this.dimension = dimension;
        this.human = human;
    }

    public abstract Coordinate getCoordinates() throws Exception;

    public abstract String playerMessage();

    public boolean isHuman() {
        return human;
    }
}
