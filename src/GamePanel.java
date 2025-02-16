package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private enum GameState { MENU, PLAYING, GAME_OVER }
    private GameState gameState = GameState.MENU; // Start from menu

    private boolean running = false;
    private Paddle paddle;
    private Ball ball;
    private BrickManager brickManager;
    private int score = 0;
    private int lastSpeedIncreaseScore = 0;
    private Powerup.Type activePowerup = null; // ✅ Tracks active power-up
    private int powerupTimer = 0; // ✅ Tracks duration of power-up effect


    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        brickManager = new BrickManager();
        paddle = new Paddle(350, 550, 100, 10);
        ball = new Ball(400, 300, 10, brickManager);
    }

    public void startGame() {
        gameState = GameState.PLAYING;
        running = true;
        score = 0;
        lastSpeedIncreaseScore = 0;
        ball.reset();
        brickManager.generateBricks();
        new Thread(this).start();
    }

    private ArrayList<Powerup> powerUps = new ArrayList<>();

    private void updateGame() {
        if (gameState != GameState.PLAYING) return;

        paddle.update();
        ball.update();
        ball.checkPaddleCollision(paddle);
        
        int earnedScore = brickManager.checkCollisions(ball);
        score += earnedScore;

         // Update power-ups
         for (Powerup powerUps : brickManager.getPowerUps()) {
            powerUps.update();
            if (powerUps.checkPaddleCollision(paddle)) {
                powerUps.activateEffect(this);
                powerUps.setInactive();
            }
        }
        if (powerupTimer > 0) {
            powerupTimer--;
            System.out.println("Power-up Timer: " + powerupTimer); // ✅ Debug print
            if (powerupTimer == 0) {
                System.out.println("Power-up Expired!"); // ✅ Debug print
                activePowerup = null;
            }
        }

        if (ball.isOutOfBounds()) {
            gameState = GameState.GAME_OVER;
            running = false;
        }

        if (brickManager.allBricksDestroyed()) {
            brickManager.generateBricks();
        }

        if (score >= lastSpeedIncreaseScore + 500) {
            ball.increaseSpeed(1.05);
            lastSpeedIncreaseScore = score;
        }
        
}

    
public void setActivePowerup(Powerup.Type type) {
        this.activePowerup = type;
        this.powerupTimer = 500; // ✅ Effect lasts for 500 frames (~4-5 sec)
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw power-ups
        for (Powerup powerUps : brickManager.getPowerUps()) {
            powerUps.draw(g);
        }
        if(activePowerup != null) {
            g.drawString("Active Power-up: " + activePowerup.name(), 220, 20);
            System.out.println("Drawing Active Power-up: " + activePowerup); 
        }   

        if (gameState == GameState.MENU) {
            drawMenu(g);
        } else if (gameState == GameState.PLAYING) {
            drawGame(g);
        } else if (gameState == GameState.GAME_OVER) {
            drawGameOver(g);
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("BRICK BREAKER", 280, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press ENTER to Start", 320, 300);
    }

    private void drawGame(Graphics g) {
        paddle.draw(g);
        ball.draw(g);
        brickManager.draw(g);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Speed: " + ball.getSpeed(), 120, 20);
        
    }

    private void drawGameOver(Graphics g) {
        drawGame(g); // Draw the game screen behind the message
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Game Over!", 320, 250);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Press ENTER to Restart", 290, 300);
    }

    // Power-up handling functions
    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
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
        if (gameState == GameState.MENU && e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame();
        } else if (gameState == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame();
        } else if (gameState == GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddle.moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.moveRight();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameState == GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.stop();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


}
