package net.intensicode.galaxina.screens;

import net.intensicode.ReleaseProperties;
import net.intensicode.core.ImageResource;
import net.intensicode.galaxina.MainContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class TitleScreen extends GalaxinaScreen
    {
    public TitleScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final ImageResource title = skin().image( "title_logo" );
        final int titlePosX = ( screen().width() - title.getWidth() ) / 2;
        final int titlePosY = title.getHeight() * 2;

        final FontGenerator creditsFont = skin().font( "menufont" );
        final String creditsText = resources().loadString( "credits.txt" );

        addScreen( visuals().sharedBackground() );
        addScreen( new ImageScreen( title, titlePosX, titlePosY, ImageScreen.MODE_ABSOLUTE ) );
        addScreen( new TitleCreditsScreen( creditsFont, creditsText ) );
        addScreen( visuals().sharedSoftkeys() );

        final FontGenerator miniFont = skin().font( "minifont" );
        final int miniHeight = miniFont.charHeight();
        addScreen( new AlignedTextScreen( miniFont, ReleaseProperties.DATE, 0, 0, FontGenerator.TOP_LEFT ) );
        final String version = ReleaseProperties.VERSION + ":" + ReleaseProperties.BUILD;
        addScreen( new AlignedTextScreen( miniFont, version, 0, miniHeight, FontGenerator.TOP_LEFT ) );

        context().musicController().play( "theme" );
        }

    public final void onInitEverytime() throws Exception
        {
        visuals().sharedSoftkeys().setSoftkeys( "MENU", "EXIT", false );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        if ( keys().checkLeftSoftAndConsume() || keys().checkFireAndConsume() || keys().checkStickDownAndConsume() )
            {
            stack().popScreen( this );
            context().screens().showMainMenu();
            }
        if ( keys().checkRightSoftAndConsume() )
            {
            system().shutdownAndExit();
            }
        }
    }
