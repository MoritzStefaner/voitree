
package com.stephanthiel.tesselation.voronoi.centroidal;

import java.util.ArrayList;
import java.util.List;

import toxi.geom.Vec2D;
import toxi.math.MathUtils;
import wblut.geom2D.WB_IndexedBisector2D;
import wblut.geom2D.WB_Voronoi2D;
import wblut.geom2D.WB_XY;

/**
 * direct Java port of the C++ CVT algorithm using random samples by John
 * Burkardt
 * 
 * http://people.sc.fsu.edu/~jburkardt/cpp_src/cvt/cvt.html
 */

public class SampleBasedCVT extends CentroidalVoronoiTesselation
{
	public static final int DEFAULT_BATCH_SIZE = 1000;
	public static final int DEFAULT_NUM_SAMPLES = 10000;
	public static final int DEFAULT_NUM_IT = 40;

	/*
	 * batch size for sampling (to determine voronoi cell centers)
	 */
	private int mBatchSize;

	/*
	 * number of samples for estimating voronoi cell centers the higher the more
	 * precise
	 */
	private int mNumSamples;
	
	/*
	 * just for finally drawing edges
	 */
	private WB_Voronoi2D mVoronoi;
	
	/*
	 * storing edges for drawing
	 */
	private ArrayList<WB_IndexedBisector2D> mEdges;

	public SampleBasedCVT(int width, int height)
	{
		mWidth = width;
		mHeight = height;
		mMaxIt = DEFAULT_NUM_IT;

		mBatchSize = DEFAULT_BATCH_SIZE;
		mNumSamples = DEFAULT_NUM_SAMPLES;

		mVoronoi = new WB_Voronoi2D();

		reset();
	}

	public ArrayList<Vec2D> cvtRandomSamples( int n_now )
	{
		ArrayList<Vec2D> samplePoints = new ArrayList<Vec2D>();
		for ( int i = 0; i < n_now; i++ )
			samplePoints.add( Vec2D.randomVector().scaleSelf( MathUtils.random( mWidth / 2 ), MathUtils.random( mHeight / 2 ) ).addSelf(
				mWidth / 2,
				mHeight / 2 ) );
		return samplePoints;
	}

	/*
	 * TODO: kd-tree!
	 */
	public void findNearest( List<Vec2D> s, List<Vec2D> r, int[] nearest )
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

	@Override
	protected void cvtIterate()
	{
		/*
		 * Take each generator as the first sample point for its region. This
		 * can slightly slow the convergence, but it simplifies the algorithm by
		 * guaranteeing that no region is completely missed by the sampling.
		 */
		ArrayList<Vec2D> r2 = new ArrayList<Vec2D>();
		for ( Vec2D v : mGenerators )
			r2.add( v.copy() );

		int[] nearest = new int[mNumSamples];

		int[] count = new int[mGenerators.size()];
		for ( int i = 0; i < mGenerators.size(); i++ )
			count[i] = 1;

		/*
		 * Generate the sampling points S.
		 */
		int have = 0;
		double energy = 0.0;
		while ( have < mNumSamples )
		{
			int get = Math.min( mNumSamples - have, mBatchSize );
			ArrayList<Vec2D> s = cvtRandomSamples( get );

			have = have + get;

			/*
			 * Find the index N of the nearest cell generator to each sample
			 * point S.
			 */
			findNearest( s, mGenerators, nearest );

			/*
			 * Add S to the centroid associated with generator N.
			 */
			for ( int j = 0; j < get; j++ )
			{
				int j2 = nearest[j];
				r2.get( j2 ).addSelf( s.get( j ) );
				energy += mGenerators.get( j2 ).sub( s.get( j ) ).magSquared();
				count[j2]++;
			}
		}

		/*
		 * Estimate the centroids.
		 */
		for ( int i = 0; i < r2.size(); i++ )
			r2.get( i ).scaleSelf( 1.0f / (float) count[i] );

		/*
		 * Determine the sum of the distances between generators and centroids.
		 */
		double it_diff = 0.0;
		for ( int i = 0; i < r2.size(); i++ )
			it_diff += r2.get( i ).distanceTo( mGenerators.get( i ) );

		/*
		 * Replace the generators by the centroids.
		 */
		for ( int i = 0; i < mGenerators.size(); i++ )
			mGenerators.get( i ).set( r2.get( i ) );

		/*
		 * Normalize the discrete energy estimate.
		 */
		energy /= mNumSamples;
	}

	@Override
	public void generate()
	{
		WB_XY[] wb_gen = new WB_XY[mGenerators.size()];
		for ( int i = 0; i < wb_gen.length; i++ )
			wb_gen[i] = new WB_XY( mGenerators.get( i ).x, mGenerators.get( i ).y );
		mEdges = mVoronoi.generateVoronoi( 0, mWidth, 0, mHeight, wb_gen );
	}
	
	public int width()
	{
		return mWidth;
	}

	public void setWidth( int width )
	{
		mWidth = width;
	}

	public int height()
	{
		return mHeight;
	}

	public void setHeight( int height )
	{
		mHeight = height;
	}

	public int batchSize()
	{
		return mBatchSize;
	}

	public void setBatchSize( int batchSize )
	{
		mBatchSize = batchSize;
	}

	public int numSamples()
	{
		return mNumSamples;
	}

	public void setNumSamples( int numSamples )
	{
		mNumSamples = numSamples;
	}

	public int itMax()
	{
		return mMaxIt;
	}

	public void setItMax( int itMax )
	{
		mMaxIt = itMax;
	}

	public ArrayList<WB_IndexedBisector2D> edges()
	{
		if ( mEdges == null )
			generate();
		return mEdges;
	}
}
