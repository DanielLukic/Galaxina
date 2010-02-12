package net.intensicode;

import net.intensicode.path.HermiteCurveInterpolation;



/**
 * TODO: Describe this!
 */
public final class TestHermiteIntepolation extends TestBase
{
    public static final void main( final String[] aArguments )
    {
        final HermiteCurveInterpolation interpolation = new HermiteCurveInterpolation();
        prepare( interpolation );
        visualize( interpolation );
    }
}
