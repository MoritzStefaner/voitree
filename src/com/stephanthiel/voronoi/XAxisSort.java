package com.stephanthiel.voronoi;

import java.util.Comparator;

import toxi.geom.Vec2D;

public class XAxisSort implements Comparator<WeightedGenerator>
{
	@Override
	public int compare( WeightedGenerator a, WeightedGenerator b )
	{
		if ( a.x == b.x )
			return 0;
		if ( a.x < b.x )
			return -1;
		else
			return 1;
	}
}
