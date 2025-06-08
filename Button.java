import javax.swing.*;
import java.awt.Image;
import java.io.Serializable;

public class Button implements Serializable  {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected ImageIcon squished;
    protected ImageIcon current_image;
    protected boolean pressed;

    /*
    public Button(int x, int y, int width, int height, Image i_depressed, Image i_pressed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//		image_depressed = i_depressed;
        squished = i_pressed;
        current_image = i_depressed;
    }
     */

    public Button(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Button() {
    }

    public ImageIcon GetImage() {
        return current_image;
    }

    public boolean IsClicked(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            pressed = true;
            current_image = squished;
            return true;
        }
        return false;
    }

    //METHODS FOR SERIALIZATION

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageIcon getSquished() {
        return squished;
    }

    public void setSquished(ImageIcon squished) {
        this.squished = squished;
    }

    public ImageIcon getCurrent_image() {
        return current_image;
    }

    public void setCurrent_image(ImageIcon current_image) {
        this.current_image = current_image;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public Boolean getPressed() {
        return pressed;
    }

}
