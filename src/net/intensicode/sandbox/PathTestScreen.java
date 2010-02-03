package net.intensicode.sandbox;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.game.drawers.EnemiesDrawer;
import net.intensicode.game.enemies.*;
import net.intensicode.game.objects.GameModel;
import net.intensicode.graphics.*;
import net.intensicode.screens.*;
import net.intensicode.util.*;

public final class PathTestScreen extends MultiScreen
    {
    public PathTestScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        myGameContext.gameModel().onInitialize( system() );
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

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkRightSoftAndConsume() ) stack().popScreen();

        if ( keys.checkLeftSoftAndConsume() ) myAutoMove = !myAutoMove;

        if ( keys.checkUpAndConsume() || myAutoMove ) moveEnemy();
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final int degrees = FixedMath.toIntRounded( myEnemy.targetDirectionFixed );
        final DirectGraphics gc = graphics();

        myDegreesPos.x = screen().width() / 2;
        myDegreesPos.y = screen().height() / 4;
        myTextFont.blitNumber( gc, myDegreesPos, degrees, FontGenerator.CENTER );

        gc.setColorRGB24( 0xFFFFFF );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir.x, myEnemyPos.y + myEnemyDir.y );
        gc.setColorRGB24( 0xFF0000 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir1.x, myEnemyPos.y + myEnemyDir1.y );
        gc.setColorRGB24( 0x00FF00 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir2.x, myEnemyPos.y + myEnemyDir2.y );
        gc.setColorRGB24( 0xFFFF00 );
        gc.drawLine( myEnemyPos.x, myEnemyPos.y, myEnemyPos.x + myEnemyDir3.x, myEnemyPos.y + myEnemyDir3.y );
        }

    // Implementation

    private void moveEnemy()
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

        final int degrees = UtilitiesEx.directionToDegrees( myEnemyDir3 );
        myEnemy.directionInDegreesFixed = myEnemy.targetDirectionFixed = FixedMath.toFixed( degrees );

        FixedMath.toInt( myEnemyDir );
        FixedMath.toInt( myEnemyDir1 );
        FixedMath.toInt( myEnemyDir2 );
        FixedMath.toInt( myEnemyDir3 );

        if ( myEnemy.pathPos >= myEnemy.path.getPathLength() ) myEnemy.pathPos = 0;
        }



    private boolean myAutoMove;

    private BitmapFontGenerator myTextFont;

    private final GameContext myGameContext;

    private final Position myEnemyPos = new Position();

    private final Position myEnemyDir = new Position();

    private final Position myEnemyDir1 = new Position();

    private final Position myEnemyDir2 = new Position();

    private final Position myEnemyDir3 = new Position();

    private final Position myDegreesPos = new Position();

    private final Enemy myEnemy = new Enemy( new Position() );
    }
