/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.util;

import junit.framework.TestCase;



public final class FixedMathTest extends TestCase
{
    public final void testBasics()
    {
        final int fixed = FixedMath.toFixed( 17 );
        final int normal = FixedMath.toInt( fixed );
        assertEquals( 17, normal );
    }

    public final void testToIntRounded()
    {
        final int fixed = FixedMath.toFixed( 75 ) / 100;
        final int normal = FixedMath.toInt( fixed );
        final int rounded = FixedMath.toIntRounded( fixed );
        assertEquals( 0, normal );
        assertEquals( 1, rounded );
    }

    public final void testFraction()
    {
        final int fixed = FixedMath.toFixed( 75 ) / 100;
        final int fraction = FixedMath.fraction( fixed );
        assertEquals( 750, FixedMath.toInt( fraction * 1000 ) );
    }

    public final void testMulSmall()
    {
        final int fixed1 = FixedMath.toFixed( 75 ) / 100;
        final int fixed2 = FixedMath.toFixed( 50 ) / 100;
        final int result = FixedMath.mul( fixed1, fixed2 );
        assertEquals( 375, FixedMath.toInt( result * 1000 ) );
    }

    public final void testMulBig()
    {
        final int fixed1 = FixedMath.toFixed( 75 );
        final int fixed2 = FixedMath.toFixed( 50 );
        final int result = FixedMath.mul( fixed1, fixed2 );
        assertEquals( 75 * 50, FixedMath.toInt( result ) );
    }

    public final void testDivSmall()
    {
        final int fixed1 = FixedMath.toFixed( 75 ) / 100;
        final int fixed2 = FixedMath.toFixed( 50 ) / 100;
        final int result = FixedMath.div( fixed1, fixed2 );
        assertEquals( 1500, FixedMath.toInt( result * 1000 ) );
    }

    public final void testDivBig()
    {
        final int fixed1 = FixedMath.toFixed( 75 );
        final int fixed2 = FixedMath.toFixed( 50 );
        final int result = FixedMath.div( fixed1, fixed2 );
        assertEquals( 1500, FixedMath.toInt( result * 1000 ) );
    }

    public final void testLengthSmall()
    {
        final int fixed1 = FixedMath.toFixed( 75 ) / 100;
        final int fixed2 = FixedMath.toFixed( 50 ) / 100;
        final int result = FixedMath.length( fixed1, fixed2 );
        final int expected = ( int ) ( 1000 * Math.sqrt( 0.75 * 0.75 + 0.5 * 0.5 ) );
        assertEquals( expected, FixedMath.toInt( result * 1000 ), 50 );
    }

    public final void testLengthBig()
    {
        final int fixed1 = FixedMath.toFixed( 75 );
        final int fixed2 = FixedMath.toFixed( 50 );
        final int result = FixedMath.length( fixed1, fixed2 );
        final int expected = ( int ) ( Math.sqrt( 75 * 75 + 50 * 50 ) );
        assertEquals( expected, FixedMath.toInt( result ) );
    }
}
