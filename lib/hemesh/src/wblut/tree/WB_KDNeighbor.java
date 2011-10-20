/**
 * 
 */
package wblut.tree;

import java.util.Map;

import wblut.geom.WB_Point;

/**
 * 
 *
 * WB_KDNeighbor stores entries from a nearest-neighbor search. It contains the
 * point, value and squared distance from the query point. 
 *
 * @author Frederik Vanhoutte, W:Blut
 */
public class WB_KDNeighbor<V> implements Comparable<WB_KDNeighbor<V>> {
	private final double	sqDistance;
	private final WB_Point	neighbor;
	private final V			value;

	WB_KDNeighbor(final double d2, final Map.Entry<WB_Point, V> neighbor) {
		sqDistance = d2;
		this.neighbor = neighbor.getKey();
		this.value = neighbor.getValue();
	}

	/**
	 * Squared distance to the query point
	 * @return squared distance
	 */
	public double sqDistance() {
		return sqDistance;
	}

	/**
	 * Value of neighbor
	 * @return value
	 */
	public V value() {
		return value;
	}

	/**
	 * Position of neighbor
	 * @return WB_Point
	 */
	public WB_Point point() {
		return neighbor;
	}

	public int compareTo(final WB_KDNeighbor<V> obj) {
		final double d = obj.sqDistance();
		if (sqDistance < d) {
			return -1;
		} else if (sqDistance > d) {
			return 1;
		}

		return 0;
	}
}