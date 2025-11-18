package game;

import javax.swing.*;
import java.awt.*;

import utils.Contstants;

public class Clock {
    // countdown timer variables
    private int remainingTime = 60; // time in seconds (1 minute)
    private int tickCounter = 0; // counts ticks to determine when a second has passed
    private final int TICKS_PER_SECOND = 1000 / 25; // 40 ticks per second at 25ms delay

    public Clock() {
        // initialize the state

    }

    
    public void drawTimer(Graphics graphicsController) {
        /**
        Draws the countdown timer.  
        It draws the remaining time on the board in the top center of the screen.
        @param graphicsController - the graphics controller to draw the timer on
        @param remainingTime - the remaining time to draw
        @return void
        */
        // format the time as MM:SS
        int minutes = this.remainingTime / 60;
        int seconds = this.remainingTime % 60;
        String text = String.format("%d:%02d", minutes, seconds);
        
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
        
        // draw the timer in the top center of the screen
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle (top row)
        Rectangle rect = new Rectangle(0, 0, Contstants.TILE_SIZE * Contstants.COLUMNS, Contstants.TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        graphicsController.drawString(text, x, y);
    }

    public void updateTimer() {
        /**
        Updates the countdown timer.
        It decrements the remaining time by one second every TICKS_PER_SECOND ticks.
        If the time runs out, the game stops.
        @return void
        */
        tickCounter++;
        
        // check if a second has passed
        if (tickCounter >= TICKS_PER_SECOND) {
            tickCounter = 0; // reset tick counter
            
            // decrement time if there's time remaining
            if (remainingTime > 0) {
                remainingTime--;
            }
            
            // optionally: stop the game when time runs out
            if (remainingTime <= 0) {
                
                // you can add game over logic here
            }
        }
    }

    public boolean isGameOver() {
        /**
        Checks if the game is over.
        It returns true if the remaining time is 0 or less.
        @return true if the game is over, false otherwise
        */
        return remainingTime <= 0;
    }

    public void setTimeToZero() {
        /**
        Sets the remaining time to zero.
        This is used to trigger game over (e.g., when player collides with enemy).
        @return void
        */
        remainingTime = 0;
    }
}

