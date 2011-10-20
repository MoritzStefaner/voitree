/**
 * 
 */
package wblut.geom;

import wblut.math.WB_Epsilon;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public abstract class WB_Linear {
	/** Origin of line. */
	protected WB_Point	origin;

	/** Direction of line. */
	protected WB_Vector	direction;

	public WB_Linear() {
		origin = new WB_Point();
		direction = new WB_Vector(1, 0, 0);
	}

	public WB_Linear(final WB_Point o, final WB_Vector d) {
		origin = o.get();
		direction = d.get();
		direction.normalize();
	}

	public WB_Linear(final WB_Point p1, final WB_Point p2) {
		origin = p1.get();
		direction = p2.subToVector(p1);
		direction.normalize();
	}

	public void set(final WB_Point o, final WB_Vector d) {
		origin.set(o);
		direction.set(d);
		direction.normalize();
	}

	public void set(final WB_Point p1, final WB_Point p2) {
		origin.set(p1);
		direction.set(p2.subToVector(p1));
		direction.normalize();
	}

	public WB_Linear(final WB_Point o, final WB_Vector d, final boolean copy) {
		if (copy) {
			origin = o.get();
			direction = d.get();
		} else {

			direction = d;
		}

		direction.normalize();
	}

	public WB_Linear(final WB_Point p1, final WB_Point p2, final boolean copy) {
		if (copy) {
			origin = p1.get();

		} else {
			origin = p1;

		}
		direction = p2.subToVector(p1);
		direction.normalize();
	}

	public void setNoCopy(final WB_Point o, final WB_Vector d) {
		origin = o;
		direction = d;
		direction.normalize();
	}

	public void setNoCopy(final WB_Point p1, final WB_Point p2) {
		origin = p1;
		direction.set(p2.subToVector(p1));
		direction.normalize();
	}

	/**
	 * Get point along line.
	 *
	 * @param t distance from origin
	 * @return point
	 */
	public WB_Point getPoint(final double t) {
		final WB_Point result = new WB_Point(direction);
		result.scale(t);
		result.moveBy(origin);
		return result;
	}

	/**
	 * Get point along line and store it in provided WB_Point.
	 *
	 * @param t distance from origin
	 * @param p WB_Point to store result in
	 */
	public void getPointInto(final double t, final WB_Point p) {
		p.moveTo(direction);
		p.scale(t);
		p.moveBy(origin);
	}

	/**
	 * Get origin.
	 *
	 * @return origin
	 */
	public WB_Point getOrigin() {
		return origin;
	}

	/**
	 * Get direction.
	 *
	 * @return direction
	 */
	public WB_Vector getDirection() {
		return direction;
	}

	public WB_Normal getNormal() {

		WB_Normal n = new WB_Normal(0, 0, 1);
		n = n.cross(direction);
		final double d = n.normalize();
		if (WB_Epsilon.isZero(d)) {
			n = new WB_Normal(1, 0, 0);
		}
		return n;
	}
}
