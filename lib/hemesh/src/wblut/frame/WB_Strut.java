/**
 * 
 */
package wblut.frame;

import wblut.geom.WB_Distance;
import wblut.geom.WB_ExplicitSegment;
import wblut.geom.WB_Plane;
import wblut.geom.WB_Point;
import wblut.geom.WB_Vector;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Strut {
	private final WB_Node	start;
	private final WB_Node	end;
	private final int		index;
	private double			radiuss;
	private double			radiuse;
	private double			offsets;
	private double			offsete;

	public WB_Strut(final WB_Node s, final WB_Node e, final int id) {
		start = s;
		end = e;
		index = id;
	}

	public WB_Strut(final WB_Node s, final WB_Node e, final int id,
			final double r) {
		start = s;
		end = e;
		index = id;
		radiuss = radiuse = r;
	}

	public WB_Strut(final WB_Node s, final WB_Node e, final int id,
			final double rs, final double re) {
		start = s;
		end = e;
		index = id;
		radiuss = rs;
		radiuse = re;
	}

	public WB_Node start() {
		return start;
	}

	public WB_Node end() {
		return end;
	}

	public int getStartIndex() {
		return start.getIndex();
	}

	public int getEndIndex() {
		return end.getIndex();
	}

	public int getIndex() {
		return index;
	}

	public WB_Vector toVector() {
		return end().subToVector(start());
	}

	public WB_Vector toNormVector() {
		final WB_Vector v = end().subToVector(start());
		v.normalize();
		return v;
	}

	public double getSqLength() {
		return WB_Distance.sqDistance(end(), start());
	}

	public double getLength() {
		return WB_Distance.distance(end(), start());
	}

	public double getRadiusStart() {
		return radiuss;
	}

	public double getRadiusEnd() {
		return radiuse;
	}

	public void setRadiusStart(final double r) {
		radiuss = r;
	}

	public void setRadiusEnd(final double r) {
		radiuse = r;
	}

	public double getOffsetStart() {
		return offsets;
	}

	public double getOffsetEnd() {
		return offsete;
	}

	public void setOffsetStart(final double o) {
		offsets = o;
	}

	public void setOffsetEnd(final double o) {
		offsete = o;
	}

	public WB_Point getCenter() {
		return end().addAndCopy(start()).mult(0.5);
	}

	public WB_ExplicitSegment toSegment() {
		return new WB_ExplicitSegment(start, end);
	}

	public WB_Plane toPlane() {
		return new WB_Plane(start().toPoint(), toVector());
	}

}
