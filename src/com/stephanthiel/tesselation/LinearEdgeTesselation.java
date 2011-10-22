package com.stephanthiel.tesselation;

import java.util.ArrayList;
import java.util.List;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedGenerator;
import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiTesselation;

import toxi.geom.Line2D;
import toxi.geom.Vec2D;

public abstract class LinearEdgeTesselation extends WeightedVoronoiTesselation
{
	protected List<Line2D> mEdges;
	
	public List<Line2D> edges()
	{
		if ( mGenerators == null )
			System.err.println("[ERROR] EuclideanTesselation: Can’t calcute edges -> no generators!");
		if ( mEdges == null && mGenerators != null )
			generate();
		return mEdges; 
	}
}
