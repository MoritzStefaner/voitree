package com.stephanthiel.tesselation.voronoi;

import java.util.ArrayList;
import java.util.List;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiCell;

import toxi.geom.Vec2D;

public abstract class VoronoiTesselation
{
	protected List<VoronoiCell> mCells;
	
	protected int mWidth;
	protected int mHeight;
	
	public void setCells( List<Vec2D> generators )
	{
		if (mCells == null)
			mCells = new ArrayList<VoronoiCell>();
		mCells.clear();
		for ( Vec2D g : generators )
			if ( g instanceof VoronoiCell )
				mCells.add( (VoronoiCell)g );
			else
				mCells.add( new VoronoiCell( g ));
	}
	
	public List<VoronoiCell> cells()
	{
		return mCells;
	}
	
	/*
	 * implement the generation of the voronoi diagram
	 */
	public abstract void generate();
}
