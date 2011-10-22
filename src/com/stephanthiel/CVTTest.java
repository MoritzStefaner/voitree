
package com.stephanthiel;

import com.stephanthiel.tesselation.voronoi.centroidal.SampleBasedCVT;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import wblut.geom2D.WB_IndexedBisector2D;

public class CVTTest extends PApplet
{
	public static final int DEFAULT_NUM_GENERATORS = 10;

	public SampleBasedCVT cvt;

	public void setup()
	{
		size( 1280, 720 );
		smooth();
		background( 255 );

		cvt = new SampleBasedCVT( width, height );
		cvt.setGenerators( cvt.cvtRandomSamples( DEFAULT_NUM_GENERATORS ) );

		fill( 0 );
		noStroke();
		for ( Vec2D v : cvt.generators() )
			ellipse( v.x, v.y, 5, 5 );

		cvt.reset();
	}

	public void draw()
	{
		cvt.cvtOnce();
		background( 255 );
		fill( 0 );
		noStroke();
		for ( Vec2D gen : cvt.generators() )
			ellipse( gen.x, gen.y, 3, 3 );

		noFill();
		stroke( 0 );
		for ( WB_IndexedBisector2D bi : cvt.edges() )
			line( bi.start.xf(), bi.start.yf(), bi.end.xf(), bi.end.yf() );
	}

	public void keyPressed()
	{
	}

	public static void main( String[] args )
	{
		PApplet.main( new String[] { CVTTest.class.getName() } );

	}

}
