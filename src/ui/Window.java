package ui;

import javax.swing.*;

public class Window {
    private JFrame window;
    private Board board;

    public Window(Board board, JFrame window) {
        this.board = board;
        this.window = window;
        
        Setup();
    }
    
    private void Setup() {
        /**
        Sets up the window.
        It creates a window frame and sets the title in the toolbar.
        It also sets the default close operation, adds the game components to the window,
        sets the window to not resizable, fits the window size around the components,
        and opens the window in the center of the screen.
        @return void
        */

        // create a window frame and set the title in the toolbar
        this.window = new JFrame("Can't Stop, Won't Stop, GameStop");
        // when we close the window, stop the app
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add the game components to the window
        this.AddGameComponents();

        // don't allow the user to resize the window
        this.window.setResizable(false);
        // fit the window size around the components (just our jpanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        this.window.pack();
        // open window in the center of the screen
        this.window.setLocationRelativeTo(null);
    }
        
    private void AddGameComponents() {
        /**
        Adds the game components to the window.
        It adds the board to the window and passes keyboard inputs to the board.
        @return void
        */

        this.window.add(board);
        this.window.addKeyListener(board);
    }

    public void start() {
        /**
        Makes the window visible.
        It sets the window to visible and starts the game.
        @return void
        */

        // make the window visible
        this.window.setVisible(true);
    }
}

