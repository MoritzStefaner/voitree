
package com.stephanthiel.voronoi.tesselation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import toxi.geom.Line2D;
import toxi.geom.Vec2D;

import com.stephanthiel.voronoi.WeightSort;
import com.stephanthiel.voronoi.WeightedGenerator;
import com.stephanthiel.voronoi.XAxisSort;

public class AdditivelyWeightedPowerVoronoi extends EuclideanTesselation
{
	List<WeightedGenerator> kgen;

	public AdditivelyWeightedPowerVoronoi(int width, int height)
	{
		mWidth = width;
		mHeight = height;
	}

	public void setGenerators( List<WeightedGenerator> mGenerators )
	{
		super.setGenerators( mGenerators );
		kgen = new ArrayList<WeightedGenerator>();
		for ( int i = 0; i < mGenerators.size(); i++ )
			kgen.add( new WeightedGenerator() );
	}

	@Override
	public void generate()
	{
		if ( mEdges == null )
			mEdges = new ArrayList<Line2D>();
		mEdges.clear();

		// Sort generators such that w1[0]<w1[1]<...<w1[N-1]
		Collections.sort( mGenerators, new WeightSort() );

		for ( int i = 0; i < mGenerators.size() - 1; i++ )
		{
			for ( int j = i + 1; j < mGenerators.size(); j++ )
			{
				WeightedGenerator a = mGenerators.get( i );
				WeightedGenerator b = mGenerators.get( j );

				float eipw = (float) Math.pow( a.x, 2 ) + (float) Math.pow( a.y, 2 );
				float ejpw = (float) Math.pow( b.x, 2 ) + (float) Math.pow( b.y, 2 );
				float bepw = b.w - a.w;
				float cpw = 0.5f * ( ejpw - eipw - bepw );
				float apw = b.x - a.x;
				float bpw = b.y - a.y;
				float dipw = -apw / bpw;
				float yspw = cpw / bpw;
				float x0pw, y0pw, yypw, xa1pw, ya1pw;

				if ( yspw > 0 && yspw < mHeight )
				{
					x0pw = 0;
					y0pw = yspw;
				}
				else
				{
					if ( dipw > 0 )
					{
						x0pw = -yspw / dipw;
						y0pw = 0;
					}
					else
					{
						x0pw = ( mHeight - yspw ) / dipw;
						y0pw = mHeight;
					}
				}
				yypw = dipw * mWidth + yspw;
				if ( yypw > 0 && yypw < mHeight )
				{
					xa1pw = mWidth;
					ya1pw = yypw;
				}
				else
				{
					if ( dipw > 0 )
					{
						xa1pw = ( mHeight - yspw ) / dipw;
						ya1pw = mHeight;
					}
					else
					{
						xa1pw = -yspw / dipw;
						ya1pw = 0;
					}
				}

				int lpw = 0;
				WeightedGenerator kgen1 = kgen.get( lpw );
				kgen1.set( x0pw, y0pw );

				for ( int k = 0; k < mGenerators.size(); k++ )
				{
					WeightedGenerator c = mGenerators.get( k );
					if ( k != i && k != j )
					{
						float c2pw = 0.5f * ( c.magSquared() - eipw - ( c.w - a.w ) );
						Vec2D ca = c.sub( a );
						float di3pw = -ca.x / ca.y;
						float ys3pw = c2pw / ca.y;
						float y20pw = di3pw * x0pw + ys3pw;
						float y21pw = di3pw * xa1pw + ys3pw;
						float sa0pw = y0pw - y20pw;
						float sa1pw = ya1pw - y21pw;
						if ( sa0pw * sa1pw <= 0 )
						{
							lpw++;
							kgen1 = kgen.get( lpw );
							kgen1.x = ( ys3pw - yspw ) / ( dipw - di3pw );
							kgen1.y = dipw * kgen1.x + yspw;
						}
					}
				}

				lpw++;
				kgen1 = kgen.get( lpw );
				kgen1.set( xa1pw, ya1pw );

				for ( int k = 0; k < lpw; k++ )
					kgen.get( k ).w = 0;

				List<WeightedGenerator> sl = kgen.subList( 0, lpw );
				List<WeightedGenerator> append = kgen.subList( lpw, kgen.size() );
				Collections.sort( sl, new XAxisSort() );
				sl.addAll( append );
				kgen = sl;

				for ( int k = 0; k < lpw; k++ )
				{
					float xxpw = ( kgen.get( k ).x + kgen.get( k + 1 ).x ) / 2;
					yypw = dipw * xxpw + yspw;
					float dspw = (float) Math.pow( xxpw - a.x, 2 ) + (float) Math.pow( yypw - a.y, 2 ) - a.w;
					float br2pw = 0;
					for ( int l = 0; l < mGenerators.size(); l++ )
					{
						WeightedGenerator c = mGenerators.get( l );
						if ( l != i && l != j )
						{
							float uspw = (float) Math.pow( xxpw - c.x, 2 ) + (float) Math.pow( yypw - c.y, 2 ) - c.w;
							if ( uspw < dspw )
							{
								br2pw = 1;
								break;
							}
						}
					}

					if ( br2pw == 0 )
						mEdges.add( new Line2D( kgen.get( k ).copy(), kgen.get( k + 1 ).copy() ) );
					else
						br2pw = 0;
				}
			}
		}
	}
}
