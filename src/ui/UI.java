package ui;

import java.awt.*;

import entities.Player;
import entities.Traveler;
import utils.Contstants;

public class UI {
    public static void drawGameOverScreen(Graphics graphicsController, String winnerText) {
        /**
        Draws the complete game over screen with background, game over text, and winner text.
        This ensures proper layering - background first, then both texts on top.
        @param graphicsController - the graphics controller
        @param winnerText - the winner message to display
        @return void
        */
        Graphics2D g2d = (Graphics2D) graphicsController;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        // Calculate positions
        int screenCenterY = Contstants.TILE_SIZE * Contstants.ROWS / 2;
        int screenWidth = Contstants.TILE_SIZE * Contstants.COLUMNS;
        
        // STEP 1: Draw the background box FIRST
        g2d.setColor(new Color(0, 0, 0, 180)); // semi-transparent black
        int boxWidth = 500;
        int boxHeight = 200;
        int boxX = (screenWidth - boxWidth) / 2;
        int boxY = screenCenterY - 100; // center the box
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        
        // STEP 2: Draw "GAME OVER" text
        g2d.setColor(new Color(255, 0, 0)); // red
        g2d.setFont(new Font("Lato", Font.BOLD, 60));
        String gameOverText = "GAME OVER";
        FontMetrics gameOverMetrics = g2d.getFontMetrics();
        int gameOverX = (screenWidth - gameOverMetrics.stringWidth(gameOverText)) / 2;
        int gameOverY = screenCenterY - 30;
        g2d.drawString(gameOverText, gameOverX, gameOverY);
        
        // STEP 3: Draw winner text BELOW game over
        g2d.setColor(new Color(255, 215, 0)); // gold
        g2d.setFont(new Font("Lato", Font.BOLD, 35));
        FontMetrics winnerMetrics = g2d.getFontMetrics();
        int winnerX = (screenWidth - winnerMetrics.stringWidth(winnerText)) / 2;
        int winnerY = screenCenterY + 50;
        g2d.drawString(winnerText, winnerX, winnerY);
    }


    public static void drawScore(Graphics graphicsController, Player player) {
        /**
        Draws the score.
        It draws the player's score on the board.
        @param graphicsController - the graphics controller to draw the score on
        @param player - the player whose score to draw
        @return void
        */
        // set the text to be displayed
        String text = "$" + player.getScore();
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) graphicsController;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // set the text color and font
        g2d.setColor(Contstants.playerScoreTextColor);
        g2d.setFont(Contstants.scoreTextFont);
        
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        // here I've sized it to be the entire bottom row of board tiles
        Rectangle rect = new Rectangle(0, Contstants.TILE_SIZE * (Contstants.ROWS - 1), Contstants.TILE_SIZE * Contstants.COLUMNS, Contstants.TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        graphicsController.drawString(text, x, y);
    }

    public static void drawTravelerScore(Graphics graphicsController, Traveler traveler) {
        /**
        Draws the traveler's score.
        It displays the traveler's score on the right side of the screen.
        @param graphicsController - the graphics controller to draw the score on
        @param traveler - the traveler whose score to draw
        @return void
        */
        String text = "Traveler: $" + traveler.getScore();
        
        // cast to Graphics2D for better rendering
        Graphics2D g2d = (Graphics2D) graphicsController;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // set color and font
        g2d.setColor(Contstants.travelerScoreTextColor);
        g2d.setFont(Contstants.scoreTextFont);
        
        // draw in the bottom right area
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int x = Contstants.TILE_SIZE * Contstants.COLUMNS - metrics.stringWidth(text) - 10;
        int y = Contstants.TILE_SIZE * (Contstants.ROWS - 1) + 
                ((Contstants.TILE_SIZE - metrics.getHeight()) / 2) + metrics.getAscent();
        
        g2d.drawString(text, x, y);
    }
    
}

