import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Board extends JPanel
        implements ActionListener, MouseInputListener, Serializable {

    public static final int B_WIDTH = 800;
    public static final int B_HEIGHT = 600;
    public static final int DELAY = 25;

    private Timer timer;
    private int key = 0;
    private boolean keyPressed = false;
    private boolean mousePressed = false;

    private boolean start_drawing = false;

    private ImageIcon[] images;
    private ImageIcon squished;
    private ArrayList<Ant> ants;
    private ArrayList<Ant> deadAnts;
    private int score;
    private Point mouseMoved;
    private ArrayList<Point> mouseMoving;
    private AntFactory antFactory;
    private boolean pause;


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            char key = e.getKeyChar();
            keyPressed = false;

            if (key == 's') {

                try {
                    FileOutputStream file = new FileOutputStream("ants.ser");
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    out.writeObject(ants);
                    out.writeObject(deadAnts);                                      //saving objects
                    out.writeObject(score + "");                                    //saving score as string

                    out.close();
                    file.close();

                    pause = true;                                               //pausing the game
                    System.out.println("SERIALIZATION - SAVED");

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

            if (key == 'r') {

                try {

                    FileInputStream file = new FileInputStream("ants.ser");
                    ObjectInputStream in = new ObjectInputStream(file);

                    ArrayList<Ant> tempAnts = (ArrayList<Ant>) in.readObject();
                    ArrayList<Ant> tempDeadAnts = (ArrayList<Ant>) in.readObject();             //reading the objects
                    score = Integer.parseInt((String) in.readObject());                      //converting string to int

                    ants = new ArrayList<>();
                    deadAnts = new ArrayList<>();

                    System.out.println(tempAnts);
                                                                                        //getting the stored elements
                    for (int i = 0; i < tempAnts.size(); i++) {
                        ants.add( (tempAnts.get(i)) );
                    }

                    for (int i = 0; i < tempDeadAnts.size(); i++) {
                        deadAnts.add( (tempDeadAnts.get(i)) );
                    }

                    in.close();
                    file.close();

                    pause = false;
                    System.out.println("SERIALIZATION - RESUMED");

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }

            if (key == 'j') {
                try {
                    XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ants.xml")));

                    encoder.writeObject(ants);
                    encoder.writeObject(deadAnts);                                  //writing using xml
                    encoder.writeObject("" + score);

                    encoder.close();
                    pause = true;
                    System.out.println("XML - SAVED");

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (key == 'k') {
                try {
                    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("ants.xml")));

                    ants = (ArrayList<Ant>) decoder.readObject();
                    deadAnts = (ArrayList<Ant>) decoder.readObject();               //reading from xml
                    score = Integer.parseInt((String) decoder.readObject());

                    decoder.close();
                    pause = false;
                    System.out.println("XML - RESUMED");

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed = true;
            key = e.getKeyCode();
        }

    }

    public Board() {
        initBoard();
    }

    private void InitializeAssets() {
        ants = new ArrayList<>();
        deadAnts = new ArrayList<>();
        mouseMoving = new ArrayList<>();
        score = 0;
        pause = false;

        antFactory = new AntFactory();

        System.out.println("Press j and k for XML Save/Resume");
        System.out.println("Press s and r for Serialization Save/Resume");

//        //Loading images
//        for (int i = 0; i < images.length; i++) {
//            if (i <= 9) {
//                images[i] = new ImageIcon("src/AntIcons/tile00" + i + ".png");
//            } else {
//                images[i] = new ImageIcon("src/AntIcons/tile0" + i + ".png");
//            }
//        }
//
//        //separate image for squished
//        squished = new ImageIcon("src/AntIcons/tile023.png");

    }

    private void initBoard() {

//        images = new ImageIcon[22];

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setFocusable(true);

        InitializeAssets();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing score string
        g.setColor(Color.RED);
        g.drawString("SCORE: " + score, 10, 10);

        //Drawing ants that are alive
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).paint(g, this);
        }

        //Drawing ants that are dead
        for (int i = 0; i < deadAnts.size(); i++) {
            deadAnts.get(i).paint(g, this);
        }

//        System.out.println("ANTS " + ants.size());
//        System.out.println("DEAD " + deadAnts.size());

        if (keyPressed) {
            drawNotification("key ", g);
        }

        if (mousePressed) {
            drawNotification("mouse ", g);
        }

    }

    private void drawNotification(String text, Graphics g) {
        g.setColor(Color.RED);
        g.drawString(text + key + " pressed", 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        repaint();
        if (!pause) {

            ants.add(antFactory.getAnt());                      //Adding ants by getting them from the factory

            for (int i = 0; i < ants.size(); i++) {
                Ant ant = ants.get(i);

                if (ant.getAlive()) {           //Updating images if alive
//                    ant.current_image = ant.getAntIcons()[(int) (Math.random() * (ant.getAntIcons().length - 1))].getImage();

                    Image tempImage = ant.getAntIcons()[ (int) (Math.random() * (ant.getAntIcons().length-1))].getImage();
                    ImageIcon tempII = new ImageIcon(tempImage);
                    ant.current_image = tempII;

                /*
                if (mouseMoving.size() > 1) {

                    if (mouseMoved.x > (ant.getX() - 10) || mouseMoved.x < (ant.getX() + ant.width + 10)) {
                        ant.addX((mouseMoving.get(1).x - mouseMoving.get(0).x) );
//                        ant.addX(100);
                    }

                    if (mouseMoved.y > (ant.getY() - 20) || mouseMoved.y < (ant.getY() + ant.height + 20)) {
                        ant.addY((mouseMoving.get(1).y - mouseMoving.get(0).y) );
//                        ant.addY(100);
                    }
                }
                 */

                    ant.moveAnt();              //Updating coords

                    if (ant.getY() < 2) {
                        ants.remove(ant);       //Removing ant from arraylist if it has reached the top
                    }

                } else {
                    score++;                            //Increasing score if the current ant is not alive
                    deadAnts.add(ant);          //Adding the recently deceased ant to the deadants array
                    ants.remove(ant);           //Removing the recently deceased ant from the main array
                }
            }
        }
    }

    public void IsClicked(int x, int y) {
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).IsClicked(x, y);            //calling IsClicked for all ants
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        IsClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMoved = new Point(e.getX(), e.getY());
        mouseMoving.add(mouseMoved);

        if (mouseMoving.size() == 3) {
            mouseMoving.remove(0);
        }
    }

    private int dX() {
        int dX;

        Point p1 = mouseMoving.get(0);
        Point p2 = mouseMoving.get(1);

        dX = p2.x - p1.x;
        return dX;
    }

    private int dY() {
        int dY;

        Point p1 = mouseMoving.get(0);
        Point p2 = mouseMoving.get(1);

        dY = p2.y - p1.y;
        return dY;
    }

    //METHODS FOR SERIALIZATION

    public void setAnts(ArrayList<Ant> ants) {
        this.ants = ants;
    }

    public void setDeadAnts(ArrayList<Ant> deadAnts) {
        this.deadAnts = deadAnts;
    }

    public ArrayList<Ant> getAnts() {
        return ants;
    }

    public ArrayList<Ant> getDeadAnts() {
        return deadAnts;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}