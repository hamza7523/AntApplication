import javax.swing.*;
import java.awt.*;

public class WavyAnt extends Ant {

    private float dX;
    private int factor;
    private boolean right;
    private float moveX;

    public WavyAnt(int x, int y, ImageIcon[] moving, ImageIcon squished) {
        super(x, y, moving, squished);
        dX = 0;
        factor = (int) (Math.random() * 30);
        right = true;
        moveX = 0.1f;
    }

    public WavyAnt() {  }

    public void moveAnt() {
        if (alive) {
            this.y -= move;

            x += Math.sin(dX) * factor;
            dX = dX + moveX;
//            if (right) {
//                dX += 0.5;
//            } else {
//                dX -= 0.5;
//                factor = -factor;
//            }

            if (dX == -1) {
                moveX = 0.1f;
            }
            if (dX == 1) {
                moveX = -0.1f;
            }

        } else {
            move = 0;
            dX = 0;
        }
    }

}
