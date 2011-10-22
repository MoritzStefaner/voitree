package com.stephanthiel.tesselation.voronoi.weighted;

import java.util.ArrayList;
import java.util.List;


import toxi.geom.Vec2D;

public abstract class WeightedVoronoiTesselation
{
	protected List<WeightedGenerator> mGenerators;
	protected int mWidth;
	protected int mHeight;
	
	public void setGenerators( List<WeightedGenerator> generators )
	{
		if (mGenerators == null)
			mGenerators = new ArrayList<WeightedGenerator>();
		mGenerators.clear();
		for ( WeightedGenerator g : generators )
			mGenerators.add( new WeightedGenerator( g ) );
	}
	
	public List<WeightedGenerator> generators()
	{
		return mGenerators;
	}
	
	public abstract void generate();
}
