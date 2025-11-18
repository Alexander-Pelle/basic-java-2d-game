import javax.swing.*;

import ui.Board;
import ui.Window;

class App {

    public static void main(String[] args) {

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

