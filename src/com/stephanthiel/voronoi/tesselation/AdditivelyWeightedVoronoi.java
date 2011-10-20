package com.stephanthiel.voronoi.tesselation;

import java.util.ArrayList;
import java.util.Collections;
import toxi.geom.Vec2D;
import toxi.math.MathUtils;

import com.stephanthiel.voronoi.WeightSort;
import com.stephanthiel.voronoi.WeightedGenerator;

public class AdditivelyWeightedVoronoi extends HyperbolicTesselation
{
	public AdditivelyWeightedVoronoi( int width, int height )
	{
		mWidth = width;
		mHeight = height;
	}
	
	@Override
	public void generate()
	{
		if ( mEdgeVertices == null )
			mEdgeVertices = new ArrayList<Vec2D>();
		mEdgeVertices.clear();
		
		// Sort mGenerators, lowest weight first
		Collections.sort( mGenerators, new WeightSort() );
		
		// Consider bisector of i and j.
		// Regarding to Additively weighted Voronoi diagram, bisectors are
		// hyperbolic arc, that is, difference of two distances is constant. Two
		// distances are distance to i and distance to j.
		for ( int i = 0; i < mGenerators.size() - 1; i++ )
		{
			for ( int j = i + 1; j < mGenerators.size(); j++ )
			{
				// At first, rotate i and j such that two y-coordinates are
				// same. Next, move two points such that middle point of i and j
				// become origin point. That is, i and j become two foci as
				// (-a,0) and (a,0).
				// Compute hyperbolic arc (x,y) for (-a,0), (a,0) and rotate
				// back, move back.
				// Regarding to parameters, see Okabe, Boots, Sugihara, Chiu.
				// Spatial Tessellations, Wiley.
				WeightedGenerator a = mGenerators.get( i );
				WeightedGenerator b = mGenerators.get( j );
				
				float distAB = a.distanceTo( b );
				float abWeightDif = b.w - a.w;
				if ( distAB > abWeightDif )
				{
					float distABh = distAB / 2;
					Vec2D abCenter = a.add( b ).scaleSelf( 0.5f );
					float yyaw = abCenter.y - a.y;
					float phaw = yyaw / distABh;
					float thaw = (float)Math.atan( phaw / (float)Math.pow( 1 - phaw * phaw, 0.5f ) );
					if ( a.x < b.x )
						thaw = MathUtils.PI - (float)Math.atan( phaw / (float)Math.pow( 1 - phaw * phaw, 0.5f ) );
					float minyaw = 0;
					float maxyaw = 0;
					int rraw = 0;
					int xjaw = 0;// x-coordinate
					float a2raw = 0.5f / abWeightDif;
					Vec2D rotated = new Vec2D();
					while ( rraw == 0 )
					{
						float a1raw = 16 * distABh * distABh * xjaw * xjaw - 4 * abWeightDif * abWeightDif * xjaw * xjaw - 4 * distABh * distABh * abWeightDif * abWeightDif + (float)Math.pow( abWeightDif, 4 );
						if ( a1raw >= 0 )
						{
							float yraw = a2raw * (float)Math.pow( a1raw, 0.5f );
							float ymraw = -yraw;// y-coordinate
							rotated.x = abCenter.x + (float)Math.cos( -thaw ) * xjaw - (float)Math.sin( -thaw ) * ymraw;// move back and rotate back
							rotated.y = abCenter.y + (float)Math.sin( -thaw ) * xjaw + (float)Math.cos( -thaw ) * ymraw;
							if ( rotated.x > 0 && rotated.x < mWidth && rotated.y > 0 && rotated.y < mHeight )
							{// inside the screen
								float d3aw = rotated.distanceTo( a ) - a.w;
								int br2aw = 0;
								for ( int k = 0; k < mGenerators.size(); k++ )
								{
									WeightedGenerator c = mGenerators.get( k );
									if ( k != i && k != j )
									{
										float d4aw = rotated.distanceTo( c ) - c.w;
										if ( d3aw > d4aw )
										{// if k is closer, (x,y) is not
											// bisector.
											br2aw = 1;
											break;
										}// if d3aw>d4aw
									}// if kaw!=iaw...
								}// next kaw
								if ( br2aw == 0 )
								{// All k is not closer than i, so (x,y) is bisector.
									mEdgeVertices.add( rotated.copy() );
								}// if br2aw==0
								if ( ymraw < minyaw )
									minyaw = ymraw;
								if ( ymraw > maxyaw )
									maxyaw = ymraw;
							}// if x2raw>0 && ....1950
						}// if a1raw>=0 1950
						xjaw++;// next x
						int rweaw = 0;
						if ( rotated.x < 0 || rotated.x > mWidth || rotated.y < 0 || rotated.y > mHeight )
							rweaw++;
						if ( rweaw == 1 )
							rraw = 1;
						if ( xjaw < 100 )
							rraw = 0;
					}// while rraw==0
					int minyawI = -mHeight;// (int)(minyaw+0.5);
					int maxyawI = mHeight;// (int)(maxyaw+0.5);
					// y loop
					for ( int yjawI = minyawI; yjawI <= maxyawI; yjawI++ )
					{
						float b1yaw = 4 * (float)Math.pow( distABh * abWeightDif, 2 ) + 4 * (float)Math.pow( abWeightDif * yjawI, 2 ) - (float)Math.pow( abWeightDif, 4 );
						float b2yaw = 1 / ( 16 * distABh * distABh - 4 * abWeightDif * abWeightDif );
						float b3yaw = b1yaw * b2yaw;
						if ( b3yaw >= 0 )
						{
							float x0aw = (float)Math.pow( b3yaw, 0.5f );
							float x10aw = abCenter.x + (float)Math.cos( -thaw ) * x0aw - (float)Math.sin( -thaw ) * yjawI;
							float y10aw = abCenter.y + (float)Math.sin( -thaw ) * x0aw + (float)Math.cos( -thaw ) * yjawI;
							if ( x10aw > 0 && x10aw < mWidth && y10aw > 0 && y10aw < mHeight )
							{
								float d5aw = (float)Math.pow( (float)Math.pow( x10aw - a.x, 2 ) + (float)Math.pow( y10aw - a.y, 2 ), 0.5f ) - a.w;
								int br3aw = 0;
								for ( int k = 0; k < mGenerators.size(); k++ )
								{
									WeightedGenerator c = mGenerators.get( k );
									if ( k != i && k != j )
									{
										float d6aw = (float)Math.pow( (float)Math.pow( x10aw - c.x, 2 ) + (float)Math.pow( y10aw - c.y, 2 ), 0.5f ) - c.w;
										if ( d5aw > d6aw )
										{
											br3aw = 1;
											break;
										}// if d5aw>d6aw
									}// if kaw!=iaw
								}// next kaw
								if ( br3aw == 0 )
								{
									mEdgeVertices.add( new Vec2D( x10aw, y10aw ) );
								}// if br3==0
							}// if x10aw>0...
						}// if b3yaw>=0
					}// next yjawI
				}// if alaw>beaw
			}// next jmw
		}// next imw
	}
}
