import javax.swing.*;
import java.io.Serializable;

public class AntFactory implements Serializable {

    private ImageIcon movingNormalAnt[];
    private ImageIcon movingFireAnt[];
    private ImageIcon NormalAntSquished;
    private ImageIcon fireAntSquished;

    public AntFactory() {
        initializeAssets();
        System.out.println("Factory created");
    }

    public Ant getAnt() {
        double probability = Math.random();

        if (probability > 0.95) {           //Generating wavy ant
            return new WavyAnt((int) (Math.random() * Board.B_WIDTH), Board.B_HEIGHT, movingNormalAnt, NormalAntSquished);
        }
        if (probability > 0.80) {               //Generating fire ant
            return new FireAnt((int) (Math.random() * Board.B_WIDTH), Board.B_HEIGHT, movingFireAnt, fireAntSquished);
        }
//        if (probability > 0.75) {            //Generating normal ant
            return new NormalAnt((int) (Math.random() * Board.B_WIDTH), Board.B_HEIGHT, movingNormalAnt, NormalAntSquished);
//        }


    }

    private void initializeAssets() {                           //reading images

        movingNormalAnt = new ImageIcon[22];
        for (int i = 0; i < movingNormalAnt.length; i++) {
            if (i <= 9) {
                movingNormalAnt[i] = new ImageIcon("C:Users/Admin/IdeaProjects/hamza_lab12/AntIcons/tile0" + i + ".png");
            } else {
                movingNormalAnt[i] = new ImageIcon("C:Users/Admin/IdeaProjects/hamza_lab12/AntIcons/tile0" + i + ".png");
            }
        }

        movingFireAnt = new ImageIcon[22];
        for (int i = 0; i < movingFireAnt.length; i++) {
            if (i <= 9) {
                movingFireAnt[i] = new ImageIcon("C:Users/Admin/IdeaProjects/hamza_lab12/FireAntIcons/tile0" + i + ".png");
            } else {
                movingFireAnt[i] = new ImageIcon("C:Users/Admin/IdeaProjects/hamza_lab12/FireAntIcons/tile0" + i + ".png");
            }
        }

        //separate image for squished
        NormalAntSquished = new ImageIcon("src/AntIcons/tile023.png");
        fireAntSquished = new ImageIcon("src/FireAntIcons/tile023.png");
    }

}
