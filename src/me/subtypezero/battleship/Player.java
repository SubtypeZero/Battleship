package me.subtypezero.battleship;

import me.subtypezero.battleship.board.Board;
import me.subtypezero.battleship.board.Point;
import me.subtypezero.battleship.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Board primary;
    private Board tracking;
    private List<Ship> ships;

    private String name;

    public Player(String name) {
        this.name = name;
        primary = new Board();
        tracking = new Board();
        ships = new ArrayList<>();
    }

    public void updateBoard() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                // 0 is empty, 1 is a friendly ship, 2 is an enemy hit, 3 is an enemy miss
                if (primary.getPos(x, y) == 0) {
                    // This is a valid place to put a ship
                    for (Ship ship : ships) {
                        for (Point point : ship.getCoords()) {
                            if (point.x == x && point.y == y) {
                                primary.setPos(x, y, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public Board getPrimary() {
        return primary;
    }

    public Board getTracking() {
        return tracking;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public String getName() {
        return name;
    }
}
