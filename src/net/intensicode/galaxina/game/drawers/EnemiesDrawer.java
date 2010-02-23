package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.util.UtilitiesEx;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class EnemiesDrawer extends ScreenBase
    {
    public EnemiesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        final EnemyType[] enemyTypes = myGameContext.gameModel().enemies.types;

        myEnemyGenerators = new SpriteGenerator[enemyTypes.length];

        for ( int idx = 0; idx < enemyTypes.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "enemy" );
            imageName.append( idx + 1 );

            final SpriteGenerator sprite = myEnemyGenerators[ idx ] = skin.sprite( imageName.toString() );
            final Size sizeInWorld = myGameContext.camera().toWorldSize( sprite.getWidth(), sprite.getHeight() );
            enemyTypes[ idx ].sizeInWorldFixed.setTo( sizeInWorld );
            }
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final Level level = myGameContext.gameModel().level;
        final DynamicArray enemies = level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( !enemy.active ) continue;
            drawEnemy( graphics, enemy );
            }
        }

    // Implementation

    private void drawEnemy( final DirectGraphics aGraphics, final Enemy aEnemy )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aEnemy.worldPosFixed );

        final SpriteGenerator sprite = myEnemyGenerators[ aEnemy.type.typeID ];
        final int directionSteps = sprite.getFrameSequenceLength();
        final int direction = FixedMath.toIntRounded( aEnemy.directionInDegreesFixed );
        final int directionIndex = UtilitiesEx.getDirectionID( direction + 45, directionSteps );
        sprite.setFrame( directionIndex % directionSteps );
        sprite.paint( aGraphics, screenPos.x, screenPos.y );

        //#if DEBUG
        aGraphics.setColorARGB32( 0xFF00FFFF );
        aGraphics.fillRect( screenPos.x - 1, screenPos.y - 1, 3, 3 );
        //#endif
        }

    private SpriteGenerator[] myEnemyGenerators;

    private final GameContext myGameContext;
    }
