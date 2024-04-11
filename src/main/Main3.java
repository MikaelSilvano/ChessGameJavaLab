//main3
package main;

import javax.swing.*;

public class Main3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new HomePage();
                new AIChessPage();
            }
        }
        );
    }
}