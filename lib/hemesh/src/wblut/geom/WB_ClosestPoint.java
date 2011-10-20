/**
 * 
 */
package wblut.geom;

import java.util.ArrayList;

import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_ClosestPoint {
	/**
	 * Closest point on plane.
	 *
	 * @param p point
	 * @param P plane
	 * @return closest point on plane
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Plane P) {
		final WB_Normal n = P.getNormal();
		final double t = n.dot(p) - P.d();
		return new WB_Point(p.x - t * n.x, p.y - t * n.y, p.z - t * n.z);
	}

	/**
	 * Closest point on plane.
	 *
	 * @param p point
	 * @param P plane
	 * @return closest point on plane
	 */
	public static WB_Point closestPoint(final WB_Plane P, final WB_XYZ p) {
		return closestPoint(P, p);
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @return closest point on segment
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Segment S) {
		final WB_Vector ab = S.getEnd().subToVector(S.getOrigin());
		final WB_Vector ac = p.subToVector(S.getOrigin());
		double t = ac.dot(ab);
		if (t <= 0) {
			t = 0;
			return S.getOrigin().get();
		} else {
			final double denom = S.getLength() * S.getLength();
			if (t >= denom) {
				t = 1;
				return S.getEnd().get();
			} else {
				t = t / denom;
				return new WB_Point(S.getPoint(t));
			}
		}
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @return closest point on segment
	 */
	public static WB_Point closestPoint(final WB_Segment S, final WB_XYZ p) {
		return closestPoint(p, S);
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @return parameterized position t of closest point on segment (0=origin, 1=end)
	 */
	public static double closestPointT(final WB_XYZ p, final WB_Segment S) {
		final WB_Vector ab = S.getEnd().subToVector(S.getOrigin());
		final WB_Vector ac = p.subToVector(S.getOrigin());
		double t = ac.dot(ab);
		if (t <= WB_Epsilon.EPSILON) {
			return 0;
		} else {
			final double denom = S.getLength() * S.getLength();
			if (t >= (denom - WB_Epsilon.EPSILON)) {
				t = 1;
				return 1;
			} else {
				t = t / denom;
				return t;
			}
		}
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @return parameterized position t of closest point on segment (0=origin, 1=end)
	 */
	public static double closestPointT(final WB_Segment S, final WB_XYZ p) {
		return closestPointT(p, S);
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @param result WB_Point to store result
	 */

	public static void closestPointInto(final WB_XYZ p, final WB_Segment S,
			final WB_Point result) {
		final WB_Vector ab = S.getEnd().subToVector(S.getOrigin());
		final WB_Vector ac = p.subToVector(S.getOrigin());
		double t = ac.dot(ab);
		if (t <= 0) {
			t = 0;
			result.set(S.getOrigin());
		} else {
			final double denom = S.getLength() * S.getLength();
			if (t >= denom) {
				t = 1;
				result.set(S.getEnd());
			} else {
				t = t / denom;
				final WB_Point o = S.getOrigin();
				result.set(o.x + t * ab.x, o.y + t * ab.y, o.z + t * ab.z);
			}
		}
	}

	/**
	 * Closest point on segment.
	 *
	 * @param p point
	 * @param S segment
	 * @param result WB_Point to store result
	 */
	public static void closestPointInto(final WB_Segment S, final WB_XYZ p,
			final WB_Point result) {
		closestPointInto(p, S, result);
	}

	/**
	 * Closest point to segment.
	 *
	 * @param p point
	 * @param a start point
	 * @param b end point
	 * @return closest point on segment
	 */
	public static WB_Point closestPointToSegment(final WB_XYZ p,
			final WB_Point a, final WB_Point b) {
		final WB_Vector ab = b.subToVector(a);
		final WB_Vector ac = p.subToVector(a);
		double t = ac.dot(ab);
		if (t <= 0) {
			t = 0;
			return a.get();
		} else {
			final double denom = ab.dot(ab);
			if (t >= denom) {
				t = 1;
				return b.get();
			} else {
				t = t / denom;
				return new WB_Point(a.x + t * ab.x, a.y + t * ab.y, a.z + t
						* ab.z);
			}
		}
	}

	/**
	 * Closest point to segment.
	 *
	 * @param p point
	 * @param a start point
	 * @param b end point
	 * @param result WB_Result to store result
	 */
	public static void closestPointToSegmentInto(final WB_XYZ p,
			final WB_Point a, final WB_Point b, final WB_Point result) {
		final WB_Vector ab = b.subToVector(a);
		final WB_Vector ac = p.subToVector(a);
		double t = ac.dot(ab);
		if (t <= 0) {
			t = 0;
			result.set(a);
		} else {
			final double denom = ab.dot(ab);
			if (t >= denom) {
				t = 1;
				result.set(b);
			} else {
				t = t / denom;
				result.set(a.x + t * ab.x, a.y + t * ab.y, a.z + t * ab.z);
			}
		}
	}

	/**
	 * Closest point on line.
	 *
	 * @param p point
	 * @param L line
	 * @return closest point on line
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Line L) {
		final WB_Vector ca = new WB_Vector(p.x - L.getOrigin().y, p.y
				- L.getOrigin().x, p.z - L.getOrigin().z);
		return L.getPoint(ca.dot(L.getDirection()));
	}

	/**
	 * Closest point on line.
	 *
	 * @param p point
	 * @param L line
	 * @param result
	 */
	public static void closestPointInto(final WB_XYZ p, final WB_Line L,
			final WB_Point result) {
		final WB_Vector ca = p.subToVector(L.getOrigin());
		result.set(L.getPoint(ca.dot(L.getDirection())));
	}

	/**
	 * Closest point on line.
	 *
	 * @param p point
	 * @param a point on line
	 * @param b point on line
	 * @return closest point on line
	 */
	public static WB_Point closestPointToLine(final WB_XYZ p, final WB_Point a,
			final WB_Point b) {
		return closestPoint(p, new WB_Line(a, b));
	}

	/**
	 * Closest point on line.
	 *
	 * @param p point
	 * @param a point on line
	 * @param b point on line
	 * @param result
	 */
	public static void closestPointToLineInto(final WB_XYZ p, final WB_Point a,
			final WB_Point b, final WB_Point result) {
		closestPointInto(p, new WB_Line(a, b), result);
	}

	/**
	 * Closest point on ray.
	 *
	 * @param p point
	 * @param R ray
	 * @return closest point on ray
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Ray R) {
		final WB_Vector ac = p.subToVector(R.getOrigin());
		double t = ac.dot(R.getDirection());
		if (t <= 0) {
			t = 0;
			return R.getOrigin().get();
		} else {
			return new WB_Point(R.getPoint(t));
		}
	}

	/**
	 * Closest point on ray.
	 *
	 * @param p point
	 * @param R ray
	 * @param result
	 */
	public static void closestPointInto(final WB_XYZ p, final WB_Ray R,
			final WB_Point result) {
		final WB_Vector ac = p.subToVector(R.getOrigin());
		double t = ac.dot(R.getDirection());
		if (t <= 0) {
			t = 0;
			result.set(R.getOrigin().get());
		} else {
			result.set(R.getPoint(t));
		}
	}

	/**
	 * Closest point on ray.
	 *
	 * @param p point
	 * @param a start point
	 * @param b point on ray
	 * @return closest point on ray
	 */
	public static WB_Point closestPointToRay(final WB_XYZ p, final WB_Point a,
			final WB_Point b) {
		return closestPoint(p, new WB_Ray(a, b));
	}

	/**
	 * Closest point on ray.
	 *
	 * @param p point
	 * @param a start point
	 * @param b point on ray
	 */
	public static void closestPointToRayInto(final WB_XYZ p, final WB_Point a,
			final WB_Point b, final WB_Point result) {
		closestPointInto(p, new WB_Ray(a, b), result);
	}

	/**
	 * Closest point on axis-aligned box.
	 *
	 * @param p point
	 * @param AABB AABB
	 * @return closest point on axis-aligned box
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_AABB AABB) {
		final WB_Point result = new WB_Point();
		double v = p.x;
		if (v < AABB.min.x) {
			v = AABB.min.x;
		}
		if (v > AABB.max.x) {
			v = AABB.max.x;
		}
		result.x = v;
		v = p.y;
		if (v < AABB.min.y) {
			v = AABB.min.y;
		}
		if (v > AABB.max.y) {
			v = AABB.max.y;
		}
		result.y = v;
		v = p.z;
		if (v < AABB.min.z) {
			v = AABB.min.z;
		}
		if (v > AABB.max.z) {
			v = AABB.max.z;
		}
		result.z = v;
		return result;
	}

	/**
	 * Closest point on axis-aligned box.
	 *
	 * @param p point
	 * @param AABB AABB
	 * @param result
	 */
	public static void closestPoint(final WB_XYZ p, final WB_AABB AABB,
			final WB_Point result) {
		double v = p.x;
		if (v < AABB.min.x) {
			v = AABB.min.x;
		}
		if (v > AABB.max.x) {
			v = AABB.max.x;
		}
		result.x = v;
		v = p.y;
		if (v < AABB.min.y) {
			v = AABB.min.y;
		}
		if (v > AABB.max.y) {
			v = AABB.max.y;
		}
		result.y = v;
		v = p.z;
		if (v < AABB.min.z) {
			v = AABB.min.z;
		}
		if (v > AABB.max.z) {
			v = AABB.max.z;
		}
		result.z = v;
	}

	// POINT-TRIANGLE

	/**
	 * Closest point on triangle.
	 *
	 * @param p point
	 * @param T triangle
	 * @return closest point on triangle
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Triangle T) {
		final WB_Vector ab = T.p2().subToVector(T.p1());
		final WB_Vector ac = T.p3().subToVector(T.p1());
		final WB_Vector ap = p.subToVector(T.p1());
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return T.p1().get();
		}

		final WB_Vector bp = p.subToVector(T.p2());
		final double d3 = ab.dot(bp);
		final double d4 = ac.dot(bp);
		if (d3 >= 0 && d4 <= d3) {
			return T.p2().get();
		}

		final double vc = d1 * d4 - d3 * d2;
		if (vc <= 0 && d1 >= 0 && d3 <= 0) {
			final double v = d1 / (d1 - d3);
			return T.p1().addAndCopy(ab.mult(v));
		}

		final WB_Vector cp = p.subToVector(T.p3());
		final double d5 = ab.dot(cp);
		final double d6 = ac.dot(cp);
		if (d6 >= 0 && d5 <= d6) {
			return T.p3().get();
		}

		final double vb = d5 * d2 - d1 * d6;
		if (vb <= 0 && d2 >= 0 && d6 <= 0) {
			final double w = d2 / (d2 - d6);
			return T.p1().addAndCopy(ac.mult(w));
		}

		final double va = d3 * d6 - d5 * d4;
		if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
			final double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));
			return T.p2().addAndCopy((T.p3().subToVector(T.p2())).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		return T.p1().addAndCopy(ab.mult(v).add(ac.mult(w)));
	}

	/**
	 * Closest point on triangle.
	 *
	 * @param p point
	 * @param T triangle
	 * @param result
	 */
	public static void closestPointInto(final WB_XYZ p, final WB_Triangle T,
			final WB_Point result) {
		final WB_Vector ab = T.p2().subToVector(T.p1());
		final WB_Vector ac = T.p3().subToVector(T.p1());
		final WB_Vector ap = p.subToVector(T.p1());
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			result.set(T.p1());
		} else {
			final WB_Vector bp = p.subToVector(T.p2());
			final double d3 = ab.dot(bp);
			final double d4 = ac.dot(bp);
			if (d3 >= 0 && d4 <= d3) {
				result.set(T.p2());
			} else {
				final double vc = d1 * d4 - d3 * d2;
				if (vc <= 0 && d1 >= 0 && d3 <= 0) {
					final double v = d1 / (d1 - d3);
					result.set(T.p1().addAndCopy(ab.mult(v)));
				} else {
					final WB_Vector cp = p.subToVector(T.p3());
					final double d5 = ab.dot(cp);
					final double d6 = ac.dot(cp);

					if (d6 >= 0 && d5 <= d6) {
						result.set(T.p3().get());
					} else {
						final double vb = d5 * d2 - d1 * d6;
						if (vb <= 0 && d2 >= 0 && d6 <= 0) {
							final double w = d2 / (d2 - d6);
							result.set(T.p1().addAndCopy(ac.mult(w)));
						} else {
							final double va = d3 * d6 - d5 * d4;
							if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
								final double w = (d4 - d3)
										/ ((d4 - d3) + (d5 - d6));
								result.set(T.p2().addAndCopy(
										(T.p3().subToVector(T.p2())).mult(w)));
							} else {
								final double denom = 1.0 / (va + vb + vc);
								final double v = vb * denom;
								final double w = vc * denom;
								result.set(T.p1().addAndCopy(
										ab.mult(v).add(ac.mult(w))));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Closest point on triangle.
	 *
	 * @param p point
	 * @param a first point
	 * @param b second point
	 * @param c third point
	 * @return closest point on triangle
	 */
	public static WB_Point closestPointToTriangle(final WB_XYZ p,
			final WB_Point a, final WB_Point b, final WB_Point c) {
		final WB_Vector ab = b.subToVector(a);
		final WB_Vector ac = c.subToVector(a);
		final WB_Vector ap = p.subToVector(a);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return a.get();
		}

		final WB_Vector bp = p.subToVector(b);
		final double d3 = ab.dot(bp);
		final double d4 = ac.dot(bp);
		if (d3 >= 0 && d4 <= d3) {
			return b.get();
		}

		final double vc = d1 * d4 - d3 * d2;
		if (vc <= 0 && d1 >= 0 && d3 <= 0) {
			final double v = d1 / (d1 - d3);
			return a.addAndCopy(ab.mult(v));
		}

		final WB_Vector cp = p.subToVector(c);
		final double d5 = ab.dot(cp);
		final double d6 = ac.dot(cp);
		if (d6 >= 0 && d5 <= d6) {
			return c.get();
		}

		final double vb = d5 * d2 - d1 * d6;
		if (vb <= 0 && d2 >= 0 && d6 <= 0) {
			final double w = d2 / (d2 - d6);
			return a.addAndCopy(ac.mult(w));
		}

		final double va = d3 * d6 - d5 * d4;
		if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
			final double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));
			return b.addAndCopy((c.subToVector(b)).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		return a.addAndCopy(ab.mult(v).add(ac.mult(w)));
	}

	/**
	 * Closest point on triangle.
	 *
	 * @param p point
	 * @param a first point
	 * @param b second point
	 * @param c third point
	 * @param result
	 */
	public static void closestPointToTriangleInto(final WB_XYZ p,
			final WB_Point a, final WB_Point b, final WB_Point c,
			final WB_Point result) {
		final WB_Vector ab = b.subToVector(a);
		final WB_Vector ac = c.subToVector(a);
		final WB_Vector ap = p.subToVector(a);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			result.set(a);
		} else {
			final WB_Vector bp = p.subToVector(b);
			final double d3 = ab.dot(bp);
			final double d4 = ac.dot(bp);
			if (d3 >= 0 && d4 <= d3) {
				result.set(b);
			} else {
				final double vc = d1 * d4 - d3 * d2;
				if (vc <= 0 && d1 >= 0 && d3 <= 0) {
					final double v = d1 / (d1 - d3);
					result.set(a.addAndCopy(ab.mult(v)));
				} else {
					final WB_Vector cp = p.subToVector(c);
					final double d5 = ab.dot(cp);
					final double d6 = ac.dot(cp);

					if (d6 >= 0 && d5 <= d6) {
						result.set(c.get());
					} else {
						final double vb = d5 * d2 - d1 * d6;
						if (vb <= 0 && d2 >= 0 && d6 <= 0) {
							final double w = d2 / (d2 - d6);
							result.set(a.addAndCopy(ac.mult(w)));
						} else {
							final double va = d3 * d6 - d5 * d4;
							if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
								final double w = (d4 - d3)
										/ ((d4 - d3) + (d5 - d6));
								result.set(b.addAndCopy((c.subToVector(b))
										.mult(w)));
							} else {
								final double denom = 1.0 / (va + vb + vc);
								final double v = vb * denom;
								final double w = vc * denom;
								result.set(a.addAndCopy(ab.mult(v).add(
										ac.mult(w))));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Closest point on periphery of triangle.
	 *
	 * @param p point
	 * @param T triangle
	 * @return closest point on periphery of triangle
	 */
	public static WB_Point closestPointOnPeriphery(final WB_XYZ p,
			final WB_Triangle T) {
		final WB_Vector ab = T.p2().subToVector(T.p1());
		final WB_Vector ac = T.p3().subToVector(T.p1());
		final WB_Vector ap = p.subToVector(T.p1());
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return T.p1().get();
		}

		final WB_Vector bp = p.subToVector(T.p2());
		final double d3 = ab.dot(bp);
		final double d4 = ac.dot(bp);
		if (d3 >= 0 && d4 <= d3) {
			return T.p2().get();
		}

		final double vc = d1 * d4 - d3 * d2;
		if (vc <= 0 && d1 >= 0 && d3 <= 0) {
			final double v = d1 / (d1 - d3);
			return T.p1().addAndCopy(ab.mult(v));
		}

		final WB_Vector cp = p.subToVector(T.p3());
		final double d5 = ab.dot(cp);
		final double d6 = ac.dot(cp);
		if (d6 >= 0 && d5 <= d6) {
			return T.p3().get();
		}

		final double vb = d5 * d2 - d1 * d6;
		if (vb <= 0 && d2 >= 0 && d6 <= 0) {
			final double w = d2 / (d2 - d6);
			return T.p1().addAndCopy(ac.mult(w));
		}

		final double va = d3 * d6 - d5 * d4;
		if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
			final double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));
			return T.p2().addAndCopy((T.p3().subToVector(T.p2())).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		final double u = 1 - v - w;
		T.p3().subToVector(T.p2());
		if (WB_Epsilon.isZero(u - 1)) {
			return T.p1().get();
		}
		if (WB_Epsilon.isZero(v - 1)) {
			return T.p2().get();
		}
		if (WB_Epsilon.isZero(w - 1)) {
			return T.p3().get();
		}
		final WB_Point A = closestPointToSegment(p, T.p2(), T.p3());
		final double dA2 = WB_Distance.sqDistance(p, A);
		final WB_Point B = closestPointToSegment(p, T.p1(), T.p3());
		final double dB2 = WB_Distance.sqDistance(p, B);
		final WB_Point C = closestPointToSegment(p, T.p1(), T.p2());
		final double dC2 = WB_Distance.sqDistance(p, C);
		if ((dA2 < dB2) && (dA2 < dC2)) {
			return A;
		} else if ((dB2 < dA2) && (dB2 < dC2)) {
			return B;
		} else {
			return C;
		}

	}

	// POINT-POLYGON

	/**
	 * Closest point on polygon.
	 *
	 * @param p point
	 * @param poly polygon
	 * @return closest point on polygon
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Polygon poly) {
		final ArrayList<WB_IndexedTriangle> tris = poly.triangulate();
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_Point closest = new WB_Point();
		WB_Point tmp;
		WB_IndexedTriangle T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint(p, T);
			final double d2 = WB_Distance.distance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}

		return closest;
	}

	/**
	 * Closest point on triangulated polygon.
	 *
	 * @param p point
	 * @param tris triangulation of polygon
	 * @return closest point on polygon
	 */
	public static WB_Point closestPoint(final WB_XYZ p,
			final ArrayList<? extends WB_Triangle> tris) {
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_Point closest = new WB_Point();
		WB_Point tmp;
		WB_Triangle T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint(p, T);
			final double d2 = WB_Distance.distance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}

		return closest;
	}

	/**
	 * Closest point on periphery of polygon.
	 *
	 * @param p point
	 * @param poly polygon
	 * @return closest point on periphery of polygon
	 */
	public static WB_Point closestPointOnPeriphery(final WB_XYZ p,
			final WB_Polygon poly) {
		final ArrayList<WB_IndexedTriangle> tris = poly.triangulate();
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_Point closest = new WB_Point();
		WB_Point tmp;
		WB_IndexedTriangle T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint(p, T);
			final double d2 = WB_Distance.sqDistance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}
		if (WB_Epsilon.isZeroSq(dmax2)) {
			dmax2 = Double.POSITIVE_INFINITY;
			WB_IndexedSegment S;
			for (int i = 0, j = poly.getN() - 1; i < poly.getN(); j = i, i++) {
				S = new WB_IndexedSegment(poly.getIndex(j), poly.getIndex(i),
						poly.getPoints());
				tmp = closestPoint(p, S);
				final double d2 = WB_Distance.sqDistance(tmp, p);
				if (d2 < dmax2) {
					closest = tmp;
					dmax2 = d2;
				}

			}

		}

		return closest;
	}

	/**
	 * Closest point on periphery of triangulated polygon.
	 *
	 * @param p point
	 * @param tris triangulation of polygon
	 * @return closest point on polygon
	 */
	public static WB_Point closestPointOnPeriphery(final WB_XYZ p,
			final WB_Polygon poly, final ArrayList<WB_IndexedTriangle> tris) {
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_Point closest = new WB_Point();
		WB_Point tmp;
		WB_IndexedTriangle T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint(p, T);
			final double d2 = WB_Distance.sqDistance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}
		if (WB_Epsilon.isZeroSq(dmax2)) {
			dmax2 = Double.POSITIVE_INFINITY;
			WB_ExplicitSegment S;
			for (int i = 0, j = poly.getN() - 1; i < poly.getN(); j = i, i++) {
				S = new WB_ExplicitSegment(poly.getPoint(j), poly.getPoint(i));
				tmp = closestPoint(p, S);
				final double d2 = WB_Distance.sqDistance(tmp, p);
				if (d2 < dmax2) {
					closest = tmp;
					dmax2 = d2;
				}

			}

		}
		return closest;
	}

	// SEGMENT-SEGMENT

	/**
	 * Closest points between two segments.
	 *
	 * @param S1 first segment
	 * @param S2 second segment
	 * @return WB_IntersectionResult
	 */
	public static WB_IntersectionResult closestPoint(final WB_Segment S1,
			final WB_Segment S2) {
		final WB_Vector d1 = S1.getEnd().subToVector(S1.getOrigin());
		final WB_Vector d2 = S2.getEnd().subToVector(S2.getOrigin());
		final WB_Vector r = S1.getOrigin().subToVector(S2.getOrigin());
		final double a = d1.dot(d1);
		final double e = d2.dot(d2);
		final double f = d2.dot(r);

		if (WB_Epsilon.isZero(a) || WB_Epsilon.isZero(e)) {
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.p1 = S1.getOrigin().get();
			i.p2 = S2.getOrigin().get();
			i.sqDist = r.mag2();
			return i;
		}

		double t1 = 0;
		double t2 = 0;
		if (WB_Epsilon.isZero(a)) {

			t2 = WB_Fast.clamp(f / e, 0, 1);
		} else {
			final double c = d1.dot(r);
			if (WB_Epsilon.isZero(e)) {

				t1 = WB_Fast.clamp(-c / a, 0, 1);
			} else {
				final double b = d1.dot(d2);
				final double denom = a * e - b * b;
				if (!WB_Epsilon.isZero(denom)) {
					t1 = WB_Fast.clamp((b * f - c * e) / denom, 0, 1);
				} else {
					t1 = 0;
				}
				final double tnom = b * t1 + f;
				if (tnom < 0) {
					t1 = WB_Fast.clamp(-c / a, 0, 1);
				} else if (tnom > e) {
					t2 = 1;
					t1 = WB_Fast.clamp((b - c) / a, 0, 1);
				} else {
					t2 = tnom / e;
				}
			}
		}
		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = false;
		i.t1 = t1;
		i.t2 = t2;
		i.p1 = S1.getPoint(t1);
		i.p2 = S2.getPoint(t2);
		i.sqDist = WB_Distance.sqDistance(i.p1, i.p2);
		return i;

	}

	/**
	 * Closest points between two segments.
	 *
	 * @param S1 first segment
	 * @param S2 second segment
	 * @param i WB_IntersectionResult
	 */
	public static void closestPointInto(final WB_Segment S1,
			final WB_Segment S2, final WB_IntersectionResult i) {
		final WB_Vector d1 = S1.getEnd().subToVector(S1.getOrigin());
		final WB_Vector d2 = S2.getEnd().subToVector(S2.getOrigin());
		final WB_Vector r = S1.getOrigin().subToVector(S2.getOrigin());
		final double a = d1.dot(d1);
		final double e = d2.dot(d2);
		final double f = d2.dot(r);

		if (WB_Epsilon.isZero(a) || WB_Epsilon.isZero(e)) {
			i.intersection = false;
			i.t1 = 0;
			i.t2 = 0;
			i.p1 = S1.getOrigin().get();
			i.p2 = S2.getOrigin().get();
			i.sqDist = r.mag2();
		} else {
			double t1 = 0;
			double t2 = 0;
			if (WB_Epsilon.isZero(a)) {
				t1 = WB_Fast.clamp(f / e, 0, 1);
			} else {
				final double c = d1.dot(r);
				if (WB_Epsilon.isZero(e)) {

					t1 = WB_Fast.clamp(-c / a, 0, 1);
				} else {
					final double b = d1.dot(d2);
					final double denom = a * e - b * b;
					if (!WB_Epsilon.isZero(denom)) {
						t1 = WB_Fast.clamp((b * f - c * e) / denom, 0, 1);
					} else {
						t1 = 0;
					}
					final double tnom = b * t1 + f;
					if (tnom < 0) {
						t1 = WB_Fast.clamp(-c / a, 0, 1);
					} else if (tnom > e) {
						t2 = 1;
						t1 = WB_Fast.clamp((b - c) / a, 0, 1);
					} else {
						t2 = tnom / e;
					}
				}
			}
			i.intersection = false;
			i.t1 = t1;
			i.t2 = t2;
			i.p1 = S1.getPoint(t1);
			i.p2 = S2.getPoint(t2);
			i.sqDist = WB_Distance.sqDistance(i.p1, i.p2);
		}

	}

	// LINE-LINE
	/**
	 * Closest point between two lines.
	 *
	 * @param L1 first line
	 * @param L2 second line
	 * @return WB_IntersectionResult
	 */
	public static WB_IntersectionResult closestPoint(final WB_Line L1,
			final WB_Line L2) {
		final double a = L1.getDirection().dot(L1.getDirection());
		final double b = L1.getDirection().dot(L2.getDirection());
		final WB_Vector r = L1.getOrigin().subToVector(L2.getOrigin());
		final double c = L1.getDirection().dot(r);
		final double e = L2.getDirection().dot(L2.getDirection());
		final double f = L2.getDirection().dot(r);
		double denom = a * e - b * b;
		if (WB_Epsilon.isZero(denom)) {
			final double t2 = r.dot(L1.getDirection());
			final WB_Point p2 = new WB_Point(L2.getPoint(t2));
			final double d2 = WB_Distance.sqDistance(L1.getOrigin().get(), p2);
			final WB_IntersectionResult i = new WB_IntersectionResult();
			i.intersection = false;
			i.t1 = 0;
			i.t2 = t2;
			i.p1 = L1.getOrigin().get();
			i.p2 = p2;
			i.sqDist = d2;
			return i;
		}
		denom = 1.0 / denom;
		final double t1 = (b * f - c * e) * denom;
		final double t2 = (a * f - b * c) * denom;
		final WB_Point p1 = new WB_Point(L1.getPoint(t1));
		final WB_Point p2 = new WB_Point(L2.getPoint(t2));
		final double d2 = WB_Distance.sqDistance(p1, p2);
		final WB_IntersectionResult i = new WB_IntersectionResult();
		i.intersection = true;
		i.t1 = t1;
		i.t2 = t2;
		i.p1 = p1;
		i.p2 = p2;
		i.sqDist = d2;
		return i;

	}

	/**
	 * Closest point between two lines.
	 *
	 * @param L1 first line
	 * @param L2 second line
	 * @param i WB_IntersectionResult
	 */
	public static void closestPointInto(final WB_Line L1, final WB_Line L2,
			final WB_IntersectionResult i) {
		final double a = L1.getDirection().dot(L1.getDirection());
		final double b = L1.getDirection().dot(L2.getDirection());
		final WB_Vector r = L1.getOrigin().subToVector(L2.getOrigin());
		final double c = L1.getDirection().dot(r);
		final double e = L2.getDirection().dot(L2.getDirection());
		final double f = L2.getDirection().dot(r);
		double denom = a * e - b * b;
		if (WB_Epsilon.isZero(denom)) {
			final double t2 = r.dot(L1.getDirection());
			final WB_Point p2 = new WB_Point(L2.getPoint(t2));
			final double d2 = WB_Distance.sqDistance(L1.getOrigin().get(), p2);
			i.intersection = false;
			i.t1 = 0;
			i.t2 = t2;
			i.p1 = L1.getOrigin().get();
			i.p2 = p2;
			i.sqDist = d2;
		} else {
			denom = 1.0 / denom;
			final double t1 = (b * f - c * e) * denom;
			final double t2 = (a * f - b * c) * denom;
			final WB_Point p1 = new WB_Point(L1.getPoint(t1));
			final WB_Point p2 = new WB_Point(L2.getPoint(t2));
			final double d2 = WB_Distance.sqDistance(p1, p2);
			i.intersection = true;
			i.t1 = t1;
			i.t2 = t2;
			i.p1 = p1;
			i.p2 = p2;
			i.sqDist = d2;
		}
	}

	// POINT-TETRAHEDRON
	/**
	 * Closest point on tetrahedron.
	 *
	 * @param p point
	 * @param T tetrahedron
	 * @return closest point on tetrahedron
	 */
	public static WB_Point closestPoint(final WB_XYZ p, final WB_Tetrahedron T) {
		WB_XYZ closestPt = p.get();
		double bestSqDist = Double.POSITIVE_INFINITY;
		if (WB_Plane.pointOtherSideOfPlane(p, T.p4, T.p1, T.p2, T.p3)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p2, T.p3);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				closestPt = q;
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p2, T.p1, T.p3, T.p4)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p3, T.p4);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				closestPt = q;
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p3, T.p1, T.p4, T.p2)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p4, T.p2);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				closestPt = q;
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p1, T.p2, T.p4, T.p3)) {
			final WB_Point q = closestPointToTriangle(p, T.p2, T.p4, T.p3);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				closestPt = q;
			}
		}

		return new WB_Point(closestPt);

	}

	/**
	 * Closest point on tetrahedron.
	 *
	 * @param p point
	 * @param T tetrahedron
	 * @param result
	 */
	public static void closestPointInto(final WB_XYZ p, final WB_Tetrahedron T,
			final WB_Point result) {
		result.set(p);
		double bestSqDist = Double.POSITIVE_INFINITY;
		if (WB_Plane.pointOtherSideOfPlane(p, T.p4, T.p1, T.p2, T.p3)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p2, T.p3);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				result.set(q);
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p2, T.p1, T.p3, T.p4)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p3, T.p4);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				result.set(q);
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p3, T.p1, T.p4, T.p2)) {
			final WB_Point q = closestPointToTriangle(p, T.p1, T.p4, T.p2);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				result.set(q);
			}
		}

		if (WB_Plane.pointOtherSideOfPlane(p, T.p1, T.p2, T.p4, T.p3)) {
			final WB_Point q = closestPointToTriangle(p, T.p2, T.p4, T.p3);
			final double sqDist = (q.subToVector(p)).mag2();
			if (sqDist < bestSqDist) {
				bestSqDist = sqDist;
				result.set(q);
			}
		}

	}
}
