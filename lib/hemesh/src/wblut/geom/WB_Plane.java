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

import wblut.geom2D.WB_XY;
import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;

// TODO: Auto-generated Javadoc
/**
 * 3D plane.
 *
 */
public class WB_Plane {

	/** Plane normal. */
	private WB_Normal	n;

	/** Origin. */
	private WB_Point	origin;

	/** d-parameter: p.n = d with p point on plane, n the normal and . the dot product. */
	private double		d;

	/** planar coordinate system. */
	private WB_Vector	u, v;

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param p1 first point on plane
	 * @param p2 second point on plane
	 * @param p3 third point on plane
	 */
	public WB_Plane(final WB_Point p1, final WB_Point p2, final WB_Point p3) {
		p1.get();
		final WB_Vector v21 = p2.subToVector(p1);
		final WB_Vector v31 = p3.subToVector(p1);
		n = new WB_Normal(v21.cross(v31));
		n.normalize();
		d = n.x * p1.x + n.y * p1.y + n.z * p1.z;
		origin = p1.get();
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param p1 first point on plane
	 * @param p2 second point on plane
	 * @param p3 third point on plane
	 */
	public void set(final WB_Point p1, final WB_Point p2, final WB_Point p3) {
		p1.get();
		final WB_Vector v21 = p2.subToVector(p1);
		final WB_Vector v31 = p3.subToVector(p1);
		n = new WB_Normal(v21.cross(v31));
		n.normalize();
		d = n.x * p1.x + n.y * p1.y + n.z * p1.z;
		origin = p1.get();
		setAxes();
	}

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param o origin
	 * @param n normal
	 */
	public WB_Plane(final WB_Point o, final WB_Vector n) {
		origin = o.get();
		this.n = new WB_Normal(n);
		this.n.normalize();
		d = this.n.dot(origin);
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param o origin
	 * @param n normal
	 */
	public void set(final WB_Point o, final WB_Vector n) {
		origin = o.get();
		this.n = new WB_Normal(n);
		this.n.normalize();
		d = this.n.dot(origin);
		setAxes();
	}

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param ox x-coordinate of origin
	 * @param oy y-coordinate of origin
	 * @param oz z-coordinate of origin
	 * @param nx x-coordinate of normal
	 * @param ny y-coordinate of normal
	 * @param nz z-coordinate of normal
	 */
	public WB_Plane(final double ox, final double oy, final double oz,
			final double nx, final double ny, final double nz) {
		origin = new WB_Point(ox, oy, oz);
		n = new WB_Normal(nx, ny, nz);
		n.normalize();
		d = n.dot(origin);
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param ox x-coordinate of origin
	 * @param oy y-coordinate of origin
	 * @param oz z-coordinate of origin
	 * @param nx x-coordinate of normal
	 * @param ny y-coordinate of normal
	 * @param nz z-coordinate of normal
	 */
	public void set(final double ox, final double oy, final double oz,
			final double nx, final double ny, final double nz) {
		origin = new WB_Point(ox, oy, oz);
		n = new WB_Normal(nx, ny, nz);
		n.normalize();
		d = n.dot(origin);
		setAxes();
	}

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param o origin
	 * @param n normal
	 */
	public WB_Plane(final WB_Point o, final WB_Normal n) {
		origin = o.get();
		this.n = n.get();
		this.n.normalize();
		d = this.n.dot(origin);
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param o origin
	 * @param n normal
	 */
	public void set(final WB_Point o, final WB_Normal n) {
		origin = o.get();
		this.n = n.get();
		this.n.normalize();
		d = this.n.dot(origin);
		setAxes();
	}

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param n normal
	 * @param d d-parameter: p.n=d, for any point p on the plane
	 */
	public WB_Plane(final WB_Vector n, final double d) {
		this.n = new WB_Normal(n);
		this.n.normalize();
		this.d = d;
		origin = WB_ClosestPoint.closestPoint(new WB_Point(), this);
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param n normal
	 * @param d d-parameter
	 */
	public void set(final WB_Vector n, final double d) {
		this.n = new WB_Normal(n);
		this.n.normalize();
		this.d = d;
		origin = WB_ClosestPoint.closestPoint(new WB_Point(), this);
		setAxes();
	}

	/**
	 * Instantiates a new WB_Plane.
	 *
	 * @param n normal
	 * @param d d-parameter
	 */
	public WB_Plane(final WB_Normal n, final double d) {
		this.n = n.get();
		this.n.normalize();
		this.d = d;
		origin = WB_ClosestPoint.closestPoint(new WB_Point(), this);
		setAxes();
	}

	/**
	 * Set plane.
	 *
	 * @param n normal
	 * @param d d-parameter
	 */
	public void set(final WB_Normal n, final double d) {
		this.n = n.get();
		this.n.normalize();
		this.d = d;
		origin = WB_ClosestPoint.closestPoint(new WB_Point(), this);
		setAxes();
	}

	/**
	 * Get copy.
	 *
	 * @return copy
	 */
	public WB_Plane get() {
		return new WB_Plane(origin, n);
	}

	/**
	 * Get plane normal.
	 *
	 * @return copy of plane normal
	 */
	public WB_Normal getNormal() {
		return n.get();
	}

	/**
	 * Get d.
	 *
	 * @return d
	 */
	public double d() {
		return d;
	}

	/**
	 * Get origin.
	 *
	 * @return origin, if plane was not created using points then this returns the projection of (0,0,0) on the plane
	 */
	public WB_Point getOrigin() {
		return origin;
	}

	/**
	 * Flip normal.
	 */
	public void flipNormal() {
		n.mult(-1);
		setAxes();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Plane o: [" + origin + "] n: [" + n + "] d: [" + d + "]";
	}

	/**
	 * Classify point to plane.
	 *
	 * @param p point
	 * @return WB.ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE, WB.ClassifyPointToPlane.POINT_BEHIND_PLANE, WB.ClassifyPointToPlane.POINT_ON_PLANE
	 */
	public WB_ClassifyPointToPlane classifyPointToPlane(final WB_XYZ p) {

		final double dist = getNormal().dot(p) - d();
		if (dist > WB_Epsilon.PLANE_EPSILON) {
			return WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE;
		}
		if (dist < -WB_Epsilon.PLANE_EPSILON) {
			return WB_ClassifyPointToPlane.POINT_BEHIND_PLANE;
		}
		return WB_ClassifyPointToPlane.POINT_ON_PLANE;
	}

	/**
	 * Classify point to plane.
	 *
	 * @param p point
	 * @param P plane
	 * @return WB.ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE, WB.ClassifyPointToPlane.POINT_BEHIND_PLANE, WB.ClassifyPointToPlane.POINT_ON_PLANE
	 */
	public static WB_ClassifyPointToPlane classifyPointToPlane(final WB_XYZ p,
			final WB_Plane P) {

		final double dist = P.getNormal().dot(p) - P.d();
		if (dist > WB_Epsilon.PLANE_EPSILON) {
			return WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE;
		}
		if (dist < -WB_Epsilon.PLANE_EPSILON) {
			return WB_ClassifyPointToPlane.POINT_BEHIND_PLANE;
		}
		return WB_ClassifyPointToPlane.POINT_ON_PLANE;
	}

	/**
	 * Check if points lies on positive side of plane defined by 3 clockwise points.
	 *
	 * @param p point to check
	 * @param a 
	 * @param b 
	 * @param c
	 * @return true, if successful
	 */
	public static boolean pointOutsideOfPlane(final WB_XYZ p, final WB_XYZ a,
			final WB_XYZ b, final WB_XYZ c) {
		return (p.subToVector(a))
				.dot((b.subToVector(a)).cross(c.subToVector(a))) >= 0;
	}

	/**
	 * Check if points lies on other side of plane compared with reference points.
	 *
	 * @param p point to check
	 * @param q reference point
	 * @param a
	 * @param b
	 * @param c
	 * @return true, if successful
	 */
	public static boolean pointOtherSideOfPlane(final WB_XYZ p, final WB_XYZ q,
			final WB_XYZ a, final WB_XYZ b, final WB_XYZ c) {
		final double signp = (p.subToVector(a)).dot((b.subToVector(a)).cross(c
				.subToVector(a)));
		final double signq = (q.subToVector(a)).dot((b.subToVector(a)).cross(c
				.subToVector(a)));
		return signp * signq <= 0;
	}

	public WB_ClassifyPolygonToPlane classifyPolygonToPlane(
			final WB_Polygon poly) {
		int numInFront = 0;
		int numBehind = 0;
		for (int i = 0; i < poly.getN(); i++) {
			switch (classifyPointToPlane(poly.getPoint(i))) {
			case POINT_IN_FRONT_OF_PLANE:
				numInFront++;
				break;
			case POINT_BEHIND_PLANE:
				numBehind++;
				break;
			}
			if (numBehind > 0 && numInFront > 0) {
				return WB_ClassifyPolygonToPlane.POLYGON_STRADDLING_PLANE;
			}
		}

		if (numInFront > 0) {
			return WB_ClassifyPolygonToPlane.POLYGON_IN_FRONT_OF_PLANE;
		}
		if (numBehind > 0) {
			return WB_ClassifyPolygonToPlane.POLYGON_BEHIND_PLANE;
		}
		return WB_ClassifyPolygonToPlane.POLYGON_ON_PLANE;
	}

	public static WB_ClassifyPolygonToPlane classifyPolygonToPlane(
			final WB_Polygon poly, final WB_Plane P) {
		int numInFront = 0;
		int numBehind = 0;
		for (int i = 0; i < poly.getN(); i++) {
			switch (classifyPointToPlane(poly.getPoint(i), P)) {
			case POINT_IN_FRONT_OF_PLANE:
				numInFront++;
				break;
			case POINT_BEHIND_PLANE:
				numBehind++;
				break;
			}
			if (numBehind != 0 && numInFront != 0) {
				return WB_ClassifyPolygonToPlane.POLYGON_STRADDLING_PLANE;
			}
		}

		if (numInFront != 0) {
			return WB_ClassifyPolygonToPlane.POLYGON_IN_FRONT_OF_PLANE;
		}
		if (numBehind != 0) {
			return WB_ClassifyPolygonToPlane.POLYGON_BEHIND_PLANE;
		}
		return WB_ClassifyPolygonToPlane.POLYGON_ON_PLANE;
	}

	/**
	 *  Are the planes equal?
	 * @param P
	 * @param Q
	 * @return true/false
	 */
	public static boolean isEqual(final WB_Plane P, final WB_Plane Q) {
		if (!WB_Epsilon.isZeroSq(WB_Distance.sqDistance(P.getOrigin(), Q))) {
			return false;
		}
		if (!WB_Epsilon.isZeroSq(WB_Distance.sqDistance(Q.getOrigin(), P))) {
			return false;
		}
		if (!P.getNormal().isParallelNorm(Q.getNormal())) {
			return false;
		}
		return true;
	}

	private void setAxes() {
		final double x = WB_Fast.abs(n.x);
		final double y = WB_Fast.abs(n.y);

		if (x >= y) {
			u = new WB_Vector(n.z, 0, -n.x);

		} else {
			u = new WB_Vector(0, n.z, -n.y);
		}
		u.normalize();
		v = WB_Vector.cross(n, u);

	}

	// Project point to plane and return coordinates relative to plane axes
	public WB_XY embedPoint(final WB_Point p) {
		return new WB_XY(u.x * (p.x - origin.x) + u.y * (p.y - origin.y) + u.z
				* (p.z - origin.z), v.x * (p.x - origin.x) + v.y
				* (p.y - origin.y) + v.z * (p.z - origin.z));
	}

	// Return embedded point coordinates relative to world axes
	public WB_Point extractPoint(final WB_XY p) {
		return new WB_Point(origin.x + p.x * u.x + p.y * v.x, origin.y + p.x
				* u.y + p.y * v.y, origin.z + p.x * u.z + p.y * v.z);
	}

	// Return embedded point coordinates relative to world axes
	public WB_Point extractPoint(final double x, final double y) {
		return new WB_Point(origin.x + x * u.x + y * v.x, origin.y + x * u.y
				+ y * v.y, origin.z + x * u.z + y * v.z);
	}

	// Return coordinates relative to plane axes
	public WB_Point relativePoint(final WB_Point p) {
		return new WB_Point(u.x * (p.x - origin.x) + u.y * (p.y - origin.y)
				+ u.z * (p.z - origin.z), v.x * (p.x - origin.x) + v.y
				* (p.y - origin.y) + v.z * (p.z - origin.z), n.x
				* (p.x - origin.x) + n.y * (p.y - origin.y) + n.z
				* (p.z - origin.z));

	}

	// Return coordinates relative to world axes
	public WB_Point extractPoint(final WB_Point p) {
		return new WB_Point(origin.x + p.x * u.x + p.y * v.x + p.z * n.x,
				origin.y + p.x * u.y + p.y * v.y + p.z * n.y, origin.z + p.x
						* u.z + p.y * v.z + p.z * n.z);
	}

	// Return coordinates relative to world axes
	public WB_Point extractPoint(final double x, final double y, final double z) {
		return new WB_Point(origin.x + x * u.x + y * v.x + z * n.x, origin.y
				+ x * u.y + y * v.y + z * n.y, origin.z + x * u.z + y * v.z + z
				* n.z);
	}

	// Return new point mirrored across plane
	public WB_Point mirrorPoint(final WB_Point p) {
		if (WB_Epsilon.isZero(WB_Distance.distance(p, this))) {
			return p.get();
		}
		return extractPoint(relativePoint(p).scale(1, 1, -1));

	}

	// Return copy of u coordinate axis in world coordinates
	public WB_Vector getU() {
		return u.get();
	}

	// Return copy of v coordinate axis in world coordinates
	public WB_Vector getV() {
		return v.get();
	}

	// Return copy of w coordinate axis in world coordinates
	public WB_Vector getW() {
		return getNormal();
	}
}
