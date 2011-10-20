package eu.stefaner.voitree;

import toxi.geom.Vec2D;
import toxi.geom.mesh2d.DelaunayTriangle;
import toxi.geom.mesh2d.DelaunayVertex;
import toxi.geom.mesh2d.Voronoi;

import java.util.ArrayList;
import java.util.HashMap;

public class WeightedVoronoi extends Voronoi {

	private HashMap<Vec2D, DelaunayVertex> vertexForPoint = new HashMap<Vec2D, DelaunayVertex>();
	private HashMap<DelaunayVertex, Vec2D> pointForVertex = new HashMap<DelaunayVertex, Vec2D>();

	public ArrayList<Vec2D> getNeighbors(Vec2D center) {

		DelaunayVertex centerVertex = vertexForPoint.get(center);

		ArrayList<Vec2D> result = new ArrayList<Vec2D>();

		for (DelaunayTriangle triangle : delaunay) {
			if (triangle.contains(centerVertex)) {
				for (DelaunayVertex nb : triangle) {
					if (nb != centerVertex && !result.contains(pointForVertex.get(nb))) {
						result.add(pointForVertex.get(nb));
					}
				}
			}
		}

		return result;
	}

	public Vec2D addAndReturnPoint(Vec2D p) {
		DelaunayVertex dv = new DelaunayVertex(p.x, p.y);
		delaunay.delaunayPlace(dv);
		
		vertexForPoint.put(p, dv);
		pointForVertex.put(dv, p);
		
		p = p.copy();
		sites.add(p);
		
		return p;
	}
}
