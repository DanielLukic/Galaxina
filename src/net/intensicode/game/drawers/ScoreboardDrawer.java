package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * TODO: Describe this!
 */
public final class ScoreboardDrawer extends AbstractScreen
{
    public ScoreboardDrawer( final GameContext aGameContext )
    {
        myGameContext = aGameContext;
    }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        final Skin skin = myGameContext.visualContext().skin();
        myFont = skin.font( "textfont" );
        myScoreFont = skin.font( "scorefont" );
    }

    public final void onControlTick( final Engine aEngine ) throws Exception
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

    public final void onDrawFrame( final DirectScreen aScreen )
    {
        final int screenWidth = aScreen.width();
        final int lineHeight = myFont.charHeight() * 12 / 10;

        final int leftPos = screenWidth / 4;
        final int rightPos = screenWidth * 3 / 4;

        myBlitPos.x = leftPos;
        myBlitPos.y = 0;
        final Graphics gc = aScreen.graphics();
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

    private BitmapFontGen myFont;

    private BitmapFontGen myScoreFont;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();

    private static final int ALIGN_HCENTER_TOP = FontGen.HCENTER | FontGen.TOP;
}
