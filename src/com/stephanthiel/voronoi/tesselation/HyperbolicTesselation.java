package com.stephanthiel.voronoi.tesselation;

import java.util.List;
import toxi.geom.Vec2D;
import com.stephanthiel.voronoi.WeightedGenerator;

public abstract class HyperbolicTesselation extends WeightedVoronoiTesselation
{
	protected List<Vec2D> mEdgeVertices;
	
	public List<WeightedGenerator> generators()
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
