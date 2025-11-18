package entities;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Point;

public class Player extends Entity {

    private String playerImagePath = "images/player.png";

    public Player() {
        /**
        Initializes the player.
        It loads the player image and initializes the player's position and score.
        @return void
        */
        super(0, 0); // start at position (0, 0)
        
        // load the assets
        loadImage(playerImagePath);
    }

    public void keyPressed(KeyEvent keyPressEvent) {
        /**
        Reacts to a key press event.
        It moves the player one tile in the direction of the pressed key.
        @param keyPressEvent - the key press event to react to
        @return void
        */
        int key = keyPressEvent.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            position.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            position.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN) {
            position.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT) {
            position.translate(-1, 0);
        }
    }

    public Point getCurrentPlayerPosition() {
        /**
        Gets the player's current position.
        It returns the player's current position as a Point object.
        @return the player's current position as a Point object
        */
        return getPosition();
    }

    public void movePlayer() {
        /**
        Moves the player.
        It prevents the player from moving off the edge of the board and updates the player's position.
        @return void
        */
        tick(); // use parent's boundary checking
    }

    public void drawPlayer(Graphics graphicsController, ImageObserver imageObserver) {
        /**
        Draws the player image.
        It draws the player image at the current player position on the board.
        @param graphicsController - the graphics controller to draw the player image on
        @param imageObserver - the image observer to observe the player image
        @return void
        */
        draw(graphicsController, imageObserver); // use parent's draw method
    }
}

