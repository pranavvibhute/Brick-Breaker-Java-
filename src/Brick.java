package src;
import java.awt.*;

public class Brick {
    private int x, y, width, height;
    private boolean visible;
    private Color color;
    private int hitsLeft;

    public Brick(int x, int y, int width, int height, Color color, int hits) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hitsLeft = hits;
        this.visible = true;
    }

    public void hit() {
        if (--hitsLeft <= 0) {
            visible = false;
        }
    }

    public void draw(Graphics g) {
        if (!visible) return;
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
