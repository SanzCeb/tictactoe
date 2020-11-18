package tictactoe.client;

import tictactoe.player.*;
import tictactoe.board.Board;
import tictactoe.player.ai.TicTacToeEasyAI;
import tictactoe.player.user.UserCommand;
import tictactoe.player.user.UserPlayer;

import java.util.Arrays;
import java.util.Optional;
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
                        var optionalPlayer1 = tryGetPlayer(commandTokens.next());

                        if (optionalPlayer1.isEmpty() || !commandTokens.hasNext()) {
                            badParameters = true;
                            break;
                        }

                        var optionalPlayer2 = tryGetPlayer(commandTokens.next());

                        if (optionalPlayer2.isEmpty()) {
                            badParameters = true;
                            break;
                        }

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
            if (badParameters){
                System.out.println("Bad parameters!");
                System.out.print("Input command: ");
                commandTokens = Arrays.stream(SCANNER.nextLine().split(" ")).iterator();
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

            if (!TIC_TAC_TOE.areCoordinatesInBound(coordinates.x, coordinates.y)) {
               errMessage = String.format("Coordinates should be from 1 to %d!", BOARD_DIMENSION);
            } else if (TIC_TAC_TOE.isCellOccupied(coordinates.x, coordinates.y)) {
                errMessage = "This cell is occupied! Choose another one!";
            } else {
                TIC_TAC_TOE.put(coordinates.x, coordinates.y);
                turnNotFinished = false;
            }

            if (!errMessage.isEmpty()) {
                System.out.println(errMessage);
            }
        }while (turnNotFinished);

        TIC_TAC_TOE_PRINTER.printBoard();
    }

    private static Optional<Player> tryGetPlayer(String playerName) {
        Player optionalPlayer;

        switch (playerName.toLowerCase()) {
            case "easy":
                optionalPlayer = new TicTacToeEasyAI(BOARD_DIMENSION);
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
