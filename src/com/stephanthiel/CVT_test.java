
package com.stephanthiel;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec2D;

public class CVT_test extends PApplet
{
	public static final int NUM = 10;
	public ArrayList<Vec2D> generators;

	public void setup()
	{
		size( 1280, 720 );
		smooth();
		background( 255 );
		
		CVT.cvt_init( width, height );
		generators = CVT.cvt_random_samples( 10 );

		fill( 0 );
		noStroke();
		for ( Vec2D v : generators )
		{
			ellipse( v.x, v.y, 5, 5 );
		}
	}

	public void draw()
	{

	}

	public void keyPressed()
	{
		ArrayList<Vec2D> cache = new ArrayList<Vec2D>();
		for ( Vec2D v : generators )
			cache.add( v.copy() );
		
		int batch = 1000;
		int sample_num = 10000;
		int it_max = 40;
		int it_fixed = 1;

		fill( 255, 0, 0 );
		CVT.cvt( generators, batch, sample_num, it_max, it_fixed, g );
		
		for (int i = 0; i < cache.size(); i++)
		{
			float dist = cache.get( i ).distanceTo( generators.get( i ) );
			println( i + ":\t" + dist );
		}

	}

	public static void main( String[] args )
	{
		PApplet.main( new String[] { CVT_test.class.getName() } );

	}

}
