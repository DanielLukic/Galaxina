package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.graphics.*;
import net.intensicode.util.Position;
import net.intensicode.screens.MultiScreen;

public final class LevelInfoScreen extends MultiScreen
    {
    public LevelInfoScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        final VisualContext visualContext = myGameContext.visualContext();
        addScreen( visualContext.sharedGameBackground() );
        myFont = visualContext.textFont();
        }

    public final void onInitEverytime() throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "START", "END", false );

        final GameModel model = myGameContext.gameModel();

        // Load the level data.. Only then it is possible to distinguish challenging stages..
        model.enemySpawner.onStartLevel();

        if ( model.enemySpawner.isChallengingStage() )
            {
            myLevelInfo = "CHALLENGING STAGE";
            }
        else
            {
            final StringBuffer buffer = new StringBuffer( "LEVEL " );
            buffer.append( model.level.numberStartingAt1 );
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
            myGameModel.startLevel();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            myGameContext.showMainMenu();
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();

        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = screen().height() / 2 - myFont.charHeight();
        myFont.blitString( gc, myLevelInfo, myBlitPos, FontGenerator.HCENTER | FontGenerator.BOTTOM );

        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = screen().height() / 2 + myFont.charHeight();
        myFont.blitString( gc, "PRESS FIRE TO START", myBlitPos, FontGenerator.HCENTER | FontGenerator.TOP );
        }


    private BitmapFontGenerator myFont;

    private String myLevelInfo = "LEVEL 1";


    private final GameModel myGameModel;

    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
    }
