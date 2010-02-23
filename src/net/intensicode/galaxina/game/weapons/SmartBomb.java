package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.util.DynamicArray;
import net.intensicode.core.KeysHandler;

public final class SmartBomb extends Weapon
    {
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
                final KeysHandler keys = GameObject.system.keys;
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

    private void updateFlashIntensity( final int aStateTicks )
        {
        final GameModel model = GameObject.model;

        final int interval = GameObject.timing.ticksPerSecond / 6;
        if ( aStateTicks < interval )
            {
            model.screenFlashIntensity = ( interval - aStateTicks ) * 255 / interval;
            }
        else
            {
            model.screenFlashIntensity = 0;
            }
        }

    private void onEngage()
        {
        myState = STATE_LOADING;
        myStateTicks = GameObject.timing.ticksPerSecond;
        }

    private void onRelease()
        {
        myState = STATE_RELEASING;
        myStateTicks = GameObject.timing.ticksPerSecond;

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
