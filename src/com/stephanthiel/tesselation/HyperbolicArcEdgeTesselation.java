package com.stephanthiel.tesselation;

import java.util.List;
import toxi.geom.Vec2D;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiCell;
import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiTesselation;

public abstract class HyperbolicArcEdgeTesselation extends WeightedVoronoiTesselation
{
	protected List<Vec2D> mEdgeVertices;
	
	public List<WeightedVoronoiCell> generators()
	{
		return mGenerators;
	}
	
	public List<Vec2D> edgeVertices()
	{
		if ( mGenerators == null )
			System.err.println("[ERROR] HyperbolicTesselation: Can’t calcute edges -> no generators!");
		if ( mEdgeVertices == null && mGenerators != null )
			generate();
		return mEdgeVertices; 
	}
}
