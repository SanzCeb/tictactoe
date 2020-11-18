package tictactoe.player.user;

import tictactoe.player.Player;

public class UserCommand {
    public final Player player1;
    public final Player player2;
    private final boolean start;

    public UserCommand(Player player1, Player player2) {
        this.start = true;
        this.player1 = player1;
        this.player2 = player2;
    }

    public UserCommand() {
        this.start = false;
        this.player1 = null;
        this.player2 = null;
    }

    public boolean isNotExitCommand() {
        return start;
    }

}
