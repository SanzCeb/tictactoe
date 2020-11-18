package tictactoe.client;

import tictactoe.board.Board;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardPrinter {
    Board b;

    public BoardPrinter(Board b) {
        this.b = b;
    }

    public void printBoard() {
        var rows = b.getRows();
        var hyphensRow = buildHyphensRow();
        System.out.println(hyphensRow);
        while (rows.hasNext()) {
            var cells = rows.next();
            var rowStr = cells.collect(Collectors.joining(" "));
            System.out.printf("| %s |%n", rowStr);
        }
        System.out.println(hyphensRow);
    }

    public void printGameState() {
        System.out.println(b.getGameState());
    }

    private String buildHyphensRow() {
        return IntStream.generate(() -> '-')
                .limit(3 + (b.getDimension() * 2))
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }
}
