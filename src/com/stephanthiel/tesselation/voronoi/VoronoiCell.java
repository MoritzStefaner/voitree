package com.stephanthiel.tesselation.voronoi;

import java.util.LinkedHashSet;
import java.util.Set;
import toxi.geom.Vec2D;

public class VoronoiCell extends Vec2D
{
	private Vec2D mCentroid;
	private Set<Vec2D> mCellVertices;
	
	public VoronoiCell()
	{
		super();
	}

	public VoronoiCell(float x, float y)
	{
		super( x, y );
	}
	
	public VoronoiCell( Vec2D mGeneratorPosition )
	{
		super( mGeneratorPosition );
		mCentroid = new Vec2D( mGeneratorPosition );
		mCellVertices = new LinkedHashSet<Vec2D>();
	}
	
	public void addCellVertex( Vec2D vertex )
	{
		mCellVertices.add( vertex );
	}
	
	public void addCellVertex( float x, float y )
	{
		mCellVertices.add( new Vec2D( x, y ) );
	}
	
	public void clearVertices()
	{
		mCellVertices.clear();
		
	}
	
	public void updateCentroid()
	{
		mCentroid.set( 0, 0 );
		for ( Vec2D v : mCellVertices )
			mCentroid.addSelf( v );
		mCentroid.scaleSelf( 1f / (float)mCellVertices.size() );
	}
	
	public Vec2D centroid()
	{
		return mCentroid;
	}
	
	public Set<Vec2D> cellVertices()
	{
		return mCellVertices;
	}
}
