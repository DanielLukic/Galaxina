package net.intensicode.path;

import junit.framework.TestCase;
import net.intensicode.util.MathFP;
import net.intensicode.util.*;
import net.intensicode.util.Utilities;


public final class SmoothPathTest extends TestCase
    {
    public final void test_directionToDegrees()
        {
        check( FixedMath.FIXED_1 );
        check( FixedMath.FIXED_5 );
        check( FixedMath.FIXED_50 );
        check( FixedMath.FIXED_100 );
        check( FixedMath.FIXED_360 );
        }

    private void check( final int aOffset )
        {
        final Position position = new Position();

        position.x = -aOffset;
        position.y = 0;
        assertEquals( 270, Utilities.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = -aOffset;
        assertEquals( 315, Utilities.directionToDegrees( position ) );

        position.x = 0;
        position.y = -aOffset;
        assertEquals( 0, Utilities.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = -aOffset;
        assertEquals( 45, Utilities.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = 0;
        assertEquals( 90, Utilities.directionToDegrees( position ) );

        position.x = aOffset;
        position.y = aOffset;
        assertEquals( 135, Utilities.directionToDegrees( position ) );

        position.x = 0;
        position.y = aOffset;
        assertEquals( 180, Utilities.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = aOffset;
        assertEquals( 225, Utilities.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = 0;
        assertEquals( 270, Utilities.directionToDegrees( position ) );

        position.x = -aOffset;
        position.y = -aOffset;
        assertEquals( 315, Utilities.directionToDegrees( position ) );

        position.x = 0;
        position.y = -aOffset;
        assertEquals( 0, Utilities.directionToDegrees( position ) );
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

    public static final void test_dir2x()
        {
        assertEquals( -360, FixedMath.toInt( -FixedMath.FIXED_360 ) );
        assertEquals( -360 * 1000, FixedMath.toInt( -FixedMath.FIXED_360 * 1000 ) );
        assertEquals( 0, FixedMath.fraction( -FixedMath.FIXED_360 ) );
        assertEquals( 0, FixedMath.fraction( -FixedMath.FIXED_360 * 1000 ) );

        assertEquals( -100, dir2x100( -FixedMath.FIXED_1, 0 ) );
        assertEquals( -100, dir2x100( -FixedMath.FIXED_50, 0 ) );
        assertEquals( -100, dir2x100( -FixedMath.FIXED_100, 0 ) );
        assertEquals( -100, dir2x100( -FixedMath.FIXED_360, 0 ) );

        assertEquals( 0, dir2x100( 0, 0 ) );
        assertEquals( 0, dir2x100( 0, FixedMath.FIXED_50 ) );
        assertEquals( -70, dir2x100( -FixedMath.FIXED_50, FixedMath.FIXED_50 ) );
        assertEquals( 70, dir2x100( +FixedMath.FIXED_50, -FixedMath.FIXED_50 ) );
        }

    private static final int dir2x100( final int aFixedX, final int aFixedY )
        {
        return MathFP.toInt( Utilities.directionToMathFP( aFixedX, aFixedY ) * 100 );
        }
    }