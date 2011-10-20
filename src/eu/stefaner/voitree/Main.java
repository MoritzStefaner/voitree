package eu.stefaner.voitree;

import processing.core.PApplet;
import toxi.geom.*;
import toxi.processing.ToxiclibsSupport;

public class Main extends PApplet {

	private static final float CENTROID_ATTR = 0.2f;
	private static final float DIST_STEEPNESS = .2f;
	private static final float FORCE_DAMP = .5f;
	private ToxiclibsSupport gfx;
	private WeightedVoronoi voronoi;
	private PolygonClipper2D clip;
	private int numItems = 50;
	private Cell[] cells;
	private float scaleFactor = 0;
	private float maxTargetRadius;

	public void setup() {

		size(1200, 800);

		colorMode(HSB);

		gfx = new ToxiclibsSupport(this);
		cells = new Cell[numItems];
		float weightsLeft = 1f;

		maxTargetRadius = 0;

		for (int i = 0; i < numItems; i++) {
			float randomWeight;
			if (i < numItems - 1) {
				randomWeight = random(DIST_STEEPNESS) * weightsLeft;
				weightsLeft -= randomWeight;
			} else {
				randomWeight = weightsLeft;
			}

			cells[i] = new Cell(new Vec2D(random(width), height * (1 - randomWeight/DIST_STEEPNESS)), randomWeight);
			cells[i].targetArea = randomWeight * width * height;
			cells[i].targetRadius = (float) Math.sqrt(cells[i].targetArea / PI);

			maxTargetRadius = Math.max(maxTargetRadius, cells[i].targetRadius);
		}

		clip = new SutherlandHodgemanClipper(new Rect(0, 0, width, height));
		voronoi = new WeightedVoronoi();
	}

	public void draw() {
		scaleFactor += .001f;
		scaleFactor = Math.min(9f, scaleFactor);

		for (Cell c : cells) {
			c.radius = (float) Math.min(c.targetRadius, Math.sqrt(scaleFactor) * maxTargetRadius);

			float pad = c.radius * c.radiusBoost;
			c.point.x = Math.max(pad, c.point.x);
			c.point.x = Math.min(c.point.x, width - pad);
			c.point.y = Math.max(pad, c.point.y);
			c.point.y = Math.min(c.point.y, height - pad);
		}

		for (Cell c : cells) {
			for (Cell c2 : cells) {
				if (c == c2)
					continue;

				Vec2D diffVec = new Vec2D(c2.point.x - c.point.x, c2.point.y - c.point.y);
				float minDistance = c.radius * c.radiusBoost + c2.radius * c2.radiusBoost;
				float delta = diffVec.magnitude() - minDistance;

				if (delta > 0) {
					continue;
				}

				diffVec.normalize();

				float ratio = c.radius * c.radiusBoost / minDistance;

				c2.point.x -= ratio * delta * diffVec.x * FORCE_DAMP;
				c2.point.y -= ratio * delta * diffVec.y * FORCE_DAMP;
				c.point.x += (1 - ratio) * delta * diffVec.x * FORCE_DAMP;
				c.point.y += (1 - ratio) * delta * diffVec.y *FORCE_DAMP;
			}
		}

		voronoi = new WeightedVoronoi();
		for (Cell c : cells) {
			voronoi.addPoint(c.point);
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

			c.point.x += (c.centroid.x - c.point.x) * CENTROID_ATTR;
			c.point.y += (c.centroid.y - c.point.y) * CENTROID_ATTR;

			float error = (float) Math.log(c.targetArea / c.area);
			c.radiusBoost += error * .001;
		}

		background(255);
		fill(0);
		smooth();

		for (Cell c : cells) {

			strokeWeight(1);
			stroke(0, 120);
			float error = (float) Math.log(c.targetArea / c.area);

			fill(map((float) error, -5, 5, 0, 128), 10 * Math.abs(error), 255 - Math.abs(error) * 10);
			gfx.polygon2D(c.poly);

		}
		for (Cell c : cells) {
			stroke(0, 60);
			strokeWeight(1);
			noFill();
			ellipse(c.point.x, c.point.y, c.radius * 2f, c.radius * 2f);

			stroke(0, 30);
			strokeWeight(1);
			noFill();
			ellipse(c.point.x, c.point.y, c.radius * 2f * c.radiusBoost, c.radius * 2f * c.radiusBoost);

			stroke(0, 50);
			strokeWeight(5);
			point(c.point.x, c.point.y);

			fill(120,20,90, 50);
			text("" + Math.floor(100 * c.area / c.targetArea) / 100f, c.point.x, c.point.y);
		}

	}
}
