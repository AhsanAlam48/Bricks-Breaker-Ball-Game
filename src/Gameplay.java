import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.security.sasl.RealmCallback;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    int delay = 8; // 5

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;

    private int balldirX = -1;
    private int balldirY = -2;

    private MapGenrator map;

    public Gameplay() {
        map = new MapGenrator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g) {

        g.setColor(Color.blue);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.setColor(Color.GREEN);

        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        g.setColor(Color.yellow);

        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            balldirX = 0;
            balldirY = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 230, 280);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score : " + score, 230, 325);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 370);
        }

        if (ballposY > 570) {
            play = false;
            balldirX = 0;
            balldirY = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over!", 230, 280);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score : " + score, 230, 325);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 370);

        }
        g.dispose();

    }

    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                balldirY = -balldirY;

            }

            A: for (int i = 0; i < map.map.length; i++) {

                for (int j = 0; j < map.map[0].length; j++) {

                    if (map.map[i][j] > 0) {

                        int BrickX = j * map.brickwidth + 80;
                        int BrickY = i * map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(BrickX, BrickY, brickwidth, brickheight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 10;
                            delay--;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }
                            break A;
                        }

                    }

                }

            }

            ballposX += balldirX;
            ballposY += balldirY;
            if (ballposX < 0) {
                balldirX = -balldirX;
            }

            if (ballposY < 0) {
                balldirY = -balldirY;
            }

            if (ballposX > 670) {
                balldirX = -balldirX;
            }

        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 585) {
                playerX = 585;
            } else {
                moveRight();
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 8) {
                playerX = 5;
            } else {
                moveLeft();

            }

        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                playerX = 350;
                score = 0;
                totalBricks = 21;
                map = new MapGenrator(3, 7);

                repaint();

            }
        }

    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

}
