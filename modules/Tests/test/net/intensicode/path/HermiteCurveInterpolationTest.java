package net.intensicode.path;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;


public final class HermiteCurveInterpolationTest extends TestCase
    {
    public final void testHermiteInterpolation()
        {
        final HermiteCurveInterpolation interpolation = new HermiteCurveInterpolation();
        interpolation.add( new Position( F0, F0 ) );
        interpolation.add( new Position( F5, F5 ) );

        check( 0, interpolation.getPosition( 0, 0 ).x );
        check( 0, interpolation.getPosition( 0, 0 ).y );
        check( 5, interpolation.getPosition( 0, F1 ).x );
        check( 5, interpolation.getPosition( 0, F1 ).y );

        interpolation.add( new Position( F10, F0 ) );

        check( 0, interpolation.getPosition( 0, 0 ).x );
        check( 0, interpolation.getPosition( 0, 0 ).y );
        check( 5, interpolation.getPosition( 0, F1 ).x );
        check( 5, interpolation.getPosition( 0, F1 ).y );
        }

    public final void testInterpolation()
        {
        final HermiteCurveInterpolation interpolation = new HermiteCurveInterpolation();
        interpolation.add( new Position( F0, F0 ) );
        interpolation.add( new Position( F5, F5 ) );
        interpolation.add( new Position( F10, F0 ) );
        }

    // Implementation

    private final void check( final int aValue, final int aFixedValue )
        {
        Assert.assertEquals( aValue, FixedMath.toInt( aFixedValue ) );
        }


    private static final int F0 = 0;

    private static final int F1 = FixedMath.FIXED_1;

    private static final int F5 = F1 * 5;

    private static final int F10 = F1 * 10;
    }
