package net.intensicode.path;

import junit.framework.TestCase;
import net.intensicode.galaxina.util.MathFP;
import net.intensicode.galaxina.util.UtilitiesEx;
import net.intensicode.util.*;


public final class SmoothPathTest extends TestCase
    {
    public final void test_directionToDegrees()
        {
        check( 1 );
        check( 5 );
        check( 50 );
        check( 100 );
        check( 360 );
        }

    private void check( final int aOffset )
        {
        final PositionF position = new PositionF();

        position.x = -aOffset;
        position.y = 0;
        assertEquals( 270, UtilitiesEx.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = -aOffset;
        assertEquals( 315, UtilitiesEx.directionToDegrees( position ) );

        position.x = 0;
        position.y = -aOffset;
        assertEquals( 0, UtilitiesEx.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = -aOffset;
        assertEquals( 45, UtilitiesEx.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = 0;
        assertEquals( 90, UtilitiesEx.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = aOffset;
        assertEquals( 135, UtilitiesEx.directionToDegrees( position ) );

        position.x = 0;
        position.y = aOffset;
        assertEquals( 180, UtilitiesEx.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = aOffset;
        assertEquals( 225, UtilitiesEx.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = 0;
        assertEquals( 270, UtilitiesEx.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = -aOffset;
        assertEquals( 315, UtilitiesEx.directionToDegrees( position ) );

        position.x = 0;
        position.y = -aOffset;
        assertEquals( 0, UtilitiesEx.directionToDegrees( position ) );
        }

    public static final void test_acos()
        {
        assertEquals( 180, acos2deg( -MathFP.ONE ) );
        assertEquals( 120, acos2deg( -MathFP.HALF ) );
        assertEquals( 90, acos2deg( 0 ) );
        assertEquals( 60, acos2deg( +MathFP.HALF ) );
        assertEquals( 0, acos2deg( +MathFP.ONE ) );
        }

    private static final int acos2deg( final int aF )
        {
        return MathFP.toInt( MathFP.rad2deg( MathFP.acos( aF ) ) + MathFP.HALF );
        }
    }