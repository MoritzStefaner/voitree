/**
 * 
 */
package wblut.geom2D;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Distance2D {
	// POINT-POINT

	/**
	 * Squared distance between 2 points.
	 *
	 * @param p
	 * @param q
	 * @return squared distance
	 */
	public static double sqDistance(final WB_XY p, final WB_XY q) {
		return ((q.x - p.x) * (q.x - p.x) + (q.y - p.y) * (q.y - p.y));
	}

	/**
	 * Distance between 2 points.
	 *
	 * @param p
	 * @param q
	 * @return distance
	 */
	public static double distance(final WB_XY p, final WB_XY q) {
		return Math.sqrt(sqDistance(p, q));
	}

	// POINT-SEGMENT
	/**
	 * Sq distance.
	 *
	 * @param p the p
	 * @param S the s
	 * @return the double
	 */
	public static double sqDistance(final WB_XY p, final WB_Segment2D S) {
		final WB_XY ab = S.getEnd().subAndCopy(S.getOrigin());
		final WB_XY ac = p.subAndCopy(S.getOrigin());
		final WB_XY bc = p.subAndCopy(S.getEnd());
		final double e = ac.dot(ab);
		if (e <= 0) {
			return ac.dot(ac);
		}
		final double f = ab.dot(ab);
		if (e >= f) {
			return bc.dot(bc);
		}
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance.
	 *
	 * @param p the p
	 * @param S the s
	 * @return the double
	 */
	public static double distance(final WB_XY p, final WB_Segment2D S) {
		return Math.sqrt(sqDistance(p, S));
	}

	/**
	 * Sq distance to segment.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double sqDistanceToSegment(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = p.subAndCopy(a);
		final WB_XY bc = p.subAndCopy(b);
		final double e = ac.dot(ab);
		if (e <= 0) {
			return ac.dot(ac);
		}
		final double f = ab.dot(ab);
		if (e >= f) {
			return bc.dot(bc);
		}
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance to segment.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double distanceToSegment(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		return Math.sqrt(sqDistanceToSegment(p, a, b));
	}

	// POINT-LINE

	/**
	 * Sq distance.
	 *
	 * @param p the p
	 * @param L the l
	 * @return the double
	 */
	public static double sqDistance(final WB_XY p, final WB_Line2D L) {
		final WB_XY ab = L.getDirection();
		final WB_XY ac = p.subAndCopy(L.getOrigin());
		final double e = ac.dot(ab);
		final double f = ab.dot(ab);
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance.
	 *
	 * @param p the p
	 * @param L the l
	 * @return the double
	 */
	public static double distance(final WB_XY p, final WB_Line2D L) {
		return Math.sqrt(sqDistance(p, L));
	}

	/**
	 * Sq distance to line.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double sqDistanceToLine(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = p.subAndCopy(a);
		final double e = ac.dot(ab);
		final double f = ab.dot(ab);
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance to line.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double distanceToLine(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		return Math.sqrt(sqDistanceToLine(p, a, b));
	}

	// POINT-RAY
	/**
	 * Sq distance.
	 *
	 * @param p the p
	 * @param R the r
	 * @return the double
	 */
	public static double sqDistance(final WB_XY p, final WB_Ray2D R) {
		final WB_XY ab = R.getDirection();
		final WB_XY ac = p.subAndCopy(R.getOrigin());
		final double e = ac.dot(ab);
		if (e <= 0) {
			return ac.dot(ac);
		}
		final double f = ab.dot(ab);
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance.
	 *
	 * @param p the p
	 * @param R the r
	 * @return the double
	 */
	public static double distance(final WB_XY p, final WB_Ray2D R) {
		return Math.sqrt(sqDistance(p, R));
	}

	/**
	 * Sq distance to ray.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double sqDistanceToRay(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		final WB_XY ab = b.subAndCopy(a);
		final WB_XY ac = p.subAndCopy(a);
		final double e = ac.dot(ab);
		if (e <= 0) {
			return ac.dot(ac);
		}
		final double f = ab.dot(ab);
		return ac.dot(ac) - e * e / f;
	}

	/**
	 * Distance to ray.
	 *
	 * @param p the p
	 * @param a the a
	 * @param b the b
	 * @return the double
	 */
	public static double distanceToRay(final WB_XY p, final WB_XY a,
			final WB_XY b) {
		return Math.sqrt(sqDistanceToRay(p, a, b));
	}

}
