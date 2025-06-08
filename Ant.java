import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Arrays;

public class Ant implements ActionListener {
	public int x;
	public int y;
	private int width;
	private int height;
	private int Distance;
	private Image image_pressed;
	private boolean nearEnough;
	private Image current_image;
	private boolean alive =true;

	Timer timer;

	ImageIcon[] images;
	int index;
	public boolean returnAlive(){
		return alive;
	}
	public void setX(int x){
		this.x=x;

	}
	public void setY(int y){
		this.y=y;
	}

	public Ant(int x, int y, int width, int height, ImageIcon[] imageIcons, Image i_pressed) {
		timer = new Timer(30, this);
		timer.start();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.images = imageIcons;
		image_pressed = i_pressed;
		System.out.println(Arrays.toString(imageIcons));
		current_image = imageIcons[0].getImage();
	}

	public Image GetImage() {
		return current_image;
	}

	public Boolean IsPressed() {
		return alive;
	}

	public void SetPressed(boolean pressed) {
		this.alive = pressed;
	}

	public boolean IsClicked(int x, int y) {
		if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
			alive = false;
			current_image = image_pressed;
			return true;
		}

		return false;
	}
	public void move(){
		if(alive==true){
			this.y=this.y-20;
		}

	}

	public void IsNear(int x, int y) {
		if(alive) {
			int dX = (this.x + width / 2) - x;
			int dY = (this.y + height / 2) - y;
			if (dX > 0) {
				this.x++;
			} else if (dX < 0) {
				this.x--;
			}
			if (dY > 0) {
				this.y++;
			} else if (dY < 0) {
				this.y--;
			}


			Distance = (int) Math.sqrt((Math.pow((dX), 2)) + (Math.pow((dY), 2)));
			System.out.println(Distance);


			if (Distance < 100) {
				nearEnough = true;
				this.x = this.x + 2;
				this.y = this.y + 2;
				return;
			}
		}
	}

		public void paint (Graphics g, ImageObserver observer){
			g.drawImage(this.current_image, this.x, this.y, observer);


		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (alive) {
			current_image = images[index].getImage();
			index++;
			if (index >= images.length) {
				index = 0;
			}
		}
	}
}

