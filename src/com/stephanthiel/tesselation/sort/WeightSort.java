package com.stephanthiel.tesselation.sort;

import java.util.Comparator;

import com.stephanthiel.tesselation.voronoi.weighted.WeightedGenerator;

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
