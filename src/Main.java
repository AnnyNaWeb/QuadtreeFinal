import java.awt.Color;

import Boid.Boid;
import Classic.ClassicColisao;
import Quadtree.Quadtree;
import Quadtree.QuadtreeDraw;

public class Main {

	static final Color[] colors = new Color[] { Color.black, Color.red, Color.green, Color.blue, Color.cyan };
	public static final int frameWidth = 640 * 2;
	public static final int frameHeight = 480 * 2;

	public static void main(String[] args) {

		boolean isQuadtree = true;
		int quant = 3000;

		if (isQuadtree) {
			Quadtree quadtree = new Quadtree(null, 0, 0, frameWidth, 0, frameHeight);
			QuadtreeDraw quadtreeDraw = new QuadtreeDraw(quadtree);
			Draw.Draw draw = new Draw.Draw(quadtreeDraw);
			for (int i = 0; i < quant; i++) {

				quadtree.insert(new Boid(colors[i % colors.length], i + 1));

			}
		} else {
			ClassicColisao classic = new ClassicColisao();

			for (int i = 0; i < quant; i++) {

				classic.insert(new Boid(colors[i % colors.length], i + 1));

			}
		}

	}
}
