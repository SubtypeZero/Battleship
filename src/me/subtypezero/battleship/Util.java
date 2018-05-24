package me.subtypezero.battleship;

import me.subtypezero.battleship.board.Point;
import me.subtypezero.battleship.ship.Ship;
import me.subtypezero.battleship.ship.Type;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Util {

    private static void placePiece(Player player, Type type) {
        boolean placed = false;

        while (!placed) {
            System.out.println(player.getName() + " Place Ship (Size " + type.getSize() + ")");
            Point pos = new Point(getNumber("Place Column: ", 9), getNumber("Place Row: ", 9));

            // Attempt to add a ship to the board
            Ship piece = new Ship(type, pos, getOrientation());

            boolean available = true;

            // Check the last coordinate (minimal checks) to make sure the ship will fit on the board
            List<Point> coords = piece.getCoords();
            Point last = coords.get(coords.size() - 1);

            if (last.x > 9 || last.y > 9) {
                // This point is not on the board
                available = false;
            }

            // Check to make sure the ship won't intersect with any other ships
            for (Ship ship : player.getShips()) {
                for (Point other : ship.getCoords()) {
                    for (Point p : coords) {
                        if (p.equals(other)) {
                            // This point is already being used by another ship
                            available = false;
                            break;
                        }
                    }
                }
            }

            if (available) {
                player.getShips().add(piece);
                player.updateBoard();
                player.getPrimary().show();
                placed = true;
            } else {
                System.out.println("A ship can't be placed there, please try again.");
            }
        }
    }

    public static int guess(Player current, Player other) {
        System.out.println(current.getName() + " Guess.");
        boolean done = false;

        while(!done) {
            current.getTracking().show();
            Point guess = new Point(getNumber("Guess Column: ", 9), getNumber("Guess Row: ", 9));

            // Let the player guess this location if they have not tried it already
            if (current.getTracking().getPos(guess) == 0) {
                // Check if there is a ship at this location
                if (other.getPrimary().getPos(guess) == 1) {
                    // The player hit an enemy ship
                    Iterator<Ship> ships = other.getShips().iterator();

                    while(ships.hasNext()) {
                        Ship ship = ships.next();
                        Iterator<Point> points = ship.getCoords().iterator();

                        while(points.hasNext()) {
                            Point pos = points.next();

                            if (pos.equals(guess)) {
                                // 0 is empty, 1 is a ship, 2 is a hit, 3 is a miss
                                current.getTracking().setPos(guess, 2);
                                other.getPrimary().setPos(guess, 2);
                                points.remove();
                                System.out.println("Hit!");

                                // Check if the ship has been destroyed
                                if (ship.getCoords().isEmpty()) {
                                    ships.remove();
                                    System.out.println("You destroyed an enemy ship!\nEnemy ships remaining: " + other.getShips().size());
                                }

                                // Check if the fleet has been destroyed
                                if (other.getShips().isEmpty()) {
                                    System.out.println(current.getName() + " won the game!");
                                    return 1;
                                }
                            }
                        }
                    }
                } else {
                    // The player missed
                    current.getTracking().setPos(guess, 3);
                    other.getPrimary().setPos(guess, 3);
                    System.out.println("Miss!");
                    done = true;
                }
            } else {
                System.out.println("You already guessed those coordinates!");
            }
        }
        return 0;
    }

    public static void setup(Player p) {
        if (p.getShips().isEmpty()) {
            placePiece(p, Type.CARRIER);
            placePiece(p, Type.BATTLESHIP);
            placePiece(p, Type.CRUISER);
            placePiece(p, Type.SUBMARINE);
            placePiece(p, Type.DESTROYER);
        }
    }

    private static int getNumber(String prompt, int max) {
        Scanner in = new Scanner(System.in);
        int value;

        try {
            System.out.print(prompt);
            value = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input!");
            return getNumber(prompt, max);
        }

        if (value >= 0 && value <= max) {
            return value;
        } else {
            System.out.println("Out of Bounds!");
            return getNumber(prompt, max);
        }
    }

    private static boolean getOrientation() {
        return getNumber("Select Orientation (0 for horizontal, 1 for vertical): ", 1) == 1;
    }
}
