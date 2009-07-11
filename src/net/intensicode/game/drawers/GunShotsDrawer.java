package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.GunShot;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;



/**
 * TODO: Describe this!
 */
public final class GunShotsDrawer extends AbstractScreen
    {
    public GunShotsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    public final void drawGunShots( final Graphics aGraphics, final GunShot[] aGunShots )
        {
        for ( int idx = 0; idx < aGunShots.length; idx++ )
            {
            final GunShot gunShot = aGunShots[ idx ];
            if ( gunShot.active == false ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( gunShot.worldPosFixed );
            myGunShotGen.setRefPixelPosition( screenPos.x, screenPos.y );
            myGunShotGen.paint( aGraphics );
            }
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        myGunShotGen = skin.sprite( "gunshot" );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();
        final GameModel model = myGameContext.gameModel();
        drawGunShots( graphics, model.gunShots.gunShots );
        }



    private Sprite myGunShotGen;

    private final GameContext myGameContext;
    }
