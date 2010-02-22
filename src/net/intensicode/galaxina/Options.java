package net.intensicode.galaxina;

import net.intensicode.core.AudioManager;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.io.StorageByID;
import net.intensicode.screens.*;

import java.io.*;

public final class Options extends StorageByID
    {
    public static final int SHOW_ENGINE_STATS = 10;

    public static final int BUFFERED_FONTGEN = 11;

    //#if CONSOLE
    public static final int SHOW_CONSOLE = 12;
    //#endif

    public static final int SHOW_STARS = 20;

    public static final int PLAY_MUSIC = 30;

    public static final int PLAY_SOUND = 31;


    public Options( final VisualConfiguration aConfiguration, final AudioManager aAudioManager )
        {
        super( RECORD_STORE_NAME );
        myConfiguration = aConfiguration;
        myAudioManager = aAudioManager;
        }

    public final boolean getOption( final int aOptionId )
        {
        switch ( aOptionId )
            {
            case Options.SHOW_ENGINE_STATS:
                return EngineStats.show;
            case Options.BUFFERED_FONTGEN:
                return BitmapFontGenerator.buffered;
            //#if CONSOLE
            case SHOW_CONSOLE:
                return ConsoleOverlay.show;
            //#endif
            case Options.SHOW_STARS:
                return myConfiguration.showStars;
            case PLAY_MUSIC:
                return myAudioManager.isMusicEnabled();
            case PLAY_SOUND:
                return myAudioManager.isSoundEnabled();
            }
        throw new IllegalArgumentException();
        }

    public final void toggleOption( final int aOptionId )
        {
        switch ( aOptionId )
            {
            case Options.SHOW_ENGINE_STATS:
                EngineStats.show = !EngineStats.show;
                return;
            case Options.BUFFERED_FONTGEN:
                BitmapFontGenerator.buffered = !BitmapFontGenerator.buffered;
                return;
            //#if CONSOLE
            case SHOW_CONSOLE:
                ConsoleOverlay.show = !ConsoleOverlay.show;
                return;
            //#endif
            case Options.SHOW_STARS:
                myConfiguration.showStars = !myConfiguration.showStars;
                return;
            case PLAY_MUSIC:
                update_music_and_sound( !myAudioManager.isMusicEnabled(), myAudioManager.isSoundEnabled() );
                return;
            case PLAY_SOUND:
                update_music_and_sound( myAudioManager.isMusicEnabled(), !myAudioManager.isSoundEnabled() );
                return;
            }
        throw new IllegalArgumentException();
        }

    // From StorageByID

    protected final void loadEntry( final int aId, final DataInputStream aInput ) throws IOException
        {
        final boolean value = aInput.readBoolean();
        switch ( aId )
            {
            case SHOW_ENGINE_STATS:
                EngineStats.show = value;
                break;
            case BUFFERED_FONTGEN:
                BitmapFontGenerator.buffered = value;
                break;
            case SHOW_STARS:
                myConfiguration.showStars = value;
                break;
            }
        }

    // From StorageIO

    public final void saveTo( final DataOutputStream aOutput ) throws IOException
        {
        writeBoolean( aOutput, SHOW_ENGINE_STATS, EngineStats.show );
        writeBoolean( aOutput, BUFFERED_FONTGEN, BitmapFontGenerator.buffered );
        writeBoolean( aOutput, SHOW_STARS, myConfiguration.showStars );
        }

    // Implementation

    private void update_music_and_sound( final boolean aEnableMusic, final boolean aEnableSound )
        {
        if ( aEnableMusic && aEnableSound )
            {
            myAudioManager.enableMusicAndSound();
            }
        else if ( aEnableMusic )
            {
            myAudioManager.enableMusicOnly();
            }
        else if ( aEnableSound )
                {
                myAudioManager.enableSoundOnly();
                }
            else
                {
                myAudioManager.disable();
                }
        }


    private final AudioManager myAudioManager;

    private final VisualConfiguration myConfiguration;

    private static final String RECORD_STORE_NAME = "options";
    }
