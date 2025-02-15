package src;
import java.awt.*;
import java.util.ArrayList;

public class BrickManager {
    private ArrayList<Brick> bricks;

    public BrickManager() {
        bricks = new ArrayList<>();
        generateBricks();
    }

    public void generateBricks() {
        bricks.clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks.add(new Brick(j * 80, i * 30 + 50, 75, 20, Color.ORANGE, 1));
            }
        }
    }

    public int checkCollisions(Ball ball) {
        for (Brick brick : bricks) {
            if (brick.isVisible() && ball.getBounds().intersects(brick.getBounds())) {
                brick.hit();
                ball.reverseY();
                return 10; // ✅ Add score for breaking a brick
            }
        }
        return 0;
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
