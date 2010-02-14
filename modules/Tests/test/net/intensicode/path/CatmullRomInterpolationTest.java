package net.intensicode.path;

import junit.framework.TestCase;
import net.intensicode.util.Position;
import net.intensicode.util.FixedMath;


public final class CatmullRomInterpolationTest extends TestCase
    {
    public final void test_isClosed()
        {
        assertTrue( interpolation().isClosed() );
        }

    public final void test_getInputPosition()
        {
        final CatmullRomInterpolation interpolation = interpolation();

        assertEquals( 5, interpolation.getNumberOfInputPositions() );
        assertEquals( pos( -50, 0 ), input( interpolation, -1 ) );
        assertEquals( pos( 0, -50 ), input( interpolation, 0 ) );
        assertEquals( pos( 50, 0 ), input( interpolation, 1 ) );
        assertEquals( pos( 0, 50 ), input( interpolation, 2 ) );
        assertEquals( pos( -50, 0 ), input( interpolation, 3 ) );
        assertEquals( pos( 0, -50 ), input( interpolation, 4 ) );
        assertEquals( pos( 50, 0 ), input( interpolation, 5 ) );
        assertEquals( pos( 0, 50 ), input( interpolation, 6 ) );
        }

    public final void test_getPosition()
        {
        final CatmullRomInterpolation interpolation = interpolation();

        assertEquals( pos( 0, -50 ), res( interpolation, 0, 0 ) );
        assertEquals( pos( 31, -31 ), res( interpolation, 0, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 50, 0 ), res( interpolation, 0, FixedMath.FIXED_1 ) );

        assertEquals( pos( 50, 0 ), res( interpolation, 1, 0 ) );
        assertEquals( pos( 31, 31 ), res( interpolation, 1, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 0, 50 ), res( interpolation, 1, FixedMath.FIXED_1 ) );

        assertEquals( pos( 0, 50 ), res( interpolation, 2, 0 ) );
        assertEquals( pos( -31, 31 ), res( interpolation, 2, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( -50, 0 ), res( interpolation, 2, FixedMath.FIXED_1 ) );

        assertEquals( pos( -50, 0 ), res( interpolation, 3, 0 ) );
        assertEquals( pos( -31, -31 ), res( interpolation, 3, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 0, -50 ), res( interpolation, 3, FixedMath.FIXED_1 ) );

        assertEquals( pos( 0, -50 ), res( interpolation, 4, 0 ) );
        assertEquals( pos( 31, -31 ), res( interpolation, 4, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 50, 0 ), res( interpolation, 4, FixedMath.FIXED_1 ) );
        }

    public final void test_getDirection()
        {
        final CatmullRomInterpolation interpolation = interpolation();

        assertEquals( pos( 13, 0 ), dir( interpolation, 0, 0 ) );
        assertEquals( pos( 10, 10 ), dir( interpolation, 0, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 0, 13 ), dir( interpolation, 0, FixedMath.FIXED_1 ) );

        assertEquals( pos( 0, 13 ), dir( interpolation, 1, 0 ) );
        assertEquals( pos( -10, 10 ), dir( interpolation, 1, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( -12, 0 ), dir( interpolation, 1, FixedMath.FIXED_1 ) );

        assertEquals( pos( -12, 0 ), dir( interpolation, 2, 0 ) );
        assertEquals( pos( -10, -10 ), dir( interpolation, 2, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 0, -12 ), dir( interpolation, 2, FixedMath.FIXED_1 ) );

        assertEquals( pos( 0, -12 ), dir( interpolation, 3, 0 ) );
        assertEquals( pos( 10, -10 ), dir( interpolation, 3, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 13, 0 ), dir( interpolation, 3, FixedMath.FIXED_1 ) );

        assertEquals( pos( 13, 0 ), dir( interpolation, 4, 0 ) );
        assertEquals( pos( 10, 10 ), dir( interpolation, 4, FixedMath.FIXED_0_5 ) );
        assertEquals( pos( 0, 13 ), dir( interpolation, 4, FixedMath.FIXED_1 ) );
        }

    private static final CatmullRomInterpolation interpolation()
        {
        final CatmullRomInterpolation interpolation = new CatmullRomInterpolation();
        interpolation.add( fixed( 0, -50 ) );
        interpolation.add( fixed( 50, 0 ) );
        interpolation.add( fixed( 0, 50 ) );
        interpolation.add( fixed( -50, 0 ) );
        interpolation.close();
        return interpolation;
        }

    private static final Position fixed( final int aX, final int aY )
        {
        return pos( FixedMath.toFixed( aX ), FixedMath.toFixed( aY ) );
        }

    private static final Position pos( final int aX, final int aY )
        {
        return new Position( aX, aY );
        }

    private static final Position input( final Interpolation aInterpolation, final int aIndex )
        {
        final Position position = inputFixed( aInterpolation, aIndex );
        FixedMath.toIntRounded( position );
        return position;
        }

    private static final Position inputFixed( final Interpolation aInterpolation, final int aIndex )
        {
        return new Position( aInterpolation.getInputPosition( aIndex ) );
        }

    private static final Position res( final Interpolation aInterpolation, final int aIndex, final int aPosIn_0_1 )
        {
        final Position position = resFixed( aInterpolation, aIndex, aPosIn_0_1 );
        FixedMath.toIntRounded( position );
        return position;
        }

    private static final Position resFixed( final Interpolation aInterpolation, final int aIndex, final int aPosIn_0_1 )
        {
        return new Position( aInterpolation.getPosition( aIndex, aPosIn_0_1 ) );
        }

    private static final Position dir( final Interpolation aInterpolation, final int aIndex, final int aPosIn_0_1 )
        {
        final Position position = dirFixed( aInterpolation, aIndex, aPosIn_0_1 );
        FixedMath.toIntRounded( position );
        return position;
        }

    private static final Position dirFixed( final Interpolation aInterpolation, final int aIndex, final int aPosIn_0_1 )
        {
        return new Position( aInterpolation.getDirection( aIndex, aPosIn_0_1 ) );
        }
    }