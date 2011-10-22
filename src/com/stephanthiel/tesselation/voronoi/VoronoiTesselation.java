package com.stephanthiel.tesselation.voronoi;

import java.util.ArrayList;
import java.util.List;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedGenerator;

import toxi.geom.Vec2D;

public abstract class VoronoiTesselation
{
	protected List<Vec2D> mGenerators;
	protected int mWidth;
	protected int mHeight;
	
	public void setGenerators( List<Vec2D> generators )
	{
		if (mGenerators == null)
			mGenerators = new ArrayList<Vec2D>();
		mGenerators.clear();
		for ( Vec2D g : generators )
			mGenerators.add( g.copy() );
	}
	
	public List<Vec2D> generators()
	{
		return mGenerators;
	}
	
	/*
	 * implement the generation of the voronoi diagram
	 */
	public abstract void generate();
}
