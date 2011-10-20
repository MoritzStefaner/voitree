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
import wblut.math.WB_Fast;
import wblut.math.WB_M33;

// TODO: Auto-generated Javadoc
/**
 * Abstract class. Implements common methods for WB_Point, WB_Vector and WB_Normal
 *
 * @author Frederik Vanhoutte (W:Blut) 2010
 */
public class WB_XYZ implements Comparable<WB_XYZ> {

	/** Coordinates. */
	public double	x, y, z;

	/**
	 * Instantiates a new WB_XYZ.
	 */
	public WB_XYZ() {
		x = y = z = 0;
	}

	/**
	 * Instantiates a new WB_XYZ.
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public WB_XYZ(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Instantiates a new WB_XYZ.
	 *
	 * @param x
	 * @param y
	 */
	public WB_XYZ(final double x, final double y) {
		this.x = x;
		this.y = y;
		z = 0;
	}

	/**
	 * Instantiates a new WB_XYZ.
	 *
	 * @param v
	 */
	public WB_XYZ(final WB_XYZ v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * return copy.
	 *
	 * @return copy
	 */
	public WB_XYZ get() {
		return new WB_XYZ(x, y, z);
	}

	/**
	 * Set coordinates.
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public void set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Set coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public void set(final double x, final double y) {
		this.x = x;
		this.y = y;
		z = 0;
	}

	/**
	 * Set coordinates.
	 *
	 * @param v
	 */
	public void set(final WB_XYZ v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * 
	 *

	 */
	public void invert() {
		x *= -1;
		y *= -1;
		z *= -1;
	}

	public double normalize() {
		final double d = mag();
		if (WB_Epsilon.isZero(d)) {
			set(0, 0, 0);
		} else {
			set(x / d, y / d, z / d);
		}
		return d;
	}

	public void trim(final double d) {
		if (mag2() > d * d) {
			normalize();
			mult(d);
		}
	}

	/**
	 * Scale.
	 *
	 * @param f scale factor
	 * @return self
	 */
	public WB_XYZ scale(final double f) {
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

	public WB_XYZ scale(final double fx, final double fy, final double fz) {
		x *= fx;
		y *= fy;
		z *= fz;
		return this;
	}

	/**
	 * Scale .
	 *
	 * @param f scale factor
	 * @param result WB_XYZ to store result
	 */
	public void scaleInto(final double f, final WB_XYZ result) {
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
	public WB_XYZ add(final double x, final double y, final double z) {
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
	public WB_XYZ add(final WB_XYZ p) {
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
	public WB_XYZ add(final WB_XYZ p, final double f) {
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
			final WB_XYZ result) {
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
	public void addInto(final WB_XYZ p, final WB_XYZ result) {
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
	 * @return new WB_XYZW
	 */
	public WB_XYZ addAndCopy(final double x, final double y, final double z) {
		return new WB_XYZ(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_XYZ
	 */
	public WB_XYZ addAndCopy(final WB_XYZ p) {
		return new WB_XYZ(x + p.x, y + p.y, z + p.z);
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param z 
	 * @return self
	 */
	public WB_XYZ sub(final double x, final double y, final double z) {
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
	public WB_XYZ sub(final WB_XYZ v) {
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
			final WB_XYZ result) {
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
	public void subInto(final WB_XYZ p, final WB_XYZ result) {
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
	 * @return new WB_XYZ
	 */
	public WB_XYZ subAndCopy(final double x, final double y, final double z) {
		return new WB_XYZ(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_XYZ
	 */
	public WB_XYZ subAndCopy(final WB_XYZ p) {
		return new WB_XYZ(x - p.x, y - p.y, z - p.z);
	}

	/**
	 * Subtract to vector.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return new WB_Vector
	 */
	public WB_Vector subToVector(final double x, final double y, final double z) {
		return new WB_Vector(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * Subtract to vector.
	 *
	 * @param p point
	 * @return new WB_Vector
	 */
	public WB_Vector subToVector(final WB_XYZ p) {
		return new WB_Vector(x - p.x, y - p.y, z - p.z);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	public WB_XYZ mult(final double f) {
		scale(f);
		return this;
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void multInto(final double f, final WB_XYZ result) {
		scaleInto(f, result);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_XYZW
	 */
	public WB_XYZ multAndCopy(final double f) {
		return new WB_XYZ(x * f, y * f, z * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	public WB_XYZ div(final double f) {
		return mult(1.0 / f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void divInto(final double f, final WB_XYZ result) {
		multInto(1.0 / f, result);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_XYZW
	 */
	public WB_XYZ divAndCopy(final double f) {
		return multAndCopy(1.0 / f);
	}

	/**
	 * Dot product.
	 *
	 * @param p
	 * @param q
	 * @return dot product
	 */
	public static double dot(final WB_XYZ p, final WB_XYZ q) {
		return (p.x * q.x + p.y * q.y + p.z * q.z);
	}

	/**
	 * Dot product.
	 *
	 * @param p
	 * @return dot product
	 */
	public double dot(final WB_XYZ p) {
		return (p.x * x + p.y * y + p.z * z);
	}

	/**
	 * Angle to vector. Normalized vectors are assumed.
	 *
	 * @param p normalized point, vector or normal
	 * @return angle
	 */
	public double angleNorm(final WB_XYZ p) {
		return Math.acos(p.x * x + p.y * y + p.z * z);
	}

	/**
	 * Absolute value of dot product.
	 *
	 * @param p
	 * @param q 
	 * @return absolute value of dot product
	 */
	public static double absDot(final WB_XYZ p, final WB_XYZ q) {
		return WB_Fast.abs(p.x * q.x + p.y * q.y + p.z * q.z);
	}

	/**
	 * Absolute value of dot product.
	 *
	 * @param p
	* @return absolute value of dot product
	 */
	public double absDot(final WB_XYZ p) {
		return WB_Fast.abs(p.x * x + p.y * y + p.z * z);
	}

	/**
	 * Cross product. Internal use only.
	 */
	public WB_XYZ cross(final WB_XYZ p) {
		return new WB_XYZ(y * p.z - z * p.y, z * p.x - x * p.z, x * p.y - y
				* p.x);
	}

	/**
	 * Cross product. Internal use only.
	 */
	public static WB_XYZ cross(final WB_XYZ p, final WB_XYZ q) {
		return new WB_XYZ(p.y * q.z - p.z * q.y, p.z * q.x - p.x * q.z, p.x
				* q.y - p.y * q.x);
	}

	/**
	 * Cross product. Internal use only.
	 */
	public void crossInto(final WB_XYZ p, final WB_XYZ result) {
		result.x = y * p.z - z * p.y;
		result.y = z * p.x - x * p.z;
		result.z = x * p.y - y * p.x;
	}

	/**
	 * Scalar triple product.
	 *
	 * @param p
	 * @param q
	 * @param r
	 * @return scalar triple product
	 */
	public static double scalarTriple(final WB_XYZ p, final WB_XYZ q,
			final WB_XYZ r) {
		return (dot(p, cross(q, r)));
	}

	/**
	 * Scalar triple product.
	 *
	 * @param p
	 * @param q
	 * @return scalar triple product.
	 */
	public double scalarTriple(final WB_XYZ p, final WB_XYZ q) {
		return (dot(this, cross(p, q)));
	}

	public static WB_M33 tensor(final WB_XYZ p, final WB_XYZ q) {
		return new WB_M33(p.x * q.x, p.x * q.y, p.x * q.z, p.y * q.x,
				p.y * q.y, p.y * q.z, p.z * q.x, p.z * q.y, p.z * q.z);
	}

	public WB_M33 tensor(final WB_XYZ q) {
		return new WB_M33(x * q.x, x * q.y, x * q.z, y * q.x, y * q.y, y * q.z,
				z * q.x, z * q.y, z * q.z);
	}

	/**
	 * Get squared magnitude.
	 *
	 * @return squared magnitude
	 */
	public double mag2() {
		return x * x + y * y + z * z;
	}

	/**
	 * Get magnitude.
	 *
	 * @return magnitude
	 */
	public double mag() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Checks if vector is zero-vector.
	 *
	 * @return true, if zero
	 */
	public boolean isZero() {
		return (mag2() < WB_Epsilon.SQEPSILON);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final WB_XYZ otherXYZ) {
		int _tmp = WB_Epsilon.compareAbs(x, otherXYZ.x);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(y, otherXYZ.y);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(z, otherXYZ.z);
		return _tmp;
	}

	public int compareToY1st(final WB_XYZ otherXYZ) {
		int _tmp = WB_Epsilon.compareAbs(y, otherXYZ.y);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(x, otherXYZ.x);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(z, otherXYZ.z);
		return _tmp;
	}

	/**
	 * Smaller than.
	 *
	 * @param otherXYZ point, vector or normal
	 * @return true, if successful
	 */
	public boolean smallerThan(final WB_XYZ otherXYZ) {
		int _tmp = WB_Epsilon.compareAbs(x, otherXYZ.x);
		if (_tmp != 0) {
			return (_tmp < 0);
		}
		_tmp = WB_Epsilon.compareAbs(y, otherXYZ.y);
		if (_tmp != 0) {
			return (_tmp < 0);
		}
		_tmp = WB_Epsilon.compareAbs(z, otherXYZ.z);
		return (_tmp < 0);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XYZ [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	/**
	 * Get coordinate from index value.
	 *
	 * @param i 0,1,2
	 * @return x-,y- or z-coordinate
	 */
	public double get(final int i) {
		if (i == 0) {
			return x;
		}
		if (i == 1) {
			return y;
		}
		if (i == 2) {
			return z;
		}
		return Double.NaN;
	}

	/**
	 * Set coordinate with index value.
	 *
	 * @param i  0,1,2
	 * @param v x-,y- or z-coordinate
	 */
	public void set(final int i, final double v) {
		if (i == 0) {
			x = v;
		} else if (i == 1) {
			y = v;
		} else if (i == 2) {
			z = v;
		}

	}

	/**
	 * Get x-coordinate as float.
	 *
	 * @return x
	 */
	public float xf() {
		return (float) x;
	}

	/**
	 * Get y-coordinate as float.
	 *
	 * @return y
	 */
	public float yf() {
		return (float) y;
	}

	/**
	 * Get z-coordinate as float.
	 *
	 * @return z
	 */
	public float zf() {
		return (float) z;
	}

	/**
	 * Is vector parallel to other vector
	 * 
	 * @param p
	 * @return true, if parallel
	 */
	public boolean isParallel(final WB_XYZ p) {
		return (cross(p).mag2() / (p.mag2() * mag2()) < WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is vector parallel to other vector
	 * 
	 * @param p
	 * @param t threshold value = (sin(threshold angle))^2
	 * @return true, if parallel
	 */
	public boolean isParallel(final WB_XYZ p, final double t) {
		return (cross(p).mag2() / (p.mag2() * mag2()) < t
				+ WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is normalized vector parallel to other normalized vector
	 * 
	 * @param p
	 * @return true, if parallel
	 */
	public boolean isParallelNorm(final WB_XYZ p) {
		return (cross(p).mag2() < WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is normalized vector parallel to other normalized vector
	 * 
	 * @param p
	 * @param t threshold value = (sin(threshold angle))^2
	 * @return true, if parallel
	 */
	public boolean isParallelNorm(final WB_XYZ p, final double t) {
		return (cross(p).mag2() < t + WB_Epsilon.SQEPSILON);
	}

	protected static int calculateHashCode(final double x, final double y,
			final double z) {
		int result = 17;

		final long a = Double.doubleToLongBits(x);
		result += 31 * result + (int) (a ^ (a >>> 32));

		final long b = Double.doubleToLongBits(y);
		result += 31 * result + (int) (b ^ (b >>> 32));

		final long c = Double.doubleToLongBits(z);
		result += 31 * result + (int) (c ^ (c >>> 32));

		return result;

	}

	protected int calculateHashCode() {
		int result = 17;

		final long a = Double.doubleToLongBits(x);
		result += 31 * result + (int) (a ^ (a >>> 32));

		final long b = Double.doubleToLongBits(y);
		result += 31 * result + (int) (b ^ (b >>> 32));

		final long c = Double.doubleToLongBits(z);
		result += 31 * result + (int) (c ^ (c >>> 32));

		return result;

	}

	/**
	 * Interpolate.
	 *
	 * @param p0 the p0
	 * @param p1 the p1
	 * @param t the t
	 * @return the w b_ point
	 */
	public static WB_XYZ interpolate(final WB_XYZ p0, final WB_XYZ p1,
			final double t) {
		return new WB_XYZ(p0.x + t * (p1.x - p0.x), p0.y + t * (p1.y - p0.y),
				p0.z + t * (p1.z - p0.z));

	}

	public static double angleBetween(final WB_XYZ corner, final WB_XYZ p1,
			final WB_XYZ p2) {
		final WB_XYZ v0 = p1.subAndCopy(corner);
		final WB_XYZ v1 = p2.subAndCopy(corner);
		v0.normalize();
		v1.normalize();
		return Math.acos(v0.dot(v1));

	}

	public static double cosAngleBetween(final WB_XYZ corner, final WB_XYZ p1,
			final WB_XYZ p2) {
		final WB_XYZ v0 = p1.subAndCopy(corner);
		final WB_XYZ v1 = p2.subAndCopy(corner);
		v0.normalize();
		v1.normalize();
		return v0.dot(v1);

	}

}
