package com.stephanthiel;

import java.util.Comparator;

import toxi.geom.Vec2D;

public class WeightSort implements Comparator<AWGenerator>
{
	@Override
	public int compare( AWGenerator a, AWGenerator b )
	{
		if ( a.w == b.w )
			return 0;
		if ( a.w < b.w )
			return -1;
		else
			return 1;
	}
}
