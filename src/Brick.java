package src;
import java.awt.*;

public class Brick {
    private int x, y, width, height;
    private boolean visible;
    private Color color;
    private int hitsLeft;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hitsLeft = setHits(color);
        this.visible = true;
    }

    public int setHits(Color color) {
        int hits = 0;
        if(color == Color.RED) {
            hits = 1;
        } else if(color == Color.ORANGE) {
            hits = 2;
        } else if(color == Color.YELLOW) {
            hits = 3;
        } else if(color == Color.GREEN) {
            hits = 4;
        } else if(color == Color.CYAN) {
            hits = 5;
        }
        return hits;
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

    public int getHitsLeft() {
        return hitsLeft;
    }  

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

}
