/**
 * 
 */
package wblut.hemesh.core;

import java.util.ArrayList;
import java.util.Iterator;

import wblut.geom.WB_Distance;
import wblut.geom.WB_ExplicitSegment;
import wblut.geom.WB_Intersection;
import wblut.geom.WB_IntersectionResult;
import wblut.geom.WB_Line;
import wblut.geom.WB_Plane;
import wblut.geom.WB_Point;
import wblut.geom.WB_Ray;
import wblut.tree.WB_AABBNode;
import wblut.tree.WB_AABBTree;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class HE_Intersection {
	/**
	 * Intersect edge with plane.
	 *
	 * @param e the e
	 * @param P plane
	 * @return u -1: no intersection, 0..1: position of intersection
	 */
	public static double getIntersection(final HE_Edge e, final WB_Plane P) {
		final WB_IntersectionResult i = WB_Intersection.getIntersection(
				e.getStartVertex(), e.getEndVertex(), P);

		if (i.intersection == false) {
			return -1.0;// intersection beyond endpoints
		}

		return i.t1;// intersection on edge
	}

	public static ArrayList<WB_Point> getIntersection(final HE_Mesh mesh,
			final WB_Ray ray) {
		WB_Plane P;
		WB_IntersectionResult lpi;
		HE_Face face;
		final ArrayList<WB_Point> p = new ArrayList<WB_Point>();
		final Iterator<HE_Face> fItr = mesh.fItr();
		final double u = Double.POSITIVE_INFINITY;
		while (fItr.hasNext()) {
			face = fItr.next();
			P = face.toPlane();
			lpi = WB_Intersection.getIntersection(ray, P);
			if (lpi.intersection) {
				if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
					p.add(lpi.p1);
				}
			}
		}

		return p;
	}

	public static ArrayList<WB_Point> getIntersection(final WB_AABBTree tree,
			final WB_Ray ray) {
		WB_Plane P;
		WB_IntersectionResult lpi;

		final ArrayList<WB_Point> p = new ArrayList<WB_Point>();
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(
				ray, tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}

		for (final HE_Face face : candidates) {
			P = face.toPlane();
			lpi = WB_Intersection.getIntersection(ray, P);
			if (lpi.intersection) {
				if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
					p.add(lpi.p1);
				}
			}
		}

		return p;
	}

	public static WB_Point getClosestIntersection(final WB_AABBTree tree,
			final WB_Ray ray) {

		final ArrayList<WB_Point> candidates = getIntersection(tree, ray);
		if (candidates.size() == 0) {
			return null;
		}
		double d2min = WB_Distance.sqDistance(candidates.get(0),
				ray.getOrigin());
		double d2;
		WB_Point result = candidates.get(0);
		for (int i = 1; i < candidates.size(); i++) {
			d2 = WB_Distance.sqDistance(candidates.get(i), ray.getOrigin());
			if (d2 < d2min) {
				d2min = d2;
				result = candidates.get(i);
			}

		}
		return result;

	}

	public static WB_Point getFurthestIntersection(final WB_AABBTree tree,
			final WB_Ray ray) {

		final ArrayList<WB_Point> candidates = getIntersection(tree, ray);
		if (candidates.size() == 0) {
			return null;
		}
		double d2max = WB_Distance.sqDistance(candidates.get(0),
				ray.getOrigin());
		double d2;
		WB_Point result = candidates.get(0);
		for (int i = 1; i < candidates.size(); i++) {
			d2 = WB_Distance.sqDistance(candidates.get(i), ray.getOrigin());
			if (d2 > d2max) {
				d2max = d2;
				result = candidates.get(i);
			}

		}
		return result;

	}

	public static ArrayList<WB_Point> getIntersection(final WB_AABBTree tree,
			final WB_Line line) {
		WB_Plane P;
		WB_IntersectionResult lpi;

		final ArrayList<WB_Point> p = new ArrayList<WB_Point>();
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(
				line, tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}

		for (final HE_Face face : candidates) {
			P = face.toPlane();
			lpi = WB_Intersection.getIntersection(line, P);
			if (lpi.intersection) {
				if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
					p.add(lpi.p1);
				}
			}
		}

		return p;
	}

	public static WB_Point getClosestIntersection(final WB_AABBTree tree,
			final WB_Line line) {

		final ArrayList<WB_Point> candidates = getIntersection(tree, line);
		if (candidates.size() == 0) {
			return null;
		}
		double d2min = WB_Distance.sqDistance(candidates.get(0),
				line.getOrigin());
		double d2;
		WB_Point result = candidates.get(0);
		for (int i = 1; i < candidates.size(); i++) {
			d2 = WB_Distance.sqDistance(candidates.get(i), line.getOrigin());
			if (d2 < d2min) {
				d2min = d2;
				result = candidates.get(i);
			}

		}
		return result;

	}

	public static WB_Point getIntersection(final HE_Face face, final WB_Ray ray) {
		final WB_Plane P = face.toPlane();
		WB_Point p = null;
		final WB_IntersectionResult lpi = WB_Intersection.getIntersection(ray,
				P);
		if (lpi.intersection) {
			if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
				p = lpi.p1;
			}
		}
		return p;
	}

	public static WB_Point getClosestIntersection(final HE_Mesh mesh,
			final WB_Ray ray) {
		WB_Plane P;
		WB_IntersectionResult lpi;
		HE_Face face;
		WB_Point p = null;
		final Iterator<HE_Face> fItr = mesh.fItr();
		double u = Double.POSITIVE_INFINITY;
		while (fItr.hasNext()) {
			face = fItr.next();
			P = face.toPlane();
			lpi = WB_Intersection.getIntersection(ray, P);
			if (lpi.intersection) {
				if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
					if (lpi.t1 < u) {
						u = lpi.t1;
						p = lpi.p1;
					}
				}
			}
		}

		return p;

	}

	public static WB_Point getIntersection(final HE_Face face,
			final WB_Line line) {
		final WB_Plane P = face.toPlane();
		WB_Point p = null;
		final WB_IntersectionResult lpi = WB_Intersection.getIntersection(line,
				P);
		if (lpi.intersection) {
			if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
				p = lpi.p1;
			}
		}
		return p;
	}

	public static WB_Point getClosestIntersection(final HE_Mesh mesh,
			final WB_Line line) {
		WB_Plane P;
		WB_IntersectionResult lpi;
		HE_Face face;
		WB_Point p = null;
		final Iterator<HE_Face> fItr = mesh.fItr();
		double u = Double.POSITIVE_INFINITY;
		while (fItr.hasNext()) {
			face = fItr.next();
			P = face.toPlane();
			lpi = WB_Intersection.getIntersection(line, P);
			if (lpi.intersection) {
				if (HE_Mesh.pointIsInFace(lpi.p1, face)) {
					if (lpi.t1 < u) {
						u = lpi.t1;
						p = lpi.p1;
					}
				}
			}
		}

		return p;

	}

	public static ArrayList<WB_ExplicitSegment> getIntersection(
			final WB_AABBTree tree, final WB_Plane P) {
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(P,
				tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}
		final ArrayList<WB_ExplicitSegment> cuts = new ArrayList<WB_ExplicitSegment>();
		for (final HE_Face face : candidates) {
			cuts.addAll(WB_Intersection.getIntersection(face.toPolygon(), P));
		}

		return cuts;
	}

	public static ArrayList<HE_Face> getPotentialIntersectedFaces(
			final WB_AABBTree tree, final WB_Plane P) {
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(P,
				tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}

		return candidates;
	}

	public static ArrayList<HE_Face> getPotentialIntersectedFaces(
			final WB_AABBTree tree, final WB_Ray R) {
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(R,
				tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}

		return candidates;
	}

	public static ArrayList<HE_Face> getPotentialIntersectedFaces(
			final WB_AABBTree tree, final WB_Line L) {
		final ArrayList<HE_Face> candidates = new ArrayList<HE_Face>();
		final ArrayList<WB_AABBNode> nodes = WB_Intersection.getIntersection(L,
				tree);
		for (final WB_AABBNode n : nodes) {
			candidates.addAll(n.getFaces());
		}

		return candidates;
	}

}
