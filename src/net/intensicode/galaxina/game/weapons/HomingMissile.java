package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.*;

public final class HomingMissile extends SecondaryWeapon
    {
    public final void onStartLevel()
        {
        myState = STATE_IDLE;
        myReloadTickCount = 0;
        myReloadTicks = GameObject.timing.ticksPerSecond;
        }

    public final void onControlTick( final boolean aAllowControl )
        {
        switch ( myState )
            {
            case STATE_IDLE:
                if ( aAllowControl ) onControl();
                break;

            case STATE_RELOADING:
                if ( myReloadTickCount > 0 ) myReloadTickCount--;
                else myState = STATE_IDLE;
                break;
            }
        }

    public final void onProjectileDone( final Object aObject )
        {
        }

    // Implementation

    private void onControl()
        {
        if ( !GameObject.system.keys.checkFire2AndConsume() ) return;
        if ( remainingShots <= 0 ) return;
        remainingShots--;

        final GameModel model = GameObject.model;
        if ( remainingShots == 0 ) deactivate();

        final Missile missile = model.missiles.prepare( model.player.worldPos );
        missile.direction.x = 0;
        missile.direction.y = -1;
        missile.playerOwned = true;
        missile.homing = true;
        missile.launch();

        myReloadTickCount = myReloadTicks;
        myState = STATE_RELOADING;
        }


    private int myReloadTicks;

    private int myReloadTickCount;

    private int myState = STATE_IDLE;

    protected static final int STATE_IDLE = 0;

    protected static final int STATE_RELOADING = 1;
    }
