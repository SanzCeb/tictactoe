package tictactoe.client;

import tictactoe.player.*;
import tictactoe.board.Board;
import tictactoe.player.ai.TicTacToeEasyAI;
import tictactoe.player.user.UserCommand;
import tictactoe.player.user.UserPlayer;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToeCLI {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Board TIC_TAC_TOE = new Board(3);
    private final static int BOARD_DIMENSION = 3;
    private final static BoardPrinter TIC_TAC_TOE_PRINTER = new BoardPrinter(TIC_TAC_TOE);

    public static void run() {
        var userCommand = runMenu();

        while (userCommand.isNotExitCommand()){
            runCommand(userCommand);
            userCommand = runMenu();
        }

    }

    private static void runCommand(UserCommand userCommand) {
        var player1 = userCommand.player1;
        var player2 = userCommand.player2;

        TIC_TAC_TOE_PRINTER.printBoard();

        while(TIC_TAC_TOE.isNotFinished()) {
            runTurn(player1);
            if (TIC_TAC_TOE.isNotFinished()) {
                runTurn(player2);
            }
        }

        TIC_TAC_TOE_PRINTER.printGameState();
    }

    public static UserCommand runMenu() {
        //Write a menu loop which can interpret two commands: start and exit
        System.out.print("Input command: ");
        var commandTokens = Arrays.stream(SCANNER.nextLine().split(" ")).iterator();
        boolean badParameters;
        UserCommand userCommand = new UserCommand();
        do {
            badParameters = false;
            switch (commandTokens.next().toLowerCase()) {
                case "start":
                    if (commandTokens.hasNext()) {
                        Player player1;

                        player1 = (commandTokens.next().equalsIgnoreCase("easy"))
                                ? new TicTacToeEasyAI(BOARD_DIMENSION)
                                : new UserPlayer(BOARD_DIMENSION);

                        if (commandTokens.hasNext()) {
                            var player2 = (commandTokens.next().equalsIgnoreCase("easy"))
                                    ? new TicTacToeEasyAI(BOARD_DIMENSION) :
                                    new UserPlayer(BOARD_DIMENSION);
                            userCommand = new UserCommand(player1, player2);
                        }
                    } else {
                        System.out.println("Bad parameters");
                        badParameters = true;
                    }
                    break;
                case "exit":
                    break;
                default:
                    badParameters = true;
                    System.out.println("Bad parameters!");
                    break;
            }
            if (badParameters){
                System.out.print("Input command: ");
                commandTokens = Arrays.stream(SCANNER.nextLine().split(" ")).iterator();
            }
        } while (badParameters);

        return  userCommand;
    }

    public static void runTurn(Player player) {
        var turnNotFinished = true;
        System.out.printf("%s", player.playerMessage());
        if (! (player instanceof UserPlayer)) {
            System.out.println();
        } else {
            System.out.print(" ");
        }
        do {
            Coordinate coordinates;
            try {
                coordinates = player.getCoordinates();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            if (!TIC_TAC_TOE.areCoordinatesInBound(coordinates.x, coordinates.y)) {
                if (player instanceof UserPlayer) {
                    System.out.printf("Coordinates should be from 1 to %d!%n", BOARD_DIMENSION);
                }
            } else if (TIC_TAC_TOE.isCellOccupied(coordinates.x, coordinates.y)) {
                if (player instanceof  UserPlayer) {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } else {
                TIC_TAC_TOE.put(coordinates.x, coordinates.y);
                turnNotFinished = false;
            }
        }while (turnNotFinished);

        TIC_TAC_TOE_PRINTER.printBoard();
    }
}
