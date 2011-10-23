
package com.stephanthiel;

import java.util.ArrayList;

import com.stephanthiel.tesselation.voronoi.centroidal.SweepLineCVT;

import processing.core.PApplet;
import processing.core.PFont;
import toxi.geom.Vec2D;

public class SweepLineCVTtest extends PApplet
{
	public static final int NUM = 20; // number of generators

	public SweepLineCVT cvt;

	public void setup()
	{
		size( 1280, 720 );
		smooth();
		background( 255 );
		PFont f = createFont( "Consolas", 10 );
		textFont( f, 10 );

		cvt = new SweepLineCVT( width, height, g );
		
		// random generators
		ArrayList<Vec2D> generators = new ArrayList<Vec2D>();
		for ( int i = 0; i < NUM; i++ )
			generators.add( new Vec2D( random( 15, width - 15 ), random( 15, height - 15 ) ) );
		cvt.setCells( generators );

		fill( 0 );
		noStroke();
		for ( Vec2D v : cvt.cells() )
			ellipse( v.x, v.y, 5, 5 );

		cvt.reset();
//		noLoop();
	}

	public void draw()
	{
		background( 255 );
		// also draws…
		cvt.cvtOnce();
	}

	public void keyPressed()
	{
	}

	public static void main( String[] args )
	{
		PApplet.main( new String[] { SweepLineCVTtest.class.getName() } );

	}

}
