/**
 * 
 */
package wblut.nurbs;

import wblut.geom.WB_ClosestPoint;
import wblut.geom.WB_Line;
import wblut.geom.WB_Point;
import wblut.geom.WB_Vector;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class WB_SurfaceOfRevolution {
	public static WB_RBSplineSurface getSurfaceOfRevolution(final WB_BSpline C,
			final WB_Point p, final WB_Vector v, double theta) {

		if (theta < 0) {
			theta *= -1;
			v.mult(-1);
		}
		while (theta > 360) {
			theta -= 360;
		}

		int narcs;
		final WB_Line L = new WB_Line(p, v);

		final double[] U;
		if (theta <= 90) {
			narcs = 1;
			U = new double[6];
		} else if (theta <= 180) {
			narcs = 2;
			U = new double[8];
			U[3] = 0.5;
			U[4] = 0.5;
		} else if (theta <= 270) {
			U = new double[10];
			narcs = 3;
			U[3] = 1.0 / 3;
			U[4] = U[3];
			U[5] = 2.0 / 3;
			U[6] = U[5];
		} else {
			U = new double[12];
			narcs = 4;
			U[3] = 0.25;
			U[4] = U[3];
			U[5] = 0.5;
			U[6] = U[5];
			U[7] = 0.75;
			U[8] = U[7];
		}
		final WB_Point[][] points = new WB_Point[1 + 2 * narcs][C.n() + 1];
		final double[][] weights = new double[1 + 2 * narcs][C.n() + 1];
		final double dtheta = theta / narcs * Math.PI / 180;
		int i = 0;
		int j = 3 + 2 * (narcs - 1);
		for (i = 0; i < 3; j++, i++) {
			U[i] = 0;
			U[j] = 1;
		}
		final int n = 2 * narcs;
		final double wm = Math.cos(dtheta * 0.5);
		double angle = 0;
		final double[] cosines = new double[narcs + 1];
		final double[] sines = new double[narcs + 1];
		for (i = 1; i <= narcs; i++) {
			angle = angle + dtheta;
			cosines[i] = Math.cos(angle);
			sines[i] = Math.sin(angle);
		}
		for (j = 0; j <= C.n(); j++) {
			final WB_Point O = WB_ClosestPoint.closestPoint(C.points()[j], L);
			final WB_Vector X = C.points()[j].subToVector(O);
			final double r = X.normalize();
			final WB_Vector Y = new WB_Vector(v.cross(X));
			final WB_Point P0 = new WB_Point(C.points()[j]);
			points[0][j] = new WB_Point(P0);
			weights[0][j] = 1;
			final WB_Vector T0 = new WB_Vector(Y);
			int index = 0;
			angle = 0.0;
			for (i = 1; i <= narcs; i++) {
				final WB_Point P2 = new WB_Point(O);
				P2.add(X, r * cosines[i]);
				P2.add(Y, r * sines[i]);
				points[index + 2][j] = new WB_Point(P2);
				weights[index + 2][j] = 1;
				final WB_Vector T2 = Y.multAndCopy(cosines[i]);
				T2.add(X, -sines[i]);
				final WB_Line L1 = new WB_Line(P0, T0);
				final WB_Line L2 = new WB_Line(P2, T2);
				points[index + 1][j] = WB_ClosestPoint.closestPoint(L1, L2).p1;
				weights[index + 1][j] = wm;
				index = index + 2;
				if (i < narcs) {
					P0.set(P2);
					T0.set(T2);
				}

			}

		}
		final WB_Knot UKnot = new WB_Knot(2, U);
		return new WB_RBSplineSurface(points, UKnot, C.knot(), weights);
	}

	public static WB_RBSplineSurface getSurfaceOfRevolution(
			final WB_RBSpline C, final WB_Point p, final WB_Vector v,
			double theta) {

		if (theta < 0) {
			theta *= -1;
			v.mult(-1);
		}
		while (theta > 360) {
			theta -= 360;
		}

		int narcs;
		final WB_Line L = new WB_Line(p, v);

		final double[] U;
		if (theta <= 90) {
			narcs = 1;
			U = new double[6];
		} else if (theta <= 180) {
			narcs = 2;
			U = new double[8];
			U[3] = 0.5;
			U[4] = 0.5;
		} else if (theta <= 270) {
			U = new double[10];
			narcs = 3;
			U[3] = 1.0 / 3;
			U[4] = U[3];
			U[5] = 2.0 / 3;
			U[6] = U[5];
		} else {
			U = new double[12];
			narcs = 4;
			U[3] = 0.25;
			U[4] = U[3];
			U[5] = 0.5;
			U[6] = U[5];
			U[7] = 0.75;
			U[8] = U[7];
		}
		final WB_Point[][] points = new WB_Point[1 + 2 * narcs][C.n() + 1];
		final double[][] weights = new double[1 + 2 * narcs][C.n() + 1];
		final double dtheta = theta / narcs * Math.PI / 180;
		int i = 0;
		int j = 3 + 2 * (narcs - 1);
		for (i = 0; i < 3; j++, i++) {
			U[i] = 0;
			U[j] = 1;
		}
		final int n = 2 * narcs;
		final double wm = Math.cos(dtheta * 0.5);
		double angle = 0;
		final double[] cosines = new double[narcs + 1];
		final double[] sines = new double[narcs + 1];
		for (i = 1; i <= narcs; i++) {
			angle = angle + dtheta;
			cosines[i] = Math.cos(angle);
			sines[i] = Math.sin(angle);
		}
		for (j = 0; j <= C.n(); j++) {
			final WB_Point O = WB_ClosestPoint.closestPoint(C.points()[j], L);
			final WB_Vector X = C.points()[j].subToVector(O);
			final double r = X.normalize();
			final WB_Vector Y = new WB_Vector(v.cross(X));
			final WB_Point P0 = new WB_Point(C.points()[j]);
			points[0][j] = new WB_Point(P0);
			weights[0][j] = C.wpoints()[j].w;
			final WB_Vector T0 = new WB_Vector(Y);
			int index = 0;
			angle = 0.0;
			for (i = 1; i <= narcs; i++) {
				final WB_Point P2 = new WB_Point(O);
				P2.add(X, r * cosines[i]);
				P2.add(Y, r * sines[i]);
				points[index + 2][j] = new WB_Point(P2);
				weights[index + 2][j] = C.wpoints()[j].w;
				final WB_Vector T2 = Y.multAndCopy(cosines[i]);
				T2.add(X, -sines[i]);
				final WB_Line L1 = new WB_Line(P0, T0);
				final WB_Line L2 = new WB_Line(P2, T2);
				points[index + 1][j] = WB_ClosestPoint.closestPoint(L1, L2).p1;
				weights[index + 1][j] = wm * C.wpoints()[j].w;
				index = index + 2;
				if (i < narcs) {
					P0.set(P2);
					T0.set(T2);
				}

			}

		}
		final WB_Knot UKnot = new WB_Knot(2, U);
		return new WB_RBSplineSurface(points, UKnot, C.knot(), weights);
	}
}
