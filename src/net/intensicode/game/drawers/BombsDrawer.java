package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Bomb;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;



/**
 * TODO: Describe this!
 */
public final class BombsDrawer extends AbstractScreen
    {
    public BombsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    public final void drawBombs( final Graphics aGraphics, final Bomb[] aBombs )
        {
        for ( int idx = 0; idx < aBombs.length; idx++ )
            {
            final Bomb bomb = aBombs[ idx ];
            if ( bomb.active == false ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( bomb.worldPosFixed );
            myBombGen.setRefPixelPosition( screenPos.x, screenPos.y );
            myBombGen.paint( aGraphics );
            }
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        myBombGen = skin.sprite( "bomb" );
        myBombGen.defineReferencePixel( myBombGen.getWidth() / 2, myBombGen.getHeight() / 2 );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();
        final GameModel model = myGameContext.gameModel();
        drawBombs( graphics, model.bombs.bombs );
        }



    private Sprite myBombGen;

    private final GameContext myGameContext;
    }
