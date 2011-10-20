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
import wblut.random.WB_MTRandom;

// TODO: Auto-generated Javadoc
/**
 * Geometric point.
 *
 * @author Frederik Vanhoutte (W:Blut) 2010
 */
public class WB_Point extends WB_XYZ {

	/**
	 * Instantiates a new WB_Point.
	 */
	public WB_Point() {
		super();
	}

	/**
	 * Instantiates a new WB_Point.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 */
	public WB_Point(final double x, final double y, final double z) {
		super(x, y, z);
	}

	/**
	 * Instantiates a new WB_Point.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public WB_Point(final double x, final double y) {
		super(x, y);
	}

	/**
	 * Instantiates a new WB_Point.
	 *
	 * @param p WB_Vector, WB_Point or WB_Normal
	 */
	public WB_Point(final WB_XYZ p) {
		super(p);
	}

	/**
	 * return copy.
	 *
	 * @return copy
	 */
	@Override
	public WB_Point get() {
		return new WB_Point(x, y, z);
	}

	/**
	 * Move to position.
	 *
	 * @param x
	 * @param y 
	 * @param z
	 * @return self
	 */
	public WB_Point moveTo(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Move to position.
	 *
	 * @param x
	 * @param y 
	 * @return self
	 */
	public WB_Point moveTo(final double x, final double y) {
		this.x = x;
		this.y = y;
		z = 0;
		return this;
	}

	/**
	 * Move to position.
	 *
	 * @param p point, vector or normal
	 * @return self
	 */
	public WB_Point moveTo(final WB_XYZ p) {
		x = p.x;
		y = p.y;
		z = p.z;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return self
	 */
	public WB_Point moveBy(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @return self
	 */
	public WB_Point moveBy(final WB_XYZ v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @param result WB_Point to store result
	 */
	public void moveByInto(final double x, final double y, final double z,
			final WB_Point result) {
		result.x = this.x + x;
		result.y = this.y + y;
		result.z = this.z + z;
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @param result WB_Point to store result
	 */
	public void moveByInto(final WB_XYZ v, final WB_Point result) {
		result.x = x + v.x;
		result.y = y + v.y;
		result.z = z + v.z;
	}

	/**
	 * Move by vector.
	 *
	 * @param x 
	 * @param y 
	 * @param z
	 * @return new WB_Point
	 */
	public WB_Point moveByAndCopy(final double x, final double y, final double z) {
		return new WB_Point(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @return new WB_Point
	 */
	public WB_Point moveByAndCopy(final WB_XYZ v) {
		return new WB_Point(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * Scale.
	 *
	 * @param f scale factor
	 * @return self
	 */
	@Override
	public WB_Point scale(final double f) {
		x *= f;
		y *= f;
		z *= f;
		return this;
	}

	/**
	 * Scale.
	 *
	 * @param fx scale factor
	 * @param fy scale factor
	 * @param fz scale factor
	 * @return self
	 */
	@Override
	public WB_Point scale(final double fx, final double fy, final double fz) {
		x *= fx;
		y *= fy;
		z *= fz;
		return this;
	}

	/**
	 * Scale .
	 *
	 * @param f scale factor
	 * @param result WB_Point to store result
	 */
	public void scaleInto(final double f, final WB_Point result) {
		result.x = x * f;
		result.y = y * f;
		result.z = z * f;
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
	public WB_Point add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return self
	 */
	@Override
	public WB_Point add(final WB_XYZ p) {
		x += p.x;
		y += p.y;
		z += p.z;
		return this;
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return self
	 */
	@Override
	public WB_Point add(final WB_XYZ p, final double f) {
		x += f * p.x;
		y += f * p.y;
		z += f * p.z;
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
	public void addInto(final double x, final double y, final double z,
			final WB_Point result) {
		result.x = (this.x + x);
		result.y = (this.y + y);
		result.z = (this.z + z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @param result 
	 */
	public void addInto(final WB_XYZ p, final WB_Point result) {
		result.x = x + p.x;
		result.y = y + p.y;
		result.z = z + p.z;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return new WB_Point
	 */
	@Override
	public WB_Point addAndCopy(final double x, final double y, final double z) {
		return new WB_Point(this.x + x, this.y + y, this.z + z);
	}

	public WB_Point addAndCopy(final double x, final double y, final double z,
			final double f) {
		return new WB_Point(this.x + f * x, this.y + f * y, this.z + f * z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_Point
	 */
	@Override
	public WB_Point addAndCopy(final WB_XYZ p) {
		return new WB_Point(x + p.x, y + p.y, z + p.z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_Point
	 */
	public WB_Point addAndCopy(final WB_XYZ p, final double f) {
		return new WB_Point(x + f * p.x, y + f * p.y, z + f * p.z);
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
	public WB_Point sub(final double x, final double y, final double z) {
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
	public WB_Point sub(final WB_XYZ v) {
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
			final WB_Point result) {
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
	public void subInto(final WB_XYZ p, final WB_Point result) {
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
	 * @return new WB_Point
	 */
	@Override
	public WB_Point subAndCopy(final double x, final double y, final double z) {
		return new WB_Point(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_Point
	 */
	@Override
	public WB_Point subAndCopy(final WB_XYZ p) {
		return new WB_Point(x - p.x, y - p.y, z - p.z);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	@Override
	public WB_Point mult(final double f) {
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
	public void multInto(final double f, final WB_Point result) {
		result.x = (x * f);
		result.y = (y * f);
		result.z = (z * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_Point
	 */
	@Override
	public WB_Point multAndCopy(final double f) {
		return new WB_Point(x * f, y * f, z * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	@Override
	public WB_Point div(final double f) {
		return mult(1.0 / f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void divInto(final double f, final WB_Point result) {
		multInto(1.0 / f, result);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_Point
	 */
	@Override
	public WB_Point divAndCopy(final double f) {
		return multAndCopy(1.0 / f);
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.geom.WB_XYZ#cross(wblut.hemesh.geom.WB_XYZ)
	 */
	@Override
	public WB_Point cross(final WB_XYZ p) {
		return new WB_Point(y * p.z - z * p.y, z * p.x - x * p.z, x * p.y - y
				* p.x);
	}

	/**
	 * Cross product.
	 *
	 * @param p
	 * @param q
	 * @return new WB_Point
	 */
	public static WB_Point cross(final WB_XYZ p, final WB_XYZ q) {
		return new WB_Point(p.y * q.z - p.z * q.y, p.z * q.x - p.x * q.z, p.x
				* q.y - p.y * q.x);
	}

	/**
	 * Cross product.
	 *
	 * @param p
	 * @param result WB_Point to store result
	 */
	public void crossInto(final WB_XYZ p, final WB_Point result) {
		result.x = y * p.z - z * p.y;
		result.y = z * p.x - x * p.z;
		result.z = x * p.y - y * p.x;
	}

	/**
	 * Normalize.
	 *
	 * @param result WB_Point to store result
	 */
	public void normalizeInto(final WB_Point result) {
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
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
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
	public void rotateAboutAxis(final double angle, final double p1x,
			final double p1y, final double p1z, final double p2x,
			final double p2y, final double p2z) {

		final WB_Transform raa = new WB_Transform();
		raa.addRotateAboutAxis(angle, new WB_Point(p1x, p1y, p1z),
				new WB_Vector(p2x - p1x, p2y - p1y, p2z - p1z));
		raa.applySelf(this);
	}

	/**
	 * Rotate vertex around an arbitrary axis.
	 *
	 * @param angle angle
	 * @param p1 first point on axis
	 * @param p2 second point on axis
	 */
	public void rotateAboutAxis(final double angle, final WB_Point p1,
			final WB_Point p2) {

		final WB_Transform raa = new WB_Transform();
		raa.addRotateAboutAxis(angle, p1, p2.subToVector(p1));

		raa.applySelf(this);

	}

	/**
	 * Rotate vertex around an arbitrary axis.
	 *
	 * @param angle angle
	 * @param p rotation point
	 * @param a axis
	 */
	public void rotateAboutAxis(final double angle, final WB_Point p,
			final WB_Vector a) {

		final WB_Transform raa = new WB_Transform();
		raa.addRotateAboutAxis(angle, p, a);

		raa.applySelf(this);

	}

	// Get n points in range (-x,x), (-y,y),(-z,z)
	public static WB_Point[] randomPoints(final int n, final double x,
			final double y, final double z) {
		final WB_MTRandom mtr = new WB_MTRandom();
		final WB_Point[] points = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			points[i] = new WB_Point(-x + 2 * mtr.nextDouble() * x, -y + 2
					* mtr.nextDouble() * y, -z + 2 * mtr.nextDouble() * z);
		}

		return points;
	}

	// Get n points in range (lx,ux), (ly,uy),(lz,uz)
	public static WB_Point[] randomPoints(final int n, final double lx,
			final double ly, final double lz, final double ux, final double uy,
			final double uz) {
		final WB_MTRandom mtr = new WB_MTRandom();
		final WB_Point[] points = new WB_Point[n];
		final double dx = ux - lx;
		final double dy = uy - ly;
		final double dz = uz - lz;

		for (int i = 0; i < n; i++) {
			points[i] = new WB_Point(lx + mtr.nextDouble() * dx, ly
					+ mtr.nextDouble() * dy, lz + mtr.nextDouble() * dz);
		}

		return points;
	}

	/**
	 * Interpolate.
	 *
	 */
	public static WB_Point interpolate(final WB_XYZ p0, final WB_XYZ p1,
			final double t) {
		return new WB_Point(p0.x + t * (p1.x - p0.x), p0.y + t * (p1.y - p0.y),
				p0.z + t * (p1.z - p0.z));

	}

	public WB_Vector toVector() {
		return new WB_Vector(x, y, z);
	}

	public WB_Normal toNormal() {
		return new WB_Normal(x, y, z);
	}

}
