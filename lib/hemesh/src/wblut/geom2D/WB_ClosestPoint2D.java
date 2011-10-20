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
public class WB_ClosestPoint2D {

	/**
	 * Closest point.
	 *
	 * @param p the p
	 * @param S the s
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p, final WB_Segment2D S) {
		final WB_XY ab = S.getEnd().subAndCopy(S.getOrigin());
		final WB_XY ac = p.subAndCopy(S.getOrigin());
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
				return new WB_XY(S.getPoint(t));
			}
		}
	}

	public static WB_XY closestPoint2D(final WB_Segment2D S, final WB_XY p) {
		return closestPoint2D(p, S);
	}

	/**
	 * Closest point into.
	 *
	 * @param p the p
	 * @param S the s
	 * @param result the result
	 */
	public static void closestPoint2DInto(final WB_XY p, final WB_Segment2D S,
			final WB_XY result) {
		final WB_XY ab = S.getEnd().subAndCopy(S.getOrigin());
		final WB_XY ac = p.subAndCopy(S.getOrigin());
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
				final WB_XY o = S.getOrigin();
				result.set(o.x + t * ab.x, o.y + t * ab.y);
			}
		}
	}

	public static void closestPoint2DInto(final WB_Segment2D S, final WB_XY p,
			final WB_XY result) {
		closestPoint2DInto(p, S, result);
	}

	/**
	 * Closest point to segment.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the w b_ point
	 */
	public static WB_XY closestPointToSegment2D(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = p.subAndCopy(a);
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
				return new WB_XY(a.x + t * ab.x, a.y + t * ab.y);
			}
		}
	}

	/**
	 * Closest point to segment into.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @param result the result
	 */
	public static void closestPointToSegment2DInto(final WB_XY p,
			final WB_XY a, final WB_XY b, final WB_XY result) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = p.subAndCopy(a);
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
				result.set(a.x + t * ab.x, a.y + t * ab.y);
			}
		}
	}

	/**
	 * Closest point.
	 *
	 * @param p the p
	 * @param L the l
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p, final WB_Line2D L) {

		if (WB_Epsilon.isZero(L.getDirection().x)) {
			return new WB_XY(L.getOrigin().x, p.y);
		}
		if (WB_Epsilon.isZero(L.getDirection().y)) {
			return new WB_XY(p.x, L.getOrigin().y);
		}

		final double m = L.getDirection().y / L.getDirection().x;
		final double b = L.getOrigin().y - m * L.getOrigin().x;

		final double x = (m * p.y + p.x - m * b) / (m * m + 1);
		final double y = (m * m * p.y + m * p.x + b) / (m * m + 1);

		return new WB_XY(x, y);

	}

	/**
	 * Closest point into.
	 *
	 * @param p the p
	 * @param L the l
	 * @param result the result
	 */
	public static void closestPoint2DInto(final WB_XY p, final WB_Line2D L,
			final WB_XY result) {
		final WB_XY ca = p.subAndCopy(L.getOrigin());
		result.set(L.getPoint(ca.dot(L.getDirection())));
	}

	/**
	 * Closest point to line.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the w b_ point
	 */
	public static WB_XY closestPointToLine2D(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_Line2D L = new WB_Line2D();
		L.setFromPoints(a, b);
		return closestPoint2D(p, L);
	}

	/**
	 * Closest point to line into.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @param result the result
	 */
	public static void closestPointToLine2DInto(final WB_XY p, final WB_XY a,
			final WB_XY b, final WB_XY result) {
		closestPoint2DInto(p, new WB_Line2D(a, b), result);
	}

	/**
	 * Closest point.
	 *
	 * @param p the p
	 * @param R the r
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p, final WB_Ray2D R) {
		final WB_XY ac = p.subAndCopy(R.getOrigin());
		double t = ac.dot(R.getDirection());
		if (t <= 0) {
			t = 0;
			return R.getOrigin().get();
		} else {
			return R.getPoint(t);
		}
	}

	/**
	 * Closest point into.
	 *
	 * @param p the p
	 * @param R the r
	 * @param result the result
	 */
	public static void closestPoint2DInto(final WB_XY p, final WB_Ray2D R,
			final WB_XY result) {
		final WB_XY ac = p.subAndCopy(R.getOrigin());
		double t = ac.dot(R.getDirection());
		if (t <= 0) {
			t = 0;
			result.set(R.getOrigin().get());
		} else {
			result.set(R.getPoint(t));
		}
	}

	/**
	 * Closest point to ray.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the w b_ point
	 */
	public static WB_XY closestPointToRay2D(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_Ray2D R = new WB_Ray2D();
		R.setFromPoints(a, b);
		return closestPoint2D(p, R);
	}

	/**
	 * Closest point to ray into.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @param result the result
	 */
	public static void closestPointToRay2DInto(final WB_XY p, final WB_XY a,
			final WB_XY b, final WB_XY result) {
		final WB_Ray2D R = new WB_Ray2D();
		R.setFromPoints(a, b);
		closestPoint2DInto(p, R, result);
	}

	/**
	 * Closest point.
	 *
	 * @param S1 the s1
	 * @param S2 the s2
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult2D closestPoint2D(final WB_Segment2D S1,
			final WB_Segment2D S2) {
		final WB_XY d1 = S1.getEnd().subAndCopy(S1.getOrigin());
		final WB_XY d2 = S2.getEnd().subAndCopy(S2.getOrigin());
		final WB_XY r = S1.getOrigin().subAndCopy(S2.getOrigin());
		final double a = d1.dot(d1);
		final double e = d2.dot(d2);
		final double f = d2.dot(r);

		if (WB_Epsilon.isZero(a) || WB_Epsilon.isZero(e)) {
			final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
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
		final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
		i.intersection = (t1 > 0) && (t1 < 1) && (t2 > 0) && (t2 < 1);
		i.t1 = t1;
		i.t2 = t2;
		i.p1 = S1.getPoint(t1);
		i.p2 = S2.getPoint(t2);
		i.sqDist = WB_Distance2D.sqDistance(i.p1, i.p2);
		return i;

	}

	/**
	 * Closest point into.
	 *
	 * @param S1 the s1
	 * @param S2 the s2
	 * @param i the i
	 */
	public static void closestPoint2DInto(final WB_Segment2D S1,
			final WB_Segment2D S2, final WB_IntersectionResult2D i) {
		final WB_XY d1 = S1.getEnd().subAndCopy(S1.getOrigin());
		final WB_XY d2 = S2.getEnd().subAndCopy(S2.getOrigin());
		final WB_XY r = S1.getOrigin().subAndCopy(S2.getOrigin());
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
			i.intersection = (t1 > 0) && (t1 < 1) && (t2 > 0) && (t2 < 1);
			i.t1 = t1;
			i.t2 = t2;
			i.p1 = S1.getPoint(t1);
			i.p2 = S2.getPoint(t2);
			i.sqDist = WB_Distance2D.sqDistance(i.p1, i.p2);
		}

	}

	/**
	 * Closest point.
	 *
	 * @param L1 the l1
	 * @param L2 the l2
	 * @return the w b_ intersection
	 */
	public static WB_IntersectionResult2D closestPoint2D(final WB_Line2D L1,
			final WB_Line2D L2) {
		final double a = L1.getDirection().dot(L1.getDirection());
		final double b = L1.getDirection().dot(L2.getDirection());
		final WB_XY r = L1.getOrigin().subAndCopy(L2.getOrigin());
		final double c = L1.getDirection().dot(r);
		final double e = L2.getDirection().dot(L2.getDirection());
		final double f = L2.getDirection().dot(r);
		double denom = a * e - b * b;
		if (WB_Epsilon.isZero(denom)) {
			final double t2 = r.dot(L1.getDirection());
			final WB_XY p2 = new WB_XY(L2.getPoint(t2));
			final double d2 = WB_Distance2D.sqDistance(L1.getOrigin().get(), p2);
			final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
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
		final WB_XY p1 = new WB_XY(L1.getPoint(t1));
		final WB_XY p2 = new WB_XY(L2.getPoint(t2));
		final double d2 = WB_Distance2D.sqDistance(p1, p2);
		final WB_IntersectionResult2D i = new WB_IntersectionResult2D();
		i.intersection = true;
		i.t1 = t1;
		i.t2 = t2;
		i.p1 = p1;
		i.p2 = p2;
		i.sqDist = d2;
		return i;

	}

	/**
	 * Closest point into.
	 *
	 * @param L1 the l1
	 * @param L2 the l2
	 * @param i the i
	 */
	public static void closestPoint2DInto(final WB_Line2D L1,
			final WB_Line2D L2, final WB_IntersectionResult2D i) {
		final double a = L1.getDirection().dot(L1.getDirection());
		final double b = L1.getDirection().dot(L2.getDirection());
		final WB_XY r = L1.getOrigin().subAndCopy(L2.getOrigin());
		final double c = L1.getDirection().dot(r);
		final double e = L2.getDirection().dot(L2.getDirection());
		final double f = L2.getDirection().dot(r);
		double denom = a * e - b * b;
		if (WB_Epsilon.isZero(denom)) {
			final double t2 = r.dot(L1.getDirection());
			final WB_XY p2 = new WB_XY(L2.getPoint(t2));
			final double d2 = WB_Distance2D.sqDistance(L1.getOrigin().get(), p2);
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
			final WB_XY p1 = new WB_XY(L1.getPoint(t1));
			final WB_XY p2 = new WB_XY(L2.getPoint(t2));
			final double d2 = WB_Distance2D.sqDistance(p1, p2);
			i.intersection = true;
			i.t1 = t1;
			i.t2 = t2;
			i.p1 = p1;
			i.p2 = p2;
			i.sqDist = d2;
		}
	}

	public static WB_IntersectionResult2D closestPoint2D(final WB_Line2D L,
			final WB_Segment2D S) {
		final WB_IntersectionResult2D i = closestPoint2D(L, new WB_Line2D(S
				.getOrigin(), S.getDirection()));
		if (i.t2 <= WB_Epsilon.EPSILON) {
			i.t2 = 0;
			i.p2 = S.getOrigin().get();
			i.sqDist = WB_Distance2D.sqDistance(i.p1, i.p2);
			i.intersection = false;
		}
		if (i.t2 >= S.getLength() - WB_Epsilon.EPSILON) {
			i.t2 = 1;
			i.p2 = S.getEnd().get();
			i.sqDist = WB_Distance2D.sqDistance(i.p1, i.p2);
			i.intersection = false;
		}
		return i;
	}

	public static WB_IntersectionResult2D closestPoint2D(final WB_Segment2D S,
			final WB_Line2D L) {

		return closestPoint2D(L, S);
	}

	public static void closestPoint2DInto(final WB_Line2D L,
			final WB_Segment2D S, final WB_IntersectionResult2D i) {
		final WB_IntersectionResult2D it = closestPoint2D(L, new WB_Line2D(S
				.getOrigin(), S.getDirection()));
		if (it.t2 <= WB_Epsilon.EPSILON) {
			it.t2 = 0;
			it.p2 = S.getOrigin().get();
			it.sqDist = WB_Distance2D.sqDistance(it.p1, it.p2);
			it.intersection = false;
		}
		if (it.t2 >= 1 - WB_Epsilon.EPSILON) {
			it.t2 = 1;
			it.p2 = S.getEnd().get();
			it.sqDist = WB_Distance2D.sqDistance(it.p1, it.p2);
			it.intersection = false;
		}
		i.intersection = it.intersection;
		i.p1 = it.p1;
		i.p2 = it.p2;
		i.t1 = it.t1;
		i.t2 = it.t2;
		i.sqDist = it.sqDist;

	}

	public static void closestPoint2DInto(final WB_Segment2D S,
			final WB_Line2D L, final WB_IntersectionResult2D i) {

		closestPoint2DInto(L, S, i);
	}

	// POINT-TRIANGLE

	/**
	 * Closest point.
	 *
	 * @param p the p
	 * @param T the t
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p, final WB_Triangle2D T) {
		final WB_XY ab = T.p2.subAndCopy(T.p1);
		final WB_XY ac = T.p3.subAndCopy(T.p1);
		final WB_XY ap = p.subAndCopy(T.p1);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return T.p1.get();
		}

		final WB_XY bp = p.subAndCopy(T.p2);
		final double d3 = ab.dot(bp);
		final double d4 = ac.dot(bp);
		if (d3 >= 0 && d4 <= d3) {
			return T.p2.get();
		}

		final double vc = d1 * d4 - d3 * d2;
		if (vc <= 0 && d1 >= 0 && d3 <= 0) {
			final double v = d1 / (d1 - d3);
			return T.p1.addAndCopy(ab.mult(v));
		}

		final WB_XY cp = p.subAndCopy(T.p3);
		final double d5 = ab.dot(cp);
		final double d6 = ac.dot(cp);
		if (d6 >= 0 && d5 <= d6) {
			return T.p3.get();
		}

		final double vb = d5 * d2 - d1 * d6;
		if (vb <= 0 && d2 >= 0 && d6 <= 0) {
			final double w = d2 / (d2 - d6);
			return T.p1.addAndCopy(ac.mult(w));
		}

		final double va = d3 * d6 - d5 * d4;
		if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
			final double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));
			return T.p2.addAndCopy((T.p3.subAndCopy(T.p2)).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		return T.p1.addAndCopy(ab.mult(v).add(ac.mult(w)));
	}

	/**
	 * Closest point into.
	 *
	 * @param p the p
	 * @param T the t
	 * @param result the result
	 */
	public static void closestPoint2DInto(final WB_XY p, final WB_Triangle2D T,
			final WB_XY result) {
		final WB_XY ab = T.p2.subAndCopy(T.p1);
		final WB_XY ac = T.p3.subAndCopy(T.p1);
		final WB_XY ap = p.subAndCopy(T.p1);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			result.set(T.p1);
		} else {
			final WB_XY bp = p.subAndCopy(T.p2);
			final double d3 = ab.dot(bp);
			final double d4 = ac.dot(bp);
			if (d3 >= 0 && d4 <= d3) {
				result.set(T.p2);
			} else {
				final double vc = d1 * d4 - d3 * d2;
				if (vc <= 0 && d1 >= 0 && d3 <= 0) {
					final double v = d1 / (d1 - d3);
					result.set(T.p1.addAndCopy(ab.mult(v)));
				} else {
					final WB_XY cp = p.subAndCopy(T.p3);
					final double d5 = ab.dot(cp);
					final double d6 = ac.dot(cp);

					if (d6 >= 0 && d5 <= d6) {
						result.set(T.p3.get());
					} else {
						final double vb = d5 * d2 - d1 * d6;
						if (vb <= 0 && d2 >= 0 && d6 <= 0) {
							final double w = d2 / (d2 - d6);
							result.set(T.p1.addAndCopy(ac.mult(w)));
						} else {
							final double va = d3 * d6 - d5 * d4;
							if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
								final double w = (d4 - d3)
										/ ((d4 - d3) + (d5 - d6));
								result.set(T.p2.addAndCopy((T.p3
										.subAndCopy(T.p2)).mult(w)));
							} else {
								final double denom = 1.0 / (va + vb + vc);
								final double v = vb * denom;
								final double w = vc * denom;
								result.set(T.p1.addAndCopy(ab.mult(v).add(
										ac.mult(w))));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Closest point to triangle.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 * @return the w b_ point
	 */
	public static WB_XY closestPointToTriangle2D(final WB_XY p, final WB_XY a,
			final WB_XY b, final WB_XY c) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = c.subAndCopy(a);
		final WB_XY ap = p.subAndCopy(a);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return a.get();
		}

		final WB_XY bp = p.subAndCopy(b);
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

		final WB_XY cp = p.subAndCopy(c);
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
			return b.addAndCopy((c.subAndCopy(b)).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		return a.addAndCopy(ab.mult(v).add(ac.mult(w)));
	}

	/**
	 * Closest point to triangle into.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 * @param result the result
	 */
	public static void closestPointToTriangle2DInto(final WB_XY p,
			final WB_XY a, final WB_XY b, final WB_XY c, final WB_XY result) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = c.subAndCopy(a);
		final WB_XY ap = p.subAndCopy(a);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			result.set(a);
		} else {
			final WB_XY bp = p.subAndCopy(b);
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
					final WB_XY cp = p.subAndCopy(c);
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
								result.set(b.addAndCopy((c.subAndCopy(b))
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
	 * Closest point on periphery.
	 *
	 * @param p the p
	 * @param T the t
	 * @return the w b_ point
	 */
	public static WB_XY closestPointOnPeriphery2D(final WB_XY p,
			final WB_Triangle2D T) {
		final WB_XY ab = T.p2.subAndCopy(T.p1);
		final WB_XY ac = T.p3.subAndCopy(T.p1);
		final WB_XY ap = p.subAndCopy(T.p1);
		final double d1 = ab.dot(ap);
		final double d2 = ac.dot(ap);
		if (d1 <= 0 && d2 <= 0) {
			return T.p1.get();
		}

		final WB_XY bp = p.subAndCopy(T.p2);
		final double d3 = ab.dot(bp);
		final double d4 = ac.dot(bp);
		if (d3 >= 0 && d4 <= d3) {
			return T.p2.get();
		}

		final double vc = d1 * d4 - d3 * d2;
		if (vc <= 0 && d1 >= 0 && d3 <= 0) {
			final double v = d1 / (d1 - d3);
			return T.p1.addAndCopy(ab.mult(v));
		}

		final WB_XY cp = p.subAndCopy(T.p3);
		final double d5 = ab.dot(cp);
		final double d6 = ac.dot(cp);
		if (d6 >= 0 && d5 <= d6) {
			return T.p3.get();
		}

		final double vb = d5 * d2 - d1 * d6;
		if (vb <= 0 && d2 >= 0 && d6 <= 0) {
			final double w = d2 / (d2 - d6);
			return T.p1.addAndCopy(ac.mult(w));
		}

		final double va = d3 * d6 - d5 * d4;
		if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
			final double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));
			return T.p2.addAndCopy((T.p3.subAndCopy(T.p2)).mult(w));
		}

		final double denom = 1.0 / (va + vb + vc);
		final double v = vb * denom;
		final double w = vc * denom;
		final double u = 1 - v - w;
		T.p3.subAndCopy(T.p2);
		if (WB_Epsilon.isZero(u - 1)) {
			return T.p1.get();
		}
		if (WB_Epsilon.isZero(v - 1)) {
			return T.p2.get();
		}
		if (WB_Epsilon.isZero(w - 1)) {
			return T.p3.get();
		}
		final WB_XY A = closestPointToSegment2D(p, T.p2, T.p3);
		final double dA2 = WB_Distance2D.sqDistance(p, A);
		final WB_XY B = closestPointToSegment2D(p, T.p1, T.p3);
		final double dB2 = WB_Distance2D.sqDistance(p, B);
		final WB_XY C = closestPointToSegment2D(p, T.p1, T.p2);
		final double dC2 = WB_Distance2D.sqDistance(p, C);
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
	 * Closest point.
	 *
	 * @param p the p
	 * @param poly the poly
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p, final WB_Polygon2D poly) {
		final ArrayList<WB_Triangle2D> tris = poly.triangulate();
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_XY closest = new WB_XY();
		WB_XY tmp;
		WB_Triangle2D T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint2D(p, T);
			final double d2 = WB_Distance2D.distance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}

		return closest;
	}

	/**
	 * Closest point.
	 *
	 * @param p the p
	 * @param tris the tris
	 * @return the w b_ point
	 */
	public static WB_XY closestPoint2D(final WB_XY p,
			final ArrayList<? extends WB_Triangle2D> tris) {
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_XY closest = new WB_XY();
		WB_XY tmp;
		WB_Triangle2D T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint2D(p, T);
			final double d2 = WB_Distance2D.distance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}

		return closest;
	}

	/**
	 * Closest point on periphery.
	 *
	 * @param p the p
	 * @param poly the poly
	 * @return the w b_ point
	 */
	public static WB_XY closestPointOnPeriphery2D(final WB_XY p,
			final WB_Polygon2D poly) {
		final ArrayList<WB_Triangle2D> tris = poly.triangulate();
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_XY closest = new WB_XY();
		WB_XY tmp;
		WB_Triangle2D T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint2D(p, T);
			final double d2 = WB_Distance2D.sqDistance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}
		if (WB_Epsilon.isZeroSq(dmax2)) {
			dmax2 = Double.POSITIVE_INFINITY;
			WB_IndexedSegment2D S;
			for (int i = 0, j = poly.n - 1; i < poly.n; j = i, i++) {
				S = new WB_IndexedSegment2D(j, i, poly.points);
				tmp = closestPoint2D(p, S);
				final double d2 = WB_Distance2D.sqDistance(tmp, p);
				if (d2 < dmax2) {
					closest = tmp;
					dmax2 = d2;
				}

			}

		}

		return closest;
	}

	/**
	 * Closest point on periphery.
	 *
	 * @param p the p
	 * @param poly the poly
	 * @param tris the tris
	 * @return the w b_ point
	 */
	public static WB_XY closestPointOnPeriphery2D(final WB_XY p,
			final WB_Polygon2D poly, final ArrayList<WB_Triangle2D> tris) {
		final int n = tris.size();
		double dmax2 = Double.POSITIVE_INFINITY;
		WB_XY closest = new WB_XY();
		WB_XY tmp;
		WB_Triangle2D T;
		for (int i = 0; i < n; i++) {
			T = tris.get(i);
			tmp = closestPoint2D(p, T);
			final double d2 = WB_Distance2D.sqDistance(tmp, p);
			if (d2 < dmax2) {
				closest = tmp;
				dmax2 = d2;
			}

		}
		if (WB_Epsilon.isZeroSq(dmax2)) {
			dmax2 = Double.POSITIVE_INFINITY;
			WB_Segment2D S;
			for (int i = 0, j = poly.n - 1; i < poly.n; j = i, i++) {
				S = new WB_IndexedSegment2D(j, i, poly.points);
				tmp = closestPoint2D(p, S);
				final double d2 = WB_Distance2D.sqDistance(tmp, p);
				if (d2 < dmax2) {
					closest = tmp;
					dmax2 = d2;
				}

			}

		}
		return closest;
	}

}
