import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Board extends JPanel
        implements ActionListener , MouseInputListener{

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 800;
    private final int DELAY = 50;
    private int count;

    private int score;
    private boolean check;

    private Timer timer;
    private int key = 0;
    private boolean keyPressed = false;
    ImageIcon button_pre = new ImageIcon("C:/Users/amanlab02/IdeaProjects/Lab09/src/tile023.png");
    private boolean mousePressed = false;
    private ArrayList<Ant> ants = new ArrayList<>();
    
    private boolean start_drawing = false;
    
    private int x_init;
    private int y_init;
    private int x_final;
    private int y_final;
    
    private Ant ant;
    private ImageIcon[] imageFrames =new ImageIcon[23];
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            keyPressed = false;

            if (key == KeyEvent.VK_SPACE) {

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {

            keyPressed = true;
            key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

            }

        }
    }

    public Board() {

        initBoard();
    }

    private void InitializeAssets() {


        ImageIcon button_pre = new ImageIcon("C:/Users/amanlab02/IdeaProjects/Lab09/src/tile023.png");
//        ImageIcon button_dep = new ImageIcon("C:/Users/amanlab02/IdeaProjects/Lab09/src/tile000.png");
        for (int i = 0; i < imageFrames.length; i++) {
            if (i <= 9) {
                imageFrames[i] = new ImageIcon("src/res/tile00" + i + ".png");

            } else {
                imageFrames[i] = new ImageIcon("src/res/tile0" + i + ".png");
            }

            int x = 100;
            int y = 700;





        }


    }

    private void initBoard() {

        addMouseListener( this );
        addMouseMotionListener( this );
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setFocusable(true);
        InitializeAssets();


            timer = new Timer(200, this);
            timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).paint(g,this);

        }
        if(keyPressed)
        {
        	drawNotification("key ", g);
        }
        
        if(mousePressed)
        {
        	drawNotification("mouse ", g);
        }
        
        if(start_drawing == true)
        {
        	g.setColor(Color.RED);
        	g.drawRect(x_init, y_init, x_final, y_final);
        }
    }
    
    private void drawNotification(String text, Graphics g)
    {
    	g.setColor(Color.RED);
    	g.drawString(text + key + " pressed", 20, 20);
    }

    private void drawButton(Graphics g) {

        g.drawImage(ant.GetImage(), ant.x, ant.y, this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        int y = 700;
        if (count % 4 == 0) {



                ant = new Ant((int) (Math.random() * 700), 700, 64, 64, imageFrames, button_pre.getImage());

                ants.add(ant);
                //ants.get(i).move();
            for (int i = 0; i < ants.size(); i++) {
                ants.get(i).move();

            }


            System.out.println(score);
            Toolkit.getDefaultToolkit().sync();
            repaint();
        }
    }
    
    public void IsClicked(int x, int y)
    {
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).IsClicked(x, y);
            if(ant.returnAlive()==false){
                score++;
                ants.remove(ant);
            }
        }

    }
    public void IsNear(int x,int y){
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).IsNear(x,y);

        }

    }
    

	@Override
	public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        IsClicked(e.getX(),e.getY());
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x_init = e.getX();
		y_init = e.getY();
		mousePressed = true;
		start_drawing = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePressed = false;
		//start_drawing = false;
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
		// TODO Auto-generated method stub
		x_final = e.getX() - x_init;
		y_final = e.getY() - y_init;
//        IsNer(e.getX(),e.getY());a
	}
}