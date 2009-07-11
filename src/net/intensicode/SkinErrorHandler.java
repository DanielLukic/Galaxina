//#condition DEBUG

package net.intensicode;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;

public final class SkinErrorHandler extends AbstractScreen
    {
    public SkinErrorHandler( final Skin aSkin )
        {
        mySkin = aSkin;
        }

    // From AbstractScreen

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        if ( mySkin.exceptions.size > 0 )
            {
            Engine.showError( "SKIN FAILURE", (Throwable) mySkin.exceptions.get( 0 ) );
            mySkin.exceptions.remove( 0 );
            }
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        }



    private final Skin mySkin;
    }
