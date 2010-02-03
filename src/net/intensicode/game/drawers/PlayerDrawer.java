package net.intensicode.game.drawers;

import net.intensicode.core.*;
import net.intensicode.game.*;
import net.intensicode.game.objects.Player;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

import javax.microedition.lcdui.Graphics;

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
        myDamageIndicator = skin.sprite( "damage" );

        myLive = skin.image( "live" );

        final Size sizeInWorld = myGameContext.camera().toWorldSize( myGalaxina.getWidth(), myGalaxina.getHeight() );

        final Player player = myGameContext.gameModel().player;
        player.sizeInWorldFixed.setTo( sizeInWorld );
        }

    public final void onControlTick() throws Exception
        {
        final int tps = timing().ticksPerSecond / 3;

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

    public final void onDrawFrame()
        {
        final Player player = myGameContext.gameModel().player;

        final DirectGraphics gc = graphics();

        if ( player.invulnerableTicks == 0 ) myLastInvulState = true;
        else myLastInvulState = !myLastInvulState;

        if ( player.visible && myLastInvulState )
            {
            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( player.worldPosFixed );
            myGalaxina.setFrame( myAnimFrame );
            myGalaxina.paint( gc, screenPos.x, screenPos.y );
            }

        if ( myIndicatorVisible )
            {
            final int indicatorFrames = myDamageIndicator.getRawFrameCount();
            final int damage = FixedMath.toInt( player.damageInPercentFixed );
            final int frameID = damage * ( indicatorFrames - 1 ) / 100;
            final int xPos = screen().width() / 2;
            final int yPos = screen().height() - myDamageIndicator.getHeight();
            myDamageIndicator.setFrame( frameID );
            myDamageIndicator.paint( gc, xPos, yPos );
            }

        final int shownLives = Math.min( 5, player.lives );
        final int liveWidth = myLive.getWidth();
        final int liveHeight = myLive.getHeight();
        final int xPosBase = ( screen().width() - shownLives * liveWidth ) / 2;
        final int yPos = screen().height() - liveHeight - liveHeight / 2 - myDamageIndicator.getHeight();
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


    private ImageResource myLive;

    private SpriteGenerator myGalaxina;

    private SpriteGenerator myDamageIndicator;


    private final GameContext myGameContext;
    }
