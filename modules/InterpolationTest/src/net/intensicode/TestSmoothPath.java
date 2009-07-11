/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.path.SmoothPath;



/**
 * TODO: Describe this!
 */
public final class TestSmoothPath extends TestBase
{
    public static final void main( final String[] aArguments )
    {
        final SmoothPath path = new SmoothPath();
        prepare( path );
        visualize( path );
    }
}
