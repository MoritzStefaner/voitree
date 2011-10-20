/**
 * 
 */
package wblut.geom;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_SimpleMesh {
	private WB_Point[]	vertices;
	private int[][]		faces;

	public WB_SimpleMesh(final WB_Point[] vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public WB_SimpleMesh(final Collection<WB_Point> vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public WB_SimpleMesh(final double[][] vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public WB_SimpleMesh(final float[][] vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public WB_SimpleMesh(final float[] vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public WB_SimpleMesh(final double[] vs, final int[][] fs) {
		setVertices(vs);
		setFaces(fs);

	}

	public void setVertices(final WB_Point[] vs) {
		vertices = vs;
	}

	public void setVertices(final Collection<WB_Point> vs) {

		final int n = vs.size();
		final Iterator<WB_Point> itr = vs.iterator();
		vertices = new WB_Point[n];
		int i = 0;
		while (itr.hasNext()) {
			vertices[i] = itr.next();
			i++;
		}
	}

	public void setVertices(final WB_Point[] vs, final boolean copy) {
		if (copy) {
			final int n = vs.length;

			vertices = new WB_Point[n];
			for (int i = 0; i < n; i++) {
				vertices[i] = new WB_Point(vs[i]);

			}
		} else {
			vertices = vs;
		}
	}

	public void setVertices(final double[][] vs) {
		final int n = vs.length;
		vertices = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			vertices[i] = new WB_Point(vs[i][0], vs[i][1], vs[i][2]);

		}
	}

	public void setVertices(final double[] vs) {
		final int n = vs.length;
		vertices = new WB_Point[n / 3];
		for (int i = 0; i < n; i += 3) {
			vertices[i] = new WB_Point(vs[i], vs[i + 1], vs[i + 2]);

		}
	}

	public void setVertices(final float[][] vs) {
		final int n = vs.length;
		vertices = new WB_Point[n];
		for (int i = 0; i < n; i++) {
			vertices[i] = new WB_Point(vs[i][0], vs[i][1], vs[i][2]);

		}
	}

	public void setVertices(final float[] vs) {
		final int n = vs.length;
		vertices = new WB_Point[n / 3];
		for (int i = 0; i < n; i += 3) {
			vertices[i] = new WB_Point(vs[i], vs[i + 1], vs[i + 2]);

		}
	}

	public void setFaces(final int[][] fs) {
		faces = fs;
	}

	public int[][] getFaces() {
		return faces;
	}

	public WB_Point[] getVertices() {
		return vertices;
	}

	public int[] getFace(final int i) {
		return faces[i];
	}

	public WB_Point getVertex(final int i) {
		return vertices[i];
	}

}
