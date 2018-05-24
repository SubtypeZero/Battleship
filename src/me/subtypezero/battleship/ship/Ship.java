package me.subtypezero.battleship.ship;

import me.subtypezero.battleship.board.Point;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<Point> coords;
    private Type type;

    public Ship(Type type, Point pos, boolean vert) {
        coords = new ArrayList<>();
        this.type = type;

        int size = type.getSize();

        if (vert) {
            // Vertical Orientation
            for (int i = 0; i < size; i++) {
                coords.add(new Point(pos.x, pos.y + i));
            }
        } else {
            // Horizontal Orientation
            for (int i = 0; i < size; i++) {
                coords.add(new Point(pos.x + i, pos.y));
            }
        }
    }

    public List<Point> getCoords() {
        return coords;
    }

    public Type getType() {
        return type;
    }
}
