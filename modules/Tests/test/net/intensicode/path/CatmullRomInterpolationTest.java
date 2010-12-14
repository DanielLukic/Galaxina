package net.intensicode.path;

import junit.framework.TestCase;
import net.intensicode.util.*;


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
        assertEquals( pos( 31, -31 ), res( interpolation, 0, 0.5f ) );
        assertEquals( pos( 50, 0 ), res( interpolation, 0, 1f ) );

        assertEquals( pos( 50, 0 ), res( interpolation, 1, 0 ) );
        assertEquals( pos( 31, 31 ), res( interpolation, 1, 0.5f ) );
        assertEquals( pos( 0, 50 ), res( interpolation, 1, 1f ) );

        assertEquals( pos( 0, 50 ), res( interpolation, 2, 0 ) );
        assertEquals( pos( -31, 31 ), res( interpolation, 2, 0.5f ) );
        assertEquals( pos( -50, 0 ), res( interpolation, 2, 1f ) );

        assertEquals( pos( -50, 0 ), res( interpolation, 3, 0 ) );
        assertEquals( pos( -31, -31 ), res( interpolation, 3, 0.5f ) );
        assertEquals( pos( 0, -50 ), res( interpolation, 3, 1f ) );

        assertEquals( pos( 0, -50 ), res( interpolation, 4, 0 ) );
        assertEquals( pos( 31, -31 ), res( interpolation, 4, 0.5f ) );
        assertEquals( pos( 50, 0 ), res( interpolation, 4, 1f ) );
        }

    public final void test_getDirection()
        {
        final CatmullRomInterpolation interpolation = interpolation();

        assertEquals( pos( 13, 0 ), dir( interpolation, 0, 0 ) );
        assertEquals( pos( 10, 10 ), dir( interpolation, 0, 0.5f ) );
        assertEquals( pos( 0, 13 ), dir( interpolation, 0, 1f ) );

        assertEquals( pos( 0, 13 ), dir( interpolation, 1, 0 ) );
        assertEquals( pos( -10, 10 ), dir( interpolation, 1, 0.5f ) );
        assertEquals( pos( -12, 0 ), dir( interpolation, 1, 1f ) );

        assertEquals( pos( -12, 0 ), dir( interpolation, 2, 0 ) );
        assertEquals( pos( -10, -10 ), dir( interpolation, 2, 0.5f ) );
        assertEquals( pos( 0, -12 ), dir( interpolation, 2, 1f ) );

        assertEquals( pos( 0, -12 ), dir( interpolation, 3, 0 ) );
        assertEquals( pos( 10, -10 ), dir( interpolation, 3, 0.5f ) );
        assertEquals( pos( 13, 0 ), dir( interpolation, 3, 1f ) );

        assertEquals( pos( 13, 0 ), dir( interpolation, 4, 0 ) );
        assertEquals( pos( 10, 10 ), dir( interpolation, 4, 0.5f ) );
        assertEquals( pos( 0, 13 ), dir( interpolation, 4, 1f ) );
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

    private static final PositionF fixed( final int aX, final int aY )
        {
        return pos( ( aX ), ( aY ) );
        }

    private static final PositionF pos( final int aX, final int aY )
        {
        return new PositionF( aX, aY );
        }

    private static final PositionF input( final Interpolation aInterpolation, final int aIndex )
        {
        return new PositionF( aInterpolation.getInputPosition( aIndex ) );
        }

    private static final PositionF res( final Interpolation aInterpolation, final int aIndex, final float aPosIn_0_1 )
        {
        return new PositionF( aInterpolation.getPosition( aIndex, aPosIn_0_1 ) );
        }

    private static final PositionF dir( final Interpolation aInterpolation, final int aIndex, final float aPosIn_0_1 )
        {
        return new PositionF( aInterpolation.getDirection( aIndex, aPosIn_0_1 ) );
        }
    }