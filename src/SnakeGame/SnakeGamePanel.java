package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {

    static final int windowHeight = 600;
    static final int windowWidth  = 600;
    static final int gameUnitSize = 60;
    static final int gameUnits = (windowWidth * windowHeight)/(gameUnitSize*gameUnitSize);
    static final int frameRefresh = 100;
    final int x[] = new int[gameUnits];
    final int y[] = new int[gameUnits];
    int snakeLength = 5;
    int score;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random rand;

    SnakeGamePanel(){
        rand = new Random();
        this.setPreferredSize(new Dimension(windowWidth,windowHeight));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startSnake();
    }
    public void startSnake(){
        createApple();
        running=true;
        timer = new Timer(frameRefresh,this);
        timer.start();
    }
    public void paint(Graphics g){
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running) {
            g.setColor(Color.lightGray);
            for (int i = 0; i < windowWidth / gameUnitSize; i++) {
                for (int ii = 0; ii < windowHeight / gameUnitSize; ii++) {
                    if (ii % 2 == 0) g.fillRect(i * 2 * gameUnitSize, ii * gameUnitSize, gameUnitSize, gameUnitSize);
                    else g.fillRect(i * 2 * gameUnitSize + gameUnitSize, ii * gameUnitSize, gameUnitSize, gameUnitSize);
                }
            }
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, gameUnitSize, gameUnitSize);
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(new Color(204, 0, 204));
                    g.fillRect(x[i], y[i], gameUnitSize, gameUnitSize);
                } else {
                    g.setColor(new Color(120, 0, 204));
                    g.fillRect(x[i], y[i], gameUnitSize, gameUnitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Arial",Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+score, (windowWidth - metrics.stringWidth("Score: "+score))/2, g.getFont().getSize());
        }
        if (!running){
            gameOver(g);
        }
    }
    public void move(){
        for(int i=snakeLength;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-gameUnitSize;
                break;
            case 'R':
                x[0]=x[0]+gameUnitSize;
                break;
            case 'D':
                y[0]=y[0]+gameUnitSize;
                break;
            case 'L':
                x[0]=x[0]-gameUnitSize;
                break;
        }
    }
    public void createApple(){
        appleX=rand.nextInt((int)(windowWidth/gameUnitSize))*gameUnitSize;
        appleY=rand.nextInt((int)(windowHeight/gameUnitSize))*gameUnitSize;
    }
    public void missingApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            snakeLength++;
            score++;
            createApple();
        }
    }
    public void detectCollision(){
        //kolizja z elementami węża
        for(int i=snakeLength;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //kolizja ze ścianami
        if(y[0]<0) running=false; //góra
        if(x[0]>windowWidth) running=false; //prawa
        if(y[0]>windowHeight) running=false; //dół
        if(x[0]<0) running=false; //lewa
        if (!running) timer.stop();
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Arial Black",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(windowWidth-metrics.stringWidth("Game Over"))/2,windowHeight/2);

        g.setFont( new Font("Arial",Font.BOLD, 35));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+score, (windowWidth - metrics.stringWidth("Score: "+score))/2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent a){
        if (running){
            move();
            missingApple();
            detectCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
