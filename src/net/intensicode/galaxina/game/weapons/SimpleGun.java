package net.intensicode.galaxina.game.weapons;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.util.*;

public final class SimpleGun extends Weapon
    {
    public static int getNumberOfSlots()
        {
        return 1 + GameObject.model.player.weaponUpgrades;
        }

    public static int getNumberOfBullets()
        {
        return 1 + GameObject.model.player.bulletUpgrades;
        }

    public static int getReloadTicks()
        {
        return GameObject.timing.ticksPerSecond / ( 3 + GameObject.model.player.reloadUpgrades );
        }

    public final boolean canFire()
        {
        return myTrackedShots.size < getNumberOfBullets();
        }

    // From Weapon

    public final void onInitialize()
        {
        final Configuration config = GameObject.model.configuration;
        final int distanceInPixels = config.readInt( "Weapons.GunShots.DISTANCE", 2 );
        myShotDistance = ( distanceInPixels );
        }

    public final void onStartLevel()
        {
        myState = STATE_IDLE;
        myReloadTickCount = 0;
        myReloadTicks = getReloadTicks();
        myTrackedShots.clear();
        for ( int idx = 0; idx < mySlots.length; idx++ ) mySlots[ idx ] = null;
        }

    public final void onControlTick( final boolean aAllowControl )
        {
        switch ( myState )
            {
            case STATE_IDLE:
                if ( aAllowControl && canFire() ) onControl();
                break;

            case STATE_RELOADING:
                if ( myReloadTickCount > 0 ) myReloadTickCount--;
                else myState = STATE_IDLE;
                break;
            }
        }

    public final void onProjectileDone( final Object aObject )
        {
        myTrackedShots.remove( aObject );
        }

    // Implementation

    private void onControl()
        {
        final KeysHandler keys = GameObject.system.keys;

        boolean fire = keys.checkFire1AndConsume() || keys.checkStickDownAndConsume();
        fire = fire || keys.checkFire3AndConsume() || keys.checkFire4AndConsume();
        if ( !fire ) return;

        final boolean fired = onFire();
        if ( fired ) onReload( fired );
        }

    private boolean onFire()
        {
        //#if DEBUG
        if ( myShotDistance == 0 ) throw new IllegalStateException();
        //#endif

        final int numberOfSlots = getNumberOfSlots();

        final float spreadWidth = myShotDistance * ( numberOfSlots - 1 );
        final float xOffset = -spreadWidth / 2;

        final float gunShotSpeed = GameObject.model.level.getGunShotSpeed();

        final PositionF startPos = GameObject.model.player.worldPos;

        for ( int idx = 0; idx < numberOfSlots; idx++ )
            {
            final GunShot shot = mySlots[ idx ] = GameObject.model.gunShots.getAvailableGunShot();
            if ( shot == null ) return false;

            shot.init( startPos, gunShotSpeed );
            shot.worldPos.x += xOffset + myShotDistance * idx;
            }

        return true;
        }

    private void onReload( final boolean aFiredFlag )
        {
        final int numberOfSlots = getNumberOfSlots();
        for ( int idx = 0; idx < numberOfSlots; idx++ )
            {
            final GunShot gunShot = mySlots[ idx ];
            if ( gunShot == null ) continue;

            gunShot.active = aFiredFlag;
            mySlots[ idx ] = null;

            if ( aFiredFlag ) track( gunShot );
            }

        if ( aFiredFlag )
            {
            myReloadTicks = getReloadTicks();
            myReloadTickCount = myReloadTicks;
            myState = STATE_RELOADING;
            }
        }

    protected final void track( final GunShot aGunShot )
        {
        myTrackedShots.add( aGunShot );
        aGunShot.source = this;
        }


    private int myReloadTicks;

    private int myReloadTickCount;

    private float myShotDistance;

    private int myState = STATE_IDLE;

    private final DynamicArray myTrackedShots = new DynamicArray();

    private final GunShot[] mySlots = new GunShot[Weapons.MAX_BULLET_UPGRADES + 1];


    private static final int STATE_IDLE = 0;

    private static final int STATE_RELOADING = 1;
    }
