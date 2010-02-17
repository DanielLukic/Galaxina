package net.intensicode.galaxina;

import net.intensicode.core.AudioResource;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public class MusicController extends ScreenBase
    {
    public void play( final String aMusicId )
        {
        //#if DEBUG
        Assert.notNull( "valid music id", aMusicId );
        //#endif
        if ( myActiveMusicId == aMusicId ) return;
        if ( myState == STATE_IDLE ) playNewMusic( aMusicId );
        else if ( myState == STATE_PLAYING ) fadeIntoNewMusic( aMusicId );
        }

    public void stop()
        {
        if ( myActiveMusicResource != null ) myActiveMusicResource.stop();
        myActiveMusicResource = null;
        myActiveMusicId = myScheduledMusicId = null;
        myState = STATE_IDLE;
        }

    // From ScreenBase

    public final void onControlTick() throws Exception
        {
        if ( myState == STATE_FADE_OUT )
            {
            final int fadeTicks = timing().ticksPerSecond;
            if ( myFadeTick < fadeTicks )
                {
                myFadeTick++;
                final int newVolume = MUSIC_VOLUME_IN_PERCENT - myFadeTick * MUSIC_VOLUME_IN_PERCENT / fadeTicks;
                myActiveMusicResource.setVolume( newVolume );
                }
            else
                {
                playNewMusic( myScheduledMusicId );
                myScheduledMusicId = null;
                }
            }
        }

    public final void onDrawFrame()
        {
        }

    // Implementation

    private void playNewMusic( final String aMusicId )
        {
        if ( myActiveMusicResource != null ) myActiveMusicResource.stop();
        myActiveMusicResource = audio().loadMusic( aMusicId );
        myActiveMusicResource.setVolume( MUSIC_VOLUME_IN_PERCENT );
        myActiveMusicResource.start();
        myActiveMusicId = aMusicId;
        myState = STATE_PLAYING;
        }

    private void fadeIntoNewMusic( final String aMusicId )
        {
        myScheduledMusicId = aMusicId;
        myFadeTick = 0;
        myState = STATE_FADE_OUT;
        }


    private int myFadeTick;

    private int myState = STATE_IDLE;

    private String myActiveMusicId;

    private String myScheduledMusicId;

    private AudioResource myActiveMusicResource;

    private static final int STATE_IDLE = 0;

    private static final int STATE_PLAYING = 1;

    private static final int STATE_FADE_OUT = 2;

    private static final int MUSIC_VOLUME_IN_PERCENT = 75;
    }
