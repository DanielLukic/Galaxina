package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class ScoreboardDrawer extends ScreenBase
    {
    public ScoreboardDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myFont = skin.font( "textfont" );
        myScoreFont = skin.font( "scorefont" );
        myDamageIndicator = skin.sprite( "damage" );
        myLive = skin.image( "live" );
        }

    public final void onControlTick() throws Exception
        {
        animateScore();
        animateIndicator();
        }

    private void animateIndicator()
        {
        final int tps = timing().ticksPerSecond / 3;
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

    private void animateScore()
        {
        final GameModel model = myGameContext.gameModel();
        final int score = model.player.score;
        if ( myShownScore < score )
            {
            myShownScore += Math.max( 1, ( score - myShownScore ) / 4 );
            }
        else if ( score < myShownScore )
            {
            myShownScore = score;
            }
        }

    public final void onDrawFrame()
        {
        drawScoreAndHiscore();
        drawRemainingLives();
        if ( myIndicatorVisible ) drawDamageIndicator();
        }

    private void drawScoreAndHiscore()
        {
        final int screenWidth = screen().width();
        final int lineHeight = myFont.charHeight() * 12 / 10;

        final int leftPos = screenWidth / 6;
        final int rightPos = screenWidth * 5 / 6;

        final DirectGraphics gc = graphics();

        myBlitPos.x = leftPos;
        myBlitPos.y = 0;
        myFont.blitString( gc, "SCORE", myBlitPos, ALIGN_HCENTER_TOP );

        myBlitPos.x = rightPos;
        myBlitPos.y = 0;
        myFont.blitString( gc, "HISCORE", myBlitPos, ALIGN_HCENTER_TOP );

        myBlitPos.x = leftPos;
        myBlitPos.y = lineHeight;
        myScoreFont.blitNumber( gc, myBlitPos, myShownScore, ALIGN_HCENTER_TOP, 7 );

        myBlitPos.x = rightPos;
        myBlitPos.y = lineHeight;
        myScoreFont.blitNumber( gc, myBlitPos, myGameContext.hiscore().topScore(), ALIGN_HCENTER_TOP, 7 );
        }

    private void drawDamageIndicator()
        {
        final int indicatorFrames = myDamageIndicator.getRawFrameCount();
        final Player player = myGameContext.gameModel().player;
        final int damage = FixedMath.toInt( player.damageInPercentFixed );
        final int frameID = damage * ( indicatorFrames - 1 ) / 100;
        final int xPos = screen().width() / 2;
        final int liveHeight = myLive.getHeight();
        final int yPos = myDamageIndicator.getHeight() + liveHeight * 2;
        myDamageIndicator.paint( graphics(), xPos, yPos, frameID );
        }

    private void drawRemainingLives()
        {
        final Player player = myGameContext.gameModel().player;

        final int shownLives = Math.min( 5, player.lives );
        final int liveWidth = myLive.getWidth();
        final int liveHeight = myLive.getHeight();
        final int xPosBase = ( screen().width() - shownLives * liveWidth ) / 2;
        for ( int idx = 0; idx < shownLives; idx++ )
            {
            final int xPos = xPosBase + idx * liveWidth;
            graphics().drawImage( myLive, xPos, liveHeight / 2, DirectGraphics.ALIGN_TOP_LEFT );
            }
        }


    private int myShownScore;

    private int myIndicatorTicks;

    private boolean myIndicatorVisible;

    private ImageResource myLive;

    private BitmapFontGenerator myFont;

    private BitmapFontGenerator myScoreFont;

    private SpriteGenerator myDamageIndicator;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();

    private static final int ALIGN_HCENTER_TOP = FontGenerator.HCENTER | FontGenerator.TOP;
    }
