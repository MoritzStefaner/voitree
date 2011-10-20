/**
 * 
 */
package wblut.frame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javolution.util.FastList;
import wblut.geom.WB_ClosestPoint;
import wblut.geom.WB_Distance;
import wblut.geom.WB_ExplicitSegment;
import wblut.geom.WB_IndexedSegment;
import wblut.geom.WB_Point;
import wblut.geom.WB_XYZ;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_Frame {
	private final FastList<WB_Strut>	struts;
	private final FastList<WB_Node>		nodes;

	public WB_Frame() {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
	}

	public WB_Frame(final WB_Point[] points,
			final WB_IndexedSegment[] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_Point point : points) {
			addNode(point, 1);
		}
		for (final WB_IndexedSegment connection : connections) {
			addStrut(connection.i1(), connection.i2());
		}
	}

	public WB_Frame(final WB_Point[] points,
			final Collection<WB_IndexedSegment> connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_Point point : points) {
			addNode(point, 1);
		}
		for (final WB_IndexedSegment connection : connections) {
			addStrut(connection.i1(), connection.i2());
		}
	}

	public WB_Frame(final Collection<WB_Point> points,
			final Collection<WB_IndexedSegment> connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_Point point : points) {
			addNode(point, 1);
		}
		for (final WB_IndexedSegment connection : connections) {
			addStrut(connection.i1(), connection.i2());
		}
	}

	public WB_Frame(final WB_XYZ[] points, final int[][] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_XYZ point : points) {
			addNode(point.x, point.y, point.z, 1);
		}
		for (final int[] connection : connections) {
			addStrut(connection[0], connection[1]);
		}
	}

	public WB_Frame(final Collection<WB_XYZ> points, final int[][] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_XYZ point : points) {
			addNode(point.x, point.y, point.z, 1);
		}
		for (final int[] connection : connections) {
			addStrut(connection[0], connection[1]);
		}
	}

	public WB_Frame(final double[][] points, final int[][] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final double[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}
		for (final int[] connection : connections) {
			addStrut(connection[0], connection[1]);
		}
	}

	public WB_Frame(final float[][] points, final int[][] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final float[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}
		for (final int[] connection : connections) {
			addStrut(connection[0], connection[1]);
		}
	}

	public WB_Frame(final int[][] points, final int[][] connections) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final int[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}
		for (final int[] connection : connections) {
			addStrut(connection[0], connection[1]);
		}
	}

	public WB_Frame(final WB_Point[] points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_Point point : points) {
			addNode(point, 1);
		}
	}

	public WB_Frame(final WB_XYZ[] points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_XYZ point : points) {
			addNode(point.x, point.y, point.z, 1);
		}
	}

	public WB_Frame(final Collection<WB_XYZ> points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final WB_XYZ point : points) {
			addNode(point.x, point.y, point.z, 1);
		}
	}

	public WB_Frame(final double[][] points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final double[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}
	}

	public WB_Frame(final float[][] points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final float[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}

	}

	public WB_Frame(final int[][] points) {
		struts = new FastList<WB_Strut>();
		nodes = new FastList<WB_Node>();
		for (final int[] point : points) {
			addNode(point[0], point[1], point[2], 1);
		}

	}

	public int addNode(final double x, final double y, final double z,
			final double v) {
		final int n = nodes.size();
		nodes.add(new WB_Node(new WB_XYZ(x, y, z), n, v));
		return n;
	}

	public int addNode(final WB_XYZ pos, final double v) {
		final int n = nodes.size();
		nodes.add(new WB_Node(pos, n, v));
		return n;
	}

	public void removeNode(final WB_Node node) {
		for (final WB_Strut strut : node.getStruts()) {
			removeStrut(strut);
		}
		nodes.remove(node);
	}

	public int addNodes(final Collection<WB_XYZ> pos) {
		int n = nodes.size();
		final Iterator<WB_XYZ> pItr = pos.iterator();
		while (pItr.hasNext()) {
			nodes.add(new WB_Node(pItr.next(), n, 1));
			n++;
		}
		return n;
	}

	public int addStrut(final int i, final int j) {
		if (i == j) {
			throw new IllegalArgumentException(
					"Strut can't connect a node to itself: " + i + " " + j
							+ ".");
		}
		final int nn = nodes.size();
		if ((i < 0) || (j < 0) || (i >= nn) || (j >= nn)) {
			throw new IllegalArgumentException(
					"Strut indices outside node range.");
		}
		final int n = struts.size();
		WB_Strut strut;
		if (i <= j) {
			strut = new WB_Strut(nodes.get(i), nodes.get(j), n);
		} else {
			strut = new WB_Strut(nodes.get(j), nodes.get(i), n);
		}
		if (!nodes.get(i).addStrut(strut)) {
			System.out.println("WB_Frame : Strut " + i + "-" + j
					+ " already added.");
		} else if (!nodes.get(j).addStrut(strut)) {
			System.out.println("WB_Frame : Strut " + i + "-" + j
					+ " already added.");
		} else {

			struts.add(strut);
		}
		return n;
	}

	public void removeStrut(final WB_Strut strut) {
		nodes.get(strut.getStartIndex()).removeStrut(strut);
		nodes.get(strut.getEndIndex()).removeStrut(strut);
		struts.remove(strut);
	}

	public ArrayList<WB_Strut> getStruts() {
		final ArrayList<WB_Strut> result = new ArrayList<WB_Strut>();
		result.addAll(struts);
		return result;
	}

	public ArrayList<WB_ExplicitSegment> getSegments() {
		final ArrayList<WB_ExplicitSegment> result = new ArrayList<WB_ExplicitSegment>();
		for (final WB_Strut strut : struts) {
			result.add(strut.toSegment());
		}
		return result;
	}

	public int getNumberOfStruts() {
		return struts.size();
	}

	public ArrayList<WB_Node> getNodes() {
		final ArrayList<WB_Node> result = new ArrayList<WB_Node>();
		result.addAll(nodes);
		return result;
	}

	public ArrayList<WB_Point> getPoints() {
		final ArrayList<WB_Point> result = new ArrayList<WB_Point>();
		result.addAll(nodes);
		return result;
	}

	public int getNumberOfNodes() {
		return nodes.size();
	}

	public WB_Node getNode(final int i) {
		if ((i < 0) || (i >= nodes.size())) {
			throw new IllegalArgumentException("Index outside of node range.");
		}
		return nodes.get(i);

	}

	public WB_Strut getStrut(final int i) {
		if ((i < 0) || (i >= struts.size())) {
			throw new IllegalArgumentException("Index outside of strut range.");
		}
		return struts.get(i);

	}

	public double getDistanceToFrame(final WB_XYZ p) {
		double d = Double.POSITIVE_INFINITY;
		for (int i = 0; i < struts.size(); i++) {
			final WB_Strut strut = struts.get(i);
			final WB_ExplicitSegment S = new WB_ExplicitSegment(strut.start(),
					strut.end());
			d = Math.min(d, WB_Distance.distance(p, S));
		}
		return d;
	}

	public WB_Point getClosestPointOnFrame(final WB_XYZ p) {
		double mind = Double.POSITIVE_INFINITY;
		WB_Point q = new WB_Point(p);
		for (int i = 0; i < struts.size(); i++) {
			final WB_Strut strut = struts.get(i);
			final WB_ExplicitSegment S = new WB_ExplicitSegment(strut.start(),
					strut.end());

			final double d = WB_Distance.distance(p, S);
			if (d < mind) {
				mind = d;
				q = WB_ClosestPoint.closestPoint(S, p);
			}

		}
		return q;
	}

	public void smoothBiNodes() {
		final WB_XYZ[] newPos = new WB_XYZ[nodes.size()];
		int id = 0;
		for (final WB_Node node : nodes) {
			if (node.getOrder() == 2) {
				newPos[id] = node.getNeighbor(0)
						.addAndCopy(node.getNeighbor(1));

				newPos[id].mult(0.5);
				newPos[id].add(node);
				newPos[id].mult(0.5);
			}
			id++;
		}
		id = 0;
		for (final WB_Node node : nodes) {
			if (node.getOrder() == 2) {
				node.set(newPos[id]);
			}
			id++;
		}
	}

	public WB_Frame refine(final double threshold) {

		final FastList<WB_XYZ> npoints = new FastList<WB_XYZ>();
		for (final WB_Node node : nodes) {
			npoints.add(node);
		}
		for (final WB_Strut strut : struts) {
			if (strut.getLength() > threshold) {
				final WB_Point start = strut.start();
				final WB_Point end = strut.end();
				final WB_Point mid = WB_Point.interpolate(start, end, 0.5);
				npoints.add(mid);
			}
		}
		final int n = getNumberOfNodes();
		int id = 0;
		final WB_Frame result = new WB_Frame(npoints);
		for (final WB_Strut strut : struts) {
			if (strut.getLength() > threshold) {
				final int start = strut.getStartIndex();
				final int end = strut.getEndIndex();
				result.addStrut(start, n + id);
				result.addStrut(n + id, end);
				id++;
			} else {
				final int start = strut.getStartIndex();
				final int end = strut.getEndIndex();
				result.addStrut(start, end);
			}
		}

		return result;

	}

	void clean(final double threshold) {

	}

}
