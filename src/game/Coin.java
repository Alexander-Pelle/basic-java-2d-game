package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import utils.Logger;
import utils.Contstants;

public class Coin {
    
    // image that represents the coin
    private BufferedImage coinImage;
    
    // current position of the coin on the board grid
    private Point position;
    
    private String coinImagePath = "images/coin.png";

    public Coin(int x, int y) {
        /**
        Initializes the coin.
        It loads the coin image and sets the coin's position.
        @param x - the x coordinate of the coin
        @param y - the y coordinate of the coin
        @return void
        */
        position = new Point(x, y);
        loadCoinImage();
    }
    
    private void loadCoinImage() {
        /**
        Loads the coin image from the images folder.
        @return void
        */
        try {
            coinImage = ImageIO.read(new File(coinImagePath));
        } catch (IOException exception) {
            Logger.imageLoadError(coinImagePath, exception);
        }
    }

    public void drawCoin(Graphics graphicsController, ImageObserver imageObserver) {
        /**
        Draws the coin image at its current position on the board.
        @param graphicsController - the graphics controller to draw the coin image on
        @param imageObserver - the image observer to observe the coin image
        @return void
        */
        graphicsController.drawImage(
            coinImage, 
            position.x * Contstants.TILE_SIZE, 
            position.y * Contstants.TILE_SIZE,
            Contstants.TILE_SIZE,
            Contstants.TILE_SIZE,
            imageObserver
        );
    }

    public Point getCurrentCoinPostion() {
        /**
        Gets the current coin position.
        @return the current coin position as a Point object
        */
        return position;
    }
    
    public Point getPosition() {
        /**
        Gets the current coin position (alternative method name).
        @return the current coin position as a Point object
        */
        return position;
    }
}

