package Draw;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import Quadtree.QuadtreeDraw;

///https://examples.javacodegeeks.com/desktop-java/awt/draw-shapes-example/
public class Draw extends Component {

	public static final int frameWidth = 640 * 1;
	public static final int frameHeight = 480 * 1;

	QuadtreeDraw quadrQuadtreeDraw;

	public Draw(QuadtreeDraw quadrQuadtreeDraw) {
		this.quadrQuadtreeDraw = quadrQuadtreeDraw;
		JFrame frame = new JFrame();
		frame.setSize((int) (frameWidth * 1.1f), (int) (frameHeight + (frameWidth * 0.1f)));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				quadrQuadtreeDraw.run = !quadrQuadtreeDraw.run;
			}
		});
	}

	/**
	 * To draw on the screen, it is first necessary to subclass a Component and
	 * override its paint() method. The paint() method is automatically called by
	 * the windowing system whenever component's area needs to be repainted.
	 */
	public void paint(Graphics g) {

		// Retrieve the graphics context; this object is used to paint shapes

		Graphics2D g2d = (Graphics2D) g;

		// Draw an oval that fills the window

		/**
		 * The coordinate system of a graphics context is such that the origin is at the
		 * northwest corner and x-axis increases toward the right while the y-axis
		 * increases toward the bottom.
		 */

		quadrQuadtreeDraw.Draw(g2d);
		repaint(10000, 0, 0, (int) (frameWidth * 1.1f), (int) (frameHeight + (frameWidth * 0.1f)));

	}
}
