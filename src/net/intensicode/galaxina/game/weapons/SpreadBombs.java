package net.intensicode.galaxina.game.weapons;

import net.intensicode.core.Configuration;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.Position;

public final class SpreadBombs extends SecondaryWeapon
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
        throw new RuntimeException( "nyi" );
        }

    // From SecondaryWeapon

    protected final void onInitializeSubclass()
        {
        if ( myBombSpread == null )
            {
            final Configuration config = GameObject.model.configuration;
            final int spreadCount = config.readInt( "Weapons.SpreadBombs.COUNT", 8 );
            myBombSpread = new Bomb[spreadCount];
            }
        remainingShots = SHOTS_PER_STOCK;
        }

    // Implementation

    private void onControl()
        {
        if ( !GameObject.system.keys.checkFire2AndConsume() ) return;
        if ( remainingShots <= 0 ) return;

        final boolean fired = onFire();
        onReload( fired );
        }

    private boolean onFire()
        {
        final GameModel model = GameObject.model;
        final Bombs bombs = model.bombs;
        final World world = model.world;

        final int bombSpeed = world.visibleSizeFixed.height / GameObject.timing.ticksPerSecond;
        final int xSpeedStart = -bombSpeed;
        final int xSpeedStep = bombSpeed * 2 / myBombSpread.length;

        final Position startPos = GameObject.model.player.worldPosFixed;

        for ( int idx = 0; idx < myBombSpread.length; idx++ )
            {
            final Bomb bomb = myBombSpread[ idx ] = bombs.getAvailableBomb();
            if ( bomb == null ) return false;

            final int yFactor = Math.abs( myBombSpread.length / 2 - idx );
            final int ySpeed = -bombSpeed + yFactor * yFactor * xSpeedStep / 8;
            bomb.init( startPos, xSpeedStart + xSpeedStep * idx, ySpeed );
            bomb.start( Bomb.FROM_PLAYER );
            }

        remainingShots--;
        if ( remainingShots == 0 ) deactivate();

        return true;
        }

    private void onReload( final boolean aFired )
        {
        for ( int idx = 0; idx < myBombSpread.length; idx++ )
            {
            final Bomb bomb = myBombSpread[ idx ];
            if ( bomb == null ) continue;
            bomb.active = aFired;
            myBombSpread[ idx ] = null;
            }

        if ( aFired )
            {
            myReloadTickCount = myReloadTicks;
            myState = STATE_RELOADING;
            }
        }


    private Bomb[] myBombSpread;

    private int myReloadTicks;

    private int myReloadTickCount;

    private int myState = STATE_IDLE;

    private static final int STATE_IDLE = 0;

    private static final int STATE_RELOADING = 1;
    }
