package Quadtree;

import java.awt.Graphics2D;

public class QuadtreeDraw {

	Quadtree quadtree;
	long start;
	long finish;
	long timeElapsed;
	public boolean run = false;

	public QuadtreeDraw(Quadtree quadtree) {
		this.quadtree = quadtree;
		timeElapsed = 0;
	}

	public void Draw(Graphics2D g2d) {

		if (run) {
			start = System.nanoTime();
			quadtree.Update();
			finish = System.nanoTime();
			timeElapsed = finish - start;
			System.out.println(timeElapsed);
			quadtree.Draw(g2d);
		} else {
			quadtree.Draw(g2d);
		}

	}

}
