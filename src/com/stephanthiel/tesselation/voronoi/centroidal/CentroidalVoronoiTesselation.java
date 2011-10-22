package com.stephanthiel.tesselation.voronoi.centroidal;

import com.stephanthiel.tesselation.voronoi.VoronoiTesselation;

public abstract class CentroidalVoronoiTesselation extends VoronoiTesselation
{
	protected int mMaxIt;
	protected int mNumIt;
	
	/*
	 * do the CVT – all iterations in one step
	 * TODO: implement within a thread so we can update a 
	 * running main application whenever the layout is done
	 * and can be updated visually
	 */
	public void cvt()
	{
		mNumIt = 0;
		while ( mNumIt < mMaxIt )
		{
			cvtIterate();
			mNumIt++;
		}
		generate();
	}
	
	/*
	 * do one iteration at a time while updating the internal iteration tracking
	 * useful for animations/step-wise applications of the algorithm
	 */
	public void cvtOnce()
	{
		cvtIterate();
		mNumIt++;
		generate();
	}
	
	/*
	 * implement the actual CVT algorithm
	 */
	protected abstract void cvtIterate();
	
	public void reset()
	{
		mNumIt = 0;
	}

	public boolean done()
	{
		return mNumIt >= mMaxIt;
	}
}
