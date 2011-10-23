package com.stephanthiel.tesselation.voronoi.centroidal;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.stephanthiel.tesselation.voronoi.VoronoiCell;
import com.stephanthiel.tesselation.voronoi.VoronoiTesselation;

import toxi.geom.Vec2D;
import toxi.math.MathUtils;
import wblut.geom2D.WB_IndexedBisector2D;
import wblut.geom2D.WB_Voronoi2D;
import wblut.geom2D.WB_XY;

/**
 * CVT implemenetation based on Frederik Vanhoutte’s
 * excellent intergration of the Fortune Sweep Algorithm
 * 
 * the CVT doesn’t currently stop when it arrives at a desired 
 * min delta between generator and centroid
 * instead the max num of iterations is done
 *
 */
public class SweepLineCVT extends CentroidalVoronoiTesselation
{
	private WB_Voronoi2D mVoronoi;
	private WB_XY[] mWBgenerators;
	
	private PGraphics mGraphics;
	
	/*
	 * storing edges for centroid calculation and drawing
	 */
	private ArrayList<WB_IndexedBisector2D> mEdges;
	
	public SweepLineCVT( int width, int height, PGraphics g )
	{
		mWidth = width;
		mHeight = height;
		mVoronoi = new WB_Voronoi2D();
		mGraphics = g;
	}
	
	@Override
	public void generate()
	{
		if ( mWBgenerators == null || mWBgenerators.length != mCells.size() )
			mWBgenerators = new WB_XY[mCells.size()];
		for ( int i = 0; i < mWBgenerators.length; i++ )
			mWBgenerators[i] = new WB_XY( mCells.get( i ).x, mCells.get( i ).y );
		mEdges = mVoronoi.generateVoronoi( 0, mWidth, 0, mHeight, mWBgenerators );
	}

	@Override
	protected void cvtIterate()
	{
		// get the sweep line edges
		if ( mEdges == null )
			generate();
		
		// draw edges
		mGraphics.noFill();
		mGraphics.stroke( 50 );
		mGraphics.strokeWeight( 1.0f );
		for ( WB_IndexedBisector2D bi : mEdges )
			mGraphics.line( bi.start.xf(), bi.start.yf(), bi.end.xf(), bi.end.yf() );
		
		for ( VoronoiCell c : mCells )
		{
			int i = mCells.indexOf( c );
			
			// debug drawing generators
			mGraphics.fill( 0 );
			mGraphics.noStroke();
			mGraphics.ellipse( c.x, c.y, 5, 5 );
			mGraphics.textAlign( PApplet.CENTER );
			mGraphics.text( i, c.x, c.y - 5 );
			
			// go through all edges and add the end points to each cell
			// they belong to (for centroid calculation)
			// duplicate cell vertices are automatically sorted out by the cell
			c.clearVertices();
			for ( WB_IndexedBisector2D bi : mEdges )
			{
				// we need to filter out zero length edges, since there 
				// seems to be a bug in the edge clipping of WB_Voronoi2D
				float edgeLength = PApplet.dist( bi.start.xf(), bi.start.yf(), bi.end.xf(), bi.end.yf() );
				// if the edge is not of length zero and bisects the generator, add its start and end
				// point to the cell
				if ( edgeLength > 0 && ( i == bi.i || i == bi.j ) )
				{
					c.addCellVertex( bi.start.xf(), bi.start.yf() );
					c.addCellVertex( bi.end.xf(), bi.end.yf() );
				}
			}
		}
		
		// add corner fillets for border cells
		Vec2D upperLeftCorner = new Vec2D();
		Vec2D upperRightCorner = new Vec2D( mWidth, 0 );
		Vec2D lowerLeftCorner = new Vec2D( 0, mHeight );
		Vec2D lowerRightCorner = new Vec2D( mWidth, mHeight );
		
		VoronoiCell topLeft = null;
		VoronoiCell topRight = null;
		VoronoiCell bottomLeft = null;
		VoronoiCell bottomRight = null;
		
		for ( VoronoiCell c : mCells )
		{
			if ( topLeft == null || c.distanceToSquared( upperLeftCorner ) < topLeft.distanceToSquared( upperLeftCorner ))
				topLeft = c;
			if ( topRight == null || c.distanceToSquared( upperRightCorner ) < topRight.distanceToSquared( upperRightCorner ))
				topRight = c;
			if ( bottomLeft == null || c.distanceToSquared( lowerLeftCorner ) < bottomLeft.distanceToSquared( lowerLeftCorner ))
				bottomLeft = c;
			if ( bottomRight == null || c.distanceToSquared( lowerRightCorner ) < bottomRight.distanceToSquared( lowerRightCorner ))
				bottomRight = c;
		}
		
		topLeft.addCellVertex( upperLeftCorner );
		topRight.addCellVertex( upperRightCorner );
		bottomLeft.addCellVertex( lowerLeftCorner );
		bottomRight.addCellVertex( lowerRightCorner );
		
		// more debug drawing and centroid update (finally)
		for ( VoronoiCell c : mCells )
		{
			// debug drawing
			mGraphics.stroke( 255, 0, 0 );
			mGraphics.strokeWeight( 0.2f );
			for ( Vec2D cv : c.cellVertices() )
				mGraphics.line( c.x, c.y, cv.x, cv.y );
			
			c.updateCentroid();
			
			// debug drawing the centroid
			mGraphics.fill( 255, 0, 0 );
			mGraphics.noStroke();
			mGraphics.ellipse( c.centroid().x, c.centroid().y, 3, 3 );
			
			// set the cell generator to be the centroid
			// basically this is the CVT algorithm… :)
			c.set( c.centroid() );
		}
		
		// debug drawing text for each bisector
		for ( WB_IndexedBisector2D a : mEdges )
		{
			Vec2D es = new Vec2D( a.end.xf(), a.end.yf() ).subSelf( a.start.xf(), a.start.yf() );
			float esMag = (float)es.magnitude();
			es.normalize();
			float esA = es.heading();
			es.scaleSelf( esMag / 2 );
			es.addSelf( a.start.xf(), a.start.yf() );
			
			mGraphics.fill( 0 );
			mGraphics.textAlign( PApplet.CENTER );
			mGraphics.pushMatrix();
			mGraphics.translate( es.x, es.y );
			mGraphics.rotate( esA );
			mGraphics.text( "(i:" + a.i + ") (j:" + a.j + ")", 0, -5 );
			mGraphics.popMatrix();
		}
	}
	
	public ArrayList<WB_IndexedBisector2D> edges()
	{
		if ( mEdges == null )
			generate();
		return mEdges;
	}
}
