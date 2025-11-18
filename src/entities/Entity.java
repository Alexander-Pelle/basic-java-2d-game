package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import utils.Logger;
import utils.Contstants;

public abstract class Entity {
    
    // image that represents the entity's position on the board
    protected BufferedImage image;
    
    // current position of the entity on the board grid
    protected Point position;

    protected int score;

    public Entity(int x, int y) {
        /**
        Initializes the entity.
        It sets the entity's position on the board.
        @param x - the x coordinate of the entity
        @param y - the y coordinate of the entity
        @return void
        */
        position = new Point(x, y);
        score = 0;
    }
    
    public void addScore(int amount) {
        /**
        Adds to the entity's score.
        @param amount - the amount to add
        @return void
        */
        score += amount;
    }

    public int getScore() {
        /**
        Gets the entity's score.
        @return the entity's score
        */
        return score;
    }

    protected void loadImage(String filename) {
        /**
        Loads the entity image.
        It loads the entity image from the images folder and throws an error if it fails to load.
        @param filename - the filename of the image to load
        @return void
        */
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException exception) {
            Logger.imageLoadError(filename, exception);
        }
    }

    public void draw(Graphics graphicsController, ImageObserver imageObserver) {
        /**
        Draws the entity image.
        It draws the entity image at the current entity position on the board.
        @param graphicsController - the graphics controller to draw the entity image on
        @param imageObserver - the image observer to observe the entity image
        @return void
        */
        graphicsController.drawImage(
            image, 
            position.x * Contstants.TILE_SIZE, 
            position.y * Contstants.TILE_SIZE,
            Contstants.TILE_SIZE,
            Contstants.TILE_SIZE,
            imageObserver
        );
    }

    public void tick() {
        /**
        Updates the entity state each game tick.
        It prevents the entity from moving off the edge of the board.
        @return void
        */
        // prevent the entity from moving off the edge of the board sideways
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x >= Contstants.COLUMNS) {
            position.x = Contstants.COLUMNS - 1;
        }
        // prevent the entity from moving off the edge of the board vertically
        if (position.y < 0) {
            position.y = 0;
        } else if (position.y >= Contstants.ROWS) {
            position.y = Contstants.ROWS - 1;
        }
    }

    public Point getPosition() {
        /**
        Gets the entity's current position.
        It returns the entity's current position as a Point object.
        @return the entity's current position as a Point object
        */
        return position;
    }

    public void setPosition(int x, int y) {
        /**
        Sets the entity's position.
        It sets the entity's position to the given coordinates.
        @param x - the x coordinate
        @param y - the y coordinate
        @return void
        */
        position.setLocation(x, y);
    }
}

