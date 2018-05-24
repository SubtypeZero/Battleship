package me.subtypezero.battleship;

public class Main {

    public static void main(String[] args) {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        Util.setup(p1);
        Util.setup(p2);

        boolean done = false;

        while(!done) {
            if (Util.guess(p1, p2) == 1 || Util.guess(p2, p1) == 1) {
                done = true;
            }
        }
    }
}
