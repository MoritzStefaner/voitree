package com.stephanthiel;

import toxi.geom.Vec2D;

public class AWGenerator extends Vec2D
{
	public float w;
	
	public AWGenerator( Vec2D pos, float weight)
	{
		super( pos );
		w = weight;
	}
	
	public String toString()
	{
		return super.toString() + "\t" + w;
	}
}
