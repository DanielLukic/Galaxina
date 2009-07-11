package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Player;
import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;
import net.intensicode.util.Size;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


/**
 * TODO: Describe this!
 */
public final class PlayerDrawer extends AbstractScreen
    {
    public PlayerDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        myGalaxina = skin.sprite( "galaxian" );
        myDamageIndicator = skin.sprite( "damage" );

        myLive = skin.image( "live" );

        final Size sizeInWorld = myGameContext.camera().toWorldSize( myGalaxina.getWidth(), myGalaxina.getHeight() );

        final Player player = myGameContext.gameModel().player;
        player.sizeInWorldFixed.setTo( sizeInWorld );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        final int tps = Engine.ticksPerSecond / 3;

        if ( myAnimTicks < tps ) myAnimTicks++;
        else myAnimTicks = 0;

        myAnimFrame = myAnimTicks * ( myGalaxina.getRawFrameCount() - 1 ) / ( tps - 1 );

        if ( myIndicatorTicks < tps * 2 ) myIndicatorTicks++;
        else myIndicatorTicks = 0;

        final Player player = myGameContext.gameModel().player;
        final int damage = FixedMath.toInt( player.damageInPercentFixed );
        if ( damage > 75 )
            {
            myIndicatorVisible = ( myIndicatorTicks % tps ) < ( tps * 2 / 3 );
            }
        else if ( damage > 50 )
            {
            myIndicatorVisible = myIndicatorTicks < ( tps * 3 / 2 );
            }
        else
            {
            myIndicatorVisible = true;
            }
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Player player = myGameContext.gameModel().player;

        final Graphics gc = aScreen.graphics();

        if ( player.invulnerableTicks == 0 ) myLastInvulState = true;
        else myLastInvulState = !myLastInvulState;

        if ( player.visible && myLastInvulState )
            {
            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( player.worldPosFixed );
            myGalaxina.setRefPixelPosition( screenPos.x, screenPos.y );
            myGalaxina.setFrame( myAnimFrame );
            myGalaxina.paint( gc );
            }

        if ( myIndicatorVisible )
            {
            final int indicatorFrames = myDamageIndicator.getRawFrameCount();
            final int damage = FixedMath.toInt( player.damageInPercentFixed );
            final int frameID = damage * ( indicatorFrames - 1 ) / 100;
            final int xPos = aScreen.width() / 2;
            final int yPos = aScreen.height() - myDamageIndicator.getHeight();
            myDamageIndicator.setRefPixelPosition( xPos, yPos );
            myDamageIndicator.setFrame( frameID );
            myDamageIndicator.paint( gc );
            }

        final int shownLives = Math.min( 5, player.lives );
        final int liveWidth = myLive.getWidth();
        final int liveHeight = myLive.getHeight();
        final int xPosBase = ( aScreen.width() - shownLives * liveWidth ) / 2;
        final int yPos = aScreen.height() - liveHeight - liveHeight / 2 - myDamageIndicator.getHeight();
        for ( int idx = 0; idx < shownLives; idx++ )
            {
            final int xPos = xPosBase + idx * liveWidth;
            gc.drawImage( myLive, xPos, yPos, Graphics.TOP | Graphics.LEFT );
            }
        }



    private int myAnimTicks;

    private int myAnimFrame;

    private int myIndicatorTicks;

    private boolean myLastInvulState;

    private boolean myIndicatorVisible;


    private Image myLive;

    private Sprite myGalaxina;

    private Sprite myDamageIndicator;


    private final GameContext myGameContext;
    }
