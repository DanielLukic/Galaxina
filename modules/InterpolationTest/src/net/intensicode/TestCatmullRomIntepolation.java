/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.path.CatmullRomInterpolation;



/**
 * TODO: Describe this!
 */
public final class TestCatmullRomIntepolation extends TestBase
{
    public static final void main( final String[] aArguments )
    {
        final CatmullRomInterpolation interpolation = new CatmullRomInterpolation();
        prepare( interpolation );
        visualize( interpolation );
    }
}
