package tictactoe.player;


public abstract class Player {
    protected int dimension;

    protected Player(int dimension) {
        this.dimension = dimension;
    }

    public abstract Coordinate getCoordinates() throws Exception;

    public abstract String playerMessage();
}
