package main;
//partly inspired by vanZeben's Java 2D Game Tutorial https://www.youtube.com/watch?v=VE7ezYCTPe4&list=PLiHSiO7e0JtCtLrl-erg7MYuzRBX2VuZd

import javax.swing.JFrame;

public class Main {
   public static void main(String[] args) {
        JFrame window = new JFrame();
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Murphys Adventures");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}