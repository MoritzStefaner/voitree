/**
 * 
 */
package wblut.frame;

import java.util.ArrayList;

import javolution.util.FastList;
import wblut.geom.WB_Point;
import wblut.geom.WB_Vector;
import wblut.geom.WB_XYZ;
import wblut.math.WB_Epsilon;
import wblut.math.WB_Fast;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Node extends WB_Point {
	private final FastList<WB_Strut>	struts;
	protected final int					index;
	private final double				value;

	public WB_Node(final WB_XYZ pos, final int id, final double v) {
		super(pos);
		index = id;
		struts = new FastList<WB_Strut>();
		value = v;
	}

	public boolean addStrut(final WB_Strut strut) {
		if ((strut.start() != this) && (strut.end() != this)) {
			return false;
		}
		for (int i = 0; i < struts.size(); i++) {
			if ((struts.get(i).start() == strut.start())
					&& (struts.get(i).end() == strut.end())) {
				return false;
			}

		}
		struts.add(strut);

		return true;
	}

	public boolean removeStrut(final WB_Strut strut) {
		if ((strut.start() != this) && (strut.end() != this)) {
			return false;
		}

		struts.remove(strut);

		return true;
	}

	public ArrayList<WB_Strut> getStruts() {
		final ArrayList<WB_Strut> result = new ArrayList<WB_Strut>();
		result.addAll(struts);
		return result;
	}

	public ArrayList<WB_Node> getNeighbors() {
		final ArrayList<WB_Node> result = new ArrayList<WB_Node>();
		for (int i = 0; i < struts.size(); i++) {
			if (struts.get(i).start() == this) {
				result.add(struts.get(i).end());
			} else {
				result.add(struts.get(i).start());
			}
		}
		return result;
	}

	public int getIndex() {
		return index;
	}

	public double findSmallestSpan() {
		double minAngle = Double.MAX_VALUE;
		for (int i = 0; i < getOrder(); i++) {
			minAngle = Math.min(minAngle, findSmallestSpanAroundStrut(i));
		}
		return minAngle;

	}

	public double findSmallestSpanAroundStrut(final WB_Strut strut) {
		return findSmallestSpanAroundStrut(struts.indexOf(strut));

	}

	public double findSmallestSpanAroundStrut(final int i) {
		final int n = struts.size();
		if ((i < 0) || (i >= n)) {
			throw new IllegalArgumentException("Index beyond strut range.");
		}
		final ArrayList<WB_Node> nnodes = getNeighbors();
		if (n == 1) {
			return 2 * Math.PI;

		} else if (n == 2) {
			final WB_Vector u = nnodes.get(0).subToVector(this);
			final WB_Vector w = nnodes.get(1).subToVector(this);
			u.normalize();
			w.normalize();

			final double udw = u.dot(w);
			if (udw < WB_Epsilon.EPSILON - 1) {
				return Math.PI;
			} else {
				return Math.acos(udw);
			}
		} else {
			double minAngle = Double.MAX_VALUE;

			final WB_Vector u = nnodes.get(i).subToVector(this);
			u.normalize();
			for (int j = 0; j < n; j++) {
				if (i != j) {
					final WB_Vector w = nnodes.get(j).subToVector(this);
					w.normalize();
					final double a = Math.acos(u.dot(w));

					minAngle = WB_Fast.min(minAngle, a);
				}
			}

			return minAngle;
		}

	}

	public double findShortestStrut() {
		double minLength = Double.MAX_VALUE;
		for (int i = 0; i < struts.size(); i++) {
			minLength = Math.min(minLength, struts.get(i).getSqLength());
		}
		return Math.sqrt(minLength);

	}

	public int getOrder() {
		return struts.size();
	}

	public double getValue() {
		return value;
	}

	public WB_Strut getStrut(final int index) {
		if ((index < 0) || (index >= struts.size())) {
			throw new IllegalArgumentException("Index outside of strut range.");
		}
		return struts.get(index);
	}

	public void removeStrut(final int index) {
		if ((index < 0) || (index >= struts.size())) {
			throw new IllegalArgumentException("Index outside of strut range.");
		}
		struts.remove(index);
	}

	public WB_Node getNeighbor(final int index) {
		if ((index < 0) || (index >= struts.size())) {
			throw new IllegalArgumentException("Index outside of strut range.");
		}

		if (struts.get(index).start() == this) {
			return struts.get(index).end();
		}
		return struts.get(index).start();
	}

	public WB_Point toPoint() {
		return new WB_Point(x, y, z);
	}

}
