package Boid;

import java.awt.Color;
import java.awt.Graphics2D;

///https://examples.javacodegeeks.com/desktop-java/awt/draw-shapes-example/
public class BoidDraw {

	private Boid boid;
	public Color color;

	public BoidDraw(Boid boid, Color color) {
		this.boid = boid;
		this.color = color;
	}

	public void Draw(Graphics2D g2d) {
		// System.out.print(boid.getX() + ".");
		// System.out.println(boid.getY());
		g2d.setColor(color);
		g2d.drawRect(boid.getX(), boid.getY(), boid.getW(), boid.getH());
	}

}
