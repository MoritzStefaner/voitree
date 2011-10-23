package com.stephanthiel.tesselation.voronoi.weighted;

import java.util.ArrayList;
import java.util.List;


import toxi.geom.Vec2D;

public abstract class WeightedVoronoiTesselation
{
	protected List<WeightedVoronoiCell> mGenerators;
	protected int mWidth;
	protected int mHeight;
	
	public void setGenerators( List<WeightedVoronoiCell> generators )
	{
		if (mGenerators == null)
			mGenerators = new ArrayList<WeightedVoronoiCell>();
		mGenerators.clear();
		for ( WeightedVoronoiCell g : generators )
			mGenerators.add( new WeightedVoronoiCell( g ) );
	}
	
	public List<WeightedVoronoiCell> generators()
	{
		return mGenerators;
	}
	
	public abstract void generate();
}
