package src;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class BrickManager {
    private ArrayList<Brick> bricks;
    private ArrayList<Powerup> powerUps;
    public BrickManager() {
        bricks = new ArrayList<>();
        powerUps = new ArrayList<>();
        generateBricks();
    }

    public void generateBricks() {
        bricks.clear();
        Color[] brickColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Color randomColor = brickColors[rand.nextInt(brickColors.length)];
                bricks.add(new Brick(j * 80, i * 30 + 50, 75, 20, randomColor));
            }
        }
    }

    
  

    public int checkCollisions(Ball ball) {
        Random random = new Random();
        for (Brick brick : bricks) {
            if (brick.isVisible() && ball.getBounds().intersects(brick.getBounds())) {
                brick.hit();
                ball.reverseY();
                // If it's a strong brick (2+ hits required) and gets destroyed, randomly spawn a power-up
            if (!brick.isVisible() && brick.getHitsLeft() >= 2 && random.nextDouble() < 0.3) {
                powerUps.add(new Powerup(brick.getX() + 20, brick.getY() + 20, brick.getWidth(), brick.getHeight(), Powerup.Type.values()[random.nextInt(Powerup.Type.values().length)]));
            }
                return 10; // ✅ Add score for breaking a brick
            }
        }
        return 0;
    }

    public ArrayList<Powerup> getPowerUps() {
        return powerUps;
    }

    public boolean allBricksDestroyed() {
        return bricks.stream().noneMatch(Brick::isVisible);
    }

    public void draw(Graphics g) {
        for (Brick brick : bricks) {
            brick.draw(g);
        }
    }
}
