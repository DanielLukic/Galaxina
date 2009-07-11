package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Level;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;


/**
 * Shows the level to played next.
 */
public final class LevelStatsScreen extends MultiScreen
    {
    public LevelStatsScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From MultiScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        addScreen( myGameContext.visualContext().sharedGameBackground() );
        addScreen( myGameContext.visualContext().sharedGameDrawers() );
        myFontGen = myGameContext.visualContext().textFont();
        }

    public void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myTimeOutTicks = Engine.ticksPerSecond * 5;
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "CONTINUE", "CONTINUE", false );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        myGameContext.gameModel().onControlTick();
        super.onControlTick( aEngine );

        if ( aEngine.keys.checkLeftSoftAndConsume() )
            {
            myTimeOutTicks = 0;
            }
        else if ( aEngine.keys.checkRightSoftAndConsume() )
            {
            myTimeOutTicks = 0;
            }
        else if ( myTimeOutTicks < Engine.ticksPerSecond * 3 && aEngine.keys.checkFireAndConsume() )
            {
            myTimeOutTicks = 0;
            }

        if ( myTimeOutTicks > 0 )
            {
            myTimeOutTicks--;
            }
        else
            {
            aEngine.popScreen( this );
            myGameContext.gameModel().onNextLevel();
            }
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        final Level level = myGameContext.gameModel().level;

        final int linesOnScreen = 1 + ( level.perfect ? 1 : ( level.bonusApplies > 0 ? 1 : 0 ) );

        myBlitPos.x = aScreen.width() / 2;
        myBlitPos.y = ( aScreen.height() - linesOnScreen * 3 / 2 ) / 2;
        myFontGen.blitString( aScreen.graphics(), "LEVEL COMPLETE", myBlitPos, FontGen.CENTER );

        if ( level.perfect )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitString( aScreen.graphics(), "PERFECT", myBlitPos, FontGen.CENTER );
            }
        else if ( level.bonusApplies > 0 )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitString( aScreen.graphics(), "BONUS", myBlitPos, FontGen.CENTER );
            }
        if ( level.bonusApplies > 0 )
            {
            myBlitPos.y += myFontGen.charHeight() * 3 / 2;
            myFontGen.blitNumber( aScreen.graphics(), myBlitPos, level.bonusApplies, FontGen.CENTER );
            }
        }


    private int myTimeOutTicks;

    private BitmapFontGen myFontGen;

    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
    }
