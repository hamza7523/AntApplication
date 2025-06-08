import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

public class Ant extends Button implements Serializable {

    protected ImageIcon[] moving;
    boolean alive;
    protected int move = 5;

    public Ant(int x, int y, ImageIcon[] moving, ImageIcon squished) {
        super(x, y);
        width = 61;
        height = 74;
        this.moving = moving;
        this.squished = squished;
        alive = true;
        current_image = moving[0];
    }

    public Ant() {  }

    public boolean IsClicked(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            pressed = true;
            current_image = squished;
            alive = false;
            return true;
        }
        return false;
    }

    public void moveAnt() {
        if (alive) {
            this.y -= move;         //increasing height
        }
        else {
            move = 0;               //move is 0 if dead
        }
    }

    public boolean getAlive() {
        return alive;
    }

    public void paint (Graphics g, ImageObserver ob) {
        try {
            g.drawImage(current_image.getImage(), x, y, ob);
        }
        catch (Exception ex) {  }
    }

    public int getY () {
        return y;
    }

    public int getX () {
        return x;
    }

    void addX (int x) {
        this.x += x;
    }

    void addY (int y) {
        this.y += y;
    }

    //METHODS FOR SERIALIZATION

    public ImageIcon[] getAntIcons() {
        return moving;
    }

    public void setMoving(ImageIcon[] moving) {
        this.moving = moving;
    }

    public ImageIcon[] getMoving() {
        return moving;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }
}