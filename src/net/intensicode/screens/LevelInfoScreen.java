package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.VisualContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;


/**
 * Shows the level to played next.
 */
public final class LevelInfoScreen extends MultiScreen
    {
    public LevelInfoScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From MultiScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final VisualContext visualContext = myGameContext.visualContext();
        addScreen( visualContext.sharedGameBackground() );
        myFont = visualContext.textFont();
        }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
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

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
            {
            aEngine.popScreen( this );
            myGameModel.startLevel();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            aEngine.popScreen( this );
            myGameContext.showMainMenu();
            }
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        final Graphics gc = aScreen.graphics();

        myBlitPos.x = aScreen.width() / 2;
        myBlitPos.y = aScreen.height() / 2 - myFont.charHeight();
        myFont.blitString( gc, myLevelInfo, myBlitPos, FontGen.HCENTER | FontGen.BOTTOM );

        myBlitPos.x = aScreen.width() / 2;
        myBlitPos.y = aScreen.height() / 2 + myFont.charHeight();
        myFont.blitString( gc, "PRESS FIRE TO START", myBlitPos, FontGen.HCENTER | FontGen.TOP );
        }


    private BitmapFontGen myFont;

    private String myLevelInfo = "LEVEL 1";


    private final GameModel myGameModel;

    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
    }
