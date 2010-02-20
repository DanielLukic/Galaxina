package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.MainContext;
import net.intensicode.graphics.*;
import net.intensicode.util.Position;

public final class LevelInfoScreen extends GalaxinaGameScreen
    {
    public LevelInfoScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        addScreen( game().sharedGameBackground() );
        }

    public final void onInitEverytime() throws Exception
        {
        game().visualContext().sharedSoftkeys().setSoftkeys( "START", "END", false );

        // Load the level data.. Only then it is possible to distinguish challenging stages..
        model().enemySpawner.onStartLevel();

        if ( model().enemySpawner.isChallengingStage() )
            {
            myLevelInfo = "CHALLENGING STAGE";
            }
        else
            {
            final StringBuffer buffer = new StringBuffer( "LEVEL " );
            buffer.append( model().level.numberStartingAt1 );
            myLevelInfo = buffer.toString();
            }
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
            {
            stack().popScreen( this );
            model().startLevel();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            context().showMainMenu();
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();

        final BitmapFontGenerator font = visuals().textFont();
        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = screen().height() / 2 - font.charHeight();
        font.blitString( gc, myLevelInfo, myBlitPos, FontGenerator.HCENTER | FontGenerator.BOTTOM );

        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = screen().height() / 2 + font.charHeight();
        font.blitString( gc, "PRESS FIRE TO START", myBlitPos, FontGenerator.HCENTER | FontGenerator.TOP );
        }


    private String myLevelInfo = "LEVEL 1";

    private final Position myBlitPos = new Position();
    }
