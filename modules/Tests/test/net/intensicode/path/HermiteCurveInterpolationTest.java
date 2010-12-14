package net.intensicode.path;

import junit.framework.*;
import net.intensicode.util.PositionF;


public final class HermiteCurveInterpolationTest extends TestCase
    {
    public final void testHermiteInterpolation()
        {
        final HermiteCurveInterpolation interpolation = new HermiteCurveInterpolation();
        interpolation.add( new PositionF( F0, F0 ) );
        interpolation.add( new PositionF( F5, F5 ) );

        check( 0, interpolation.getPosition( 0, 0 ).x );
        check( 0, interpolation.getPosition( 0, 0 ).y );
        check( 5, interpolation.getPosition( 0, F1 ).x );
        check( 5, interpolation.getPosition( 0, F1 ).y );

        interpolation.add( new PositionF( F10, F0 ) );

        check( 0, interpolation.getPosition( 0, 0 ).x );
        check( 0, interpolation.getPosition( 0, 0 ).y );
        check( 5, interpolation.getPosition( 0, F1 ).x );
        check( 5, interpolation.getPosition( 0, F1 ).y );
        }

    public final void testInterpolation()
        {
        final HermiteCurveInterpolation interpolation = new HermiteCurveInterpolation();
        interpolation.add( new PositionF( F0, F0 ) );
        interpolation.add( new PositionF( F5, F5 ) );
        interpolation.add( new PositionF( F10, F0 ) );
        }

    // Implementation

    private final void check( final float aValue1, final float aValue2 )
        {
        Assert.assertEquals( aValue1, aValue2 );
        }


    private static final float F0 = 0;

    private static final float F1 = 1f;

    private static final float F5 = F1 * 5;

    private static final float F10 = F1 * 10;
    }
