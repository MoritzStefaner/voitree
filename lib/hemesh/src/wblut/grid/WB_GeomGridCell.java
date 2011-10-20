/**
 * 
 */
package wblut.grid;

import java.util.ArrayList;

import wblut.geom.WB_AABB;
import wblut.geom.WB_Point;
import wblut.geom.WB_Segment;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_GeomGridCell {
	protected int					index;
	protected WB_AABB				aabb;
	protected ArrayList<WB_Point>	points;
	protected ArrayList<WB_Segment>	segments;

	public WB_GeomGridCell(final int index, final WB_Point min,
			final WB_Point max) {
		this.index = index;
		points = new ArrayList<WB_Point>();
		segments = new ArrayList<WB_Segment>();
		aabb = new WB_AABB(min, max);

	}

	public void addPoint(final WB_Point p) {
		points.add(p);
	}

	public void removePoint(final WB_Point p) {
		points.remove(p);
	}

	public void addSegment(final WB_Segment seg) {
		segments.add(seg);
	}

	public void removeSegment(final WB_Segment seg) {
		segments.remove(seg);
	}

	public ArrayList<WB_Point> getPoints() {
		return points;
	}

	public ArrayList<WB_Segment> getSegments() {
		return segments;
	}

	public int getIndex() {
		return index;
	}

	public WB_AABB getAABB() {
		return aabb;
	}

	public boolean isEmpty() {
		return points.isEmpty() && segments.isEmpty();

	}
}
