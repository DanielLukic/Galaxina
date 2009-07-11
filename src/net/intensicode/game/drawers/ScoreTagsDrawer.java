package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.ScoreTag;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;
import net.intensicode.util.Visitor;



/**
 * TODO: Describe this!
 */
public final class ScoreTagsDrawer extends AbstractScreen
{
    public ScoreTagsDrawer( final GameContext aGameContext )
    {
        myGameContext = aGameContext;
    }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        final Skin skin = myGameContext.visualContext().skin();
        myFontGen = skin.font( "bonusfont" );
    }

    public final void onControlTick( final Engine aEngine ) throws Exception
    {
    }

    public final void onDrawFrame( final DirectScreen aScreen )
    {
        myGameContext.gameModel().scoreMarkers.scoreTags.each( DRAW_TAG );
    }



    private BitmapFontGen myFontGen;

    private final GameContext myGameContext;


    private static final int ALIGN_CENTER = FontGen.HCENTER | FontGen.VCENTER;



    private final Visitor DRAW_TAG = new Visitor()
    {
        public final void visit( final Object aObject )
        {
            final ScoreTag scoreTag = ( ScoreTag ) aObject;
            if ( scoreTag.active == false ) return;
            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( scoreTag.worldPosFixed );
            myFontGen.blitNumber( screen().graphics(), screenPos, scoreTag.score, ALIGN_CENTER );
        }
    };
}
