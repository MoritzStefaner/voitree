/**
 * 
 */
package wblut.geom;

import java.util.ArrayList;
import java.util.LinkedList;

import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;
import wblut.tree.WB_AABBNode;
import wblut.tree.WB_AABBTree;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Intersection {

	// SEGMENT-PLANE
	/**
	 * Intersect.
	 *
	 * @param S the s
	 * @param P the p
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Segment S,
			final WB_Plane P) {
		final WB_Vector ab = S.getEnd().subToVector(S.getOrigin());
		double t = (P.d() - P.getNormal().dot(S.getOrigin()))
				/ P.getNormal().dot(ab);
		if (t >= -WB_Epsilon.EPSILON && t <= 1.0 + WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, 1);

			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p1 = S.getPoint(t);
			i.p2 = i.p1;
			i.sqDist = 0;
			return i;
		}
		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = false;
		i.t1 = t;
		i.t2 = t;
		i.sqDist = Float.POSITIVE_INFINITY;
		return i;
	}

	/**
	 * Intersect into.
	 *
	 * @param S the s
	 * @param P the p
	 * @param i the i
	 */
	public static void getIntersectionInto(final WB_Segment S,
			final WB_Plane P, final WB_IntersectionResult i) {
		final WB_Vector ab = S.getEnd().subToVector(S.getOrigin());
		double t = (P.d() - P.getNormal().dot(S.getOrigin()))
				/ P.getNormal().dot(ab);
		if (t >= -WB_Epsilon.EPSILON && t <= 1.0 + WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, 1);
			S.getPointInto(t, i.p1);
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p2 = i.p1;
			i.sqDist = 0;
		} else {
			i.intersection = false;
			i.t1 = t;
			i.t2 = t;
			i.sqDist = Float.POSITIVE_INFINITY;
		}
	}

	/**
	 * Intersect.
	 *
	 * @param a the a
	 * @param b the b
	 * @param P the p
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Point a,
			final WB_Point b, final WB_Plane P) {
		final WB_Vector ab = b.subToVector(a);
		double t = (P.d() - P.getNormal().dot(a)) / P.getNormal().dot(ab);
		if (t >= -WB_Epsilon.EPSILON && t <= 1.0 + WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, 1);

			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p1 = new WB_Point(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y),
					a.z + t * (b.z - a.z));
			i.p2 = i.p1;
			i.sqDist = 0;
			return i;

		}
		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = false;
		i.t1 = 0;
		i.t2 = 0;
		i.sqDist = Float.POSITIVE_INFINITY;
		return i;
	}

	/**
	 * Intersect into.
	 *
	 * @param a the a
	 * @param b the b
	 * @param P the p
	 * @param i the i
	 */
	public static void getIntersectionInto(final WB_Point a, final WB_Point b,
			final WB_Plane P, final WB_IntersectionResult i) {
		final WB_Vector ab = b.subToVector(a);
		double t = (P.d() - P.getNormal().dot(a)) / P.getNormal().dot(ab);

		if (t >= -WB_Epsilon.EPSILON && t <= 1.0 + WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, 1);
			i.p1.set(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y), a.z + t
					* (b.z - a.z));
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p2 = i.p1;
			i.sqDist = 0;
		} else {
			i.intersection = false;
			i.t1 = t;
			i.t2 = t;
			i.sqDist = Float.POSITIVE_INFINITY;
		}

	}

	// RAY-PLANE
	/**
	 * Intersect.
	 *
	 * @param R the s
	 * @param P the p
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Ray R,
			final WB_Plane P) {
		final WB_Vector ab = R.getDirection();
		double t = (P.d() - P.getNormal().dot(R.getOrigin()))
				/ P.getNormal().dot(ab);

		if (t >= -WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, Double.POSITIVE_INFINITY);
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p1 = R.getPoint(t);
			i.p2 = i.p1;
			i.sqDist = 0;
			return i;
		}
		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = false;
		i.t1 = t;
		i.t2 = t;
		i.sqDist = Float.POSITIVE_INFINITY;
		return i;
	}

	public static WB_IntersectionResult getIntersection(final WB_Ray R,
			final WB_AABB aabb) {
		final WB_Vector d = R.getDirection();
		final WB_Point p = R.getOrigin();
		double tmin = 0.0;
		double tmax = Double.POSITIVE_INFINITY;
		if (WB_Epsilon.isZero(d.x)) {
			if ((p.x < aabb.min.x) || (p.x > aabb.max.x)) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}
		} else {
			final double ood = 1.0 / d.x;
			double t1 = (aabb.min.x - p.x) * ood;
			double t2 = (aabb.max.x - p.x) * ood;
			if (t1 > t2) {
				final double tmp = t1;
				t1 = t2;
				t2 = tmp;
			}
			tmin = Math.max(tmin, t1);
			tmax = Math.min(tmax, t2);
			if (tmin > tmax) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}

		}
		if (WB_Epsilon.isZero(d.y)) {
			if ((p.y < aabb.min.y) || (p.y > aabb.max.y)) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}
		} else {
			final double ood = 1.0 / d.y;
			double t1 = (aabb.min.y - p.y) * ood;
			double t2 = (aabb.max.y - p.y) * ood;
			if (t1 > t2) {
				final double tmp = t1;
				t1 = t2;
				t2 = tmp;
			}
			tmin = Math.max(tmin, t1);
			tmax = Math.min(tmax, t2);
			if (tmin > tmax) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}

		}
		if (WB_Epsilon.isZero(d.z)) {
			if ((p.z < aabb.min.z) || (p.z > aabb.max.z)) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}
		} else {
			final double ood = 1.0 / d.z;
			double t1 = (aabb.min.z - p.z) * ood;
			double t2 = (aabb.max.z - p.z) * ood;
			if (t1 > t2) {
				final double tmp = t1;
				t1 = t2;
				t2 = tmp;
			}
			tmin = Math.max(tmin, t1);
			tmax = Math.min(tmax, t2);
			if (tmin > tmax) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				i.intersection = false;
				i.t1 = 0;
				i.t2 = 0;
				i.p1 = null;
				i.p2 = null;
				i.sqDist = Double.POSITIVE_INFINITY;
				return i;
			}

		}

		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = true;
		i.t1 = tmin;
		i.t2 = 0;
		i.p1 = R.getPoint(tmin);
		i.p2 = null;
		i.sqDist = WB_Distance.sqDistance(p, i.p1);

		return i;
	}

	/**
	 * Intersect into.
	 *
	 * @param R the s
	 * @param P the p
	 * @param i the i
	 */
	public static void getInterseciontInto(final WB_Ray R, final WB_Plane P,
			final WB_IntersectionResult i) {
		final WB_Vector ab = R.getDirection();
		double t = (P.d() - P.getNormal().dot(R.getOrigin()))
				/ P.getNormal().dot(ab);

		if (t >= -WB_Epsilon.EPSILON) {
			t = WB_Epsilon.clampEpsilon(t, 0, Double.POSITIVE_INFINITY);
			R.getPointInto(t, i.p1);
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p2 = i.p1;
			i.sqDist = 0;
		} else {
			i.intersection = false;
			i.t1 = t;
			i.t2 = t;
			i.sqDist = Float.POSITIVE_INFINITY;
		}
	}

	// LINE-PLANE

	/**
	 * Intersect.
	 *
	 * @param L the l
	 * @param P the p
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Line L,
			final WB_Plane P) {
		final WB_Vector ab = L.getDirection();
		final double denom = P.getNormal().dot(ab);
		if (!WB_Epsilon.isZero(denom)) {
			final double t = (P.d() - P.getNormal().dot(L.getOrigin())) / denom;

			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = t;
			i.t2 = t;
			i.p1 = L.getPoint(t);
			i.p2 = i.p1;
			i.sqDist = 0;
			return i;
		} else {
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.sqDist = Float.POSITIVE_INFINITY;
			return i;
		}
	}

	// PLANE-PLANE

	/**
	 * Intersect.
	 *
	 * @param P1 the p1
	 * @param P2 the p2
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Plane P1,
			final WB_Plane P2) {

		final WB_Normal N1 = P1.getNormal().get();
		final WB_Normal N2 = P2.getNormal().get();
		final WB_Point N1xN2 = new WB_Point(N1.cross(N2));
		final double d1 = P1.d();
		final double d2 = P2.d();
		if (WB_Epsilon.isZeroSq(N1xN2.mag2())) {
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.sqDist = Float.POSITIVE_INFINITY;
			return i;
		} else {
			final double N1N2 = N1.dot(N2);
			final double det = 1 - N1N2 * N1N2;
			final double c1 = (d1 - d2 * N1N2) / det;
			final double c2 = (d2 - d1 * N1N2) / det;
			final WB_Point O = new WB_Point(N1.multAndCopy(c1).add(
					N2.multAndCopy(c2)));

			final WB_Line L = new WB_Line(O, N1xN2);
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = 0;
			i.t2 = 0;
			i.p1 = O;
			i.p2 = O.addAndCopy(N1xN2);
			i.L = L;
			i.sqDist = 0;
			return i;

		}

	}

	// PLANE-PLANE-PLANE
	/**
	 * Intersect.
	 *
	 * @param P1 the p1
	 * @param P2 the p2
	 * @param P3 the p3
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult getIntersection(final WB_Plane P1,
			final WB_Plane P2, final WB_Plane P3) {

		final WB_Normal N1 = P1.getNormal().get();
		final WB_Normal N2 = P2.getNormal().get();
		final WB_Normal N3 = P3.getNormal().get();

		final double denom = N1.dot(N2.cross(N3));

		if (WB_Epsilon.isZero(denom)) {
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.sqDist = Float.POSITIVE_INFINITY;
			return i;
		} else {
			final WB_Point N1xN2 = new WB_Point(N1.cross(N2));
			final WB_Point N2xN3 = new WB_Point(N2.cross(N3));
			final WB_Point N3xN1 = new WB_Point(N3.cross(N1));
			final double d1 = P1.d();
			final double d2 = P2.d();
			final double d3 = P3.d();
			final WB_Point p = N2xN3.multAndCopy(d1);
			p.add(N3xN1.multAndCopy(d2));
			p.add(N1xN2.multAndCopy(d3));
			p.div(denom);

			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = true;
			i.t1 = 0;
			i.t2 = 0;
			i.p1 = p;
			i.p2 = i.p1;
			i.sqDist = 0;
			return i;

		}

	}

	// AABB-AABB

	/**
	 * Check intersection.
	 *
	 * @param one the one
	 * @param other the other
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_AABB one,
			final WB_AABB other) {
		if (one.max.x < other.min.x || one.min.x > other.max.x) {
			return false;
		}
		if (one.max.y < other.min.y || one.min.y > other.max.y) {
			return false;
		}
		if (one.max.z < other.min.z || one.min.z > other.max.z) {
			return false;
		}
		return true;
	}

	// OBB-OBB

	/**
	 * Check intersection.
	 *
	 * @param AABB the aABB
	 * @param P the p
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_AABB AABB, final WB_Plane P) {
		final WB_Point c = AABB.max.addAndCopy(AABB.min).mult(0.5);
		final WB_Point e = AABB.max.subAndCopy(c);
		final double r = e.x * WB_Fast.abs(P.getNormal().x) + e.y
				* WB_Fast.abs(P.getNormal().y) + e.z
				* WB_Fast.abs(P.getNormal().z);
		final double s = P.getNormal().dot(c) - P.d();
		return WB_Fast.abs(s) <= r;
	}

	// OBB-PLANE

	/**
	 * Check intersection.
	 *
	 * @param AABB the aABB
	 * @param S the s
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_AABB AABB,
			final WB_Sphere S) {
		final double d2 = WB_Distance.sqDistance(S.getCenter(), AABB);
		return d2 <= S.getRadius() * S.getRadius();
	}

	// OBB-SPHERE

	/**
	 * Check intersection.
	 *
	 * @param T the t
	 * @param S the s
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_Triangle T,
			final WB_Sphere S) {
		final WB_Point p = WB_ClosestPoint.closestPoint(S.getCenter(), T);

		return (p.subToVector(S.getCenter())).mag2() <= S.getRadius()
				* S.getRadius();
	}

	// TRIANGLE-AABB

	/**
	 * Check intersection.
	 *
	 * @param T the t
	 * @param AABB the aABB
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_Triangle T,
			final WB_AABB AABB) {
		double p0, p1, p2, r;
		final WB_Point c = AABB.max.addAndCopy(AABB.min).mult(0.5);
		final double e0 = (AABB.max.x - AABB.min.x) * 0.5;
		final double e1 = (AABB.max.y - AABB.min.y) * 0.5;
		final double e2 = (AABB.max.z - AABB.min.z) * 0.5;
		final WB_Point v0 = T.p1().get();
		final WB_Point v1 = T.p2().get();
		final WB_Point v2 = T.p3().get();

		v0.sub(c);
		v1.sub(c);
		v2.sub(c);

		final WB_Vector f0 = v1.subToVector(v0);
		final WB_Vector f1 = v2.subToVector(v1);
		final WB_Vector f2 = v0.subToVector(v2);

		// a00
		final WB_Vector a = new WB_Vector(0, -f0.z, f0.y);// u0xf0
		if (a.isZero()) {
			a.set(0, v0.y, v0.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a01
		a.set(0, -f1.z, f1.y);// u0xf1
		if (a.isZero()) {
			a.set(0, v1.y, v1.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a02
		a.set(0, -f2.z, f2.y);// u0xf2
		if (a.isZero()) {
			a.set(0, v2.y, v2.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a10
		a.set(f0.z, 0, -f0.x);// u1xf0
		if (a.isZero()) {
			a.set(v0.x, 0, v0.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}
		// a11
		a.set(f1.z, 0, -f1.x);// u1xf1
		if (a.isZero()) {
			a.set(v1.x, 0, v1.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a12
		a.set(f2.z, 0, -f2.x);// u1xf2
		if (a.isZero()) {
			a.set(v2.x, 0, v2.z);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a20
		a.set(-f0.y, f0.x, 0);// u2xf0
		if (a.isZero()) {
			a.set(v0.x, v0.y, 0);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}
		// a21
		a.set(-f1.y, f1.x, 0);// u2xf1
		if (a.isZero()) {
			a.set(v1.x, v1.y, 0);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		// a22
		a.set(-f2.y, f2.x, 0);// u2xf2
		if (a.isZero()) {
			a.set(v2.x, v2.y, 0);
		}
		if (!a.isZero()) {
			p0 = v0.dot(a);
			p1 = v1.dot(a);
			p2 = v2.dot(a);
			r = e0 * WB_Fast.abs(a.x) + e1 * WB_Fast.abs(a.y) + e2
					* WB_Fast.abs(a.z);
			if (WB_Fast.max(WB_Fast.min(p0, p1, p2), -WB_Fast.max(p0, p1, p2)) > r) {
				return false;
			}
		}

		if (WB_Fast.max(v0.x, v1.x, v2.x) < -e0
				|| WB_Fast.max(v0.x, v1.x, v2.x) > e0) {
			return false;
		}
		if (WB_Fast.max(v0.y, v1.y, v2.y) < -e1
				|| WB_Fast.max(v0.y, v1.y, v2.y) > e1) {
			return false;
		}
		if (WB_Fast.max(v0.z, v1.z, v2.z) < -e2
				|| WB_Fast.max(v0.z, v1.z, v2.z) > e2) {
			return false;
		}

		WB_Vector n = f0.cross(f1);
		WB_Plane P;
		if (!n.isZero()) {
			P = new WB_Plane(n, n.dot(v0));
		} else {
			n = f0.cross(f2);
			n = f0.cross(n);
			if (!n.isZero()) {
				P = new WB_Plane(n, n.dot(v0));
			} else {
				final WB_Vector t = T.p3().subToVector(T.p1());
				final double a1 = T.p1().dot(t);
				final double a2 = T.p2().dot(t);
				final double a3 = T.p3().dot(t);
				if (a1 < WB_Fast.min(a2, a3)) {
					if (a2 < a3) {
						return checkIntersection(new WB_ExplicitSegment(T.p1(),
								T.p3()), AABB);
					} else {
						return checkIntersection(new WB_ExplicitSegment(T.p1(),
								T.p2()), AABB);
					}
				} else if (a2 < WB_Fast.min(a1, a3)) {
					if (a1 < a3) {
						return checkIntersection(new WB_ExplicitSegment(T.p2(),
								T.p3()), AABB);
					} else {
						return checkIntersection(new WB_ExplicitSegment(T.p2(),
								T.p1()), AABB);
					}
				} else {
					if (a1 < a2) {
						return checkIntersection(new WB_ExplicitSegment(T.p3(),
								T.p2()), AABB);
					} else {
						return checkIntersection(new WB_ExplicitSegment(T.p3(),
								T.p1()), AABB);
					}
				}

			}

		}
		return checkIntersection(AABB, P);

	}

	// SEGMENT-AABB
	/**
	 * Check intersection.
	 *
	 * @param S the s
	 * @param AABB the aABB
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_Segment S,
			final WB_AABB AABB) {
		final WB_Vector e = AABB.max.subToVector(AABB.min);
		final WB_Vector d = S.getEnd().subToVector(S.getOrigin());
		final WB_Point m = new WB_Point(S.getEnd().x + S.getOrigin().x
				- AABB.min.x - AABB.max.x, S.getEnd().y + S.getOrigin().y
				- AABB.min.y - AABB.max.y, S.getEnd().z + S.getOrigin().z
				- AABB.min.z - AABB.max.z);
		double adx = WB_Fast.abs(d.x);
		if (WB_Fast.abs(m.x) > e.x + adx) {
			return false;
		}
		double ady = WB_Fast.abs(d.y);
		if (WB_Fast.abs(m.y) > e.y + ady) {
			return false;
		}
		double adz = WB_Fast.abs(d.z);
		if (WB_Fast.abs(m.z) > e.z + adz) {
			return false;
		}
		adx += WB_Epsilon.EPSILON;
		ady += WB_Epsilon.EPSILON;
		adz += WB_Epsilon.EPSILON;
		if (WB_Fast.abs(m.y * d.z - m.z * d.y) > e.y * adz + e.z * ady) {
			return false;
		}
		if (WB_Fast.abs(m.z * d.x - m.x * d.z) > e.x * adz + e.z * adx) {
			return false;
		}
		if (WB_Fast.abs(m.x * d.y - m.y * d.x) > e.x * ady + e.y * adx) {
			return false;
		}
		return true;

	}

	// SPHERE-SPHERE

	/**
	 * Check intersection.
	 *
	 * @param S1 the s1
	 * @param S2 the s2
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_Sphere S1,
			final WB_Sphere S2) {
		final WB_Vector d = S1.getCenter().subToVector(S2.getCenter());
		final double d2 = d.mag2();
		final double radiusSum = S1.getRadius() + S2.getRadius();
		return d2 <= radiusSum * radiusSum;
	}

	// RAY-SPHERE

	/**
	 * Check intersection.
	 *
	 * @param R the r
	 * @param S the s
	 * @return true, if successful
	 */
	public static boolean checkIntersection(final WB_Ray R, final WB_Sphere S) {
		final WB_Vector m = R.getOrigin().subToVector(S.getCenter());
		final double c = m.dot(m) - S.getRadius() * S.getRadius();
		if (c <= 0) {
			return true;
		}
		final double b = m.dot(R.getDirection());
		if (b >= 0) {
			return false;
		}
		final double disc = b * b - c;
		if (disc < 0) {
			return false;
		}
		return true;
	}

	public static boolean checkIntersection(final WB_Ray R, final WB_AABB AABB) {
		double t0 = 0;
		double t1 = Double.POSITIVE_INFINITY;
		final double irx = 1.0 / R.direction.x;
		double tnear = (AABB.min.x - R.origin.x) * irx;
		double tfar = (AABB.max.x - R.origin.x) * irx;
		double tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		final double iry = 1.0 / R.direction.y;
		tnear = (AABB.min.y - R.origin.y) * iry;
		tfar = (AABB.max.y - R.origin.y) * iry;
		tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		final double irz = 1.0 / R.direction.z;
		tnear = (AABB.min.z - R.origin.z) * irz;
		tfar = (AABB.max.z - R.origin.z) * irz;
		tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		return true;
	}

	public static ArrayList<WB_AABBNode> getIntersection(final WB_Ray R,
			final WB_AABBTree tree) {
		final ArrayList<WB_AABBNode> result = new ArrayList<WB_AABBNode>();
		final LinkedList<WB_AABBNode> queue = new LinkedList<WB_AABBNode>();
		queue.add(tree.getRoot());
		WB_AABBNode current;
		while (!queue.isEmpty()) {
			current = queue.pop();
			if (checkIntersection(R, current.getAABB())) {
				if (current.isLeaf()) {
					result.add(current);
				} else {
					if (current.getPosChild() != null) {
						queue.add(current.getPosChild());
					}
					if (current.getNegChild() != null) {
						queue.add(current.getNegChild());
					}
					if (current.getMidChild() != null) {
						queue.add(current.getMidChild());
					}
				}
			}

		}

		return result;
	}

	public static boolean checkIntersection(final WB_Line L, final WB_AABB AABB) {
		double t0 = Double.NEGATIVE_INFINITY;
		double t1 = Double.POSITIVE_INFINITY;
		final double irx = 1.0 / L.direction.x;
		double tnear = (AABB.min.x - L.origin.x) * irx;
		double tfar = (AABB.max.x - L.origin.x) * irx;
		double tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		final double iry = 1.0 / L.direction.y;
		tnear = (AABB.min.y - L.origin.y) * iry;
		tfar = (AABB.max.y - L.origin.y) * iry;
		tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		final double irz = 1.0 / L.direction.z;
		tnear = (AABB.min.z - L.origin.z) * irz;
		tfar = (AABB.max.z - L.origin.z) * irz;
		tmp = tnear;
		if (tnear > tfar) {
			tnear = tfar;
			tfar = tmp;
		}
		t0 = (tnear > t0) ? tnear : t0;
		t1 = (tfar < t1) ? tfar : t1;
		if (t0 > t1) {
			return false;
		}
		return true;
	}

	public static ArrayList<WB_AABBNode> getIntersection(final WB_Line L,
			final WB_AABBTree tree) {
		final ArrayList<WB_AABBNode> result = new ArrayList<WB_AABBNode>();
		final LinkedList<WB_AABBNode> queue = new LinkedList<WB_AABBNode>();
		queue.add(tree.getRoot());
		WB_AABBNode current;
		while (!queue.isEmpty()) {
			current = queue.pop();
			if (checkIntersection(L, current.getAABB())) {
				if (current.isLeaf()) {
					result.add(current);
				} else {
					if (current.getPosChild() != null) {
						queue.add(current.getPosChild());
					}
					if (current.getNegChild() != null) {
						queue.add(current.getNegChild());
					}
					if (current.getMidChild() != null) {
						queue.add(current.getMidChild());
					}
				}
			}

		}

		return result;
	}

	public static ArrayList<WB_AABBNode> getIntersection(final WB_Segment S,
			final WB_AABBTree tree) {
		final ArrayList<WB_AABBNode> result = new ArrayList<WB_AABBNode>();
		final LinkedList<WB_AABBNode> queue = new LinkedList<WB_AABBNode>();
		queue.add(tree.getRoot());
		WB_AABBNode current;
		while (!queue.isEmpty()) {
			current = queue.pop();
			if (checkIntersection(S, current.getAABB())) {
				if (current.isLeaf()) {
					result.add(current);
				} else {
					if (current.getPosChild() != null) {
						queue.add(current.getPosChild());
					}
					if (current.getNegChild() != null) {
						queue.add(current.getNegChild());
					}
					if (current.getMidChild() != null) {
						queue.add(current.getMidChild());
					}
				}
			}

		}

		return result;
	}

	public static ArrayList<WB_AABBNode> getIntersection(final WB_Plane P,
			final WB_AABBTree tree) {
		final ArrayList<WB_AABBNode> result = new ArrayList<WB_AABBNode>();
		final LinkedList<WB_AABBNode> queue = new LinkedList<WB_AABBNode>();
		queue.add(tree.getRoot());
		WB_AABBNode current;
		while (!queue.isEmpty()) {
			current = queue.pop();
			if (checkIntersection(current.getAABB(), P)) {
				if (current.isLeaf()) {
					result.add(current);
				} else {
					if (current.getPosChild() != null) {
						queue.add(current.getPosChild());
					}
					if (current.getNegChild() != null) {
						queue.add(current.getNegChild());
					}
					if (current.getMidChild() != null) {
						queue.add(current.getMidChild());
					}
				}
			}

		}

		return result;
	}

	public static ArrayList<WB_ExplicitSegment> getIntersection(
			final WB_Polygon poly, final WB_Plane P) {

		final WB_ClassifyPolygonToPlane cptp = P.classifyPolygonToPlane(poly);
		final ArrayList<WB_ExplicitSegment> result = new ArrayList<WB_ExplicitSegment>();
		/*
		 * if (cptp == WB_ClassifyPolygonToPlane.POLYGON_ON_PLANE) { return
		 * poly.toSegments(); } if ((cptp ==
		 * WB_ClassifyPolygonToPlane.POLYGON_BEHIND_PLANE) || (cptp ==
		 * WB_ClassifyPolygonToPlane.POLYGON_BEHIND_PLANE)) { return result; }
		 */
		final ArrayList<WB_Point> splitVerts = new ArrayList<WB_Point>();
		final int numVerts = poly.getN();
		if (numVerts > 0) {
			WB_Point a = poly.getPoint(numVerts - 1);
			WB_ClassifyPointToPlane aSide = P.classifyPointToPlane(a);
			WB_Point b;
			WB_ClassifyPointToPlane bSide;
			for (int n = 0; n < numVerts; n++) {
				final WB_IntersectionResult i = new WB_IntersectionResult();
				b = poly.getPoint(n);
				bSide = P.classifyPointToPlane(b);
				if (bSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
						WB_Intersection.getIntersectionInto(b, a, P, i);
						splitVerts.add(i.p1);
					}
				} else if (bSide == WB_ClassifyPointToPlane.POINT_BEHIND_PLANE) {
					if (aSide == WB_ClassifyPointToPlane.POINT_IN_FRONT_OF_PLANE) {
						WB_Intersection.getIntersectionInto(a, b, P, i);
						splitVerts.add(i.p1);
					}
				}
				if (aSide == WB_ClassifyPointToPlane.POINT_ON_PLANE) {
					splitVerts.add(a);

				}
				a = b;
				aSide = bSide;

			}
		}

		for (int i = 0; i < splitVerts.size(); i += 2) {
			result.add(new WB_ExplicitSegment(splitVerts.get(i), splitVerts
					.get(i + 1)));

		}

		return result;

	}

}
