package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class BombsDrawer extends ScreenBase
    {
    public BombsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    public final void drawBombs( final DirectGraphics aGraphics, final Bomb[] aBombs )
        {
        for ( int idx = 0; idx < aBombs.length; idx++ )
            {
            final Bomb bomb = aBombs[ idx ];
            if ( !bomb.active ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( bomb.worldPosFixed );
            myBombGen.paint( aGraphics, screenPos.x, screenPos.y );
            }
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myBombGen = skin.sprite( "bomb" );
        myBombGen.defineReferencePixel( myBombGen.getWidth() / 2, myBombGen.getHeight() / 2 );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();
        final GameModel model = myGameContext.gameModel();
        drawBombs( graphics, model.bombs.bombs );
        }



    private SpriteGenerator myBombGen;

    private final GameContext myGameContext;
    }
