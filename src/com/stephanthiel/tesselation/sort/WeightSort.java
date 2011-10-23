package com.stephanthiel.tesselation.sort;

import java.util.Comparator;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedVoronoiCell;

import toxi.geom.Vec2D;

public class WeightSort implements Comparator<WeightedVoronoiCell>
{
	@Override
	public int compare( WeightedVoronoiCell a, WeightedVoronoiCell b )
	{
		if ( a.w == b.w )
			return 0;
		if ( a.w < b.w )
			return -1;
		else
			return 1;
	}
}
