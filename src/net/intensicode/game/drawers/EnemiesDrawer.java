package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyType;
import net.intensicode.game.objects.Level;
import net.intensicode.util.UtilitiesEx;
import net.intensicode.util.*;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;


/**
 * TODO: Describe this!
 */
public final class EnemiesDrawer extends AbstractScreen
    {
    public EnemiesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        final EnemyType[] enemyTypes = myGameContext.gameModel().enemies.types;

        myEnemyGenerators = new Sprite[enemyTypes.length];

        for ( int idx = 0; idx < enemyTypes.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "enemy" );
            imageName.append( idx + 1 );

            final Sprite sprite = myEnemyGenerators[ idx ] = skin.sprite( imageName.toString() );
            final Size sizeInWorld = myGameContext.camera().toWorldSize( sprite.getWidth(), sprite.getHeight() );
            enemyTypes[ idx ].sizeInWorldFixed.setTo( sizeInWorld );
            }
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();

        final Level level = myGameContext.gameModel().level;
        final DynamicArray enemies = level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( enemy.active == false ) continue;
            drawEnemy( graphics, enemy );
            }
        }

    // Implementation

    private final void drawEnemy( final Graphics aGraphics, final Enemy aEnemy )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aEnemy.worldPosFixed );

        final Sprite sprite = myEnemyGenerators[ aEnemy.type.typeID ];
        final int directionSteps = sprite.getFrameSequenceLength();
        final int direction = FixedMath.toIntRounded( aEnemy.directionInDegreesFixed );
        final int directionIndex = UtilitiesEx.getDirectionID( direction + 45, directionSteps );
        sprite.setFrame( directionIndex % directionSteps );
        sprite.setRefPixelPosition( screenPos.x, screenPos.y );
        sprite.paint( aGraphics );
        }



    private Sprite[] myEnemyGenerators;

    private final GameContext myGameContext;
    }
