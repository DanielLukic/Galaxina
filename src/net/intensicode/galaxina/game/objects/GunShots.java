package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Rectangle;
import net.intensicode.galaxina.game.GameObject;

/**
 * TODO: Describe this!
 */
public final class GunShots extends GameObject
    {
    public final boolean isProjectileApproaching( final Rectangle aRectangle )
        {
        for ( int idx = 0; idx < gunShots.length; idx++ )
            {
            final GunShot shot = gunShots[ idx ];
            if ( shot == null || !shot.active ) continue;
            if ( shot.worldPosFixed.x < aRectangle.x ) continue;
            if ( shot.worldPosFixed.x > aRectangle.x + aRectangle.width ) continue;
            if ( shot.worldPosFixed.y < aRectangle.y ) continue;
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
