package net.intensicode.galaxina.game.enemies;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.weapons.Bomb;

public final class BombDropper extends EnemyWeapon
    {
    public final void onControlTick()
        {
        if ( myReloadTicks > 0 ) myReloadTicks--;
        }

    public final void attachTo( final PositionF aParentPosition )
        {
        myParentPosition = aParentPosition;
        }

    public final boolean canFire()
        {
        return myReloadTicks == 0 && model.level.anotherEnemyBombAllowed();
        }

    public final boolean canFireOnRun()
        {
        return true;
        }

    public final boolean isTargetGood()
        {
        final float xDelta = Math.abs( myParentPosition.x - model.player.worldPos.x );
        return xDelta < model.player.sizeInWorld.width;
        }

    public final void fireIfPossible()
        {
        if ( !canFire() ) return;

        final Bomb bomb = model.bombs.getAvailableBomb();
        final float dropHeight = model.world.visibleSize.height;
        final float dropSpeed = dropHeight / timing.ticksPerSecond / 2;
        final float xDelta = ( model.player.worldPos.x - myParentPosition.x ) / 30;
        bomb.init( myParentPosition, xDelta, dropSpeed );
        bomb.start( Bomb.FROM_ENEMY );
        model.level.onEnemyBombLaunched();
        myReloadTicks = timing.ticksPerSecond;
        }



    private int myReloadTicks;

    private PositionF myParentPosition;
    }
