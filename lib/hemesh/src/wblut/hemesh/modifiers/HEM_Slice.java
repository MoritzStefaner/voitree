/*
 * Copyright (c) 2010, Frederik Vanhoutte This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * http://creativecommons.org/licenses/LGPL/2.1/ This library is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */
package wblut.hemesh.modifiers;

import java.util.Iterator;

import wblut.geom.WB_ClassifyPolygonToPlane;
import wblut.geom.WB_Plane;
import wblut.hemesh.core.HE_Face;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.core.HE_Selection;

// TODO: Auto-generated Javadoc
/**
 * Planar cut of a mesh. Faces on positive side of cut plane are removed.
 * 
 * @author Frederik Vanhoutte (W:Blut)
 * 
 */
public class HEM_Slice extends HEM_Modifier {

	/** Cut plane. */
	private WB_Plane	P;

	/** Reverse planar cut. */
	private boolean		reverse		= false;

	/** Cap holes? */
	private boolean		capHoles	= true;

	/** Keep center of cut mesh? */
	private boolean		keepCenter	= false;

	/** Store cut faces */
	public HE_Selection	cut;

	/** Store cap faces */
	public HE_Selection	cap;

	private double		offset;

	/**
	 * Set offset.
	 *
	 * @param d offset
	 * @return self
	 */
	public HEM_Slice setOffset(final double d) {
		offset = d;
		return this;
	}

	/**
	 * Instantiates a new HEM_Slice.
	 */
	public HEM_Slice() {
		super();

	}

	/**
	 * Set cut plane.
	 *
	 * @param P cut plane
	 * @return self
	 */
	public HEM_Slice setPlane(final WB_Plane P) {
		this.P = P;
		return this;
	}

	public HEM_Slice setPlane(final double ox, final double oy,
			final double oz, final double nx, final double ny, final double nz) {
		P = new WB_Plane(ox, oy, oz, nx, ny, nz);
		return this;
	}

	/**
	 * Set reverse option.
	 *
	 * @param b true, false
	 * @return self
	 */
	public HEM_Slice setReverse(final Boolean b) {
		reverse = b;
		return this;
	}

	/**
	 * Set option to cap holes.
	 *
	 * @param b true, false;
	 * @return self
	 */

	public HEM_Slice setCap(final Boolean b) {
		capHoles = b;
		return this;
	}

	/**
	 * Set option to reset mesh center.
	 *
	 * @param b true, false;
	 * @return self
	 */

	public HEM_Slice setKeepCenter(final Boolean b) {
		keepCenter = b;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.HE_Modifier#apply(wblut.hemesh.HE_Mesh)
	 */
	@Override
	public HE_Mesh apply(final HE_Mesh mesh) {
		cut = new HE_Selection(mesh);
		cap = new HE_Selection(mesh);
		// no plane defined
		if (P == null) {
			return mesh;
		}

		// empty mesh
		if (mesh.numberOfVertices() == 0) {
			return mesh;
		}

		final WB_Plane lP = P.get();
		if (reverse) {
			lP.flipNormal();
		}
		lP.set(lP.getNormal(), lP.d() + offset);
		HEM_SliceSurface ss;

		ss = new HEM_SliceSurface().setPlane(lP);
		mesh.modify(ss);

		cut = ss.cut;
		final HE_Selection newFaces = new HE_Selection(mesh);
		HE_Face face;
		final Iterator<HE_Face> fItr = mesh.fItr();
		while (fItr.hasNext()) {
			face = fItr.next();
			final WB_ClassifyPolygonToPlane cptp = WB_Plane
					.classifyPolygonToPlane(face.toPolygon(), lP);
			if ((cptp == WB_ClassifyPolygonToPlane.POLYGON_IN_FRONT_OF_PLANE)
					|| (cptp == WB_ClassifyPolygonToPlane.POLYGON_ON_PLANE)) {
				newFaces.add(face);
			} else {
				if (cut.contains(face)) {
					cut.remove(face);
				}
			}

		}

		mesh.replaceFaces(newFaces.getFacesAsArray());
		cut.cleanSelection();
		mesh.cleanUnusedElementsByFace();
		if (capHoles) {
			cap.addFaces(mesh.capHoles());
		} else {
			mesh.pairHalfedgesSilent();
			mesh.capHalfedges();
		}

		// mesh.triangulateConcaveFaces();
		if (!keepCenter) {
			mesh.resetCenter();
		}
		return mesh;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * wblut.hemesh.modifiers.HEB_Modifier#modifySelected(wblut.hemesh.HE_Mesh)
	 */
	@Override
	public HE_Mesh apply(final HE_Selection selection) {
		return apply(selection.parent);
	}

}
