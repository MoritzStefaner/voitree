/*
 * Copyright (c) 2010, Frederik Vanhoutte This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * http://creativecommons.org/licenses/LGPL/2.1/ This library is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */
package wblut.geom2D;

import java.util.ArrayList;

import javolution.util.FastList;
import javolution.util.FastMap;
import wblut.geom.WB_Point;
import wblut.geom.WB_ExplicitPolygon;
import wblut.math.WB_Epsilon;
import wblut.tree.WB_BSPTree2D;
import wblut.tree.WB_KDNeighbor2D;
import wblut.tree.WB_KDTree2D;

// TODO: Auto-generated Javadoc
/**
 * Planar polygon class.
 */
public class WB_Polygon2D {

	/** Ordered array of WB_Point. */
	public WB_XY[]	points;

	/** Number of points. */
	public int		n;

	/**
	 * Instantiates a new WB_Polygon.
	 */
	public WB_Polygon2D() {
		points = new WB_XY[0];
		n = 0;
	}

	/**
	 * Instantiates a new WB_Polygon.
	 *
	 * @param points array of WB_Point, no copies are made
	 * @param n number of points
	 */
	public WB_Polygon2D(final WB_XY[] points, final int n) {
		this.points = points;
		this.n = n;
	}

	/**
	 * Instantiates a new WB_Polygon.
	 *
	 * @param points array of WB_Point
	 * @param n number of points
	 * @param copy copy points?
	 */
	public WB_Polygon2D(final WB_XY[] points, final int n, final boolean copy) {
		if (copy == false) {
			this.points = points;
		} else {
			this.points = new WB_XY[n];
			for (int i = 0; i < n; i++) {
				this.points[i] = points[i].get();
			}

		}
		this.n = n;
	}

	/**
	 * Instantiates a new WB_Polygon2D.
	 *
	 * @param points arrayList of WB_XY
	 */
	public WB_Polygon2D(final ArrayList<WB_XY> points) {
		n = points.size();
		this.points = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			this.points[i] = points.get(i);
		}
	}

	/**
	 * Set polygon.
	 *
	 * @param points array of WB_Point, no copies are made
	 * @param n number of points
	 */
	public void set(final WB_XY[] points, final int n) {
		this.points = points;
		this.n = n;
	}

	/**
	 * Set polygon.
	 *
	 * @param poly source polygon, no copies are made
	 */
	public void set(final WB_Polygon2D poly) {
		points = poly.points;
		n = poly.n;
	}

	/**
	 * Set polygon.
	 *
	 * @param points arrayList of WB_Point, no copies are made
	 * @param n number of points
	 */
	public void set(final ArrayList<WB_XY> points, final int n) {
		this.points = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			this.points[i] = points.get(i);
		}
		this.n = n;
	}

	/**
	 * Get deep copy.
	 *
	 * @return copy
	 */
	public WB_Polygon2D get() {
		final WB_XY[] newPoints = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			newPoints[i] = points[i].get();
		}
		return new WB_Polygon2D(newPoints, n);

	}

	/**
	 * Get shallow copy.
	 *
	 * @return copy
	 */
	public WB_Polygon2D getNoCopy() {
		return new WB_Polygon2D(points, n);

	}

	/**
	 * Closest point on polygon to given point.
	 *
	 * @param p point
	 * @return closest point of polygon
	 */
	public WB_XY closestPoint(final WB_XY p) {
		double d = Double.POSITIVE_INFINITY;
		int id = -1;
		for (int i = 0; i < n; i++) {
			final double cd = WB_Distance2D.sqDistance(p, points[i]);
			if (cd < d) {
				id = i;
				d = cd;
			}
		}
		return points[id];
	}

	/**
	 * Index of closest point on polygon to given point.
	 *
	 * @param p point
	 * @return index of closest point of polygon
	 */
	public int closestIndex(final WB_XY p) {
		double d = Double.POSITIVE_INFINITY;
		int id = -1;
		for (int i = 0; i < n; i++) {
			final double cd = WB_Distance2D.sqDistance(p, points[i]);
			if (cd < d) {
				id = i;
				d = cd;
			}
		}
		return id;
	}

	/**
	 * Subclass Ear for ear-clipping purposes.
	 */
	public static class Ear {

		/** The in. */
		public int	i, ip, in;

		/**
		 * Instantiates a new ear.
		 *
		 * @param i the i
		 * @param ip the ip
		 * @param in the in
		 */
		Ear(final int i, final int ip, final int in) {
			this.i = i;
			this.ip = ip;
			this.in = in;
		}
	}

	/**
	 * Checks if point at index is ear.
	 *
	 * @param i index to check
	 * @return ear or null
	 */
	public Ear isEar(final int i) {

		if (isConvex(i) == WB_VertexType2D.CONCAVE) {
			return null;
		}

		int in = i + 1;
		if (in == n) {
			in = 0;
		}

		int ip = i - 1;
		if (ip == -1) {
			ip = n - 1;
		}

		boolean isInternal = true;
		int ic = in + 1;
		if (ic == n) {
			ic = 0;
		}
		while (ic != ip) {
			if (WB_Triangle2D.pointInTriangle2D(points[ic], points[i],
					points[in], points[ip])) {
				isInternal = false;
				break;
			}
			ic++;
			if (ic == n) {
				ic = 0;
			}
		}
		return (isInternal) ? new Ear(i, ip, in) : null;
	}

	/**
	 * Find ear of polygon.
	 *
	 * @return ear or null
	 */
	public Ear findEar() {
		for (int i = 0; i < n; i++) {
			final Ear result = isEar(i);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	/**
	 * Checks if point at index is convex.
	 *
	 * @param i index
	 * @return WB.VertexType.FLAT,WB.VertexType.CONVEX,WB.VertexType.CONCAVE
	 */
	public WB_VertexType2D isConvex(final int i) {
		final WB_XY vp = points[(i == 0) ? n - 1 : i - 1].subAndCopy(points[i]);
		vp.normalize();
		final WB_XY vn = points[(i == n - 1) ? 0 : i + 1].subAndCopy(points[i]);
		vn.normalize();

		final double cross = vp.x * vn.y - vp.y * vn.x;

		if (WB_Epsilon.isZero(cross)) {
			return WB_VertexType2D.FLAT;
		} else if (Math.acos(vp.dot(vn)) < Math.PI) {
			return WB_VertexType2D.CONVEX;
		} else {
			return WB_VertexType2D.CONCAVE;
		}
	}

	/**
	 * Triangulate polygon.
	 *
	 * @return arrayList of WB_Triangle, points are not copied
	 */
	public ArrayList<WB_Triangle2D> triangulate() {
		final ArrayList<WB_Triangle2D> tris = new ArrayList<WB_Triangle2D>();

		WB_Polygon2D tmp = new WB_Polygon2D(points, n);
		if (tmp.n > 3) {
			while (tmp.n > 3) {
				final Ear ear = tmp.findEar();
				if (ear != null) {
					tris.add(new WB_Triangle2D(tmp.points[ear.ip],
							tmp.points[ear.i], tmp.points[ear.in]));
					tmp = tmp.removePoint(ear.i);

				} else {
					// System.out.println("no more ears in tmp " + tmp.n);
					break;
				}
			}
			if (tmp.n == 3) {
				tris.add(new WB_Triangle2D(tmp.points[0], tmp.points[1],
						tmp.points[2]));
			}
		} else if (tmp.n == 3) {
			tris.add(new WB_Triangle2D(points[0], points[1], points[2]));

		}
		return tris;
	}

	/**
	 * Triangulate polygon.
	 *
	 * @return arrayList of WB_IndexedTriangle, points are not copied
	 */
	public ArrayList<WB_IndexedTriangle2D> indexedTriangulate() {
		final ArrayList<WB_IndexedTriangle2D> tris = new ArrayList<WB_IndexedTriangle2D>();
		final FastMap<WB_XY, Integer> pointIndices = new FastMap<WB_XY, Integer>();
		for (int i = 0; i < n; i++) {
			pointIndices.put(points[i], i);
		}

		WB_Polygon2D tmp = new WB_Polygon2D(points, n);
		WB_Polygon2D tmp3d = getNoCopy();
		if (tmp.n > 3) {
			while (tmp.n > 3) {
				final Ear ear = tmp.findEar();
				if (ear != null) {
					final int ip = pointIndices.get(tmp3d.points[ear.ip]);
					final int i = pointIndices.get(tmp3d.points[ear.i]);
					final int in = pointIndices.get(tmp3d.points[ear.in]);
					tris.add(new WB_IndexedTriangle2D(ip, i, in, points));
					tmp = tmp.removePoint(ear.i);
					tmp3d = tmp3d.removePoint(ear.i);
				} else {
					// System.out.println("no more ears in tmp " + tmp.n);
					break;
				}
			}
			if (tmp.n == 3) {
				final int ip = pointIndices.get(tmp3d.points[0]);
				final int i = pointIndices.get(tmp3d.points[1]);
				final int in = pointIndices.get(tmp3d.points[2]);
				tris.add(new WB_IndexedTriangle2D(ip, i, in, points));
			}
		} else if (tmp.n == 3) {
			tris.add(new WB_IndexedTriangle2D(0, 1, 2, points));

		}
		return tris;
	}

	/**
	 * Removes point.
	 *
	 * @param i index of point to remove
	 * @return new WB_Polygon with point removed
	 */
	public WB_Polygon2D removePoint(final int i) {
		final WB_XY[] newPoints = new WB_XY[n - 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		for (int j = i; j < n - 1; j++) {
			newPoints[j] = points[j + 1];
		}
		return new WB_Polygon2D(newPoints, n - 1);

	}

	/**
	 * Remove flat points.
	 *
	 * @return new WB_Polygon with superfluous points removed
	 */
	public WB_Polygon2D removeFlatPoints() {
		return removeFlatPoints(0);
	}

	private WB_Polygon2D removeFlatPoints(final int start) {
		for (int i = start; i < n; i++) {
			if (isConvex(i) == WB_VertexType2D.FLAT) {
				return removePoint(i).removeFlatPoints(i);
			}
		}
		return this;
	}

	/**
	 * Adds point.
	 *
	 * @param i index to put point
	 * @param p point
	 * @return new WB_Polygon with point added
	 */
	public WB_Polygon2D addPoint(final int i, final WB_XY p) {
		final WB_XY[] newPoints = new WB_XY[n + 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		newPoints[i] = p;
		for (int j = i + 1; j < n + 1; j++) {
			newPoints[j] = points[j - 1];
		}
		return new WB_Polygon2D(newPoints, n + 1);

	}

	/**
	 * Refine polygon and smooth with simple Laplacian filter.
	 *
	 * @return new refined WB_Polygon
	 */
	public WB_Polygon2D smooth() {
		final WB_XY[] newPoints = new WB_XY[2 * n];

		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			newPoints[2 * i] = points[j].addAndCopy(points[i]);
			newPoints[2 * i].mult(0.5);
			newPoints[2 * i + 1] = points[i].get();
		}
		final WB_XY[] sPoints = new WB_XY[2 * n];
		for (int i = 0, j = 2 * n - 1; i < 2 * n; j = i, i++) {
			int k = i + 1;
			if (k == 2 * n) {
				k = 0;
			}
			sPoints[i] = newPoints[j].addAndCopy(newPoints[k]);
			sPoints[i].mult(0.5);
		}

		return new WB_Polygon2D(sPoints, 2 * n);

	}

	public static void trimConvexPolygon(final WB_Polygon2D poly, final double d) {
		final WB_Polygon2D cpoly = poly.get();
		final int n = cpoly.n; // get number of vertices
		// iterate over n-1 edges
		final WB_Polygon2D frontPoly = new WB_Polygon2D();// needed by
		// splitPolygon
		// to store one half
		final WB_Polygon2D backPoly = new WB_Polygon2D();// needed by
		// splitPolygon
		// to store other half
		WB_XY p1, p2, origin;
		WB_XY v, normal;
		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			p1 = cpoly.points[i];// startpoint of edge
			p2 = cpoly.points[j];// endpoint of edge
			// vector along edge
			v = p2.subAndCopy(p1);
			v.normalize();
			// edge normal is perpendicular to edge and plane normal
			normal = new WB_XY(v.y, -v.x);
			// center of edge
			origin = p1.addAndCopy(p2).mult(0.5);
			// offset cutting plane origin by the desired distance d
			origin.add(d * normal.x, d * normal.y);

			splitPolygonInto(poly, new WB_Line2D(origin, v), frontPoly,
					backPoly);
			poly.set(frontPoly);

		}
	}

	public void trimConvexPolygon(final double d) {
		trimConvexPolygon(this, d);
	}

	public static void trimConvexPolygon(final WB_Polygon2D poly,
			final double[] d) {

		// iterate over n-1 edges
		final WB_Polygon2D frontPoly = new WB_Polygon2D();// needed by
		// splitPolygon
		// to store one half
		final WB_Polygon2D backPoly = new WB_Polygon2D();// needed by
		// splitPolygon
		// to store other half
		WB_XY p1, p2, origin;
		WB_XY v, normal;
		for (int i = 0, j = poly.n - 1; i < poly.n; j = i, i++) {
			p1 = poly.points[i];// startpoint of edge
			p2 = poly.points[j];// endpoint of edge
			// vector along edge
			v = p2.subAndCopy(p1);
			v.normalize();
			// edge normal is perpendicular to edge and plane normal
			normal = new WB_XY(v.y, -v.x);
			// center of edge
			origin = p1.addAndCopy(p2).mult(0.5);
			// offset cutting plane origin by the desired distance d
			origin.add(d[i] * normal.x, d[i] * normal.y);

			splitPolygonInto(poly, new WB_Line2D(origin, v), frontPoly,
					backPoly);
			poly.set(frontPoly);

		}
	}

	public void trimConvexPolygon(final double[] d) {
		trimConvexPolygon(this, d);
	}

	/**
	 * Split polygon into.
	 *
	 * @param poly the poly
	 * @param L split line
	 * @param frontPoly front subpoly
	 * @param backPoly back subpoly
	 */
	public static void splitPolygonInto(final WB_Polygon2D poly,
			final WB_Line2D L, final WB_Polygon2D frontPoly,
			final WB_Polygon2D backPoly) {
		int numFront = 0;
		int numBack = 0;

		final ArrayList<WB_XY> frontVerts = new ArrayList<WB_XY>(20);
		final ArrayList<WB_XY> backVerts = new ArrayList<WB_XY>(20);

		final int numVerts = poly.n;
		if (numVerts > 0) {
			WB_XY a = poly.points[numVerts - 1];
			WB_ClassifyPointToLine2D aSide = L.classifyPointToLine2D(a);
			WB_XY b;
			WB_ClassifyPointToLine2D bSide;

			for (int n = 0; n < numVerts; n++) {
				WB_IntersectionResult2D i = new WB_IntersectionResult2D();
				b = poly.points[n];
				bSide = L.classifyPointToLine2D(b);
				if (bSide == WB_ClassifyPointToLine2D.POINT_IN_FRONT_OF_LINE) {
					if (aSide == WB_ClassifyPointToLine2D.POINT_BEHIND_LINE) {
						i = WB_ClosestPoint2D.closestPoint2D(L,
								new WB_ExplicitSegment2D(a, b));
						frontVerts.add(i.p1);
						numFront++;
						backVerts.add(i.p1);
						numBack++;
					}
					frontVerts.add(b);
					numFront++;
				} else if (bSide == WB_ClassifyPointToLine2D.POINT_BEHIND_LINE) {
					if (aSide == WB_ClassifyPointToLine2D.POINT_IN_FRONT_OF_LINE) {
						i = WB_ClosestPoint2D.closestPoint2D(L,
								new WB_ExplicitSegment2D(a, b));

						/*
						 * if (classifyPointToPlane(i.p1, P) !=
						 * ClassifyPointToPlane.POINT_ON_PLANE) { System.out
						 * .println("Inconsistency: intersection not on plane");
						 * }
						 */

						frontVerts.add(i.p1);
						numFront++;
						backVerts.add(i.p1);
						numBack++;
					} else if (aSide == WB_ClassifyPointToLine2D.POINT_ON_LINE) {
						backVerts.add(a);
						numBack++;
					}
					backVerts.add(b);
					numBack++;
				} else {
					frontVerts.add(b);
					numFront++;
					if (aSide == WB_ClassifyPointToLine2D.POINT_BEHIND_LINE) {
						backVerts.add(b);
						numBack++;
					}
				}
				a = b;
				aSide = bSide;

			}
			frontPoly.set(frontVerts, numFront);
			backPoly.set(backVerts, numBack);
		}

	}

	public void splitPolygonInto(final WB_Line2D L,
			final WB_Polygon2D frontPoly, final WB_Polygon2D backPoly) {
		splitPolygonInto(get(), L, frontPoly, backPoly);

	}

	public FastList<WB_IndexedSegment2D> toSegments() {
		final FastList<WB_IndexedSegment2D> segments = new FastList<WB_IndexedSegment2D>(
				n);
		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			segments.add(new WB_IndexedSegment2D(j, i, points));

		}
		return segments;
	}

	public FastList<WB_ExplicitSegment2D> toExplicitSegments() {
		final FastList<WB_ExplicitSegment2D> segments = new FastList<WB_ExplicitSegment2D>(
				n);
		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			segments.add(new WB_ExplicitSegment2D(points[j], points[i]));

		}
		return segments;
	}

	public WB_Polygon2D negate() {
		final WB_XY[] negPoints = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			negPoints[i] = points[n - 1 - i];
		}
		return new WB_Polygon2D(negPoints, n);

	}

	public static ArrayList<WB_Polygon2D> negate(
			final ArrayList<WB_Polygon2D> polys) {
		final ArrayList<WB_Polygon2D> neg = new ArrayList<WB_Polygon2D>();
		for (int i = 0; i < polys.size(); i++) {
			neg.add(polys.get(i).negate());
		}
		return neg;

	}

	public static ArrayList<WB_ExplicitSegment2D> intersectionSeg(
			final WB_Polygon2D P, final WB_Polygon2D Q) {
		final FastList<WB_ExplicitSegment2D> pos = new FastList<WB_ExplicitSegment2D>();
		final FastList<WB_ExplicitSegment2D> neg = new FastList<WB_ExplicitSegment2D>();
		final FastList<WB_ExplicitSegment2D> coSame = new FastList<WB_ExplicitSegment2D>();
		final FastList<WB_ExplicitSegment2D> coDiff = new FastList<WB_ExplicitSegment2D>();
		final ArrayList<WB_ExplicitSegment2D> intersect = new ArrayList<WB_ExplicitSegment2D>();
		final WB_BSPTree2D tree = new WB_BSPTree2D();
		tree.build(P);
		for (int i = 0, j = Q.n - 1; i < Q.n; j = i, i++) {
			pos.clear();
			neg.clear();
			coSame.clear();
			coDiff.clear();
			final WB_ExplicitSegment2D S = new WB_ExplicitSegment2D(
					Q.points[j], Q.points[i]);
			tree.partitionSegment(S, pos, neg, coSame, coDiff);
			intersect.addAll(pos);
			intersect.addAll(coSame);

		}
		tree.build(Q);
		for (int i = 0, j = P.n - 1; i < P.n; j = i, i++) {
			pos.clear();
			neg.clear();
			coSame.clear();
			coDiff.clear();
			final WB_ExplicitSegment2D S = new WB_ExplicitSegment2D(
					P.points[j], P.points[i]);
			tree.partitionSegment(S, pos, neg, coSame, coDiff);
			intersect.addAll(pos);
			intersect.addAll(coSame);

		}

		return intersect;

	}

	public static ArrayList<WB_Polygon2D> intersection(final WB_Polygon2D P,
			final WB_Polygon2D Q) {
		return extractPolygons(intersectionSeg(P, Q));
	}

	public static ArrayList<WB_ExplicitSegment2D> unionSeg(
			final WB_Polygon2D P, final WB_Polygon2D Q) {
		final WB_Polygon2D nP = P.negate();
		final WB_Polygon2D nQ = Q.negate();
		return WB_ExplicitSegment2D.negate(intersectionSeg(nP, nQ));
	}

	public static ArrayList<WB_Polygon2D> union(final WB_Polygon2D P,
			final WB_Polygon2D Q) {
		return extractPolygons(unionSeg(P, Q));
	}

	public static ArrayList<WB_ExplicitSegment2D> subtractSeg(
			final WB_Polygon2D P, final WB_Polygon2D Q) {
		final WB_Polygon2D nQ = Q.negate();
		return intersectionSeg(P, nQ);
	}

	public static ArrayList<WB_Polygon2D> subtract(final WB_Polygon2D P,
			final WB_Polygon2D Q) {
		return extractPolygons(subtractSeg(P, Q));
	}

	public static ArrayList<WB_Polygon2D> exclusiveOr(final WB_Polygon2D P,
			final WB_Polygon2D Q) {
		final ArrayList<WB_Polygon2D> tmp = subtract(P, Q);
		tmp.addAll(subtract(Q, P));
		return tmp;
	}

	public static ArrayList<WB_Polygon2D> extractPolygons(
			final ArrayList<WB_ExplicitSegment2D> segs) {
		final ArrayList<WB_Polygon2D> result = new ArrayList<WB_Polygon2D>();
		final ArrayList<WB_ExplicitSegment2D> leftovers = new ArrayList<WB_ExplicitSegment2D>();
		final ArrayList<WB_ExplicitSegment2D> cleanedsegs = clean(segs);
		leftovers.addAll(cleanedsegs);
		while (leftovers.size() > 0) {
			final ArrayList<WB_ExplicitSegment2D> currentPolygon = new ArrayList<WB_ExplicitSegment2D>();
			final boolean loopFound = tryToFindLoop(leftovers, currentPolygon);
			if (loopFound) {
				final ArrayList<WB_XY> points = new ArrayList<WB_XY>();
				for (int i = 0; i < currentPolygon.size(); i++) {
					points.add(currentPolygon.get(i).getOrigin());

				}
				if (points.size() > 2) {
					WB_Polygon2D poly = new WB_Polygon2D(points);
					poly = poly.removeFlatPoints();
					result.add(poly);
				}
			}
			leftovers.removeAll(currentPolygon);
		}
		return result;
	}

	private static ArrayList<WB_ExplicitSegment2D> clean(
			final ArrayList<WB_ExplicitSegment2D> segs) {
		final ArrayList<WB_ExplicitSegment2D> cleanedsegs = new ArrayList<WB_ExplicitSegment2D>();
		final WB_KDTree2D<Integer> tree = new WB_KDTree2D<Integer>();
		int i = 0;
		for (i = 0; i < segs.size(); i++) {
			if (!WB_Epsilon.isZeroSq(WB_Distance2D.sqDistance(segs.get(i)
					.getOrigin(), segs.get(i).getEnd()))) {
				tree.put(segs.get(i).getOrigin(), 2 * i);
				tree.put(segs.get(i).getEnd(), 2 * i + 1);
				cleanedsegs.add(new WB_ExplicitSegment2D(segs.get(i)
						.getOrigin(), segs.get(i).getEnd(), false));
				break;
			}

		}
		for (; i < segs.size(); i++) {
			if (!WB_Epsilon.isZeroSq(WB_Distance2D.sqDistance(segs.get(i)
					.getOrigin(), segs.get(i).getEnd()))) {
				WB_XY origin = segs.get(i).getOrigin();
				WB_XY end = segs.get(i).getEnd();

				WB_KDNeighbor2D<Integer>[] nn = tree.getNearestNeighbors(
						origin, 1);

				if (WB_Epsilon.isZeroSq(nn[0].sqDistance())) {
					origin = nn[0].point();
				} else {
					tree.put(segs.get(i).getOrigin(), 2 * i);
				}
				nn = tree.getNearestNeighbors(end, 1);
				if (WB_Epsilon.isZeroSq(nn[0].sqDistance())) {
					end = nn[0].point();
				} else {
					tree.put(segs.get(i).getEnd(), 2 * i + 1);
				}
				cleanedsegs.add(new WB_ExplicitSegment2D(origin, end, false));
			}

		}
		return cleanedsegs;
	}

	private static boolean tryToFindLoop(
			final ArrayList<WB_ExplicitSegment2D> segs,
			final ArrayList<WB_ExplicitSegment2D> loop) {
		final ArrayList<WB_ExplicitSegment2D> localSegs = new ArrayList<WB_ExplicitSegment2D>();
		localSegs.addAll(segs);
		WB_Segment2D start = localSegs.get(0);
		loop.add(localSegs.get(0));
		boolean found = false;
		do {
			found = false;
			for (int i = 0; i < localSegs.size(); i++) {
				if (WB_Epsilon.isZeroSq(WB_Distance2D.sqDistance(
						localSegs.get(i).getOrigin(), start.getEnd()))) {
					// if (localSegs.get(i).origin() == start.end()) {
					start = localSegs.get(i);
					loop.add(localSegs.get(i));
					found = true;
					break;
				}
			}
			if (found) {
				localSegs.remove(start);
			}

		} while ((start != segs.get(0)) && found);
		if ((loop.size() > 0) && (start == segs.get(0))) {
			return true;
		}
		return false;
	}

	public WB_ExplicitPolygon toPolygon() {
		final WB_Point[] points3D = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			points3D[i] = new WB_Point(points[i].x, points[i].y, 0);
		}
		return new WB_ExplicitPolygon(points3D, n);

	}

}