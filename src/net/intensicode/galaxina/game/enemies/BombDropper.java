package net.intensicode.galaxina.game.enemies;

import net.intensicode.util.Position;
import net.intensicode.galaxina.game.objects.Bomb;

public final class BombDropper extends EnemyWeapon
    {
    public final void onControlTick()
        {
        if ( myReloadTicks > 0 ) myReloadTicks--;
        }

    public final void attachTo( final Position aParentPosition )
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
        final int xDelta = Math.abs( myParentPosition.x - model.player.worldPosFixed.x );
        return xDelta < model.player.sizeInWorldFixed.width;
        }

    public final void fireIfPossible()
        {
        if ( !canFire() ) return;

        final Bomb bomb = model.bombs.getAvailableBomb();
        final int dropHeight = model.world.visibleSizeFixed.height;
        final int dropSpeed = dropHeight / timing.ticksPerSecond / 2;
        final int xDelta = ( model.player.worldPosFixed.x - myParentPosition.x ) / 30;
        bomb.init( myParentPosition, xDelta, dropSpeed );
        bomb.start( Bomb.FROM_ENEMY );
        model.level.onEnemyBombLaunched();
        myReloadTicks = timing.ticksPerSecond;
        }



    private int myReloadTicks;

    private Position myParentPosition;
    }
