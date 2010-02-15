package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.graphics.*;
import net.intensicode.screens.MultiScreen;
import net.intensicode.util.Position;

public final class GameOverScreen extends MultiScreen
    {
    public GameOverScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        addScreen( myGameContext.sharedGameBackground() );
        addScreen( myGameContext.sharedGameDrawers() );
        myFont = myGameContext.visualContext().textFont();
        }

    public final void onInitEverytime() throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "START", "END", false );
        }

    public final void onControlTick() throws Exception
        {
        myGameContext.gameModel().onControlTick();
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
            {
            stack().popScreen( this );
            myGameContext.gameModel().startGame();
            }
        else
        if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            myGameContext.pauseGame();
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();
        myBlitPos.x = screen().width() / 2;
        myBlitPos.y = screen().height() / 2 + myFont.charHeight();
        myFont.blitString( gc, "GAME OVER", myBlitPos, FontGenerator.HCENTER | FontGenerator.TOP );
        }


    private BitmapFontGenerator myFont;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
    }
