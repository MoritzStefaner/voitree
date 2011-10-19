
package com.stephanthiel;

import java.util.ArrayList;

import processing.core.PGraphics;

import toxi.geom.Vec2D;
import toxi.math.MathUtils;

public class CVT
{
	public static final int DEFAULT_INIT_NUM = 10;
	
	private static int mWidth;
	private static int mHeight;
	
	public static void cvt_init( int width, int height )
	{
		mWidth = width;
		mHeight = height;
	}
	
	public static void cvt( ArrayList<Vec2D> r, int batch, int sample_num, int it_max, int it_fixed, PGraphics g )
	{
		System.out.println( "Starting CVT: " + batch + "\t" + sample_num + "\t" + it_max + "\t" + it_fixed );
		int it_num = 0;
		while ( it_num < it_max )
		{
			System.out.print( "iteration " + it_num + ":\t" );
			cvt_iterate( r, batch, sample_num, g );
			it_num++;
		}
		System.out.println( "CVT done" );
	}

	public static void cvt_iterate( ArrayList<Vec2D> r, int batch, int sample_num, PGraphics g )
	{
		/*
		 * Take each generator as the first sample point for its region.
		 * This can slightly slow the convergence, but it simplifies the
		 * algorithm by guaranteeing that no region is completely missed
		 * by the sampling.
		 */
		
		ArrayList<Vec2D> r2 = new ArrayList<Vec2D>();
		for ( Vec2D v : r )
			r2.add( v.copy() );
		
		int[] nearest = new int[sample_num];
		
		int[] count = new int[r.size()];
		for ( int i = 0; i < r.size(); i++ )
			count[i] = 1;
		
		/*
		 * Generate the sampling points S.
		 */
		int have = 0;
		double energy = 0.0;
		while ( have < sample_num )
		{
			int get = Math.min( sample_num - have, batch );
			ArrayList<Vec2D> s = cvt_random_samples( get );
			
			have = have + get;
			
			/*
			 * Find the index N of the nearest cell generator to each sample point S.
			 */
			find_nearest( s, r, nearest );
			
			/*
			 * Add S to the centroid associated with generator N.
			 */
			for ( int j = 0; j < get; j++ )
			{	
				int j2 = nearest[j];
				r2.get( j2 ).addSelf( s.get( j ) );
				energy += r.get( j2 ).sub( s.get( j ) ).magSquared();
				count[j2]++;
			}
		}
		
		/*
		 * Estimate the centroids.
		 */
		for ( int i = 0; i < r2.size(); i++ )
			r2.get( i ).scaleSelf( 1.0f / (float)count[i] );
		
		/*
		 * Determine the sum of the distances between generators and centroids.
		 */
		double it_diff = 0.0;
		for ( int i = 0; i < r2.size(); i++ )
			it_diff += r2.get( i ).distanceTo( r.get( i ) ); 
		
		/*
		 * Replace the generators by the centroids.
		 */
		for ( int i = 0; i < r.size(); i++ )
			r.get( i ).set( r2.get( i ) );
		
		
		for (Vec2D v : r )
			g.ellipse( v.x, v.y, 3, 3 );
		
		/*
		 * Normalize the discrete energy estimate.
		 */
		energy /= sample_num;
		
		System.out.println( it_diff + "\t" + energy );
	}
	
	public static ArrayList<Vec2D> cvt_random_samples( int n_now )
	{
		ArrayList<Vec2D> samplePoints = new ArrayList<Vec2D>();
		for ( int i = 0; i < n_now; i++ )
			samplePoints.add( Vec2D.randomVector().scaleSelf( MathUtils.random( mWidth / 2 ), MathUtils.random( mHeight / 2 ) ).addSelf( mWidth / 2, mHeight / 2 ) );
		return samplePoints;
	}
		
	public static void find_nearest( ArrayList<Vec2D> s, ArrayList<Vec2D> r, int[] nearest )
	{
		float dist_sq_min = Float.MAX_VALUE;
		for ( int i = 0; i < s.size(); i++ )
		{
			nearest[i] = -1;
			for ( int j = 0; j < r.size(); j++ )
			{
				float dist_sq = r.get( j ).distanceToSquared( s.get( i ) );
				if ( j == 0 || dist_sq < dist_sq_min )
				{
					dist_sq_min = dist_sq;
					nearest[i] = j;
				}
			}
		}
	}
}
