package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class PlayerDrawer extends ScreenBase
    {
    public PlayerDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myGalaxina = skin.sprite( "galaxian" );

        final SizeF sizeInWorld = myGameContext.camera().toWorldSize( myGalaxina.getWidth(), myGalaxina.getHeight() );

        final Player player = myGameContext.gameModel().player;
        player.sizeInWorld.setTo( sizeInWorld );

        myFont = skin().font( "scorefont" );
        }

    public final void onControlTick() throws Exception
        {
        final int tps = timing().ticksPerSecond / 3;

        if ( myAnimTicks < tps ) myAnimTicks++;
        else myAnimTicks = 0;

        myAnimFrame = myAnimTicks * ( myGalaxina.getRawFrameCount() - 1 ) / ( tps - 1 );
        }

    public final void onDrawFrame()
        {
        final Player player = myGameContext.gameModel().player;

        final DirectGraphics gc = graphics();

        if ( player.invulnerableTicks == 0 ) myLastInvulState = true;
        else myLastInvulState = !myLastInvulState;

        if ( player.visible && myLastInvulState )
            {
            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( player.worldPos );
            myGalaxina.setFrame( myAnimFrame );
            myGalaxina.paint( gc, screenPos.x, screenPos.y );
            }

        myBlitPos.x = myGameContext.visualContext().configuration().touchSecondaryFire.x;
        myBlitPos.y = myGameContext.visualContext().configuration().touchSecondaryFire.y;
        if ( player.secondaryWeapon != null )
            {
            myFont.blitNumber( gc, myBlitPos, player.secondaryWeapon.remainingShots, FontGenerator.CENTER );
            }
        }


    private int myAnimTicks;

    private int myAnimFrame;

    private boolean myLastInvulState;


    private SpriteGenerator myGalaxina;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();

    private BitmapFontGenerator myFont;
    }
