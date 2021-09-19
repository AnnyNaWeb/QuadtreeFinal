package Quadtree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Boid.Boid;

public class Quadtree {
	// Set<No<P>> nos;
	final int maxLen = 5;

	ArrayList<Boid> boids;
	ArrayList<Boid> boidsSaindo;
	Quadtree noroesteTree;
	Quadtree nordesteTree;
	Quadtree sudoesteTree;
	Quadtree sudesteTree;
	Quadtree pai;

	int minX, maxX;
	int minY, maxY;
	int index;
	boolean dividido;

	public Quadtree(Quadtree pai, int index, int minX, int maxX, int minY, int maxY) {
		this.pai = pai;
		this.index = index;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;

		this.noroesteTree = null; // top left
		this.nordesteTree = null; // topright
		this.sudoesteTree = null; // bottomleft
		this.sudesteTree = null; // bottomright

		dividido = false;
		boids = new ArrayList<>();
		boidsSaindo = new ArrayList<>();
		// nos = new HashSet<>();

	}

	private String getIndex() {

		if (pai != null) {
			return pai.index + "/" + index;
		} else {
			return "" + index;
		}

	}

	public void CheckFora(Quadtree A, Quadtree B, Quadtree C) {

		for (int i = boids.size() - 1; i > -1; i--) {
			if (!estaDentro(boids.get(i))) {

				Boid b = boids.get(i);
				boids.remove(b);
				// System.out.println(index + " removed " + b.index);

				if (A.insert(b)) {
					continue;
				}

				if (B.insert(b)) {
					continue;
				}

				if (C.insert(b)) {
					continue;
				}

				if (pai != null) {
					// System.out.println(getIndex() + " let to father: " + b.index);
					pai.boidsSaindo.add(b);
				}
			}
		}
	}

	private void CalcularColisao() {
		if (!dividido) {

			for (int i = 0; i < boids.size(); i++) {
				for (int j = i + 1; j < boids.size(); j++) {
					if (boids.get(i).ChecarColisao(boids.get(j))) {
						break;
					}
				}
			}

		}
	}

	public void Update() {
		if (!dividido) {

			for (int i = 0; i < boids.size(); i++) {
				boids.get(i).Move();
			}
			CalcularColisao();

		} else {
			boidsSaindo.clear();

			noroesteTree.Update();
			noroesteTree.CheckFora(nordesteTree, sudoesteTree, sudesteTree);

			nordesteTree.Update();
			nordesteTree.CheckFora(noroesteTree, sudoesteTree, sudesteTree);

			sudoesteTree.Update();
			sudoesteTree.CheckFora(noroesteTree, nordesteTree, sudesteTree);

			sudesteTree.Update();
			sudesteTree.CheckFora(noroesteTree, nordesteTree, sudoesteTree);

			dividido = noroesteTree.TemBoid() || nordesteTree.TemBoid() || sudoesteTree.TemBoid()
					|| sudesteTree.TemBoid();

			if (pai != null) {
				for (int i = 0; i < boidsSaindo.size(); i++) {
					// System.out.println(getIndex() + " asking for father to " +
					// boidsSaindo.get(i).index);
					if (!pai.insert(boidsSaindo.get(i))) {
						// System.out.println(getIndex() + "'s father did not help with " +
						// boidsSaindo.get(i).index);

						pai.UltimaChance(boidsSaindo.get(i));
					}
				}
			}
		}
	}

	private void UltimaChance(Boid boid) {
		if (pai != null) {
			if (!pai.insert(boid)) {
				pai.UltimaChance(boid);
			}

		} else {
			System.out.println(getIndex() + "sem pai");
		}
	}

	public void Draw(Graphics2D g2d) {

		g2d.setColor(Color.red);

		g2d.drawRect(getX(), getY(), getWidth(), getHeight());

		if (!dividido) {
			// System.out.println("Drawing until: " + cont);

			for (int i = 0; i < boids.size(); i++) {

				boids.get(i).getBoidDraw().Draw(g2d);
			}
		} else {
			noroesteTree.Draw(g2d);
			nordesteTree.Draw(g2d);
			sudoesteTree.Draw(g2d);
			sudesteTree.Draw(g2d);
		}

	}

	public boolean TemBoid() {
		return boids.size() > 0 || dividido;
	}

	private boolean estaDentro(Boid boid) {
		return (boid.getX() >= minX && boid.getX() <= maxX) && (boid.getY() >= minY && boid.getY() <= maxY);
	}

	public boolean insert(Boid boid) {

		if (!estaDentro(boid)) {
			// System.out.println(getIndex() + " is small to: " + boid.index);
			return false;
		}
		if (!dividido) {
			if (boids.size() < maxLen) {
				boids.add(boid);
				// System.out.println(getIndex() + " Added " + boid.index + " with " +
				// boids.size());
			} else {
				subdivision(boid);
			}
			return true;
		} else {

			// System.out.println(getIndex() + " testing children");
			if (noroesteTree.insert(boid)) {
				return true;
			}

			if (nordesteTree.insert(boid)) {
				return true;
			}

			if (sudoesteTree.insert(boid)) {
				return true;
			}

			if (sudesteTree.insert(boid)) {
				return true;
			}

			System.out.println(getIndex() + " Could not add: " + boid.index);
			return false;
		}
	}

	public void subdivision(Boid boid) {
		// System.out.println(getIndex() + " Is Subdivision.");

		dividido = true;
		int width = getWidth() / 2;
		int height = getHeight() / 2;

		this.noroesteTree = new Quadtree(this, 1, minX, minX + width, minY, minY + height); // top left
		this.nordesteTree = new Quadtree(this, 2, minX + width, maxX, minY, minY + height); // topright

		this.sudoesteTree = new Quadtree(this, 3, minX, minX + width, minY + height, maxY); // bottomleft
		this.sudesteTree = new Quadtree(this, 4, minX + width, maxX, minY + height, maxY); // bottomright

		boids.add(boid);

		for (int i = 0; i < boids.size(); i++) {
			if (noroesteTree.insert(boids.get(i))) {
				continue;
			}
			if (nordesteTree.insert(boids.get(i))) {
				continue;
			}
			if (sudoesteTree.insert(boids.get(i))) {
				continue;
			}
			if (sudesteTree.insert(boids.get(i))) {
				continue;
			}

			System.out.println(getIndex() + " lefted over:" + boids.get(i).index);
		}
		boids.clear();

	}

	public int getX() {
		return minX;
	}

	public int getY() {
		return minY;
	}

	public int getWidth() {
		return maxX - minX;
	}

	public int getHeight() {
		return maxY - minY;
	}

}
