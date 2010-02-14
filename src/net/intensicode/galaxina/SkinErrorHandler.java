//#condition DEBUG

package net.intensicode.galaxina;

import net.intensicode.core.SkinManager;
import net.intensicode.screens.ScreenBase;

public final class SkinErrorHandler extends ScreenBase
    {
    public SkinErrorHandler( final SkinManager aSkin )
        {
        mySkin = aSkin;
        }

    // From ScreenBase

    public final void onControlTick() throws Exception
        {
        if ( mySkin.exceptionsFromLoaderThread.size > 0 )
            {
            system().showError( "skin loader failure", (Throwable) mySkin.exceptionsFromLoaderThread.get( 0 ) );
            mySkin.exceptionsFromLoaderThread.remove( 0 );
            }
        }

    public final void onDrawFrame()
        {
        }



    private final SkinManager mySkin;
    }
