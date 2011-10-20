
package com.stephanthiel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PGraphicsJava2D;
import toxi.geom.Vec2D;

public class AW_test extends PApplet
{
	int NUM = 10;// number of generators
	int kaw, iaw, jaw;
	float alaw, beaw, al2aw, xcaw, ycaw, yyaw, phaw, thaw, minyaw, maxyaw;
	int rraw, xjaw;
	float a1raw, a2raw, yraw;
	float ymraw, x2raw, y2raw, d3aw, d4aw;
	int x2rawI, y2rawI, rweaw;
	int yjawI, minyawI, maxyawI, x10awI, y10awI;
	float b1yaw, b2yaw, b3yaw, x0aw, x10aw, y10aw, d5aw, d6aw;
	int br2aw, br3aw;

	float x1aw[] = new float[NUM];
	float y1aw[] = new float[NUM];
	float w1aw[] = new float[NUM];
	int xaw[] = new int[NUM];
	int yaw[] = new int[NUM];
	int waw[] = new int[NUM];
	float saw[] = new float[NUM];
	String sssaw[] = new String[NUM];

	int col1, col2, col3;
	private Graphics2D graphics;
	
	ArrayList<AWGenerator> generators;

	public void setup()
	{
		size( 640, 480);
		col1 = color( 0 );
		col2 = color( 255, 255, 0 );
		col3 = color( 255 );
		
		graphics = ((PGraphicsJava2D)g).g2;
		background( 0 );
		generators = new ArrayList<AWGenerator>();
		// random generators
		for ( kaw = 1; kaw <= NUM; kaw++ )
		{
			AWGenerator g = new AWGenerator( new Vec2D( random( 15, width - 15), random( 15, height - 15 ) ), random(1, 100) );
			generators.add( g );
			
			x1aw[kaw - 1] = g.x; // x-coordinate
															// xç¿ïW
			y1aw[kaw - 1] = g.y;// y-coordinate
															// yç¿ïW
			w1aw[kaw - 1] = g.w;// weights èdÇ›
			xaw[kaw - 1] = (int) ( x1aw[kaw - 1] + 0.5 );
			yaw[kaw - 1] = (int) ( y1aw[kaw - 1] + 0.5 );
			waw[kaw - 1] = (int) ( w1aw[kaw - 1] + 0.5 );
			fill(255, 0, 0);
			ellipse(xaw[kaw-1], yaw[kaw-1], 4, 4);
		}
		noLoop();
	}

	// sort such that he1[0]<he1[1]<...<he1[NN-1]
	// sortiert nach x
	void heapaw( float he1aw[], float he2aw[], float he3aw[], int NNaw )
	{
		int kkaw, kksaw, iiaw, jjaw, mmaw;
		float b1aw, b2aw, b3aw, c1aw, c2aw, c3aw;
		kksaw = (int) ( NNaw / 2 );
		for ( kkaw = kksaw; kkaw >= 1; kkaw-- )
		{
			iiaw = kkaw;
			b1aw = he1aw[iiaw - 1];
			b2aw = he2aw[iiaw - 1];
			b3aw = he3aw[iiaw - 1];
			while ( 2 * iiaw <= NNaw )
			{
				jjaw = 2 * iiaw;
				if ( jjaw + 1 <= NNaw )
				{
					if ( he1aw[jjaw - 1] < he1aw[jjaw] )
					{
						jjaw++;
					}
				}
				if ( he1aw[jjaw - 1] <= b1aw )
				{
					break;
				}
				he1aw[iiaw - 1] = he1aw[jjaw - 1];
				he2aw[iiaw - 1] = he2aw[jjaw - 1];
				he3aw[iiaw - 1] = he3aw[jjaw - 1];
				iiaw = jjaw;
			}// wend
			he1aw[iiaw - 1] = b1aw;
			he2aw[iiaw - 1] = b2aw;
			he3aw[iiaw - 1] = b3aw;
		}// next kk
		for ( mmaw = NNaw - 1; mmaw >= 1; mmaw-- )
		{
			c1aw = he1aw[mmaw];
			c2aw = he2aw[mmaw];
			c3aw = he3aw[mmaw];
			he1aw[mmaw] = he1aw[0];
			he2aw[mmaw] = he2aw[0];
			he3aw[mmaw] = he3aw[0];
			iiaw = 1;
			while ( 2 * iiaw <= mmaw )
			{
				kkaw = 2 * iiaw;
				if ( kkaw + 1 <= mmaw )
				{
					if ( he1aw[kkaw - 1] <= he1aw[kkaw] )
					{
						kkaw++;
					}
				}
				if ( he1aw[kkaw - 1] <= c1aw )
				{
					break;
				}
				he1aw[iiaw - 1] = he1aw[kkaw - 1];
				he2aw[iiaw - 1] = he2aw[kkaw - 1];
				he3aw[iiaw - 1] = he3aw[kkaw - 1];
				iiaw = kkaw;
			}// wend
			he1aw[iiaw - 1] = c1aw;
			he2aw[iiaw - 1] = c2aw;
			he3aw[iiaw - 1] = c3aw;
		}// next mm
	}

	// Main subroutine
	public void draw()
	{
		fill(255);
		for (AWGenerator g : generators)
			ellipse( g.x, g.y, 2, 2 );
		
		// Sort generators such that w1[0]<w1[1]<...<w1[N-1]
		heapaw( w1aw, x1aw, y1aw, NUM );
		Collections.sort( generators, new WeightSort() );
		
		for ( int i = 0; i < w1aw.length; i++ )
			println( x1aw[i] + "\t\t" + y1aw[i] + "\t\t" + w1aw[i] );
		
		for (AWGenerator g : generators)
			println( g );
		
		stroke( col3 );

		// Consider bisector of i and j.
		// Regarding to Additively weighted Voronoi diagram, bisectors are
		// hyperbolic arc, that is, difference of two distances is constant. Two
		// distances are distance to i and distance to j.
		for ( iaw = 1; iaw <= NUM - 1; iaw++ )
		{
			for ( jaw = iaw + 1; jaw <= NUM; jaw++ )
			{
				// At first, rotate i and j such that two y-coordinates are
				// same. Next, move two points such that middle point of i and j
				// become origin point. That is, i and j become two foci as
				// (-a,0) and (a,0).
				// Compute hyperbolic arc (x,y) for (-a,0), (a,0) and rotate
				// back, move back.
				// Regarding to parameters, see Okabe, Boots, Sugihara, Chiu.
				// Spatial Tessellations, Wiley.
				alaw = pow( pow( x1aw[iaw - 1] - x1aw[jaw - 1], 2 ) + pow( y1aw[iaw - 1] - y1aw[jaw - 1], 2 ), 0.5f );
				beaw = w1aw[jaw - 1] - w1aw[iaw - 1];
				if ( alaw > beaw )
				{
					al2aw = alaw / 2;
					xcaw = ( x1aw[iaw - 1] + x1aw[jaw - 1] ) / 2;
					ycaw = ( y1aw[iaw - 1] + y1aw[jaw - 1] ) / 2;
					yyaw = ycaw - y1aw[iaw - 1];
					phaw = yyaw / al2aw;
					thaw = atan( phaw / pow( 1 - phaw * phaw, 0.5f ) );
					if ( x1aw[iaw - 1] < x1aw[jaw - 1] )
					{
						thaw = PI - atan( phaw / pow( 1 - phaw * phaw, 0.5f ) );
					}
					minyaw = 0;
					maxyaw = 0;
					rraw = 0;
					xjaw = 0;// x-coordinate xç¿ïW
					a2raw = 0.5f / beaw;
					while ( rraw == 0 )
					{
						a1raw = 16 * al2aw * al2aw * xjaw * xjaw - 4 * beaw * beaw * xjaw * xjaw - 4 * al2aw * al2aw * beaw * beaw + pow( beaw, 4 );
						if ( a1raw >= 0 )
						{
							yraw = a2raw * pow( a1raw, 0.5f );
							ymraw = -yraw;// y-coordinate yç¿ïW
							x2raw = xcaw + cos( -thaw ) * xjaw - sin( -thaw ) * ymraw;// move
																							// back
																							// and
																							// rotate
																							// back
							y2raw = ycaw + sin( -thaw ) * xjaw + cos( -thaw ) * ymraw;
							if ( x2raw > 0 && x2raw < width && y2raw > 0 && y2raw < height )
							{// inside the screen
								d3aw = pow( pow( x2raw - x1aw[iaw - 1], 2 ) + pow( y2raw - y1aw[iaw - 1], 2 ), 0.5f ) - w1aw[iaw - 1];// distance
																																			// from
																																			// (x,y)
																																			// to
																																			// i
								br2aw = 0;
								for ( kaw = 1; kaw <= NUM; kaw++ )
								{
									if ( kaw != iaw && kaw != jaw )
									{
										d4aw = pow( pow( x2raw - x1aw[kaw - 1], 2 ) + pow( y2raw - y1aw[kaw - 1], 2 ), 0.5f ) - w1aw[kaw - 1];// distance
																																					// from
																																					// (x,y)
																																					// to
																																					// k
										if ( d3aw > d4aw )
										{// if k is closer, (x,y) is not
											// bisector.
											br2aw = 1;
											break;
										}// if d3aw>d4aw
									}// if kaw!=iaw...
								}// next kaw
								if ( br2aw == 0 )
								{// All k is not closer than i, so (x,y) is
									// bisector.
									x2rawI = (int) ( x2raw + 0.5 );
									y2rawI = (int) ( y2raw + 0.5 );
									line( x2rawI, y2rawI, x2rawI, y2rawI );
								}// if br2aw==0
								if ( ymraw < minyaw )
								{
									minyaw = ymraw;
								}
								if ( ymraw > maxyaw )
								{
									maxyaw = ymraw;
								}
							}// if x2raw>0 && ....1950
						}// if a1raw>=0 1950
						xjaw++;// next x
						rweaw = 0;
						if ( x2raw < 0 || x2raw > width || y2raw < 0 || y2raw > height )
						{
							rweaw++;
						}
						if ( rweaw == 1 )
						{
							rraw = 1;
						}
						if ( xjaw < 100 )
						{
							rraw = 0;
						}
					}// while rraw==0
					minyawI = -height;// (int)(minyaw+0.5);
					maxyawI = height;// (int)(maxyaw+0.5);
					// y loop
					for ( yjawI = minyawI; yjawI <= maxyawI; yjawI++ )
					{
						b1yaw = 4 * pow( al2aw * beaw, 2 ) + 4 * pow( beaw * yjawI, 2 ) - pow( beaw, 4 );
						b2yaw = 1 / ( 16 * al2aw * al2aw - 4 * beaw * beaw );
						b3yaw = b1yaw * b2yaw;
						if ( b3yaw >= 0 )
						{
							x0aw = pow( b3yaw, 0.5f );
							x10aw = xcaw + cos( -thaw ) * x0aw - sin( -thaw ) * yjawI;
							y10aw = ycaw + sin( -thaw ) * x0aw + cos( -thaw ) * yjawI;
							if ( x10aw > 0 && x10aw < width && y10aw > 0 && y10aw < height )
							{
								d5aw = pow( pow( x10aw - x1aw[iaw - 1], 2 ) + pow( y10aw - y1aw[iaw - 1], 2 ), 0.5f ) - w1aw[iaw - 1];
								br3aw = 0;
								for ( kaw = 1; kaw <= NUM; kaw++ )
								{
									if ( kaw != iaw && kaw != jaw )
									{
										d6aw = pow( pow( x10aw - x1aw[kaw - 1], 2 ) + pow( y10aw - y1aw[kaw - 1], 2 ), 0.5f ) - w1aw[kaw - 1];
										if ( d5aw > d6aw )
										{
											br3aw = 1;
											break;
										}// if d5aw>d6aw
									}// if kaw!=iaw
								}// next kaw
								if ( br3aw == 0 )
								{
									x10awI = (int) ( x10aw + 0.5 );
									y10awI = (int) ( y10aw + 0.5 );
									line( x10awI, y10awI, x10awI, y10awI );
								}// if br3==0
							}// if x10aw>0...
						}// if b3yaw>=0
					}// next yjawI
				}// if alaw>beaw
			}// next jmw
		}// next imw
	}

	public static void main( String[] args )
	{
		PApplet.main( new String[] { AW_test.class.getName() } );
	}

}
