
package com.stephanthiel.voronoi;

import toxi.geom.Vec2D;

public class WeightedGenerator extends Vec2D
{
	public float w;

	public WeightedGenerator()
	{
		super();
		w = 1.0f;
	}

	public WeightedGenerator(float x, float y)
	{
		super( x, y );
		w = 1.0f;
	}

	public WeightedGenerator(float x, float y, float weight)
	{
		super( x, y );
		w = weight;
	}

	public WeightedGenerator(Vec2D pos, float weight)
	{
		super( pos );
		w = weight;
	}

	public WeightedGenerator(WeightedGenerator generator)
	{
		super( generator );
		w = generator.w;
	}

	public String toString()
	{
		return super.toString() + "\t" + w;
	}
}
