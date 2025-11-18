package entities;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Enemy extends Entity {

    private Random random;
    private int moveCounter; // counter to control movement speed
    private final int MOVE_DELAY = 20; // enemy moves every 20 ticks (slower than player)

    public Enemy(int x, int y) {
        /**
        Initializes the enemy.
        It loads the enemy image and initializes the enemy's position.
        @param x - the x coordinate of the enemy
        @param y - the y coordinate of the enemy
        @return void
        */
        super(x, y);
        
        // load the assets
        loadImage("images/enemy.png");

        // initialize random movement
        random = new Random();
        moveCounter = 0;
    }

    public void updateEnemy() {
        /**
        Updates the enemy state.
        It moves the enemy randomly and prevents it from moving off the board.
        @return void
        */
        moveCounter++;
        
        // only move every MOVE_DELAY ticks
        if (moveCounter >= MOVE_DELAY) {
            moveCounter = 0;
            moveRandomly();
        }
        
        // prevent from going off board
        tick();
    }

    private void moveRandomly() {
        /**
        Moves the enemy randomly.
        It randomly chooses a direction and moves the enemy one tile in that direction.
        @return void
        */
        int direction = random.nextInt(4);
        
        switch (direction) {
            case 0: // up
                position.translate(0, -1);
                break;
            case 1: // right
                position.translate(1, 0);
                break;
            case 2: // down
                position.translate(0, 1);
                break;
            case 3: // left
                position.translate(-1, 0);
                break;
        }
    }

    public void drawEnemy(Graphics graphicsController, ImageObserver imageObserver) {
        /**
        Draws the enemy image.
        It draws the enemy image at the current enemy position on the board.
        @param graphicsController - the graphics controller to draw the enemy image on
        @param imageObserver - the image observer to observe the enemy image
        @return void
        */
        draw(graphicsController, imageObserver);
    }

    public boolean collidesWithPlayer(Player player) {
        /**
        Checks if the enemy collides with the player.
        It returns true if the enemy and player are on the same tile.
        @param player - the player to check collision with
        @return true if collision detected, false otherwise
        */
        return position.equals(player.getPosition());
    }
}

