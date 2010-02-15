package net.intensicode.galaxina.screens;

import net.intensicode.core.ImageResource;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class TitleScreen extends MultiScreen
    {
    public TitleScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        myVisualContext = aMainContext.visualContext();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final ImageResource title = skin().image( "title_logo" );
        final int titlePosX = ( screen().width() - title.getWidth() ) / 2;
        final int titlePosY = title.getHeight() / 2;

        final FontGenerator creditsFont = skin().font( "menufont" );
        final String creditsText = resources().loadString( "credits.txt" );

        addScreen( myVisualContext.sharedBackground() );
        addScreen( new ImageScreen( title, titlePosX, titlePosY, ImageScreen.MODE_ABSOLUTE ) );
        addScreen( new TitleCreditsScreen( creditsFont, creditsText ) );
        addScreen( myVisualContext.sharedSoftkeys() );

        audio().loadMusic( "theme" ).start();
        }

    public final void onInitEverytime() throws Exception
        {
        myVisualContext.sharedSoftkeys().setSoftkeys( "MENU", "EXIT", false );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        if ( keys().checkLeftSoftAndConsume() )
            {
            stack().popScreen( this );
            myMainContext.showMainMenu();
            }
        if ( keys().checkRightSoftAndConsume() )
            {
            system().shutdownAndExit();
            }
        }


    private final MainContext myMainContext;

    private final VisualContext myVisualContext;
    }
