/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.sandbox;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.game.drawers.EnemiesDrawer;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyPath;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Utilities;
import net.intensicode.screens.ImageScreen;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FixedMath;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;


/**
 * TODO: Describe this!
 */
public final class PathTestScreen extends MultiScreen
    {
    public PathTestScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();

        myGameContext.gameModel().onInitialize( aEngine );
        myGameContext.gameModel().startGame();
        myGameContext.gameModel().startLevel();

        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "AUTO", "BACK", false );

        addScreen( new ImageScreen( skin.image( "background" ) ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );
        addScreen( myGameContext.camera() );
        addScreen( new EnemiesDrawer( myGameContext ) );

        final EnemyPath path = new EnemyPath( myGameContext.gameModel().world );
        path.addRelativePos( 50, 0 );
        path.addRelativePos( 0, 50 );
        path.addRelativePos( -50, 0 );
        path.addRelativePos( 0, -50 );
        path.close();

        myEnemy.init( myGameContext.gameModel().enemies.types[ 0 ], path, new Position() );
        myEnemy.active = true;

        myGameContext.gameModel().level.activeEnemies.add( myEnemy );

        myTextFont = myGameContext.visualContext().textFont();
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkRightSoftAndConsume() ) aEngine.popScreen();

        if ( keys.checkLeftSoftAndConsume() ) myAutoMove = !myAutoMove;

        if ( keys.checkUpAndConsume() || myAutoMove ) moveEnemy();
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        final int degrees = FixedMath.toIntRounded( myEnemy.targetDirectionFixed );
        final Graphics gc = aScreen.graphics();

        myDegreesPos.x = aScreen.width() / 2;
        myDegreesPos.y = aScreen.height() / 4;
        myTextFont.blitNumber( gc, myDegreesPos, degrees, FontGen.CENTER );

        gc.setColor( 0xFFFFFF );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir.x, myEnemyPos.y + myEnemyDir.y );
        gc.setColor( 0xFF0000 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir1.x, myEnemyPos.y + myEnemyDir1.y );
        gc.setColor( 0x00FF00 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir2.x, myEnemyPos.y + myEnemyDir2.y );
        gc.setColor( 0xFFFF00 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir3.x, myEnemyPos.y + myEnemyDir3.y );
        }

    // Implementation

    private final void moveEnemy()
        {
        myEnemy.moveAlongPath();
        myEnemy.directionInDegreesFixed = myEnemy.targetDirectionFixed;

        myEnemyPos.setTo( myEnemy.path.getPosition( myEnemy.pathPos ) );
        myEnemyPos.setTo( myGameContext.camera().toScreen( myEnemyPos ) );
        myEnemyDir.setTo( myEnemy.path.getDirection( myEnemy.pathPos ) );

        final GameModel model = myGameContext.gameModel();
        final int offset1 = myEnemy.type.getSpeedFixed( model ) * Enemy.LOOK_BEHIND_STEPS;
        final int offset2 = myEnemy.type.getSpeedFixed( model ) * Enemy.LOOK_AHEAD_STEPS;
        myEnemyDir1.setTo( myEnemy.path.getDirection( myEnemy.pathPos - offset1 ) );
        myEnemyDir2.setTo( myEnemy.path.getDirection( myEnemy.pathPos + offset2 ) );

        myEnemyDir.x *= 5;
        myEnemyDir.y *= 5;
        myEnemyDir1.x *= 5;
        myEnemyDir1.y *= 5;
        myEnemyDir2.x *= 5;
        myEnemyDir2.y *= 5;

        myEnemyDir3.x = myEnemyDir1.x + myEnemyDir2.x;
        myEnemyDir3.y = myEnemyDir1.y + myEnemyDir2.y;

        final int degrees = Utilities.directionToDegrees( myEnemyDir3 );
        myEnemy.directionInDegreesFixed = myEnemy.targetDirectionFixed = FixedMath.toFixed( degrees );

        FixedMath.toInt( myEnemyDir );
        FixedMath.toInt( myEnemyDir1 );
        FixedMath.toInt( myEnemyDir2 );
        FixedMath.toInt( myEnemyDir3 );

        if ( myEnemy.pathPos >= myEnemy.path.getPathLength() ) myEnemy.pathPos = 0;
        }



    private boolean myAutoMove;

    private BitmapFontGen myTextFont;

    private final GameContext myGameContext;

    private final Position myEnemyPos = new Position();

    private final Position myEnemyDir = new Position();

    private final Position myEnemyDir1 = new Position();

    private final Position myEnemyDir2 = new Position();

    private final Position myEnemyDir3 = new Position();

    private final Position myDegreesPos = new Position();

    private final Enemy myEnemy = new Enemy( new Position() );
    }