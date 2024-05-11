package SnakeGame;

import javax.swing.*;

public class SnakeGameFrame extends JFrame {
    SnakeGameFrame(){
        this.add(new SnakeGamePanel());
        this.setTitle("Gra Snake");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
