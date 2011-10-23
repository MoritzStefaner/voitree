
package com.stephanthiel.tesselation.voronoi.weighted;

import com.stephanthiel.tesselation.voronoi.VoronoiCell;

import toxi.geom.Vec2D;

public class WeightedVoronoiCell extends VoronoiCell
{
	public float w;

	public WeightedVoronoiCell()
	{
		super();
		w = 1.0f;
	}

	public WeightedVoronoiCell(float x, float y)
	{
		super( x, y );
		w = 1.0f;
	}

	public WeightedVoronoiCell(float x, float y, float weight)
	{
		super( x, y );
		w = weight;
	}

	public WeightedVoronoiCell(Vec2D pos, float weight)
	{
		super( pos );
		w = weight;
	}

	public WeightedVoronoiCell(WeightedVoronoiCell generator)
	{
		super( generator );
		w = generator.w;
	}

	public String toString()
	{
		return super.toString() + "\t" + w;
	}
}
