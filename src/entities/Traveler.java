package entities;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.util.*;

import game.Coin;
import utils.Contstants;

public class Traveler extends Entity {

    private int moveCounter;
    private final int MOVE_DELAY = 15; // traveler moves every 15 ticks
    private Point targetCoin; // the coin the traveler is currently moving toward

    private String travelerImage = "images/traveler.png";

    public Traveler(int x, int y) {
        /**
        Initializes the traveler.
        It loads the traveler image and initializes the traveler's position and score.
        @param x - the x coordinate of the traveler
        @param y - the y coordinate of the traveler
        @return void
        */
        super(x, y);
        
        // load the assets - you'll need a traveler.png image
        loadImage(travelerImage);

        // initialize state
        moveCounter = 0;
        targetCoin = null;
    }

    public void updateTraveler(ArrayList<Coin> coins) {
        /**
        Updates the traveler state.
        It finds the nearest coin and moves toward it using pathfinding.
        @param coins - the list of coins on the board
        @return void
        */
        moveCounter++;
        
        // only move every MOVE_DELAY ticks
        if (moveCounter >= MOVE_DELAY) {
            moveCounter = 0;
            
            // find nearest coin
            Coin nearestCoin = findNearestCoin(coins);
            
            if (nearestCoin != null) {
                targetCoin = nearestCoin.getCurrentCoinPostion();
                moveTowardTarget();
            }
        }
        
        // prevent from going off board
        tick();
    }

    private Coin findNearestCoin(ArrayList<Coin> coins) {
        /**
        Finds the nearest coin to the traveler.
        @param coins - the list of coins on the board
        @return the nearest coin, or null if no coins exist
        */
        if (coins.isEmpty()) {
            return null;
        }

        Coin nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Coin coin : coins) {
            int distance = manhattanDistance(position, coin.getCurrentCoinPostion());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = coin;
            }
        }

        return nearest;
    }

    private int manhattanDistance(Point a, Point b) {
        /**
        Calculates the Manhattan distance between two points.
        @param a - the first point
        @param b - the second point
        @return the Manhattan distance
        */
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private void moveTowardTarget() {
        /**
        Moves the traveler one step toward the target coin using pathfinding.
        Uses BFS to find the shortest path.
        @return void
        */
        if (targetCoin == null) {
            return;
        }

        // if already at target, don't move
        if (position.equals(targetCoin)) {
            return;
        }

        // use BFS to find shortest path
        Point nextStep = findNextStep();
        
        if (nextStep != null) {
            position.setLocation(nextStep);
        }
    }

    private Point findNextStep() {
        /**
        Finds the next step toward the target using BFS pathfinding.
        @return the next position to move to, or null if no path exists
        */
        if (targetCoin == null) {
            return null;
        }

        // BFS to find shortest path
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        queue.add(position);
        visited.add(position);
        cameFrom.put(position, null); 

        Point[] directions = {
            new Point(0, -1),  // up
            new Point(1, 0),   // right
            new Point(0, 1),   // down
            new Point(-1, 0)   // left
        };

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // found target
            if (current.equals(targetCoin)) {
                // reconstruct path and return first step
                return reconstructFirstStep(cameFrom, current);
            }

            // explore neighbors
            for (Point dir : directions) {
                Point neighbor = new Point(current.x + dir.x, current.y + dir.y);

                // check if neighbor is valid and not visited
                if (isValidPosition(neighbor) && !visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        // no path found - move closer using simple heuristic
        return moveCloserSimple();
    }

    private Point reconstructFirstStep(Map<Point, Point> cameFrom, Point target) {
        /**
        Reconstructs the path and returns the first step from current position.
        @param cameFrom - the map of where each position came from
        @param target - the target position
        @return the first step in the path
        */
        Point current = target;
        Point previous = cameFrom.get(current);

        // backtrack until we find the step right after our current position
        while (previous != null && !previous.equals(position)) {
            current = previous;
            previous = cameFrom.get(current);
        }

        return current;
    }

    private Point moveCloserSimple() {
        /**
        Simple fallback movement - move one step closer to target.
        @return the next position
        */
        if (targetCoin == null) {
            return position;
        }

        int dx = Integer.compare(targetCoin.x, position.x);
        int dy = Integer.compare(targetCoin.y, position.y);

        // try horizontal movement first
        if (dx != 0) {
            Point next = new Point(position.x + dx, position.y);
            if (isValidPosition(next)) {
                return next;
            }
        }

        // try vertical movement
        if (dy != 0) {
            Point next = new Point(position.x, position.y + dy);
            if (isValidPosition(next)) {
                return next;
            }
        }

        return position;
    }

    private boolean isValidPosition(Point p) {
        /**
        Checks if a position is valid (within board bounds).
        @param p - the position to check
        @return true if valid, false otherwise
        */
        return p.x >= 0 && p.x < Contstants.COLUMNS && 
               p.y >= 0 && p.y < Contstants.ROWS;
    }



    public void drawTraveler(Graphics graphicsController, ImageObserver imageObserver) {
        /**
        Draws the traveler image.
        @param graphicsController - the graphics controller
        @param imageObserver - the image observer
        @return void
        */
        draw(graphicsController, imageObserver);
    }

    public boolean isAtPosition(Point coinPosition) {
        /**
        Checks if the traveler is at a specific position.
        @param coinPosition - the position to check
        @return true if traveler is at that position
        */
        return position.equals(coinPosition);
    }
}

