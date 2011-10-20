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
package wblut.geom;

import java.util.ArrayList;
import java.util.List;

import wblut.geom2D.WB_Polygon2D;
import wblut.geom2D.WB_VertexType2D;
import wblut.geom2D.WB_XY;
import wblut.math.WB_Epsilon;
import wblut.tree.WB_KDNeighbor;
import wblut.tree.WB_KDTree;

// TODO: Auto-generated Javadoc
/**
 * Planar polygon class.
 */
public class WB_ExplicitPolygon implements WB_Polygon {

	/** Ordered array of WB_Point. */
	private WB_Point[]	points;

	/** Number of points. */
	public int			n;

	/** Stored plane of polygon. */
	private WB_Plane	P;

	/** Status of stored plane. */
	private boolean		updated;

	/**
	 * Instantiates a new WB_Polygon.
	 */
	public WB_ExplicitPolygon() {
		points = new WB_Point[0];
		n = 0;
		updated = false;
	}

	/**
	 * Instantiates a new WB_Polygon.
	 *
	 * @param points array of WB_Point, no copies are made
	 * @param n number of points
	 */
	public WB_ExplicitPolygon(final WB_Point[] points, final int n) {
		this.points = points;
		this.n = n;
		P = getPlane();
		updated = true;
	}

	/**
	 * Instantiates a new WB_Polygon.
	 *
	 * @param points array of WB_Point
	 * @param n number of points
	 * @param copy copy points?
	 */
	public WB_ExplicitPolygon(final WB_Point[] points, final int n,
			final boolean copy) {
		if (copy == false) {
			this.points = points;
		} else {
			this.points = new WB_Point[n];
			for (int i = 0; i < n; i++) {
				this.points[i] = points[i].get();
			}

		}
		this.n = n;
		P = getPlane();
	}

	/**
	 * Instantiates a new WB_Polygon.
	 *
	 * @param points arrayList of WB_Point
	 */
	public WB_ExplicitPolygon(final ArrayList<WB_Point> points) {
		n = points.size();
		this.points = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			this.points[i] = points.get(i);
		}

		P = getPlane();
	}

	/**
	 * Set polygon.
	 *
	 * @param points array of WB_Point, no copies are made
	 * @param n number of points
	 */
	public void set(final WB_Point[] points, final int n) {
		this.points = points;
		this.n = n;
		P = getPlane();
	}

	/**
	 * Set polygon.
	 *
	 * @param poly source polygon, no copies are made
	 */
	public void set(final WB_Polygon poly) {
		points = poly.getPoints();
		n = poly.getN();
		P = getPlane();
	}

	/**
	 * Set polygon.
	 *
	 * @param points arrayList of WB_Point, no copies are made
	 * @param n number of points
	 */
	public void set(final ArrayList<WB_Point> points, final int n) {
		this.points = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			this.points[i] = points.get(i);
		}
		this.n = n;
		P = getPlane();
	}

	/**
	 * Get deep copy.
	 *
	 * @return copy
	 */
	public WB_ExplicitPolygon get() {
		final WB_Point[] newPoints = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			newPoints[i] = points[i].get();
		}
		return new WB_ExplicitPolygon(newPoints, n);

	}

	/**
	 * Get shallow copy.
	 *
	 * @return copy
	 */
	public WB_ExplicitPolygon getNoCopy() {
		return new WB_ExplicitPolygon(points, n);

	}

	/**
	 * Closest point on polygon to given point.
	 *
	 * @param p point
	 * @return closest point of polygon
	 */
	public WB_Point closestPoint(final WB_Point p) {
		double d = Double.POSITIVE_INFINITY;
		int id = -1;
		for (int i = 0; i < n; i++) {
			final double cd = WB_Distance.sqDistance(p, points[i]);
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
	public int closestIndex(final WB_Point p) {
		double d = Double.POSITIVE_INFINITY;
		int id = -1;
		for (int i = 0; i < n; i++) {
			final double cd = WB_Distance.sqDistance(p, points[i]);
			if (cd < d) {
				id = i;
				d = cd;
			}
		}
		return id;
	}

	/**
	 * Plane of polygon.
	 *
	 * @return plane
	 */
	public WB_Plane getPlane() {
		if (updated) {
			return P;
		}
		final WB_Normal normal = new WB_Normal();
		final WB_Point center = new WB_Point();
		WB_Point p0;
		WB_Point p1;
		for (int i = 0, j = n - 1; i < n; j = i, i++) {

			p0 = points[j];
			p1 = points[i];
			normal.x += (p0.y - p1.y) * (p0.z + p1.z);
			normal.y += (p0.z - p1.z) * (p0.x + p1.x);
			normal.z += (p0.x - p1.x) * (p0.y + p1.y);
			center.add(p1);
		}
		normal.normalize();
		center.div(n);
		P = new WB_Plane(center, normal);
		updated = true;
		return P;

	}

	/**
	 * Checks if point at index is convex.
	 *
	 * @param i index
	 * @return WB.VertexType.FLAT,WB.VertexType.CONVEX,WB.VertexType.CONCAVE
	 */
	public WB_VertexType2D isConvex(final int i) {

		final WB_Vector vp = points[(i == 0) ? n - 1 : i - 1]
				.subToVector(points[i]);
		vp.normalize();
		final WB_Vector vn = points[(i == n - 1) ? 0 : i + 1]
				.subToVector(points[i]);
		vn.normalize();

		final double cross = vp.cross(vn).mag2();

		if (WB_Epsilon.isZeroSq(cross)) {
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
	 * @return arrayList of WB_IndexedTriangle, points are not copied
	 */
	public ArrayList<WB_IndexedTriangle> triangulate() {
		final ArrayList<WB_IndexedTriangle> tris = new ArrayList<WB_IndexedTriangle>();
		WB_Polygon2D tmp = toPolygon2D();
		WB_ExplicitPolygon tmp3d = get();
		if (tmp.n > 3) {
			while (tmp.n > 3) {
				final WB_Polygon2D.Ear ear = tmp.findEar();
				if (ear != null) {
					tris.add(new WB_IndexedTriangle(ear.ip, ear.i, ear.in,
							tmp3d.points));
					tmp = tmp.removePoint(ear.i);
					tmp3d = tmp3d.removePoint(ear.i);
				} else {
					// System.out.println("no more ears in tmp " + tmp.n);
					break;
				}
			}
			if (tmp.n == 3) {
				tris.add(new WB_IndexedTriangle(0, 1, 2, tmp3d.points));
			}
		} else if (tmp.n == 3) {
			tris.add(new WB_IndexedTriangle(0, 1, 2, points));

		}
		return tris;
	}

	/**
	 * Removes point.
	 *
	 * @param i index of point to remove
	 * @return new WB_Polygon with point removed
	 */
	public WB_ExplicitPolygon removePoint(final int i) {
		final WB_Point[] newPoints = new WB_Point[n - 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		for (int j = i; j < n - 1; j++) {
			newPoints[j] = points[j + 1];
		}
		return new WB_ExplicitPolygon(newPoints, n - 1);

	}

	public void removePointSelf(final int i) {
		final WB_Point[] newPoints = new WB_Point[n - 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		for (int j = i; j < n - 1; j++) {
			newPoints[j] = points[j + 1];
		}
		set(newPoints, n - 1);

	}

	/**
	 * Adds point.
	 *
	 * @param i index to put point
	 * @param p point
	 * @return new WB_Polygon with point added
	 */
	public WB_ExplicitPolygon addPoint(final int i, final WB_Point p) {
		final WB_Point[] newPoints = new WB_Point[n + 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		newPoints[i] = p;
		for (int j = i + 1; j < n + 1; j++) {
			newPoints[j] = points[j - 1];
		}
		return new WB_ExplicitPolygon(newPoints, n + 1);

	}

	public void addPointSelf(final int i, final WB_Point p) {
		final WB_Point[] newPoints = new WB_Point[n + 1];
		for (int j = 0; j < i; j++) {
			newPoints[j] = points[j];
		}
		newPoints[i] = p;
		for (int j = i + 1; j < n + 1; j++) {
			newPoints[j] = points[j - 1];
		}
		set(newPoints, n + 1);

	}

	/**
	 * Refine polygon and smooth with simple Laplacian filter.
	 *
	 * @return new refined WB_Polygon
	 */
	public WB_ExplicitPolygon smooth() {
		final WB_Point[] newPoints = new WB_Point[2 * n];

		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			newPoints[2 * i] = points[j].addAndCopy(points[i]);
			newPoints[2 * i].mult(0.5);
			newPoints[2 * i + 1] = points[i].get();
		}
		final WB_Point[] sPoints = new WB_Point[2 * n];
		for (int i = 0, j = 2 * n - 1; i < 2 * n; j = i, i++) {
			int k = i + 1;
			if (k == 2 * n) {
				k = 0;
			}
			sPoints[i] = newPoints[j].addAndCopy(newPoints[k]);
			sPoints[i].mult(0.5);
		}

		return new WB_ExplicitPolygon(sPoints, 2 * n);

	}

	public static void trimConvexPolygon(final WB_ExplicitPolygon poly,
			final double d) {
		final WB_ExplicitPolygon cpoly = poly.get();
		final int n = cpoly.n; // get number of vertices
		final WB_Plane P = cpoly.getPlane(); // get plane of poly
		// iterate over n-1 edges
		final WB_ExplicitPolygon frontPoly = new WB_ExplicitPolygon();// needed
																		// by
																		// splitPolygon
		// to store one half
		final WB_ExplicitPolygon backPoly = new WB_ExplicitPolygon();// needed
																		// by
																		// splitPolygon
		// to store other half
		WB_Point p1, p2, origin;
		WB_Vector v, normal;
		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			p1 = cpoly.points[i];// startpoint of edge
			p2 = cpoly.points[j];// endpoint of edge
			// vector along edge
			v = p2.subToVector(p1);
			v.normalize();
			// edge normal is perpendicular to edge and plane normal
			normal = v.cross(P.getNormal());
			// center of edge
			origin = p1.addAndCopy(p2).mult(0.5);
			// offset cutting plane origin by the desired distance d
			origin.add(d * normal.x, d * normal.y, d * normal.z);

			splitPolygonInto(poly, new WB_Plane(origin, normal), frontPoly,
					backPoly);
			poly.set(frontPoly);

		}
	}

	public void trimConvexPolygon(final double d) {
		trimConvexPolygon(this, d);
	}

	public static void trimConvexPolygon(final WB_ExplicitPolygon poly,
			final double[] d) {

		final WB_Plane P = poly.getPlane(); // get plane of poly
		// iterate over n-1 edges
		final WB_ExplicitPolygon frontPoly = new WB_ExplicitPolygon();// needed
																		// by
																		// splitPolygon
		// to store one half
		final WB_ExplicitPolygon backPoly = new WB_ExplicitPolygon();// needed
																		// by
																		// splitPolygon
		// to store other half
		WB_Point p1, p2, origin;
		WB_Vector v, normal;
		for (int i = 0, j = poly.n - 1; i < poly.n; j = i, i++) {
			p1 = poly.points[i];// startpoint of edge
			p2 = poly.points[j];// endpoint of edge
			// vector along edge
			v = p2.subToVector(p1);
			v.normalize();
			// edge normal is perpendicular to edge and plane normal
			normal = v.cross(P.getNormal());
			// center of edge
			origin = p1.addAndCopy(p2).mult(0.5);
			// offset cutting plane origin by the desired distance d
			origin.add(d[j] * normal.x, d[j] * normal.y, d[j] * normal.z);

			splitPolygonInto(poly, new WB_Plane(origin, normal), frontPoly,
					backPoly);
			poly.set(frontPoly);

		}
	}

	public void trimConvexPolygon(final double[] d) {
		trimConvexPolygon(this, d);
	}

	/**
	 * Split polygon into pre test.
	 *
	 * @param poly the poly
	 * @param P the p
	 * @param frontPoly the front poly
	 * @param backPoly the back poly
	 */
	public static void splitPolygonIntoPreTest(final WB_ExplicitPolygon poly,
			final WB_Plane P, final WB_ExplicitPolygon frontPoly,
			final WB_ExplicitPolygon backPoly) {
		int numFront = 0;
		int numBack = 0;

		final WB_AABB AABB = new WB_AABB(poly.points, poly.n);
		if (WB_Intersection.checkIntersection(AABB, P)) {

			final ArrayList<WB_Point> frontVerts = new ArrayList<WB_Point>(20);
			final ArrayList<WB_Point> backVerts = new ArrayList<WB_Point>(20);

			final int numVerts = poly.n;
			WB_Point a = poly.points[numVerts - 1];
			WB_ClassifyPointToPlane aSide = P.classifyPointToPlane(a);
			WB_Point b;
			WB_ClassifyPointToPlane bSide;

			for (int n = 0; n < numVerts; n++) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				b = poly.points[n];
				bSide = P.classifyPointToPlane(b);
				if (bSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
						WB_Intersection.getIntersectionInto(b, a, P, i);

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
					}
					frontVerts.add(b);
					numFront++;
				} else if (bSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
						WB_Intersection.getIntersectionInto(a, b, P, i);

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
					} else if (aSide == WB_ClassifyPointToPlane.POINT_ON_PLANE) {
						backVerts.add(a);
						numBack++;
					}
					backVerts.add(b);
					numBack++;
				} else {
					frontVerts.add(b);
					numFront++;
					if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
						backVerts.add(b);
						numBack++;
					}
				}
				a = b;
				aSide = bSide;

			}
			frontPoly.set(frontVerts, numFront);
			backPoly.set(backVerts, numBack);
		} else {
			int c = 0;
			WB_Point a = poly.points[c];
			WB_ClassifyPointToPlane aSide = P.classifyPointToPlane(a);

			if (aSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
				frontPoly.set(poly.get());
				backPoly.set(new WB_ExplicitPolygon());

			} else if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
				backPoly.set(poly.get());
				frontPoly.set(new WB_ExplicitPolygon());
			} else {
				c++;
				do {
					a = poly.points[c];
					aSide = P.classifyPointToPlane(a);
					c++;
				} while (aSide == WB_ClassifyPointToPlane.POINT_ON_PLANE
						&& c < poly.n);
				if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
					backPoly.set(poly.get());
					frontPoly.set(new WB_ExplicitPolygon());
				} else {
					frontPoly.set(poly.get());
					backPoly.set(new WB_ExplicitPolygon());

				}

			}

		}

	}

	/**
	 * Split polygon into.
	 *
	 * @param poly the poly
	 * @param P the p
	 * @param frontPoly the front poly
	 * @param backPoly the back poly
	 */
	public static void splitPolygonInto(final WB_ExplicitPolygon poly,
			final WB_Plane P, final WB_ExplicitPolygon frontPoly,
			final WB_ExplicitPolygon backPoly) {
		int numFront = 0;
		int numBack = 0;

		final ArrayList<WB_Point> frontVerts = new ArrayList<WB_Point>(20);
		final ArrayList<WB_Point> backVerts = new ArrayList<WB_Point>(20);

		final int numVerts = poly.n;
		if (numVerts > 0) {
			WB_Point a = poly.points[numVerts - 1];
			WB_ClassifyPointToPlane aSide = P.classifyPointToPlane(a);
			WB_Point b;
			WB_ClassifyPointToPlane bSide;

			for (int n = 0; n < numVerts; n++) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				b = poly.points[n];
				bSide = P.classifyPointToPlane(b);
				if (bSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
						WB_Intersection.getIntersectionInto(b, a, P, i);

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
					}
					frontVerts.add(b);
					numFront++;
				} else if (bSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
						WB_Intersection.getIntersectionInto(a, b, P, i);

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
					} else if (aSide == WB_ClassifyPointToPlane.POINT_ON_PLANE) {
						backVerts.add(a);
						numBack++;
					}
					backVerts.add(b);
					numBack++;
				} else {
					frontVerts.add(b);
					numFront++;
					if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
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

	public void splitPolygonInto(final WB_Plane P,
			final WB_ExplicitPolygon frontPoly,
			final WB_ExplicitPolygon backPoly) {
		splitPolygonInto(get(), P, frontPoly, backPoly);

	}

	public void splitPolygonIntoPreTest(final WB_Plane P,
			final WB_ExplicitPolygon frontPoly,
			final WB_ExplicitPolygon backPoly) {
		splitPolygonIntoPreTest(get(), P, frontPoly, backPoly);

	}

	public ArrayList<WB_IndexedSegment> getSegments() {
		final ArrayList<WB_IndexedSegment> segments = new ArrayList<WB_IndexedSegment>(
				n);
		for (int i = 0, j = n - 1; i < n; j = i, i++) {
			segments.add(new WB_IndexedSegment(i, j, points));

		}
		return segments;
	}

	public WB_ExplicitPolygon negate() {
		final WB_Point[] negPoints = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			negPoints[i] = points[n - 1 - i];
		}
		return new WB_ExplicitPolygon(negPoints, n);

	}

	public static ArrayList<WB_ExplicitPolygon> negate(
			final ArrayList<WB_ExplicitPolygon> polys) {
		final ArrayList<WB_ExplicitPolygon> neg = new ArrayList<WB_ExplicitPolygon>();
		for (int i = 0; i < polys.size(); i++) {
			neg.add(polys.get(i).negate());
		}
		return neg;

	}

	public static ArrayList<WB_ExplicitPolygon> extractPolygons(
			final ArrayList<WB_ExplicitSegment> segs) {
		final ArrayList<WB_ExplicitPolygon> result = new ArrayList<WB_ExplicitPolygon>();
		final ArrayList<WB_ExplicitSegment> leftovers = new ArrayList<WB_ExplicitSegment>();
		final ArrayList<WB_ExplicitSegment> cleanedsegs = clean(segs);
		leftovers.addAll(cleanedsegs);
		while (leftovers.size() > 0) {
			final ArrayList<WB_ExplicitSegment> currentPolygon = new ArrayList<WB_ExplicitSegment>();
			final boolean loopFound = tryToFindLoop(leftovers, currentPolygon);
			if (loopFound) {
				final ArrayList<WB_Point> points = new ArrayList<WB_Point>();
				for (int i = 0; i < currentPolygon.size(); i++) {
					points.add(currentPolygon.get(i).getOrigin());

				}
				if (points.size() > 2) {
					final WB_ExplicitPolygon poly = new WB_ExplicitPolygon(
							points);
					result.add(poly);
				}
			}
			leftovers.removeAll(currentPolygon);
		}
		return result;
	}

	public static ArrayList<WB_ExplicitSegment> clean(
			final List<WB_ExplicitSegment> segs) {
		final ArrayList<WB_ExplicitSegment> cleanedsegs = new ArrayList<WB_ExplicitSegment>();
		final WB_KDTree<Integer> tree = new WB_KDTree<Integer>();
		int i = 0;
		for (i = 0; i < segs.size(); i++) {
			if (!WB_Epsilon.isZeroSq(WB_Distance.sqDistance(segs.get(i)
					.getOrigin(), segs.get(i).getEnd()))) {
				tree.put(segs.get(i).getOrigin(), 2 * i);
				tree.put(segs.get(i).getEnd(), 2 * i + 1);
				cleanedsegs.add(new WB_ExplicitSegment(segs.get(i).getOrigin(),
						segs.get(i).getEnd(), false));
				break;
			}

		}
		for (; i < segs.size(); i++) {
			if (!WB_Epsilon.isZeroSq(WB_Distance.sqDistance(segs.get(i)
					.getOrigin(), segs.get(i).getEnd()))) {
				WB_Point origin = segs.get(i).getOrigin();
				WB_Point end = segs.get(i).getEnd();

				WB_KDNeighbor<Integer>[] nn = tree.getNearestNeighbors(origin,
						1);

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
				cleanedsegs.add(new WB_ExplicitSegment(origin, end, false));
			}

		}
		return cleanedsegs;
	}

	private static boolean tryToFindLoop(
			final ArrayList<WB_ExplicitSegment> segs,
			final ArrayList<WB_ExplicitSegment> loop) {
		final ArrayList<WB_ExplicitSegment> localSegs = new ArrayList<WB_ExplicitSegment>();
		localSegs.addAll(segs);
		WB_ExplicitSegment start = localSegs.get(0);
		loop.add(localSegs.get(0));
		boolean found = false;
		do {
			found = false;
			for (int i = 0; i < localSegs.size(); i++) {
				if (WB_Epsilon.isZeroSq(WB_Distance.sqDistance(localSegs.get(i)
						.getOrigin(), start.getEnd()))) {
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

	public WB_Polygon2D toPolygon2D() {
		final WB_XY[] lpoints = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			lpoints[i] = P.embedPoint(points[i]);
		}
		return new WB_Polygon2D(lpoints, n);

	}

	/*
	 * (non-Javadoc)
	 * @see wblut.geom.WB_Polygon#getN()
	 */
	public int getN() {
		return n;
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.geom.WB_Polygon#getPoint(int)
	 */
	public WB_Point getPoint(final int i) {
		return points[i];
	}

	public int getIndex(final int i) {
		return i;
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.geom.WB_Polygon#getPoints()
	 */
	public WB_Point[] getPoints() {

		return points;
	}

}