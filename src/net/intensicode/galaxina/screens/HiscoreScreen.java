package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.Hiscore;
import net.intensicode.graphics.*;
import net.intensicode.util.*;

public final class HiscoreScreen extends GalaxinaScreen
    {
    public HiscoreScreen( final MainContext aMainLogic )
        {
        super( aMainLogic );
        myHiscore = aMainLogic.hiscore();
        myTitleFont = visuals().menuFont();
        myTextFont = visuals().textFont();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( visuals().sharedBackground() );
        addScreen( visuals().sharedSoftkeys() );

        myTitlePos.x = screen().width() / 2;
        myTitlePos.y = myTitleFont.charHeight();
        }

    public final void onInitEverytime() throws Exception
        {
        //#if ONLINE_HISCORE
        visuals().sharedSoftkeys().setSoftkeys( I18n._( "UPDATE" ), I18n._( "BACK" ) );
        //#else
        //# visuals().sharedSoftkeys().setSoftkeys( I18n._("BACK"), I18n._("BACK") );
        //#endif
        }

    public final void onControlTick() throws Exception
        {
        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() || keys.checkFireAndConsume() )
            {
            stack().popScreen( this );
            //#if ONLINE_HISCORE
            context().updateHiscore();
            //#else
            //# context().screens().showMainMenu();
            //#endif
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            context().screens().showMainMenu();
            }

        final int linesOnScreen = myHiscore.numberOfEntries();
        final int lineHeight = ( screen().height() - myTitleFont.charHeight() * 2 ) * 2 / 3 / linesOnScreen;

        myBlitPos.y = myTitleFont.charHeight() * 2 + lineHeight / 2;
        myBlitPos.y += lineHeight / 2;

        if ( myBlinkTicks < timing().ticksPerSecond * 2 / 3 ) myBlinkTicks++;
        else myBlinkTicks = 0;

        super.onControlTick();
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();

        myTitleFont.blitString( gc, I18n._( "HISCORE" ), myTitlePos, FontGenerator.CENTER );

        final int linesOnScreen = myHiscore.numberOfEntries();
        final int lineHeight = ( screen().height() - myTitleFont.charHeight() * 2 ) * 2 / 3 / linesOnScreen;

        int maxCharsPerLine = 5 + 2 + 2 + 2;
        for ( int idx = 0; idx < linesOnScreen; idx++ )
            {
            final int charsOnLine = 5 + 2 + 2 + 2 + myHiscore.name( idx ).length();
            maxCharsPerLine = Math.max( maxCharsPerLine, charsOnLine );
            }

        myBlitPos.y = myTitleFont.charHeight() * 2 + lineHeight / 2;

        final int scoreAlign = screen().width() * 10 / 32 + 10;
        final int levelAlign = screen().width() * 15 / 32 + 10;
        final int nameAlign = screen().width() * 16 / 32 + 10;

        myBlitPos.x = scoreAlign;
        myTextFont.blitString( gc, I18n._( "SCORE" ), myBlitPos, FontGenerator.RIGHT | FontGenerator.TOP );
        myBlitPos.x = levelAlign;
        myTextFont.blitString( gc, I18n._( "LVL" ), myBlitPos, FontGenerator.RIGHT | FontGenerator.TOP );
        myBlitPos.x = nameAlign;
        myTextFont.blitString( gc, I18n._( "NAME" ), myBlitPos, FontGenerator.LEFT | FontGenerator.TOP );

        myBlitPos.y += lineHeight / 2;

        for ( int idx = 0; idx < linesOnScreen; idx++ )
            {
            myBlitPos.y += lineHeight;

            if ( myHiscore.isLatestRank( idx ) && myBlinkTicks < timing().ticksPerSecond / 4 ) continue;

            myBlitPos.x = scoreAlign;
            myTextFont.blitNumber( gc, myBlitPos, myHiscore.score( idx ), FontGenerator.RIGHT | FontGenerator.TOP );

            myBlitPos.x = levelAlign;
            myTextFont.blitNumber( gc, myBlitPos, myHiscore.level( idx ), FontGenerator.RIGHT | FontGenerator.TOP );

            myBlitPos.x = nameAlign;
            myTextFont.blitString( gc, myHiscore.name( idx ), myBlitPos, FontGenerator.LEFT | FontGenerator.TOP );
            }
        }


    private int myBlinkTicks;

    private final Hiscore myHiscore;

    private final FontGenerator myTextFont;

    private final FontGenerator myTitleFont;

    private final Position myBlitPos = new Position();

    private final Position myTitlePos = new Position();
    }
