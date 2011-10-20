/**
 * 
 */
package wblut.random;

import wblut.geom.WB_Normal;
import wblut.geom.WB_Point;
import wblut.geom.WB_Vector;

/**
 * 
 * Random generator for vectors uniformly distributed inside the unit sphere.
 * 
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_RandomSphere {
	private final WB_MTRandom	randomGen;

	public WB_RandomSphere() {
		randomGen = new WB_MTRandom();
	}

	/**
	 * Set random seed.
	 *
	 * @param seed seed
	 * @return self
	 */
	public WB_RandomSphere setSeed(final long seed) {
		randomGen.setSeed(seed);
		return this;
	}

	/**
	 * 
	 * @return next random WB_Normal inside unit sphere
	 */
	public WB_Point nextPoint() {
		final double eps = randomGen.nextDouble();
		final double z = 1.0 - 2.0 * eps;
		final double r = Math.sqrt(1.0 - z * z);
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Point(r * Math.cos(t), r * Math.sin(t), z);
	}

	/**
	 * 
	 * @return next random WB_Normal inside unit sphere
	 */
	public WB_Vector nextVector() {
		final double eps = randomGen.nextDouble();
		final double z = 1.0 - 2.0 * eps;
		final double r = Math.sqrt(1.0 - z * z);
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Vector(r * Math.cos(t), r * Math.sin(t), z);
	}

	/**
	 * 
	 * @return next random WB_Normal inside unit sphere
	 */
	public WB_Normal nextNormal() {
		final double eps = randomGen.nextDouble();
		final double z = 1.0 - 2.0 * eps;
		final double r = Math.sqrt(1.0 - z * z);
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Normal(r * Math.cos(t), r * Math.sin(t), z);
	}

}
