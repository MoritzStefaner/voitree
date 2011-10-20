/**
 * 
 */
package wblut.geom2D;

import java.util.ArrayList;

import wblut.math.WB_Epsilon;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Triangulation2D {
	/**
	 * Planar Delaunay triangulation.
	 *
	 * @param points 
	 * @param numPoints 
	 * @return 2D array of WB_Triangle2D
	 */
	public static WB_Triangle2D[] triangulate(final WB_XY[] points,
			final int numPoints) {

		final int nv = points.length;
		if (nv < 3) {
			return null;
		}
		if (nv == 3) {
			final WB_Triangle2D[] result = new WB_Triangle2D[1];
			result[0] = new WB_Triangle2D(points[0], points[1], points[2]);
			return result;
		}

		final WB_XY[] lpoints = new WB_XY[nv + 3];

		for (int i = 0; i < nv; i++) {
			lpoints[i] = new WB_XY(points[i].x, points[i].y);
		}

		final ArrayList<WB_IndexedTriangle2D> triangles = new ArrayList<WB_IndexedTriangle2D>();
		final ArrayList<WB_Circle> circumCircles = new ArrayList<WB_Circle>();

		final WB_XY vc = new WB_XY();
		for (int i = 0; i < nv; i++) {
			vc.add(lpoints[i]);
		}
		vc.div(nv);
		double dmax = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < nv; i++) {
			dmax = Math.max(dmax, WB_Distance2D.sqDistance(lpoints[i], vc));
		}
		dmax = Math.sqrt(dmax);
		if (WB_Epsilon.isZero(dmax)) {
			return null;
		}
		final WB_XY jitter = new WB_XY();

		for (int i = 0; i < nv; i++) {
			lpoints[i].sub(vc);
			jitter.set(Math.random() - 0.5, Math.random() - 0.5);
			jitter.normalize();
			jitter.mult(WB_Epsilon.JITTER_EPSILON);
			lpoints[i].add(jitter);
		}
		WB_XY u = lpoints[0].get();
		int trial = 0;
		while (WB_Epsilon.isZeroSq(u.mag2())) {
			trial++;
			u = lpoints[trial].get();
		}

		u.normalize();
		lpoints[nv] = u.get().mult(3 * dmax);
		lpoints[nv + 1] = lpoints[nv].get();
		final double c = Math.cos(Math.PI / 1.5);
		final double s = Math.sin(Math.PI / 1.5);
		lpoints[nv + 1] = new WB_XY(lpoints[nv].x * c - lpoints[nv].y * s,
				lpoints[nv].x * s + lpoints[nv].y * c);
		lpoints[nv + 2] = new WB_XY(lpoints[nv + 1].x * c - lpoints[nv + 1].y
				* s, lpoints[nv + 1].x * s + lpoints[nv + 1].y * c);

		final WB_IndexedTriangle2D envelop = new WB_IndexedTriangle2D(nv,
				nv + 1, nv + 2, lpoints);

		triangles.add(envelop);
		circumCircles.add(envelop.getCircumcircle());
		for (int i = 0; i < nv; i++) {

			final ArrayList<WB_IndexedSegment2D> edges = new ArrayList<WB_IndexedSegment2D>();
			final ArrayList<WB_IndexedTriangle2D> trianglesToRemove = new ArrayList<WB_IndexedTriangle2D>();
			final ArrayList<WB_Circle> circlesToRemove = new ArrayList<WB_Circle>();
			for (int j = 0; j < triangles.size(); j++) {
				final WB_IndexedTriangle2D tj = triangles.get(j);
				final WB_Circle cj = circumCircles.get(j);
				final double rad = cj.getRadius() + WB_Epsilon.EPSILON;
				if (lpoints[i].subAndCopy(cj.getCenter()).mag2() < rad * rad) {
					edges.add(new WB_IndexedSegment2D(tj.i1, tj.i2, lpoints));
					edges.add(new WB_IndexedSegment2D(tj.i2, tj.i3, lpoints));
					edges.add(new WB_IndexedSegment2D(tj.i3, tj.i1, lpoints));
					circlesToRemove.add(cj);
					trianglesToRemove.add(tj);
				}
			}
			final ArrayList<WB_IndexedSegment2D> duplicateEdges = new ArrayList<WB_IndexedSegment2D>();
			for (int j = 0; j < edges.size(); j++) {
				final WB_IndexedSegment2D ej = edges.get(j);
				for (int k = j + 1; k < edges.size(); k++) {
					final WB_IndexedSegment2D ek = edges.get(k);
					if ((ej.i1() == ek.i2()) && (ej.i2() == ek.i1())) {
						if (!duplicateEdges.contains(ej)) {
							duplicateEdges.add(ej);
						}
						if (!duplicateEdges.contains(ek)) {
							duplicateEdges.add(ek);
						}
					}
					if ((ej.i1() == ek.i1()) && (ej.i2() == ek.i2())) {
						if (!duplicateEdges.contains(ej)) {
							duplicateEdges.add(ej);
						}
						if (!duplicateEdges.contains(ek)) {
							duplicateEdges.add(ek);
						}
					}
				}
			}
			edges.removeAll(duplicateEdges);

			for (int j = 0; j < edges.size(); j++) {
				final WB_IndexedSegment2D ej = edges.get(j);

				final WB_IndexedTriangle2D it = new WB_IndexedTriangle2D(ej
						.i1(), ej.i2(), i, lpoints);
				triangles.add(it);
				circumCircles.add(it.getCircumcircle());

			}
			triangles.removeAll(trianglesToRemove);
			circumCircles.removeAll(circlesToRemove);
		}

		final ArrayList<WB_IndexedTriangle2D> trianglesToRemove = new ArrayList<WB_IndexedTriangle2D>();
		for (int i = 0; i < triangles.size(); i++) {
			final WB_IndexedTriangle2D ti = triangles.get(i);
			if ((ti.i1 >= nv) || (ti.i2 >= nv) || (ti.i3 >= nv)
					|| (ti.degenerate)) {
				trianglesToRemove.add(ti);
			}
		}
		triangles.removeAll(trianglesToRemove);

		final WB_Triangle2D[] result = new WB_Triangle2D[triangles.size()];

		for (int i = 0; i < triangles.size(); i++) {
			result[i] = new WB_Triangle2D(points[triangles.get(i).i1],
					points[triangles.get(i).i2], points[triangles.get(i).i3]);
		}
		return result;
	}

}
