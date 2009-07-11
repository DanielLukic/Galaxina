package net.intensicode.game.weapons;

import net.intensicode.core.Configuration;
import net.intensicode.core.Engine;
import net.intensicode.game.objects.*;
import net.intensicode.util.Position;


/**
 * TODO: Describe this!
 */
public final class SpreadBombs extends Weapon
    {
    public final boolean isSecondary()
        {
        return true;
        }

    public final void onInitialize()
        {
        final Configuration config = GameObject.model.configuration;
        final int spreadCount = config.readInt( "Weapons.SpreadBombs.COUNT", 8 );
        myBombSpread = new Bomb[spreadCount];
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
        throw new RuntimeException( "nyi" );
        }

    // Implementation

    private void onControl( final Engine aEngine )
        {
        if ( !aEngine.keys.checkFire2AndConsume() ) return;

        final boolean fired = onFire();
        onReload( fired );
        }

    private boolean onFire()
        {
        final GameModel model = GameObject.model;
        final Bombs bombs = model.bombs;
        final World world = model.world;

        final int bombSpeed = world.visibleSizeFixed.height / Engine.ticksPerSecond;
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

    protected int myReloadTicks;

    protected int myReloadTickCount;

    protected int myState = STATE_IDLE;

    protected static final int STATE_IDLE = 0;

    protected static final int STATE_RELOADING = 1;
    }
