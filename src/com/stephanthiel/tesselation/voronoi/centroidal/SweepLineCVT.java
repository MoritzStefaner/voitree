package com.stephanthiel.tesselation.voronoi.centroidal;

import java.util.ArrayList;

import com.stephanthiel.tesselation.voronoi.VoronoiTesselation;

import toxi.geom.Vec2D;
import wblut.geom2D.WB_IndexedBisector2D;
import wblut.geom2D.WB_Voronoi2D;
import wblut.geom2D.WB_XY;

/**
 * CVT implemenetation based on Frederik Vanhoutte’s
 * excellent intergration of the Fortune Sweep Algorithm
 *
 */
public class SweepLineCVT extends CentroidalVoronoiTesselation
{
	private WB_Voronoi2D mVoronoi;
	private WB_XY[] mWBgenerators;
	
	/*
	 * storing edges for centroid calculation and drawing
	 */
	private ArrayList<WB_IndexedBisector2D> mEdges;
	
	public SweepLineCVT( int width, int height )
	{
		mWidth = width;
		mHeight = height;
		mVoronoi = new WB_Voronoi2D();
	}
	
	@Override
	public void generate()
	{
		if ( mWBgenerators == null || mWBgenerators.length != mGenerators.size() )
			mWBgenerators = new WB_XY[mGenerators.size()];
		for ( int i = 0; i < mWBgenerators.length; i++ )
			mWBgenerators[i] = new WB_XY( mGenerators.get( i ).x, mGenerators.get( i ).y );
		mEdges = mVoronoi.generateVoronoi( 0, mWidth, 0, mHeight, mWBgenerators );
	}

	@Override
	protected void cvtIterate()
	{
		if ( mEdges == null )
			generate();
		
		for ( Vec2D g : mGenerators )
		{
			int i = mGenerators.indexOf( g );
			int cellNumVertices = 0;
			Vec2D centroid = new Vec2D();
			for ( WB_IndexedBisector2D bi : mEdges )
			{
				if ( bi.i == i )
				{
					System.out.println( "adding: " + bi.end + " - " + g + " (" + i + ":" + bi.i + ")");
					centroid.add( bi.end.xf(), bi.end.yf() );
					cellNumVertices++;
				}
				
				if ( bi.j == i )
				{
					System.out.println( "adding: " + bi.start + " - " + g + " (" + i + ":" + bi.j + ")");
					centroid.add( bi.start.xf(), bi.start.yf() );
					cellNumVertices++;
				}
			}
			
			centroid.scaleSelf( 1f / (float)cellNumVertices );
			g.set( centroid );
		}
	}
	
	public ArrayList<WB_IndexedBisector2D> edges()
	{
		if ( mEdges == null )
			generate();
		return mEdges;
	}
}
