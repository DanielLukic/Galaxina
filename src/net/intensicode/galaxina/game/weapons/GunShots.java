package net.intensicode.galaxina.game.weapons;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.GameObject;

public final class GunShots extends GameObject
    {
    public final boolean isProjectileApproaching( final RectangleF aRectangle )
        {
        for ( int idx = 0; idx < gunShots.length; idx++ )
            {
            final GunShot shot = gunShots[ idx ];
            if ( shot == null || !shot.active ) continue;
            if ( shot.worldPos.x < aRectangle.x ) continue;
            if ( shot.worldPos.x > aRectangle.x + aRectangle.width ) continue;
            if ( shot.worldPos.y < aRectangle.y ) continue;
            return true;
            }
        return false;
        }

    public GunShot[] gunShots;



    public final GunShot getAvailableGunShot()
        {
        for ( int idx = 0; idx < gunShots.length; idx++ )
            {
            final GunShot shot = gunShots[ idx ];
            if ( !shot.active ) return shot;
            }
        return null;
        }

    // From GameObject

    protected final void onInitOnce() throws Exception
        {
        final int maxGunShots = model.configuration.readInt( "Weapons.GunShots.MAX", MAX_GUN_SHOTS );
        gunShots = new GunShot[maxGunShots];
        for ( int idx = 0; idx < gunShots.length; idx++ ) gunShots[ idx ] = new GunShot();
        }

    public final void onStartGame()
        {
        }

    public final void onStartLevel()
        {
        for ( int idx = 0; idx < gunShots.length; idx++ )
            {
            gunShots[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < gunShots.length; idx++ )
            {
            final GunShot shot = gunShots[ idx ];
            if ( !shot.active ) continue;
            shot.onControlTick( model );
            }
        }



    private static final int MAX_GUN_SHOTS = 64;
    }
