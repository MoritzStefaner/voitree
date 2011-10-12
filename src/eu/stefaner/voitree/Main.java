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
		float sumWeights = 0;
		for (int i = 0; i < numItems; i++) {
			float randomWeight;
			if (i < numItems - 1) {
				randomWeight = random(.3f) * weightsLeft;
				weightsLeft -= randomWeight;
			} else {
				randomWeight = weightsLeft;
			}

			cells[i] = new Cell(new Vec2D(random(width), height - height * ((float) (randomWeight))), randomWeight);
			cells[i].targetArea = randomWeight * width * height;
			cells[i].targetRadius = (float) Math.sqrt(cells[i].targetArea / PI);
		}

		clip = new SutherlandHodgemanClipper(new Rect(0, 0, width, height));
		voronoi = new WeightedVoronoi();
	}

	public void draw() {

		float factor = 0.01f;

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

			float pad = c.targetRadius * .5f;
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
			for (Cell c2 : cells) {
				if (c == c2)
					continue;

				Vec2D diffVec = new Vec2D(c2.centroid.x - c.centroid.x, c2.centroid.y - c.centroid.y);

				if (diffVec.magnitude() > c.targetRadius + c2.targetRadius) {
					continue;
				}

				diffVec.normalizeTo(c.targetRadius + c2.targetRadius);

				c2.point.x += (float) (c.point.x + diffVec.x - c2.point.x) * FORCE * (c.targetRadius / (c.targetRadius + c2.targetRadius));
				c2.point.y += (float) (c.point.y + diffVec.y - c2.point.y) * FORCE * (c.targetRadius / (c.targetRadius + c2.targetRadius));

				break;
			}
		}

		FORCE += .1;
		FORCE = Math.min(FORCE, .6f);

		background(0);
		fill(0);
		smooth();

		for (Cell c : cells) {

			strokeWeight(1);
			stroke(255, 100);
			fill(map((float) Math.sqrt(c.targetArea / c.area), 0, 3, 200, 300), 200, (int) (100 * Math.abs(1 - Math.sqrt(c.targetArea / c.area))), 128);
			gfx.polygon2D(c.poly);

			stroke(255, 50);
			strokeWeight(1);
			noFill();
			ellipse(c.centroid.x, c.centroid.y, c.targetRadius * 2f, c.targetRadius * 2f);

			stroke(255, 50);
			strokeWeight(5);
			point(c.point.x, c.point.y);
		}

	}
}
