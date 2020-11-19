package tictactoe.client;

import tictactoe.player.*;
import tictactoe.board.Board;
import tictactoe.player.ai.TicTacToeEasyAI;
import tictactoe.player.ai.TicTacToeMediumAI;
import tictactoe.player.user.UserCommand;
import tictactoe.player.user.UserPlayer;

import java.util.Optional;
import java.util.Scanner;

public class TicTacToeCLI {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static int BOARD_DIMENSION = 3;
    private final static int COMMAND_POSITION = 0;
    private final static int PLAYER_1_POSITION = 1;
    private final static int PLAYER_2_POSITION = 2;
    private static Board tic_tac_toe = new Board(BOARD_DIMENSION);
    private static BoardPrinter tic_tac_toe_printer = new BoardPrinter(tic_tac_toe);



    public static void run() {

        var userCommand = runMenu();

        while (userCommand.isNotExitCommand()){
            runCommand(userCommand);
            tic_tac_toe = new Board(BOARD_DIMENSION);
            tic_tac_toe_printer = new BoardPrinter(tic_tac_toe);
            userCommand = runMenu();
        }

    }

    private static void runCommand(UserCommand userCommand) {
        var player1 = userCommand.player1;
        var player2 = userCommand.player2;

        tic_tac_toe_printer.printBoard();

        while(tic_tac_toe.isNotFinished()) {
            runTurn(player1);
            if (tic_tac_toe.isNotFinished()) {
                runTurn(player2);
            }
        }

        tic_tac_toe_printer.printGameState();
    }

    public static UserCommand runMenu() {
        System.out.print("Input command: ");
        var commandTokens = SCANNER.nextLine().split(" ");
        boolean badParameters;
        UserCommand userCommand = new UserCommand();

        do {
            badParameters = false;
            try {
                switch (commandTokens[COMMAND_POSITION]) {
                    case "start":
                        var optionalPlayer1 = tryGetPlayer(commandTokens[PLAYER_1_POSITION]);
                        var optionalPlayer2 = tryGetPlayer(commandTokens[PLAYER_2_POSITION]);

                        if (optionalPlayer1.isPresent() && optionalPlayer2.isPresent()) {
                            userCommand = new UserCommand(optionalPlayer1.get(), optionalPlayer2.get());
                        } else {
                            badParameters = true;
                        }

                        break;
                    case "exit":
                        break;
                    default:
                        badParameters = true;
                        break;
                }
            }catch (NullPointerException ignored) {
                badParameters = true;
            }

            if (badParameters) {
                System.out.println("Bad parameters!");
                System.out.print("Input command: ");
                commandTokens = SCANNER.nextLine().split(" ");
            }

        } while (badParameters);

        return  userCommand;
    }

    public static void runTurn(Player player) {
        var turnNotFinished = true;
        System.out.print(player.playerMessage());
        do {
            Coordinate coordinates;
            String errMessage = "";

            try {
                coordinates = player.getCoordinates();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (!tic_tac_toe.areCoordinatesInBound(coordinates.x, coordinates.y)) {
               errMessage = String.format("Coordinates should be from 1 to %d!", BOARD_DIMENSION);
            } else if (tic_tac_toe.isCellOccupied(coordinates.x, coordinates.y)) {
                errMessage = "This cell is occupied! Choose another one!";
            } else {
                tic_tac_toe.put(coordinates.x, coordinates.y);
                turnNotFinished = false;
            }

            if (!errMessage.isEmpty()) {
                System.out.println(errMessage);
            }
        }while (turnNotFinished);

        tic_tac_toe_printer.printBoard();
    }

    private static Optional<Player> tryGetPlayer(String playerName) {
        Player optionalPlayer;

        switch (playerName.toLowerCase()) {
            case "easy":
                optionalPlayer = new TicTacToeEasyAI(tic_tac_toe);
                break;
            case "medium":
                optionalPlayer = new TicTacToeMediumAI(tic_tac_toe);
                break;
            case "user":
                optionalPlayer = new UserPlayer(BOARD_DIMENSION);
                break;
            default:
                optionalPlayer = null;
                break;
        }

        return Optional.ofNullable(optionalPlayer);
    }

}
