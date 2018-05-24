package me.subtypezero.battleship.board;

public class Board {
    private int[][] board;

    public Board() {
        board = new int[10][10];
    }

    public void show() {
        System.out.println();
        for (int y = 0; y < 10; y++) {
            String row = "";

            for (int x = 0; x < 10; x++) {
                row += board[x][y] + " ";
            }
            System.out.println(row.trim());
        }
        System.out.println();
    }

    public int getPos(int x, int y) {
        return board[x][y];
    }

    public int getPos(Point pos) {
        return board[pos.x][pos.y];
    }

    public void setPos(int x, int y, int value) {
        board[x][y] = value;
    }

    public void setPos(Point pos, int value) {
        board[pos.x][pos.y] = value;
    }
}
