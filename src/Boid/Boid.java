package Boid;

import java.awt.Color;

///https://examples.javacodegeeks.com/desktop-java/awt/draw-shapes-example/
public class Boid {

	BoidDraw boidDraw;

	private float X;
	private int W;

	private float Y;
	private int H;

	private float veloPadrao = 0.05f;
	private float veloX;
	private float veloY;

	public int index;

	public Boid(Color color, int index) {
		this.index = index;
		W = H = 5;

		int margem = 5;

		X = (index % 100) * (W + margem);
		Y = (index / 100) * (H + margem);

		boidDraw = new BoidDraw(this, color);
		veloX = veloPadrao;
		veloY = -veloPadrao;
	}

	public void setPos(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public int getX() {
		return (int) X;
	}

	public int getY() {
		return (int) Y;
	}

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}

	public BoidDraw getBoidDraw() {
		return boidDraw;
	}

	public void Move() {

		if (X > Draw.Draw.frameWidth) {
			veloX = -veloPadrao;
		} else if (X <= 0) {
			veloX = veloPadrao;
		}

		if (Y > Draw.Draw.frameHeight) {
			veloY = -veloPadrao;
		} else if (Y <= 0) {
			veloY = veloPadrao;
		}

		X += veloX;
		Y += veloY;

		// System.out.println(index + ": " + X + "/" + veloX + ";" + Y + "/" + veloY);
	}

	private void Inverter() {
		veloY = -veloY;
		veloX = -veloX;
		X += veloX;
		Y += veloY;
	}

	public boolean ChecarColisao(Boid boid) {
		if (colisao(boid)) {
			Inverter();
			boid.Inverter();

			X += (veloX / veloX) * W;
			Y += (veloY / veloY) * H;
			return true;

		}

		return false;
	}

	private boolean colisao(Boid boid) {
		// System.out.println("I " + boid.index + "/I " + index);
		// System.out.println("I " + boid.X + "/I " + boid.Y);
		// System.out.println("I " + X + "/I " + Y);
		return (Math.abs(boid.X - X) < W) && (Math.abs(boid.Y - Y) < H);
	}

}