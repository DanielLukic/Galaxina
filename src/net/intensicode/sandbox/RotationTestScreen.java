/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.sandbox;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.game.drawers.EnemiesDrawer;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.screens.ImageScreen;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FixedMath;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;


/**
 * TODO: Describe this!
 */
public final class RotationTestScreen extends MultiScreen
    {
    public RotationTestScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();

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

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
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
            aEngine.popScreen();
            }

        myEnemy.directionInDegreesFixed = ( myEnemy.directionInDegreesFixed + FixedMath.FIXED_360 ) % FixedMath.FIXED_360;
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        myDegreesPos.x = aScreen.width() / 2;
        myDegreesPos.y = aScreen.height() / 4;
        final int degrees = FixedMath.toIntRounded( myEnemy.directionInDegreesFixed );
        myTextFont.blitNumber( aScreen.graphics(), myDegreesPos, degrees, FontGen.CENTER );
        }



    private BitmapFontGen myTextFont;

    private final GameContext myGameContext;

    private final Position myDegreesPos = new Position();

    private final Enemy myEnemy = new Enemy( new Position() );
    }
