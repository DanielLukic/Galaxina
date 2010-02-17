package net.intensicode.galaxina;

import net.intensicode.core.AudioResource;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Assert;

public class MusicController extends ScreenBase
    {
    public final void play( final String aMusicId )
        {
        //#if DEBUG
        Assert.notNull( "valid music id", aMusicId );
        //#endif
        if ( myActiveMusicId == aMusicId ) return;
        if ( myActiveMusicResource != null ) myActiveMusicResource.stop();
        myActiveMusicResource = audio().loadMusic( aMusicId );
        myActiveMusicId = aMusicId;
        }

    public final void stop()
        {
        if ( myActiveMusicResource == null ) return;
        myActiveMusicResource.stop();
        myActiveMusicResource = null;
        myActiveMusicId = null;
        }

    // From ScreenBase

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        }


    private String myActiveMusicId;

    private AudioResource myActiveMusicResource;
    }
