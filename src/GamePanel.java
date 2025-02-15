package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private boolean running = false;
    private boolean gameOver = false;
    private Paddle paddle;
    private Ball ball;
    private BrickManager brickManager;
    private int score = 0;
    private int lastSpeedIncreaseScore = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        brickManager = new BrickManager(); // ✅ Initialize first
        paddle = new Paddle(350, 550, 100, 10);
        ball = new Ball(400, 300, 10, brickManager); // ✅ Pass brickManager to Ball

        startGame(); // ✅ Start game immediately
    }

    public void startGame() {
        running = true;
        gameOver = false;
        new Thread(this).start();
    }

    private void updateGame() {
        if (gameOver) return;

        paddle.update();
        ball.update();
        ball.checkPaddleCollision(paddle);
        
        int earnedScore = brickManager.checkCollisions(ball);
        score += earnedScore; // ✅ Score properly updated

        if (ball.isOutOfBounds()) {
            gameOver = true;
            running = false;
        }

        if (brickManager.allBricksDestroyed()) {
            brickManager.generateBricks();
        }

        if (score >= lastSpeedIncreaseScore + 200) {
            ball.increaseSpeed(0.5);
            lastSpeedIncreaseScore = score;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);
        brickManager.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);

        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Press ENTER to restart", 300, 300);
        }
    }

    @Override
    public void run() {
        while (running) {
            updateGame();
            repaint();
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void resetGame() {
        gameOver = false;
        score = 0;
        lastSpeedIncreaseScore = 0;
        ball.reset();
        brickManager.generateBricks();
        startGame();
    }
}
