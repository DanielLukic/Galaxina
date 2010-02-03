package net.intensicode.sandbox;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.game.drawers.EnemiesDrawer;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.graphics.*;
import net.intensicode.screens.*;
import net.intensicode.util.*;

public final class RotationTestScreen extends MultiScreen
    {
    public RotationTestScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "", "BACK", false );

        addScreen( new ImageScreen( skin.image( "background" ) ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );
        addScreen( myGameContext.camera() );
        addScreen( new EnemiesDrawer( myGameContext ) );

        myEnemy.type = myGameContext.gameModel().enemies.types[ 1 ];
        myEnemy.active = true;

        myGameContext.gameModel().level.activeEnemies.add( myEnemy );

        myTextFont = myGameContext.visualContext().textFont();
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkLeftAndConsume() )
            {
            myEnemy.directionInDegreesFixed -= FixedMath.FIXED_5;
            }
        if ( keys.checkRightAndConsume() )
            {
            myEnemy.directionInDegreesFixed += FixedMath.FIXED_5;
            }
        if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen();
            }

        myEnemy.directionInDegreesFixed = ( myEnemy.directionInDegreesFixed + FixedMath.FIXED_360 ) % FixedMath.FIXED_360;
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        myDegreesPos.x = screen().width() / 2;
        myDegreesPos.y = screen().height() / 4;
        final int degrees = FixedMath.toIntRounded( myEnemy.directionInDegreesFixed );
        myTextFont.blitNumber( graphics(), myDegreesPos, degrees, FontGenerator.CENTER );
        }



    private BitmapFontGenerator myTextFont;

    private final GameContext myGameContext;

    private final Position myDegreesPos = new Position();

    private final Enemy myEnemy = new Enemy( new Position() );
    }
