/**
 * 
 */
package wblut.geom2D;

import java.util.ArrayList;

import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Intersection2D {

	/**
	 * Intersect2 d.
	 *
	 * @param S1 the s1
	 * @param S2 the s2
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult2D intersect2D(final WB_Segment2D S1,
			final WB_Segment2D S2) {
		final double a1 = WB_Triangle2D.twiceSignedTriArea2D(S1.getOrigin(), S1
				.getEnd(), S2.getEnd());
		final double a2 = WB_Triangle2D.twiceSignedTriArea2D(S1.getOrigin(), S1
				.getEnd(), S2.getOrigin());
		if (!WB_Epsilon.isZero(a1) && !WB_Epsilon.isZero(a2) && a1 * a2 < 0) {
			final double a3 = WB_Triangle2D.twiceSignedTriArea2D(S2.getOrigin(),
					S2.getEnd(), S1.getOrigin());
			final double a4 = a3 + a2 - a1;
			if (a3 * a4 < 0) {
				final double t1 = a3 / (a3 - a4);
				final double t2 = a1 / (a1 - a2);
				final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
				i.intersection = true;
				i.t1 = t1;
				i.t2 = t2;
				i.p1 = S1.getPoint(t1);
				i.p2 = S2.getPoint(t2);
				i.sqDist = 0;
				return i;

			}

		}
		final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
		i.intersection = false;
		i.t1 = 0;
		i.t2 = 0;
		i.sqDist = Float.POSITIVE_INFINITY;
		return i;

	}

	/**
	 * Intersect2 d into.
	 *
	 * @param S1 the s1
	 * @param S2 the s2
	 * @param i the i
	 */
	public static void intersect2DInto(final WB_Segment2D S1,
			final WB_Segment2D S2, final WB_IntersectionResult2D i) {
		final double a1 = WB_Triangle2D.twiceSignedTriArea2D(S1.getOrigin(), S1
				.getEnd(), S2.getEnd());
		final double a2 = WB_Triangle2D.twiceSignedTriArea2D(S1.getOrigin(), S1
				.getEnd(), S2.getOrigin());
		if (!WB_Epsilon.isZero(a1) && !WB_Epsilon.isZero(a2) && a1 * a2 < 0) {
			final double a3 = WB_Triangle2D.twiceSignedTriArea2D(S2.getOrigin(),
					S2.getEnd(), S1.getOrigin());
			final double a4 = a3 + a2 - a1;
			if (a3 * a4 < 0) {
				final double t1 = a3 / (a3 - a4);
				final double t2 = a1 / (a1 - a2);
				i.intersection = true;
				i.t1 = t1;
				i.t2 = t2;
				i.p1 = S1.getPoint(t1);
				i.p2 = S2.getPoint(t2);
				i.sqDist = 0;

			}

		} else {
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.sqDist = Float.POSITIVE_INFINITY;
		}
	}

	public static WB_ExplicitSegment2D[] splitSegment2D(
			final WB_ExplicitSegment2D S, final WB_Line2D L) {
		final WB_ExplicitSegment2D[] result = new WB_ExplicitSegment2D[2];
		final WB_IntersectionResult2D ir2D = WB_ClosestPoint2D.closestPoint2D(
				S, L);
		if (!ir2D.intersection) {
			return null;
		}
		if (L.classifyPointToLine2D(S.getOrigin()) == WB_ClassifyPointToLine2D.POINT_IN_FRONT_OF_LINE) {
			result[0] = new WB_ExplicitSegment2D(S.getOrigin(), ir2D.p2);
			result[1] = new WB_ExplicitSegment2D(ir2D.p2, S.getEnd());
		} else if (L.classifyPointToLine2D(S.getOrigin()) == WB_ClassifyPointToLine2D.POINT_BEHIND_LINE) {
			result[1] = new WB_ExplicitSegment2D(S.getOrigin(), ir2D.p2);
			result[0] = new WB_ExplicitSegment2D(ir2D.p2, S.getEnd());
		}

		return result;

	}

	public static double[] intervalIntersection(final double u0,
			final double u1, final double v0, final double v1) {
		if ((u0 >= u1) || (v0 >= v1)) {
			throw new IllegalArgumentException(
					"Interval degenerate or reversed.");
		}
		final double[] result = new double[3];
		if ((u1 < v0) || (u0 > v1)) {
			return result;
		}
		if (u1 > v0) {
			if (u0 < v1) {
				result[0] = 2;
				if (u0 < v0) {
					result[1] = v0;
				} else {
					result[1] = u0;
				}
				if (u1 > v1) {
					result[2] = v1;
				} else {
					result[2] = u1;
				}
			} else {
				result[0] = 1;
				result[1] = u0;
			}
		} else {
			result[0] = 1;
			result[1] = u1;
		}
		return result;

	}

	public static WB_Polygon2D[] splitPolygon2D(final WB_Polygon2D poly,
			final WB_Line2D L) {
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

		}
		final WB_Polygon2D[] result = new WB_Polygon2D[2];
		result[0] = new WB_Polygon2D(frontVerts);
		result[1] = new WB_Polygon2D(backVerts);
		return result;

	}

	public static ArrayList<WB_XY> intersect2D(final WB_Circle C0,
			final WB_Circle C1) {
		final ArrayList<WB_XY> result = new ArrayList<WB_XY>();
		final WB_XY u = C1.getCenter().subAndCopy(C0.getCenter());
		final double d2 = u.mag2();
		final double d = Math.sqrt(d2);
		if (WB_Epsilon.isEqualAbs(d, C0.getRadius() + C1.getRadius())) {
			result.add(WB_XY.interpolate(C0.getCenter(), C1.getCenter(), C0.getRadius()
					/ (C0.getRadius() + C1.getRadius())));
			return result;
		}
		if (d > (C0.getRadius() + C1.getRadius())
				|| d < WB_Fast.abs(C0.getRadius() - C1.getRadius())) {
			return result;
		}
		final double r02 = C0.getRadius() * C0.getRadius();
		final double r12 = C1.getRadius() * C1.getRadius();
		final double a = (r02 - r12 + d2) / (2 * d);
		final double h = Math.sqrt(r02 - a * a);
		final WB_XY c = u.multAndCopy(a / d).add(C0.getCenter());
		final double p0x = c.x + h * (C1.getCenter().y - C0.getCenter().y) / d;
		final double p0y = c.y - h * (C1.getCenter().x - C0.getCenter().x) / d;
		final double p1x = c.x - h * (C1.getCenter().y - C0.getCenter().y) / d;
		final double p1y = c.y + h * (C1.getCenter().x - C0.getCenter().x) / d;
		final WB_XY p0 = new WB_XY(p0x, p0y);
		result.add(p0);
		final WB_XY p1 = new WB_XY(p1x, p1y);
		if (!WB_Epsilon.isZeroSq(WB_Distance2D.sqDistance(p0, p1))) {
			result.add(new WB_XY(p1x, p1y));
		}
		return result;
	}

	public static ArrayList<WB_XY> intersect2D(final WB_Line2D L,
			final WB_Circle C) {
		final ArrayList<WB_XY> result = new ArrayList<WB_XY>();

		final double b = 2 * (L.getDirection().x * (L.getOrigin().x - C.getCenter().x) + L
				.getDirection().y
				* (L.getOrigin().y - C.getCenter().y));
		final double c = C.getCenter().mag2() + L.getOrigin().mag2() - 2
				* (C.getCenter().x * L.getOrigin().x + C.getCenter().y * L.getOrigin().y)
				- C.getRadius() * C.getRadius();
		double disc = b * b - 4 * c;
		if (disc < -WB_Epsilon.EPSILON) {
			return result;
		}

		if (WB_Epsilon.isZero(disc)) {
			result.add(L.getPoint(-0.5 * b));
			return result;
		}
		disc = Math.sqrt(disc);
		result.add(L.getPoint(0.5 * (-b + disc)));
		result.add(L.getPoint(0.5 * (-b - disc)));
		return result;
	}

}
