package src;

import java.awt.*;
import java.util.Random;

public class Powerup {
    public enum Type{
        EXPAND_PADDLE,    // 🔵 Expands paddle size
        SHRINK_PADDLE,    // 🔴 Shrinks paddle size
        SLOW_BALL,        // 🟢 Slows down ball speed
        FAST_BALL,        // 🔴 Speeds up ball
    }

    private int x, y, width, height;
    private Type type;
    private boolean active;
    private int fallSpeed = 2;

    public Powerup(int x, int y, int width, int height, Type type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.active = true;
    }

    public void update() {
        if(!active) return;
        y += fallSpeed;
    }

    public void draw(Graphics g)
    {
        if (!active) return;

        switch (type) {
            case EXPAND_PADDLE:
                g.setColor(Color.BLUE);
                break;
            case SLOW_BALL:
                g.setColor(Color.GREEN);
                break;
            case SHRINK_PADDLE:
                g.setColor(Color.RED);
                break;
            case FAST_BALL:
                g.setColor(Color.RED);
                break;
        }
        g.fillOval(x, y, width, height);
    }

    public boolean checkPaddleCollision(Paddle paddle) {
        return active && getBounds().intersects(paddle.getBounds());
    }

    public void activateEffect(GamePanel gamePanel) {
        if (!active) return;
    
        gamePanel.setActivePowerup(type); // ✅ Store active power-up in GamePanel
        System.out.println("Power-up Activated: " + type); // ✅ Debug print
        switch (type) {
            case EXPAND_PADDLE:
                gamePanel.getPaddle().expand();
                break;
            case SLOW_BALL:
                gamePanel.getBall().slowDown();
                break;
            case SHRINK_PADDLE:
                gamePanel.getPaddle().shrink();
                break;
            case FAST_BALL:
                gamePanel.getBall().speedUp();
                break;
        }
    
        active = false; // ✅ Disable power-up after activation
    }
    

    public boolean isActive() {
        return active;
    }

    public void setInactive() {
        active = false; // ✅ Power-up disappears after being collected
    }
        
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
