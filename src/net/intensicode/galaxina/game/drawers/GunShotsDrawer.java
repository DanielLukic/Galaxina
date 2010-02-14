package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class GunShotsDrawer extends ScreenBase
    {
    public GunShotsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    public final void drawGunShots( final DirectGraphics aGraphics, final GunShot[] aGunShots )
        {
        for ( int idx = 0; idx < aGunShots.length; idx++ )
            {
            final GunShot gunShot = aGunShots[ idx ];
            if ( !gunShot.active ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( gunShot.worldPosFixed );
            myGunShotGen.paint( aGraphics, screenPos.x, screenPos.y );
            }
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myGunShotGen = skin.sprite( "gunshot" );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();
        final GameModel model = myGameContext.gameModel();
        drawGunShots( graphics, model.gunShots.gunShots );
        }

    private SpriteGenerator myGunShotGen;

    private final GameContext myGameContext;
    }
