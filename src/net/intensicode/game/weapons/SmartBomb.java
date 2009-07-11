package net.intensicode.game.weapons;

import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.GameObject;
import net.intensicode.util.DynamicArray;

/**
 * TODO: Describe this!
 */
public final class SmartBomb extends Weapon
    {
    public final boolean isSecondary()
        {
        return true;
        }

    public final void onInitialize()
        {
        }

    public final void onStartLevel()
        {
        myState = STATE_IDLE;
        myStateTicks = 0;
        }

    public final void onControlTick( final boolean aAllowControl )
        {
        switch ( myState )
            {
            case STATE_IDLE:
                final Keys keys = GameObject.engine.keys;
                if ( aAllowControl && keys.checkFire2AndConsume() ) onEngage();
                break;
            case STATE_LOADING:
                updateFlashIntensity( myStateTicks );
                if ( myStateTicks > 0 ) myStateTicks--;
                else onRelease();
                break;
            case STATE_RELEASING:
                GameObject.model.screenFlashIntensity = 0;
                if ( myStateTicks > 0 ) myStateTicks--;
                else myState = STATE_IDLE;
                break;
            default:
                //#if DEBUG
                throw new IllegalStateException();
                //#endif
            }
        }

    public final void onProjectileDone( final Object aObject )
        {
        }

    // Implementation

    private final void updateFlashIntensity( final int aStateTicks )
        {
        final GameModel model = GameObject.model;

        final int interval = Engine.ticksPerSecond / 6;
        if ( aStateTicks < interval )
            {
            model.screenFlashIntensity = ( interval - aStateTicks ) * 255 / interval;
            }
        else
            {
            model.screenFlashIntensity = 0;
            }
        }

    private final void onEngage()
        {
        myState = STATE_LOADING;
        myStateTicks = Engine.ticksPerSecond;
        }

    private final void onRelease()
        {
        myState = STATE_RELEASING;
        myStateTicks = Engine.ticksPerSecond;

        final DynamicArray enemies = GameObject.model.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( enemy == null || !enemy.active ) continue;
            enemy.explode();
            }
        }



    private int myStateTicks;

    private int myState = STATE_IDLE;

    private static final int STATE_IDLE = 0;

    private static final int STATE_LOADING = 1;

    private static final int STATE_RELEASING = 2;
    }
