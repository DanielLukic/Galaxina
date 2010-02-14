package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

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
        }

    public final void onControlTick() throws Exception
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
        final int screenWidth = screen().width();
        final int lineHeight = myFont.charHeight() * 12 / 10;

        final int leftPos = screenWidth / 4;
        final int rightPos = screenWidth * 3 / 4;

        myBlitPos.x = leftPos;
        myBlitPos.y = 0;
        final DirectGraphics gc = graphics();
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



    private int myShownScore = 0;

    private BitmapFontGenerator myFont;

    private BitmapFontGenerator myScoreFont;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();

    private static final int ALIGN_HCENTER_TOP = FontGenerator.HCENTER | FontGenerator.TOP;
    }
