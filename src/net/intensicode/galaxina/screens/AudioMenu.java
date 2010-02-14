package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

import java.io.IOException;

public final class AudioMenu extends MenuBase
    {
    public AudioMenu( final FontGenerator aMenuFont ) throws IOException
        {
        super( aMenuFont );
        }

    public final AudioMenu shareSoftkeys( final SoftkeysScreen aSoftkeys )
        {
        mySoftkeys = aSoftkeys;
        return this;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        //#if MUSIC_ONLY
        //# addMenuEntry( MUSIC_ONLY, I18n._( "PLAY MUSIC" ) );
        //#elif MUSIC_OR_SOUND
        //# addMenuEntry( MUSIC_ONLY, I18n._( "PLAY MUSIC" ) );
        //# addMenuEntry( SOUND_ONLY, I18n._( "PLAY SOUNDS" ) );
        //#else
        addMenuEntry( MUSIC_AND_SOUND, I18n._( "MUSIC AND SOUND" ) );
        addMenuEntry( SOUND_ONLY, I18n._( "SOUND ONLY" ) );
        //#endif
        addMenuEntry( SILENT_MODE, I18n._( "SILENT MODE" ) );

        //#if false
        updateSelectedEntry( 2 );
        //#else
        //# updateSelectedEntry( 1 );
        //#endif

        addScreen( mySoftkeys );

        super.onInitOnce();
        }

    // From MenuBase

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        final AudioManager audio = system().audio;
        switch ( aSelectedEntry.id )
            {
            case MUSIC_AND_SOUND:
                audio.enableMusicAndSound();
                break;
            case MUSIC_ONLY:
                audio.enableMusicOnly();
                break;
            case SOUND_ONLY:
                audio.enableSoundOnly();
                break;
            case SILENT_MODE:
                audio.disable();
                break;
            }

        stack().popScreen( this );
        }

    protected final void beforeInitEverytime() throws Exception
        {
        mySoftkeys.setSoftkeys( I18n._( "SELECT" ), I18n._( "EXIT" ) );
        }


    private SoftkeysScreen mySoftkeys;

    private static final int MUSIC_AND_SOUND = 0;

    private static final int MUSIC_ONLY = 1;

    private static final int SOUND_ONLY = 2;

    private static final int SILENT_MODE = 3;
    }
