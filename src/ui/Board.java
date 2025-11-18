package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

import entities.Player;
import entities.Enemy;
import entities.Traveler;
import game.Clock;
import game.Coin;
import utils.Contstants;

public class Board extends JPanel implements ActionListener, KeyListener {
    
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    // objects that appear on the game board
    private Player player;
    private ArrayList<Coin> coins;
    private Map<Point, Coin> coinPositionMap; // O(1) lookup for collision detection
    private ArrayList<Enemy> enemies;
    private Traveler traveler;
    
    private Clock clock = new Clock();

    private boolean collisionDetected = false;
    
    public boolean getCollisionDetected() {
        /**
        Gets the collision detected flag.
        @return the collision detected flag
        */
        return collisionDetected;
    }
    

    public Board() {
        /**
        Initializes the board.
        It sets the game board size and background color, initializes the game state,
        and starts the timer.
        @return void
        */
        
        // set the game board size
        setPreferredSize(new Dimension(Contstants.TILE_SIZE * Contstants.COLUMNS, Contstants.TILE_SIZE * Contstants.ROWS));
        // set the game board background color
        setBackground(Contstants.tileColor1);

        // initialize the game state
        player = new Player();
        coinPositionMap = new HashMap<>(); // Initialize the map
        coins = populateBoardWithCoins();
        enemies = createEnemies();
        traveler = new Traveler(Contstants.COLUMNS - 1, Contstants.ROWS - 1); // start in opposite corner from player

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(Contstants.DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /**
        Updates the board state.
        It moves the player, collects coins, updates the timer, and repaints the board.
        @param actionEvent - the action event to update the board state
        @return void
        */

        // update the countdown timer
        clock.updateTimer();

        // only update game state if game is not over
        if (!clock.isGameOver()) {
            // prevent the player from disappearing off the board
            player.movePlayer();

            // give the player points for collecting coins
            collectCoins();

            // update traveler and let it collect coins
            traveler.updateTraveler(coins);
            collectCoinsForTraveler();

            // update enemies
            updateEnemies();

            // check for collision with enemies
            if (checkEnemyCollision()) {
                clock.setTimeToZero();
                collisionDetected = true;
            }
        }

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    private void drawCoins(Graphics graphicsController) {
        /**
        Draws the coins.
        It draws the coins on the board.
        @param graphicsController - the graphics controller to draw the coins on
        @return void
        */
        for (Coin coin : coins) {
            coin.drawCoin(graphicsController, this);
        }
    }

    @Override
    public void paintComponent(Graphics graphicsController) {
        /**
        Paints the board.
        It draws the background, score, coins, and player on the board.
        @param graphicsController - the graphics controller to paint the board on
        @return void
        */
        super.paintComponent(graphicsController);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        
        drawBackground(graphicsController);
        
        drawCoins(graphicsController);
        
        drawEnemies(graphicsController);
        
        traveler.drawTraveler(graphicsController, this);
        
        player.drawPlayer(graphicsController, this);
        
        // draw UI elements last so they appear on top
        UI.drawScore(graphicsController, player);
        UI.drawTravelerScore(graphicsController, traveler);
        clock.drawTimer(graphicsController);

        // draw game over message if game is over
        if (clock.isGameOver()) {
            String winnerText;
            if (traveler.getScore() > player.getScore() || collisionDetected) {
                winnerText = "The traveler wins!";
            } else {
                winnerText = "The player wins!";
            }
            UI.drawGameOverScreen(graphicsController, winnerText);
        }

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent keyPressEvent) {
        /**
        Reacts to a key typed event.
        It is not used but must be defined as part of the KeyListener interface.
        @param keyPressEvent - the key typed event to react to
        @return void
        */
    }

    @Override
    public void keyPressed(KeyEvent keyPressEvent) {
        /**
        Reacts to a key pressed event.
        It moves the player one tile in the direction of the pressed key.
        @param keyPressEvent - the key pressed event to react to
        @return void
        */
        // only allow player movement if game is not over
        if (!clock.isGameOver()) {
            // react to key down events
            player.keyPressed(keyPressEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyPressEvent) {
        /**
        Reacts to a key released event.
        It is not used but must be defined as part of the KeyListener interface.
        @param keyPressEvent - the key released event to react to
        @return void
        */
        // react to key up events
    }

    private void drawBackground(Graphics graphicsController) {
        /**
        Draws the background.
        It draws a checkered background on the board.
        @param graphicsController - the graphics controller to draw the background on
        @return void
        */
        // draw a checkered background
        
        // set the color to the second tile color
        graphicsController.setColor(Contstants.tileColor2);

        for (int row = 0; row < Contstants.ROWS; row++) {
            for (int col = 0; col < Contstants.COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    graphicsController.fillRect(
                        col * Contstants.TILE_SIZE, 
                        row * Contstants.TILE_SIZE, 
                        Contstants.TILE_SIZE, 
                        Contstants.TILE_SIZE
                    );
                }
            }    
        }
    }

    

    private Coin createCoin() {
        /**
        Creates a new coin at a random unoccupied position.
        Keeps trying until it finds an empty spot to avoid overlapping coins.
        @return a new Coin object
        */
        Random randomNumberGenerator = new Random();
        Point newPosition;
        int attempts = 0;
        int maxAttempts = 100; // prevent infinite loop
        
        do {
            int coinXPosition = randomNumberGenerator.nextInt(Contstants.COLUMNS);
            int coinYPosition = randomNumberGenerator.nextInt(Contstants.ROWS);
            newPosition = new Point(coinXPosition, coinYPosition);
            attempts++;
        } while (coinPositionMap.containsKey(newPosition) && attempts < maxAttempts);
        
        return new Coin(newPosition.x, newPosition.y);
    }
    private ArrayList<Coin> populateBoardWithCoins() {
        /**
        Populates the board with coins.
        It creates the given number of coins in random positions on the board.
        Also populates the coinPositionMap for O(1) collision detection.
        @return the coins on the board
        */
        ArrayList<Coin> coins = new ArrayList<>();
        

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < Contstants.TOTAL_COINS; i++) { 
            Coin coin = createCoin();
            coins.add(coin);
            coinPositionMap.put(coin.getCurrentCoinPostion(), coin); // Add to map for O(1) lookup
        }
            return coins;
    }

    private void collectCoins() {
        /**
        Collects the coins.
        It allows the player to pickup coins and adds points to the player's score.
        Uses O(1) HashMap lookup for collision detection (optimized from O(n) iteration).
        @return void
        */
        Point playerPos = player.getCurrentPlayerPosition();
        Coin coin = coinPositionMap.get(playerPos); // O(1) lookup instead of O(n) iteration
        
        if (coin != null) {
            // give the player some points for picking this up
            player.addScore(100);
            
            // remove old coin
            coins.remove(coin);
            coinPositionMap.remove(playerPos);
            
            // add new coin
            Coin newCoin = createCoin();
            coins.add(newCoin);
            coinPositionMap.put(newCoin.getCurrentCoinPostion(), newCoin);
        }
    }
 
    public void addCoin(Coin coin) {
        /**
        Adds a coin to the board.
        It adds the given coin to both the ArrayList and the HashMap.
        @param coin - the coin to add to the board
        @return void
        */
        coins.add(coin);
        coinPositionMap.put(coin.getCurrentCoinPostion(), coin); // Maintain map consistency
    }

    private ArrayList<Enemy> createEnemies() {
        /**
        Creates enemies.
        It creates a list of enemies in random positions on the board.
        @return the list of enemies
        */
        ArrayList<Enemy> enemyList = new ArrayList<>();
        Random rand = new Random();
        
        // create 3 enemies
        int numEnemies = 3;
        for (int i = 0; i < numEnemies; i++) {
            int enemyX = rand.nextInt(Contstants.COLUMNS);
            int enemyY = rand.nextInt(Contstants.ROWS);
            enemyList.add(new Enemy(enemyX, enemyY));
        }
        
        return enemyList;
    }

    private void updateEnemies() {
        /**
        Updates the enemies.
        It updates each enemy's position and behavior.
        @return void
        */
        for (Enemy enemy : enemies) {
            enemy.updateEnemy();
        }
    }

    private void drawEnemies(Graphics graphicsController) {
        /**
        Draws the enemies.
        It draws all enemies on the board.
        @param graphicsController - the graphics controller to draw the enemies on
        @return void
        */
        for (Enemy enemy : enemies) {
            enemy.drawEnemy(graphicsController, this);
        }
    }

    private boolean checkEnemyCollision() {
        /**
        Checks for collision with enemies.
        If the player collides with an enemy, the game ends.
        @return true if collision detected, false otherwise
        */
        for (Enemy enemy : enemies) {
            if (enemy.collidesWithPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    private void collectCoinsForTraveler() {
        /**
        Allows the traveler to collect coins.
        Uses O(1) HashMap lookup for collision detection (optimized from O(n) iteration).
        @return void
        */
        Point travelerPos = traveler.getPosition();
        Coin coin = coinPositionMap.get(travelerPos); // O(1) lookup instead of O(n) iteration
        
        if (coin != null) {
            // traveler collects the coin
            traveler.addScore(100);
            
            // remove old coin
            coins.remove(coin);
            coinPositionMap.remove(travelerPos);
            
            // add new coin
            Coin newCoin = createCoin();
            coins.add(newCoin);
            coinPositionMap.put(newCoin.getCurrentCoinPostion(), newCoin);
        }
    }

    
}

