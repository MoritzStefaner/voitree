
package com.stephanthiel;

import java.util.ArrayList;

import com.stephanthiel.tesselation.voronoi.centroidal.SweepLineCVT;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import wblut.geom2D.WB_IndexedBisector2D;

public class SweepLineCVTtest extends PApplet
{
	public static final int NUM = 10;// number of generators

	public SweepLineCVT cvt;

	public void setup()
	{
		size( 1280, 720 );
		smooth();
		background( 255 );

		cvt = new SweepLineCVT( width, height );
		
		// random generators
		ArrayList<Vec2D> generators = new ArrayList<Vec2D>();
		for ( int i = 0; i < NUM; i++ )
			generators.add( new Vec2D( random( 15, width - 15 ), random( 15, height - 15 ) ) );
		cvt.setGenerators( generators );

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
		PApplet.main( new String[] { SweepLineCVTtest.class.getName() } );

	}

}
