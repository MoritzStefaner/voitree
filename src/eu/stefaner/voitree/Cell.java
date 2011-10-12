package eu.stefaner.voitree;

import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

public class Cell {

	public Vec2D point;
	public float weight;
	public Polygon2D poly;
	public Vec2D centroid;
	public float area;
	public float targetArea;

	public Cell(Vec2D p, float weight) {
		this.point = p;
		this.centroid = p.copy();
		this.weight = weight;
	}

}
