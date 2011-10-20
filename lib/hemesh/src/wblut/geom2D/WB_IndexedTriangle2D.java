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

// TODO: Auto-generated Javadoc
/**
 * A subclass of WB_Triangle referencing points as array indices.
 */
public class WB_IndexedTriangle2D extends WB_Triangle2D {

	/** index of first point. */
	public int	i1;
	/** index of second point. */
	public int	i2;
	/** index of third point. */
	public int	i3;

	/**
	 * Instantiates a new w b_ indexed triangle.
	 *
	 * @param i1 index of first point
	 * @param i2 index of second point
	 * @param i3 index of third point
	 * @param points points as WB_Point[]
	 */
	public WB_IndexedTriangle2D(final int i1, final int i2, final int i3,
			final WB_XY[] points) {
		super(points[i1], points[i2], points[i3]);
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;

	}

	/**
	 * Reverse the triangle.
	 */
	public void reverse() {
		final WB_XY tmp = p1;
		p1 = p2;
		p2 = tmp;
		final int t = i1;
		i1 = i2;
		i2 = t;

	}

}