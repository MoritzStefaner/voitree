package eu.stefaner.voitree;

import toxi.geom.Polygon2D;
import toxi.geom.Vec2D;

public class Cell {

	public Vec2D point = new Vec2D();
	public float weight = 0f;
	public Polygon2D poly = new Polygon2D();
	public Vec2D centroid = new Vec2D();
	public float area = 0f;
	public float targetArea = 0f;
	public float targetRadius = 0f;
	public float radiusBoost = 1f;
	public float radius = 0f; 

	public Cell(Vec2D p, float weight) {
		this.point = p;
		this.centroid = p.copy();
		this.weight = weight;
	}

}
