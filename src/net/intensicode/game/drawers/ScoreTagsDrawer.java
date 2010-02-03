package net.intensicode.game.drawers;

import net.intensicode.core.SkinManager;
import net.intensicode.game.*;
import net.intensicode.game.objects.ScoreTag;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class ScoreTagsDrawer extends ScreenBase
    {
    public ScoreTagsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myFontGen = skin.font( "bonusfont" );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        myGameContext.gameModel().scoreMarkers.scoreTags.each( DRAW_TAG );
        }



    private BitmapFontGenerator myFontGen;

    private final GameContext myGameContext;


    private static final int ALIGN_CENTER = FontGenerator.HCENTER | FontGenerator.VCENTER;



    private final Visitor DRAW_TAG = new Visitor()
    {
    public final void visit( final Object aObject )
        {
        final ScoreTag scoreTag = (ScoreTag) aObject;
        if ( !scoreTag.active ) return;
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( scoreTag.worldPosFixed );
        myFontGen.blitNumber( graphics(), screenPos, scoreTag.score, ALIGN_CENTER );
        }
    };
    }
