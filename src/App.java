import javax.swing.*;

import ui.Board;
import ui.Window;

class App {

    public static void main(String[] args) {
        // invokeLater() is used here to prevent our graphics processing from
        // blocking the GUI. https://stackoverflow.com/a/22534931/4655368
        // this is a lot of boilerplate code that you shouldn't be too concerned about.
        // just know that when main runs it will call initWindow() once.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create the board and window
                Board board = new Board();
                JFrame windowFrame = new JFrame();
                Window window = new Window(board, windowFrame);
                window.start();
                
            }
        });
    }
}

