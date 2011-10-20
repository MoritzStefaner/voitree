/**
 * 
 */
package wblut.random;

import wblut.geom.WB_Normal;
import wblut.geom.WB_Point;
import wblut.geom.WB_Vector;

/**
 * 
 * Random generator for vectors uniformly distributed inside the unit disk.
 * 
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_RandomDisc {
	private final WB_MTRandom	randomGen;

	public WB_RandomDisc() {
		randomGen = new WB_MTRandom();
	}

	/**
	 * Set random seed.
	 *
	 * @param seed seed
	 * @return self
	 */
	public WB_RandomDisc setSeed(final long seed) {
		randomGen.setSeed(seed);
		return this;
	}

	/**
	 * 
	 * @return next random WB_Point inside unit disk
	 */
	public WB_Point nextPoint() {
		final double r = Math.sqrt(randomGen.nextDouble());
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Point(r * Math.cos(t), r * Math.sin(t), 0);
	}

	/**
	 * 
	 * @return next random WB_Vector inside unit disk
	 */
	public WB_Vector nextVector() {
		final double r = Math.sqrt(randomGen.nextDouble());
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Vector(r * Math.cos(t), r * Math.sin(t), 0);
	}

	/**
	 * 
	 * @return next random WB_Normal inside unit disk
	 */
	public WB_Normal nextNormal() {
		final double r = Math.sqrt(randomGen.nextDouble());
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Normal(r * Math.cos(t), r * Math.sin(t), 0);
	}

}