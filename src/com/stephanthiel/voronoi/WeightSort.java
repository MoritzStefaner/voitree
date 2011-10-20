package com.stephanthiel.voronoi;

import java.util.Comparator;

import toxi.geom.Vec2D;

public class WeightSort implements Comparator<WeightedGenerator>
{
	@Override
	public int compare( WeightedGenerator a, WeightedGenerator b )
	{
		if ( a.w == b.w )
			return 0;
		if ( a.w < b.w )
			return -1;
		else
			return 1;
	}
}
