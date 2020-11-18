package tictactoe.player.user;

import tictactoe.player.Coordinate;
import tictactoe.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserPlayer extends Player {
    private static final Scanner SCANNER = new Scanner(System.in);

    public UserPlayer(int dimension) {
        super(dimension, true);
    }

    @Override
    public Coordinate getCoordinates() throws Exception {
            try{
                var coordinateY = SCANNER.nextInt() - 1;
                var coordinateX = dimension - SCANNER.nextInt();
                return  new Coordinate(coordinateX, coordinateY);
            } catch (InputMismatchException ignored) {
                throw new Exception("Bad parameters!");
            }
    }

    @Override
    public String playerMessage() {
        return "Enter the coordinates: ";
    }

}
