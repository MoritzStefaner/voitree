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

import wblut.math.WB_Fast;

// TODO: Auto-generated Javadoc
/**
 * 3D line segment.
 */
public class WB_ExplicitSegment2D implements WB_Segment2D {

	/** Origin. */
	private WB_XY		origin;

	/** Direction. */
	private WB_XY		direction;

	/** Length. */
	protected double	length;

	/** End. */
	private WB_XY		end;

	/**
	 * Instantiates a new WB_Segment.
	 */
	public WB_ExplicitSegment2D() {
		origin = new WB_XY();
		direction = new WB_XY(1, 0);
		end = new WB_XY();
		length = 0;

	}

	/**
	 * Instantiates a new WB_Segment.
	 *
	 * @param o start point
	 * @param d direction
	 * @param l length
	 */
	public WB_ExplicitSegment2D(final WB_XY o, final WB_XY d, final double l) {
		origin = o.get();
		direction = d.get();
		direction.normalize();
		length = l;
		end = new WB_XY(direction);
		end.mult(l).add(origin);
	}

	/**
	 * Set segment.
	 *
	 * @param o start point
	 * @param d direction
	 * @param l length
	 */
	public void set(final WB_XY o, final WB_XY d, final double l) {
		origin = o.get();
		direction = d.get();
		direction.normalize();
		length = l;
		end = new WB_XY(direction);
		end.mult(l).add(origin);
	}

	/**
	 * Instantiates a new WB_Segment.
	 *
	 * @param p1 start point
	 * @param p2 end point
	 */
	public WB_ExplicitSegment2D(final WB_XY p1, final WB_XY p2) {
		origin = p1.get();
		end = p2.get();
		direction = p2.subAndCopy(p1);
		direction.normalize();
		length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));

	}

	public WB_ExplicitSegment2D(final WB_XY p1, final WB_XY p2,
			final boolean copy) {
		if (copy) {
			origin = p1.get();
			end = p2.get();
			direction = p2.subAndCopy(p1);
			direction.normalize();
			length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));
		} else {

			origin = p1;
			end = p2;
			direction = p2.subAndCopy(p1);
			direction.normalize();
			length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));
		}
	}

	/**
	 * Set segment.
	 *
	 * @param p1 start point
	 * @param p2 end point
	 */
	public void set(final WB_XY p1, final WB_XY p2) {
		origin = p1.get();
		end = p2.get();
		direction = p2.subAndCopy(p1);
		direction.normalize();
		length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));

	}

	public void set(final WB_XY p1, final WB_XY p2, final boolean copy) {
		if (copy) {
			origin = p1.get();
			end = p2.get();
			direction = p2.subAndCopy(p1);
			direction.normalize();
			length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));
		} else {

			origin = p1;
			end = p2;
			direction = p2.subAndCopy(p1);
			direction.normalize();
			length = Math.sqrt(WB_Distance2D.sqDistance(p1, p2));
		}
	}

	/**
	 * Get point along segment.
	 *
	 * @param t 0..1: origin to end
	 * @return point
	 */
	public WB_XY getPoint(final double t) {
		final WB_XY result = new WB_XY(direction);
		result.scale(WB_Fast.clamp(t, 0, 1) * length);
		result.moveBy(origin);
		return result;
	}

	/**
	 * Get point along segment.
	 *
	 * @param t 0..1: origin to end
	 * @param result WB_XY to store result
	 */
	public void getPointInto(final double t, final WB_XY result) {
		result.moveTo(direction);
		if (WB_Fast.clamp(t, 0, 1) == t) {
			result.scale(t * length);
		}
		result.moveBy(origin);

	}

	/**
	 * Get origin.
	 *
	 * @return origin
	 */
	public WB_XY getOrigin() {
		return origin;
	}

	/**
	 * Get direction.
	 *
	 * @return direction
	 */
	public WB_XY getDirection() {
		return direction;
	}

	/**
	 * Get end.
	 *
	 * @return end
	 */
	public WB_XY getEnd() {
		return end;
	}

	/**
	 * Get center.
	 *
	 * @return center
	 */
	public WB_XY getCenter() {
		return end.addAndCopy(origin).mult(0.5);
	}

	/**
	 * Get length.
	 *
	 * @return length
	 */
	public double getLength() {
		return length;
	}

	public void reverse() {
		set(end, origin);
	}

	public WB_ExplicitSegment2D negate() {

		return new WB_ExplicitSegment2D(end, origin);

	}

	public static ArrayList<WB_ExplicitSegment2D> negate(
			final ArrayList<WB_ExplicitSegment2D> segs) {
		final ArrayList<WB_ExplicitSegment2D> neg = new ArrayList<WB_ExplicitSegment2D>();
		for (int i = 0; i < segs.size(); i++) {
			neg.add(segs.get(i).negate());
		}

		return neg;

	}

	/**
	 * @return copy
	 */
	public WB_ExplicitSegment2D get() {
		// TODO Auto-generated method stub
		return new WB_ExplicitSegment2D(origin, end);
	}

}