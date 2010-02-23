package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.Level;
import net.intensicode.graphics.*;
import net.intensicode.screens.MultiScreen;
import net.intensicode.util.Position;

public final class LevelStatsScreen extends MultiScreen
    {
    public LevelStatsScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        addScreen( myGameContext.sharedGameBackground() );
        addScreen( myGameContext.sharedGameDrawers() );
        myFontGen = myGameContext.visualContext().textFont();
        }

    public void onInitEverytime() throws Exception
        {
        myTimeOutTicks = timing().ticksPerSecond * 5;
        myGameContext.sharedSoftkeys().setSoftkeys( "CONTINUE", "CONTINUE" );
        }

    public final void onControlTick() throws Exception
        {
        myGameContext.gameModel().onControlTick();
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() || keys.checkStickDownAndConsume() )
            {
            myTimeOutTicks = 0;
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            myTimeOutTicks = 0;
            }
        else if ( myTimeOutTicks < timing().ticksPerSecond * 3 && keys().checkFireAndConsume() )
                {
                myTimeOutTicks = 0;
                }

        if ( myTimeOutTicks > 0 )
            {
            myTimeOutTicks--;
            }
        else
            {
            stack().popScreen( this );
            myGameContext.gameModel().onNextLevel();
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final Level level = myGameContext.gameModel().level;

        final int linesOnScreen = 1 + ( level.perfect ? 1 : ( level.bonusApplies > 0 ? 1 : 0 ) );

        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = ( screen().height() - linesOnScreen * 3 / 2 ) / 2;
        myFontGen.blitString( graphics(), "LEVEL COMPLETE", myBlitPos, FontGenerator.CENTER );

        if ( level.perfect )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitString( graphics(), "PERFECT", myBlitPos, FontGenerator.CENTER );
            }
        else if ( level.bonusApplies > 0 )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitString( graphics(), "BONUS", myBlitPos, FontGenerator.CENTER );
            }
        if ( level.bonusApplies > 0 )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitNumber( graphics(), myBlitPos, level.bonusApplies, FontGenerator.CENTER );
            }
        }


    private int myTimeOutTicks;

    private BitmapFontGenerator myFontGen;

    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
    }
