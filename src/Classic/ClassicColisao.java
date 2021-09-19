package Classic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import Boid.Boid;

public class ClassicColisao extends Component {
	ArrayList<Boid> boids;
	long start;
	long finish;
	long timeElapsed;

	boolean run;

	public ClassicColisao() {
		JFrame frame = new JFrame();
		frame.setSize((int) (Draw.Draw.frameWidth * 1.1f),
				(int) (Draw.Draw.frameHeight + (Draw.Draw.frameWidth * 0.1f)));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		run = false;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				run = !run;
			}
		});

		boids = new ArrayList<>();
	}

	public void insert(Boid boid) {

		boids.add(boid);

	}

	private void Draw(Graphics2D g2d) {

		g2d.setColor(Color.red);
		g2d.drawRect(0, 0, Draw.Draw.frameWidth, Draw.Draw.frameHeight);

		for (int i = 0; i < boids.size(); i++) {

			boids.get(i).getBoidDraw().Draw(g2d);
		}
	}

	private void Update() {
		for (int i = 0; i < boids.size(); i++) {
			boids.get(i).Move();
			for (int j = i + 1; j < boids.size(); j++) {
				if (boids.get(i).ChecarColisao(boids.get(j))) {
				}
			}
		}
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

		if (run) {
			start = System.nanoTime();
			Update();
			finish = System.nanoTime();
			timeElapsed = finish - start;
			System.out.println(timeElapsed);
			Draw(g2d);
		} else {
			Draw(g2d);
		}
		repaint(1000, 0, 0, (int) (Draw.Draw.frameWidth * 1.1f),
				(int) (Draw.Draw.frameHeight + (Draw.Draw.frameWidth * 0.1f)));

	}
}
