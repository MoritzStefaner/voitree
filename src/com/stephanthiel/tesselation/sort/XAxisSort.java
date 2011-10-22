package com.stephanthiel.tesselation.sort;

import java.util.Comparator;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedGenerator;

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
