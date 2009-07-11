package net.intensicode.game.weapons;

import net.intensicode.core.Engine;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.GameObject;
import net.intensicode.game.objects.Missile;
import net.intensicode.util.FixedMath;

/**
 * TODO: Describe this!
 */
public final class HomingMissile extends Weapon
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
        myReloadTickCount = 0;
        myReloadTicks = Engine.ticksPerSecond;
        }

    public final void onControlTick( final boolean aAllowControl )
        {
        final Engine engine = GameObject.engine;
        switch ( myState )
            {
            case STATE_IDLE:
                if ( aAllowControl ) onControl( engine );
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

    private final void onControl( final Engine aEngine )
        {
        if ( !aEngine.keys.checkFire2AndConsume() ) return;

        final GameModel model = GameObject.model;
        final Missile missile = model.missiles.prepare( model.player.worldPosFixed );
        missile.directionFixed.x = 0;
        missile.directionFixed.y = -FixedMath.FIXED_1;
        missile.playerOwned = true;
        missile.homing = true;
        missile.type = Missile.TYPE_HOMING;
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
