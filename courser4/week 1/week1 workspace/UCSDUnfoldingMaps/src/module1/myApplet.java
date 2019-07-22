package module1;
import processing.core.PApplet;
import processing.core.PImage;
public class myApplet extends PApplet{
	private String URL = "palmTrees.jpg";
	private PImage backgroundImg;
	public void setup() {
		size(400,400);
		background(250,250,250);
		backgroundImg = loadImage("palmTrees.jpg","jpg");
		
	}
	public void draw() {
		backgroundImg.resize(0,height);
		image(backgroundImg,0,0);
		int[] color = getcolor(second());
		fill(color[0],color[1],color[2]);
		ellipse(50,50,width/5,height/5);
	}
	public int[] getcolor(float seconds) {
		int[] color = new int[3];
		float diff = Math.abs(30-seconds);
		float ratio = diff/30;
		color[0]=(int)(255*ratio);
		color[1]=(int)(255*ratio);
		color[2]=(int)(0);
		return color;
	}
}
