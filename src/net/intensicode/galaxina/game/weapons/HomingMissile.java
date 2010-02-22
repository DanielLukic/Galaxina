package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.galaxina.game.objects.GameObject;
import net.intensicode.galaxina.game.objects.Missile;
import net.intensicode.util.FixedMath;

public final class HomingMissile extends Weapon
    {
    public final void onInitialize()
        {
        }

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

    private void onControl(  )
        {
        if ( !GameObject.system.keys.checkFire2AndConsume() ) return;

        final GameModel model = GameObject.model;
        final Missile missile = model.missiles.prepare( model.player.worldPosFixed );
        missile.directionFixed.x = 0;
        missile.directionFixed.y = -FixedMath.FIXED_1;
        missile.playerOwned = true;
        missile.homing = true;
        missile.launch();

        myReloadTickCount = myReloadTicks;
        myState = STATE_RELOADING;
        }



    protected int myReloadTicks;

    protected int myReloadTickCount;

    protected int myState = STATE_IDLE;

    protected static final int STATE_IDLE = 0;

    protected static final int STATE_RELOADING = 1;
    }
