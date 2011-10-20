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

import wblut.math.WB_Fast;

// TODO: Auto-generated Javadoc
/**
 *  3D Ray.
 */
public class WB_Ray2D {

	/** Origin. */
	private WB_XY	origin;

	/**Direction. */
	private WB_XY	direction;

	/**
	 * Instantiates a new WB_Ray2D.
	 */
	public WB_Ray2D() {
		origin = new WB_XY();
		direction = new WB_XY(1, 0);

	}

	/**
	 * Instantiates a new WB_Ray2D.
	 *
	 * @param o origin
	 * @param d direction
	 */
	public WB_Ray2D(final WB_XY o, final WB_XY d) {
		origin = o.get();
		direction = d.get();
		direction.normalize();

	}

	/**
	 * Set ray.
	 *
	 * @param o origin
	 * @param d direction
	 */
	public void set(final WB_XY o, final WB_XY d) {
		origin = o.get();
		direction = d.get();
		direction.normalize();

	}

	/**
	 * Set ray.
	 *
	 * @param p1 origin
	 * @param p2 point on ray
	 */
	public void setFromPoints(final WB_XY p1, final WB_XY p2) {
		origin = p1.get();
		direction = p2.subAndCopy(p1);
		direction.normalize();

	}

	/**
	 * Get point along ray.
	 *
	 * @param t positive distance from origin
	 * @return point
	 */
	public WB_XY getPoint(final double t) {
		final WB_XY result = new WB_XY(direction);
		result.scale(WB_Fast.max(0, t));
		result.moveBy(origin);
		return result;
	}

	/**
	 * Get point along ray.
	 *
	 * @param t positive distance from origin
	 * @param p WB_XY to store result
	 */
	public void getPointInto(final double t, final WB_XY p) {
		p.moveTo(direction);
		if (t > 0) {
			p.scale(t);
		}
		p.moveBy(origin);
	}

	/**
	 * Get origin.
	 *
	 * @return the WB_XY
	 */
	public WB_XY getOrigin() {
		return origin;
	}

	/**
	 * Get direction.
	 *
	 * @return the WB_Vctor
	 */
	public WB_XY getDirection() {
		return direction;
	}
}
