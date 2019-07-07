package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Map extends JPanel implements ActionListener {

    private final int SIZEX = 640;
    private final int SIZEY = 480;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 1200;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = true;
    private boolean pressedUp = false;
    private boolean pressedDown = false;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    private boolean inGame = true;
    private boolean isPause = false;
    private int count = 0;
    private int[] moves = {3, 3};



    public Map(){
        setBackground(Color.pink);
        loadImages();
        initGame();
        addKeyListener(new MapKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i*DOT_SIZE;
            y[i] = 96;
        }
        if(timer == null)
            timer = new Timer(400, this);
        timer.start();
        createApple();
    }

    private void direction(){
        if (pressedUp) {
            up = true;
            down = false;
            left = false;
            right = false;
        }
        if(pressedDown){
            down = true;
            up = false;
            left = false;
            right = false;
        }
        if(pressedLeft){
            left = true;
            up = false;
            down = false;
            right = false;
        }
        if(pressedRight){
            right = true;
            up = false;
            down = false;
            left = false;
        }
    }

    public void createApple(){
        appleX = new Random().nextInt(40)*DOT_SIZE;
        appleY = new Random().nextInt(30)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawString("Score: " + count, 0, 16);
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over ";
            g.drawString(str + "\n Your score: " + count, 200, 200);
        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(moves[0] == 2)
            x[0] -= DOT_SIZE;
        if(moves[0] == 3)
            x[0] += DOT_SIZE;
        if(moves[0] == 0)
            y[0] -= DOT_SIZE;
        if(moves[0] == 1)
            y[0] += DOT_SIZE;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            count++;
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
        }
        if(x[0]>SIZEX)
            inGame = false;
        if(x[0] < 0)
            inGame = false;
        if(y[0] > SIZEY)
            inGame = false;
        if(y[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            direction();
            move();
            turns();
            checkCollisions();
        }
        repaint();
    }

    private void restart(){
        inGame = true;
        left = false;
        up = false;
        down = false;
        right = true;
        count = 0;
        initGame();
    }

    private void pause(){
        if(isPause) {
            timer.start();
            isPause = false;
        }
        else {
            timer.stop();
            isPause = true;
        }

    }

    private void turns(){
        moves[0] = moves[1];
    }



    class MapKeyListener extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right) {
                pressedLeft = true;
                moves[1] = 2;
                pressedUp = false;
                pressedDown = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left) {
                pressedRight = true;
                moves[1] = 3;
                pressedUp = false;
                pressedDown = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                pressedDown = true;
                moves[1] = 1;
                pressedRight = false;
                pressedLeft = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                pressedUp = true;
                moves[1] = 0;
                pressedLeft = false;
                pressedRight = false;
            }
            if(key == KeyEvent.VK_R || key == KeyEvent.VK_SPACE){
                restart();
            }


            if(key == KeyEvent.VK_P) {
                pause();
            }
        }
    }
}
