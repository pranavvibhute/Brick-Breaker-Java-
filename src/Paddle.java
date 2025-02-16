package src;
import java.awt.*;

public class Paddle {
    private int x, y, width, height;
    private int speed = 6;
    private int dx = 0;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        dx = -speed;
    }

    public void moveRight() {
        dx = speed;
    }

    public void stop() {
        dx = 0;
    }

    public void update() {
        x += dx;
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void expand() {
        this.width = Math.min(this.width + 40, 200); // Max width 200px
    }
    
    public void shrink() {
        this.width = Math.max(this.width - 40, 80); // Min width 80px
    }
}
