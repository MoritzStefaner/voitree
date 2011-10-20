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

import wblut.math.WB_Epsilon;

// TODO: Auto-generated Javadoc
/**
 * Normal. A normal behaves differently under non-uniform scaling transforms than a vector.
 *
 * @author Frederik Vanhoutte (W:Blut) 2010
 */
public class WB_Normal extends WB_Vector {

	/**
	 * Instantiates a new WB_Normal.
	 */
	public WB_Normal() {
		super();
	}

	/**
	 * Instantiates a new WB_Normal.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 */
	public WB_Normal(final double x, final double y, final double z) {
		super(x, y, z);
	}

	/**
	 * Instantiates a new WB_Normal.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public WB_Normal(final double x, final double y) {
		super(x, y);
	}

	/**
	 * Instantiates a new WB_Normal.
	 *
	 * @param v point, vector or normal
	 */
	public WB_Normal(final WB_XYZ v) {
		super(v);
	}

	/**
	 * Get copy.
	 *
	 * @return copy
	 */
	@Override
	public WB_Normal get() {
		return new WB_Normal(x, y, z);
	}

	/**
	 * Add.
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return self
	 */
	@Override
	public WB_Normal add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Add.
	 *
	 * @param p 
	 * @return self
	 */
	@Override
	public WB_Normal add(final WB_XYZ p) {
		x += p.x;
		y += p.y;
		z += p.z;
		return this;
	}

	/**
	 * Add.
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @param result WB_Normal to store result
	 */
	public void addInto(final double x, final double y, final double z,
			final WB_Normal result) {
		result.x = (this.x + x);
		result.y = (this.y + y);
		result.z = (this.z + z);
	}

	/**
	 * Add.
	 *
	 * @param p 
	 * @param result WB_Normal to store result
	 */
	public void addInto(final WB_XYZ p, final WB_Normal result) {
		result.x = x + p.x;
		result.y = y + p.y;
		result.z = z + p.z;
	}

	/**
	 * Adds into new WB_Normal.
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal addAndCopy(final double x, final double y, final double z) {
		return new WB_Normal(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Adds into new WB_Normal.
	 *
	 * @param p 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal addAndCopy(final WB_XYZ p) {
		return new WB_Normal(x + p.x, y + p.y, z + p.z);
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return self
	 */
	@Override
	public WB_Normal sub(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * 
	 *
	 * @param v 
	 * @return self
	 */
	@Override
	public WB_Normal sub(final WB_XYZ v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @param result 
	 */
	public void subInto(final double x, final double y, final double z,
			final WB_Normal result) {
		result.x = (this.x - x);
		result.y = (this.y - y);
		result.z = (this.z - z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @param result 
	 */
	public void subInto(final WB_XYZ p, final WB_Normal result) {
		result.x = x - p.x;
		result.y = y - p.y;
		result.z = z - p.z;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal subAndCopy(final double x, final double y, final double z) {
		return new WB_Normal(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal subAndCopy(final WB_XYZ p) {
		return new WB_Normal(x - p.x, y - p.y, z - p.z);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	@Override
	public WB_Normal mult(final double f) {
		x *= f;
		y *= f;
		z *= f;
		return this;
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void multInto(final double f, final WB_Normal result) {
		result.x = (x * f);
		result.y = (y * f);
		result.z = (z * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal multAndCopy(final double f) {
		return new WB_Normal(x * f, y * f, z * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	@Override
	public WB_Normal div(final double f) {
		return mult(1.0 / f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void divInto(final double f, final WB_Normal result) {
		multInto(1.0 / f, result);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_Normal
	 */
	@Override
	public WB_Normal divAndCopy(final double f) {
		return multAndCopy(1.0 / f);
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.geom.WB_XYZ#cross(wblut.hemesh.geom.WB_XYZ)
	 */
	@Override
	public WB_Normal cross(final WB_XYZ p) {
		return new WB_Normal(y * p.z - z * p.y, z * p.x - x * p.z, x * p.y - y
				* p.x);
	}

	/**
	 * Cross product of two normals.
	 *
	 * @param p point, vector or normal
	 * @param q point, vector or normal
	 * @return new WB_Normal
	 */
	public static WB_Normal cross(final WB_XYZ p, final WB_XYZ q) {
		return new WB_Normal(p.y * q.z - p.z * q.y, p.z * q.x - p.x * q.z, p.x
				* q.y - p.y * q.x);
	}

	/**
	 * Cross product wit normal.
	 *
	 * @param p point, vector or normal
	 * @param result WB_Normal to store result
	 */
	public void crossInto(final WB_XYZ p, final WB_Normal result) {
		result.x = y * p.z - z * p.y;
		result.y = z * p.x - x * p.z;
		result.z = x * p.y - y * p.x;
	}

	/**
	 * Normalize.
	 *
	 * @param result WB_Normal to store result
	 */
	public void normalizeInto(final WB_Normal result) {
		final double d = mag();
		if (WB_Epsilon.isZero(d)) {
			result.set(0, 0, 0);
		} else {
			result.set(x, y, z);
			result.div(d);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString() {
		return "Normal [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	/**
	 * Rotate vertex around an arbitrary axis.
	 *
	 * @param angle angle
	 * @param p1x x-coordinate of first point on axis
	 * @param p1y y-coordinate of first point on axis
	 * @param p1z z-coordinate of first point on axis
	 * @param p2x x-coordinate of second point on axis
	 * @param p2y y-coordinate of second point on axis
	 * @param p2z z-coordinate of second point on axis
	 */
	@Override
	public void rotateAboutAxis(final double angle, final double p1x,
			final double p1y, final double p1z, final double p2x,
			final double p2y, final double p2z) {

		final WB_Transform raa = new WB_Transform();
		raa.addRotateAboutAxis(angle, new WB_Point(p1x, p1y, p1z),
				new WB_Normal(p2x - p1x, p2y - p1y, p2z - p1z));
		raa.applySelf(this);
	}

	/**
	 * Rotate vertex around an arbitrary axis.
	 *
	 * @param angle angle
	 * @param p1 first point on axis
	 * @param p2 second point on axis
	 */
	@Override
	public void rotateAboutAxis(final double angle, final WB_Point p1,
			final WB_Point p2) {

		final WB_Transform raa = new WB_Transform();
		raa.addRotateAboutAxis(angle, p1, p2.subToVector(p1));

		raa.applySelf(this);

	}

	public WB_Vector toVector() {
		return new WB_Vector(x, y, z);
	}

	@Override
	public WB_Point toPoint() {
		return new WB_Point(x, y, z);
	}
}
