/**
 * 
 */
package wblut.geom2D;

import wblut.geom.WB_XYZ;
import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;
import wblut.random.WB_MTRandom;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_XY {
	/** Coordinates. */
	public double	x, y;

	/**
	 * Instantiates a new WB_XY.
	 */
	public WB_XY() {
		x = y = 0;
	}

	/**
	 * Instantiates a new WB_XY.
	 *
	 * @param x
	 * @param y
	 */
	public WB_XY(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Instantiates a new WB_XY.
	 *
	 * @param v
	 */
	public WB_XY(final WB_XY v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * Instantiates a new WB_XY.
	 *
	 * @param v
	 */
	public WB_XY(final WB_XYZ v) {
		x = v.x;
		y = v.y;
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
	}

	/**
	 * Set coordinates.
	 *
	 * @param v
	 */
	public void set(final WB_XY v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * Dot product.
	 *
	 * @param p
	 * @param q
	 * @return dot product
	 */
	public static double dot(final WB_XY p, final WB_XY q) {
		return (p.x * q.x + p.y * q.y);
	}

	/**
	 * Dot product.
	 *
	 * @param p
	 * @return dot product
	 */
	public double dot(final WB_XY p) {
		return (p.x * x + p.y * y);
	}

	/**
	 * Perp dot product.
	 *
	 * @param p
	 * @param q
	 * @return dot product
	 */
	public static double perpDot(final WB_XY p, final WB_XY q) {
		return (-p.y * q.x + p.x * q.y);
	}

	/**
	 * Perp dot product.
	 *
	 * @param p
	 * @return dot product
	 */
	public double perpDot(final WB_XY p) {
		return (-p.x * y + p.y * x);
	}

	/**
	 * Angle to vector. Normalized vectors are assumed.
	 *
	 * @param p normalized point, vector or normal
	 * @return angle
	 */
	public double angleNorm(final WB_XY p) {
		return Math.acos(p.x * x + p.y * y);
	}

	/**
	 * Absolute value of dot product.
	 *
	 * @param p
	 * @param q 
	 * @return absolute value of dot product
	 */
	public static double absDot(final WB_XY p, final WB_XY q) {
		return WB_Fast.abs(p.x * q.x + p.y * q.y);
	}

	/**
	 * Absolute value of dot product.
	 *
	 * @param p
	* @return absolute value of dot product
	 */
	public double absDot(final WB_XY p) {
		return WB_Fast.abs(p.x * x + p.y * y);
	}

	/**
	 * Get squared magnitude.
	 *
	 * @return squared magnitude
	 */
	public double mag2() {
		return x * x + y * y;
	}

	/**
	 * Get magnitude.
	 *
	 * @return magnitude
	 */
	public double mag() {
		return Math.sqrt(x * x + y * y);
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
	public int compareTo(final WB_XY otherXY) {
		int _tmp = WB_Epsilon.compareAbs(x, otherXY.x);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(y, otherXY.y);

		return _tmp;
	}

	public int compareToY1st(final WB_XY otherXY) {
		int _tmp = WB_Epsilon.compareAbs(y, otherXY.y);
		if (_tmp != 0) {
			return _tmp;
		}
		_tmp = WB_Epsilon.compareAbs(x, otherXY.x);

		return _tmp;

	}

	/**
	 * Smaller than.
	 *
	 * @param otherXYZ point, vector or normal
	 * @return true, if successful
	 */
	public boolean smallerThan(final WB_XY otherXYZ) {
		int _tmp = WB_Epsilon.compareAbs(x, otherXYZ.x);
		if (_tmp != 0) {
			return (_tmp < 0);
		}
		_tmp = WB_Epsilon.compareAbs(y, otherXYZ.y);
		return (_tmp < 0);

	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XY [x=" + x + ", y=" + y + "]";
	}

	/**
	 * Get coordinate from index value.
	 *
	 * @param i 0,1
	 * @return x- or y-coordinate
	 */
	public double get(final int i) {
		if (i == 0) {
			return x;
		}
		if (i == 1) {
			return y;
		}

		return Double.NaN;
	}

	/**
	 * Set coordinate with index value.
	 *
	 * @param i  0,1
	 * @param v x- or y-coordinate
	 */
	public void set(final int i, final double v) {
		if (i == 0) {
			x = v;
		} else if (i == 1) {
			y = v;
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
	 * Cross product. Internal use only.
	 */
	public WB_XYZ cross(final WB_XY p) {
		return new WB_XYZ(0, 0, x * p.y - y * p.x);
	}

	/**
	 * Cross product. Internal use only.
	 */
	public static WB_XYZ cross(final WB_XY p, final WB_XY q) {
		return new WB_XYZ(0, 0, p.x * q.y - p.y * q.x);
	}

	/**
	 * Cross product. Internal use only.
	 */
	public void crossInto(final WB_XY p, final WB_XYZ result) {
		result.x = 0;
		result.y = 0;
		result.z = x * p.y - y * p.x;
	}

	/**
	 * return copy.
	 *
	 * @return copy
	 */
	public WB_XY get() {
		return new WB_XY(x, y);
	}

	/*
	 */
	public WB_XY perp() {
		return new WB_XY(-y, x);
	}

	/**
	 */
	public void perpInto(final WB_XY result) {
		result.set(-y, x);
	}

	/**
	 * Move to position.
	 *
	 * @param x
	 * @param y 
	 * @return self
	 */
	public WB_XY moveTo(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Move to position.
	 *
	 * @param p point, vector or normal
	 * @return self
	 */
	public WB_XY moveTo(final WB_XY p) {
		x = p.x;
		y = p.y;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param x
	 * @param y
	 * @return self
	 */
	public WB_XY moveBy(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @return self
	 */
	public WB_XY moveBy(final WB_XY v) {
		x += v.x;
		y += v.y;
		return this;
	}

	/**
	 * Move by vector.
	 *
	 * @param x 
	 * @param y 
	 * @param result WB_XY to store result
	 */
	public void moveByInto(final double x, final double y, final WB_XY result) {
		result.x = this.x + x;
		result.y = this.y + y;
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @param result WB_XY to store result
	 */
	public void moveByInto(final WB_XY v, final WB_XY result) {
		result.x = x + v.x;
		result.y = y + v.y;
	}

	/**
	 * Move by vector.
	 *
	 * @param x 
	 * @param y 
	 * @return new WB_XY
	 */
	public WB_XY moveByAndCopy(final double x, final double y) {
		return new WB_XY(this.x + x, this.y + y);
	}

	/**
	 * Move by vector.
	 *
	 * @param v point, vector or normal
	 * @return new WB_XY
	 */
	public WB_XY moveByAndCopy(final WB_XY v) {
		return new WB_XY(x + v.x, y + v.y);
	}

	/**
	 * Scale.
	 *
	 * @param f scale factor
	 * @return self
	 */
	public WB_XY scale(final double f) {
		x *= f;
		y *= f;
		return this;
	}

	/**
	 * Scale .
	 *
	 * @param f scale factor
	 * @param result WB_XY to store result
	 */
	public void scaleInto(final double f, final WB_XY result) {
		result.x = x * f;
		result.y = y * f;
	}

	/**
	 * Scale.
	 *
	 * @param fx scale factor
	 * @param fy scale factor
	 * @return self
	 */
	public WB_XY scale(final double fx, final double fy) {
		x *= fx;
		y *= fy;
		return this;
	}

	/**
	 * Scale .
	 *
	 * @param fx scale factor
	 * @param fy scale factor
	 * @param result WB_XY to store result
	 */
	public void scaleInto(final double fx, final double fy, final WB_XY result) {
		result.x = x * fx;
		result.y = y * fy;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return self
	 */
	public WB_XY add(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return self
	 */
	public WB_XY add(final double x, final double y, final double f) {
		this.x += f * x;
		this.y += f * y;
		return this;
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return self
	 */
	public WB_XY add(final WB_XY p) {
		x += p.x;
		y += p.y;
		return this;
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return self
	 */
	public WB_XY add(final WB_XY p, final double f) {
		x += f * p.x;
		y += f * p.y;
		return this;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @param result 
	 */
	public void addInto(final double x, final double y, final WB_XY result) {
		result.x = (this.x + x);
		result.y = (this.y + y);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @param result 
	 */
	public void addInto(final WB_XYZ p, final WB_XY result) {
		result.x = x + p.x;
		result.y = y + p.y;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return new WB_XY
	 */
	public WB_XY addAndCopy(final double x, final double y, final double f) {
		return new WB_XY(this.x + f * x, this.y + f * y);
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return new WB_XY
	 */
	public WB_XY addAndCopy(final double x, final double y) {
		return new WB_XY(this.x + x, this.y + y);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_XY
	 */
	public WB_XY addAndCopy(final WB_XY p) {
		return new WB_XY(x + p.x, y + p.y);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_XY
	 */
	public WB_XY addAndCopy(final WB_XY p, final double f) {
		return new WB_XY(x + f * p.x, y + f * p.y);
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return self
	 */
	public WB_XY sub(final double x, final double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * 
	 *
	 * @param v 
	 * @return self
	 */
	public WB_XY sub(final WB_XY v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 

	 * @param result 
	 */
	public void subInto(final double x, final double y, final WB_XY result) {
		result.x = (this.x - x);
		result.y = (this.y - y);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @param result 
	 */
	public void subInto(final WB_XY p, final WB_XY result) {
		result.x = x - p.x;
		result.y = y - p.y;
	}

	/**
	 * 
	 *
	 * @param x 
	 * @param y 
	 * @return new WB_XY
	 */
	public WB_XY subAndCopy(final double x, final double y) {
		return new WB_XY(this.x - x, this.y - y);
	}

	/**
	 * 
	 *
	 * @param p 
	 * @return new WB_XY
	 */
	public WB_XY subAndCopy(final WB_XY p) {
		return new WB_XY(x - p.x, y - p.y);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	public WB_XY mult(final double f) {
		x *= f;
		y *= f;
		return this;
	}

	/**
	 * 
	 *
	 * @return self
	 */
	public WB_XY invert() {
		x *= -1;
		y *= -1;
		return this;
	}

	public double normalize() {
		final double d = mag();
		if (WB_Epsilon.isZero(d)) {
			set(0, 0);
		} else {
			set(x / d, y / d);
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
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void multInto(final double f, final WB_XY result) {
		result.x = (x * f);
		result.y = (y * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_XY
	 */
	public WB_XY multAndCopy(final double f) {
		return new WB_XY(x * f, y * f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return self
	 */
	public WB_XY div(final double f) {
		return mult(1.0 / f);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @param result 
	 */
	public void divInto(final double f, final WB_XY result) {
		multInto(1.0 / f, result);
	}

	/**
	 * 
	 *
	 * @param f 
	 * @return new WB_XY
	 */
	public WB_XY divAndCopy(final double f) {
		return multAndCopy(1.0 / f);
	}

	/**
	 * Normalize.
	 *
	 * @param result WB_XY to store result
	 */
	public void normalizeInto(final WB_XY result) {
		final double d = mag();
		if (WB_Epsilon.isZero(d)) {
			result.set(0, 0);
		} else {
			result.set(x, y);
			result.div(d);
		}
	}

	public static WB_XY[] randomPoints(final int n, final double x,
			final double y) {
		final WB_MTRandom mtr = new WB_MTRandom();
		final WB_XY[] points = new WB_XY[n];
		for (int i = 0; i < n; i++) {
			points[i] = new WB_XY(-x + 2 * mtr.nextDouble() * x, -y + 2
					* mtr.nextDouble() * y);
		}

		return points;
	}

	public static WB_XY[] randomPoints(final int n, final double lx,
			final double ly, final double ux, final double uy) {
		final WB_MTRandom mtr = new WB_MTRandom();
		final WB_XY[] points = new WB_XY[n];
		final double dx = ux - lx;
		final double dy = uy - ly;

		for (int i = 0; i < n; i++) {
			points[i] = new WB_XY(lx + mtr.nextDouble() * dx, ly
					+ mtr.nextDouble() * dy);
		}

		return points;
	}

	/**
	 * Is vector parallel to other vector
	 * 
	 * @param p
	 * @return true, if parallel
	 */
	public boolean isParallel(final WB_XY p) {
		return (cross(p).mag2() / (p.mag2() * mag2()) < WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is vector parallel to other vector
	 * 
	 * @param p
	 * @param t threshold value = (sin(threshold angle))^2
	 * @return true, if parallel
	 */
	public boolean isParallel(final WB_XY p, final double t) {
		return (cross(p).mag2() / (p.mag2() * mag2()) < t
				+ WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is normalized vector parallel to other normalized vector
	 * 
	 * @param p
	 * @return true, if parallel
	 */
	public boolean isParallelNorm(final WB_XY p) {
		return (cross(p).mag2() < WB_Epsilon.SQEPSILON);
	}

	/**
	 * Is normalized vector parallel to other normalized vector
	 * 
	 * @param p
	 * @param t threshold value = (sin(threshold angle))^2
	 * @return true, if parallel
	 */
	public boolean isParallelNorm(final WB_XY p, final double t) {
		return (cross(p).mag2() < t + WB_Epsilon.SQEPSILON);
	}

	protected static int calculateHashCode(final double x, final double y) {
		int result = 17;

		final long a = Double.doubleToLongBits(x);
		result += 31 * result + (int) (a ^ (a >>> 32));

		final long b = Double.doubleToLongBits(y);
		result += 31 * result + (int) (b ^ (b >>> 32));

		return result;

	}

	protected int calculateHashCode() {
		int result = 17;

		final long a = Double.doubleToLongBits(x);
		result += 31 * result + (int) (a ^ (a >>> 32));

		final long b = Double.doubleToLongBits(y);
		result += 31 * result + (int) (b ^ (b >>> 32));

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
	public static WB_XY interpolate(final WB_XY p0, final WB_XY p1,
			final double t) {
		return new WB_XY(p0.x + t * (p1.x - p0.x), p0.y + t * (p1.y - p0.y));

	}

	public void rotate(final double angle) {
		final double tmpx = x;
		final double tmpy = y;
		final double ca = Math.cos(angle);
		final double sa = Math.sin(angle);
		x = ca * tmpx - sa * tmpy;
		y = sa * tmpx + ca * tmpy;
	}

	public void rotate(final double angle, final WB_XY origin) {
		final double tmpx = x - origin.x;
		final double tmpy = y - origin.y;
		final double ca = Math.cos(angle);
		final double sa = Math.sin(angle);
		x = origin.x + ca * tmpx - sa * tmpy;
		y = origin.y + sa * tmpx + ca * tmpy;
	}

	public void rotate(final double cosangle, final double sinangle) {
		final double tmpx = x;
		final double tmpy = y;
		x = cosangle * tmpx - sinangle * tmpy;
		y = sinangle * tmpx + cosangle * tmpy;
	}

	public void rotate(final double cosangle, final double sinangle,
			final WB_XY origin) {
		final double tmpx = x - origin.x;
		final double tmpy = y - origin.y;
		x = origin.x + cosangle * tmpx - sinangle * tmpy;
		y = origin.y + sinangle * tmpx + cosangle * tmpy;
	}
}
