package game;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(656, 532);
        setLocation(400, 400);
        add(new Map());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();

    }
}
