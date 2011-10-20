/**
 * 
 */
package wblut.hemesh.creators;

import toxi.geom.mesh.Face;
import toxi.geom.mesh.TriangleMesh;
import wblut.geom.WB_Point;
import wblut.hemesh.core.HE_Mesh;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class HEC_FromToxiTriangleMesh extends HEC_Creator {
	private TriangleMesh	tmesh;

	/**
	 * Instantiates a new HEC_FromToxiTriangleMesh.
	 *
	 */
	public HEC_FromToxiTriangleMesh() {
		super();
		override = true;
	}

	/**
	 * Instantiates a new HEC_FromToxiTriangleMesh.
	 *
	 * @param tmesh TriangleMesh generated in toxiclib
	 */
	public HEC_FromToxiTriangleMesh(final TriangleMesh tmesh) {
		this();
		this.tmesh = tmesh;
	}

	public HEC_FromToxiTriangleMesh setMesh(final TriangleMesh tmesh) {
		this.tmesh = tmesh;
		return this;

	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.creators.HEC_Creator#createBase()
	 */
	@Override
	protected HE_Mesh createBase() {
		if (tmesh == null) {
			return null;
		}
		final int numFaces = tmesh.getNumFaces();
		final int[][] faces = new int[numFaces][3];
		final WB_Point[] vertices = new WB_Point[3 * numFaces];
		for (int i = 0; i < numFaces; i++) {
			final Face f = tmesh.faces.get(i);
			faces[i][0] = i * 3;
			faces[i][1] = i * 3 + 1;
			faces[i][2] = i * 3 + 2;
			vertices[i * 3] = new WB_Point(f.a.x, f.a.y, f.a.z);
			vertices[i * 3 + 1] = new WB_Point(f.b.x, f.b.y, f.b.z);
			vertices[i * 3 + 2] = new WB_Point(f.c.x, f.c.y, f.c.z);
		}
		final HEC_FromFacelist ffl = new HEC_FromFacelist().setVertices(
				vertices).setFaces(faces).setDuplicate(true);
		return ffl.createBase();
	}

}
