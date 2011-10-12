package eu.stefaner.voitree;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Polygon2D;
import toxi.geom.PolygonClipper2D;
import toxi.geom.Rect;
import toxi.geom.SutherlandHodgemanClipper;
import toxi.geom.Vec2D;
import toxi.processing.ToxiclibsSupport;

public class Main extends PApplet {

	private ToxiclibsSupport gfx;
	private WeightedVoronoi voronoi;
	private PolygonClipper2D clip;
	private int numItems = 100;
	private int fullArea;
	private Cell[] cells;
	private float FORCE = 0;

	public void setup() {

		size(1200, 800);

		colorMode(HSB);

		gfx = new ToxiclibsSupport(this);
		cells = new Cell[numItems];
		float weightsLeft = 1f;

		for (int i = 0; i < numItems; i++) {
			float randomWeight;
			if (i < numItems - 1) {
				randomWeight = random(.3f) * weightsLeft;
				weightsLeft -= randomWeight;
			} else {
				randomWeight = weightsLeft;
			}

			cells[i] = new Cell(new Vec2D( random(width), height - height * ((float) (randomWeight))), randomWeight);
			cells[i].targetArea = randomWeight * width * height;
		}

		clip = new SutherlandHodgemanClipper(new Rect(0, 0, width, height));
		voronoi = new WeightedVoronoi();
	}

	public void draw() {

		float factor = .2f;

		for (Cell c : cells) {
			for (Polygon2D p : voronoi.getRegions()) {
				if (p.containsPoint(c.point)) {
					c.poly = clip.clipPolygon(p);
					c.centroid = c.poly.getCentroid();
					break;
				}
			}

			c.point.x += (c.centroid.x - c.point.x) * factor;
			c.point.y += (c.centroid.y - c.point.y) * factor;

			float pad = 10;
			c.point.x = Math.max(pad, c.point.x);
			c.point.x = Math.min(c.point.x, width - pad);
			c.point.y = Math.max(pad, c.point.y);
			c.point.y = Math.min(c.point.y, height - pad);

		}

		voronoi = new WeightedVoronoi();
		for (Cell c : cells) {
			try {
				c.point = voronoi.addAndReturnPoint(c.point);
			} catch (Exception e) {
				println("error adding point");
			}

		}

		for (Cell c : cells) {
			try {
				for (Polygon2D p : voronoi.getRegions()) {
					if (p.containsPoint(c.point)) {
						c.poly = clip.clipPolygon(p);
						c.centroid = c.poly.getCentroid();
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			c.area = Math.abs(c.poly.getArea());
		}

		for (Cell c : cells) {

			double force = Math.sqrt(c.targetArea / c.area);
			ArrayList<Vec2D> nb = voronoi.getNeighbors(c.point);

			for (Vec2D p2 : nb) {
				if (p2 == null)
					continue;

				for (Cell c2 : cells) {
					if (p2.distanceTo(c2.point) < 1) {
						Vec2D diffVec = c2.point.sub(c.centroid);
						diffVec.normalizeTo((float) Math.sqrt(c.targetArea / TWO_PI) + (float) Math.sqrt(c2.targetArea / TWO_PI));

						c2.point.x += (float) (c.centroid.x + diffVec.x * force - c2.point.x) * FORCE * force;
						c2.point.y += (float) (c.centroid.y + diffVec.y * force - c2.point.y) * FORCE * force;

						//c2.point.x += (float) (c.centroid.x + (c2.point.x - c.centroid.x) * force - c2.point.x) * FORCE;
						//c2.point.y += (float) (c.centroid.y + (c2.point.y - c.centroid.y) * force - c2.point.y) * FORCE;
						break;
					}
				}
			}

		}

		FORCE += .01;
		FORCE = Math.min(FORCE, .1f);

		background(0);
		fill(0);
		smooth();

		for (Cell c : cells) {

			strokeWeight(1);
			stroke(255, 100);
			fill(map((float) Math.sqrt(c.targetArea / c.area), 0, 3, 200, 300), 200, (int) (100 * Math.abs(1 - Math.sqrt(c.targetArea / c.area))), 128);
			gfx.polygon2D(c.poly);

			stroke(255, 128);
			strokeWeight(1);
			ellipse(c.centroid.x, c.centroid.y, (float) (Math.sqrt(c.weight) * 80), (float) (Math.sqrt(c.weight) * 80));

			stroke(255, 50);
			strokeWeight(5);
			point(c.point.x, c.point.y);
		}

	}
}
