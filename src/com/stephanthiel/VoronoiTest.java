
package com.stephanthiel;

import java.util.ArrayList;
import java.util.Collections;

import com.stephanthiel.tesselation.voronoi.weighted.PWVoronoi;
import com.stephanthiel.tesselation.voronoi.weighted.AWVoronoi;
import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiCell;

import processing.core.PApplet;
import toxi.geom.Line2D;
import toxi.geom.Vec2D;

public class VoronoiTest extends PApplet
{
	public static final int NUM = 10;// number of generators

	public AWVoronoi voronoi1;
	public PWVoronoi voronoi2;

	public void setup()
	{
		size( 640, 480 );
		background( 0 );
		smooth();

		// random generators
		ArrayList<WeightedVoronoiCell> generators1 = new ArrayList<WeightedVoronoiCell>();
		for ( int i = 0; i < NUM; i++ )
			generators1.add( new WeightedVoronoiCell( random( 15, width - 15 ), random( 15, height - 15 ), random( 1, 100 ) ) );

		voronoi1 = new AWVoronoi( width, height );
		voronoi1.setGenerators( generators1 );
		voronoi1.generate();

		// random generators
		ArrayList<WeightedVoronoiCell> generators2 = new ArrayList<WeightedVoronoiCell>();
		for ( int i = 0; i < NUM; i++ )
			generators2.add( new WeightedVoronoiCell( generators1.get( i ), random( 1, 10000 ) ) );
		voronoi2 = new PWVoronoi( width, height );
		voronoi2.setGenerators( generators2 );
		voronoi2.generate();

		noLoop();
	}

	// Main subroutine
	public void draw()
	{
		background( 30 );
		
		fill( 255, 0, 0 );
		noStroke();
		for ( WeightedVoronoiCell g : voronoi2.generators() )
			ellipse( g.x, g.y, 6, 6 );
		
		fill( 80 );
		noStroke();
		for ( WeightedVoronoiCell g : voronoi1.generators() )
			ellipse( g.x, g.y, 4, 4 );

		stroke( 255 );
		for ( Vec2D v : voronoi1.edgeVertices() )
			point( v.x, v.y );

		stroke( 255, 0, 0 );
		for ( Line2D l : voronoi2.edges() )
			line( l.a.x, l.a.y, l.b.x, l.b.y );
	}

	public static void main( String[] args )
	{
		PApplet.main( new String[] { VoronoiTest.class.getName() } );
	}

}
