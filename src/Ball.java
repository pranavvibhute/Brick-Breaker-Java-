package src;
import java.awt.*;
import java.util.Random;

public class Ball {
    private int x, y, size;
    private double dx, dy;
    private double speed = 2.5;

    public Ball(int x, int y, int size, BrickManager brickManager) {
        this.x = x;
        this.y = y;
        this.size = size;
        randomizeDirection();
    }

    private void randomizeDirection() {
        Random rand = new Random();
        dx = rand.nextBoolean() ? speed : -speed;
        dy = -speed;
    }

    public void update() {
        x += dx;
        y += dy;

        if (x <= 0 || x + size >= 800) {
            reverseX();
        }
        if (y <= 0) {
            reverseY();
        }
    }

    public void checkPaddleCollision(Paddle paddle) {
        if (getBounds().intersects(paddle.getBounds())) {
            dy = -Math.abs(dy);
        }
    }

    public void reverseX() {
        dx = -dx;
    }

    public void reverseY() {
        dy = -dy;
    }

    public void increaseSpeed(double amount) {
        speed += amount;
    }

    public boolean isOutOfBounds() {
        return y > 600;
    }

    public void reset() {
        x = 400;
        y = 300;
        randomizeDirection();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
