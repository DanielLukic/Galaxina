package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.weapons.GunShot;

public final class Satellite extends WorldObjectWithSize
    {
    public final void init( final Player aOwningPlayer, final int aIndex, final int aCount, final Satellite aPrevious )
        {
        myOwningPlayer = aOwningPlayer;
        myIndex = aIndex;
        myNumberOfSatellites = aCount;
        myPreviousSatellite = aPrevious;

        active = true;

        repeatAnimation = true;
        tickCount = 0;
        animTicks = GameObject.timing.ticksPerSecond / 2;

        worldPos.setTo( myOwningPlayer.worldPos );
        }

    public final void onStartGame() throws Exception
        {
        active = false;
        }

    public final void onStartLevel() throws Exception
        {
        }

    public final void onControlTick()
        {
        if ( !active ) return;

        tickAnimation();

        final float sinTableSize = Sinus.SIN_TABLE_SIZE;
        if ( myPreviousSatellite == null )
            {
            tickRotation();
            }
        else
            {
            final float rotationAngleOffsetInSinusSize = sinTableSize / myNumberOfSatellites;
            myRotationAngleInSinusSize = myPreviousSatellite.myRotationAngleInSinusSize + rotationAngleOffsetInSinusSize;
            }

        final int rotationSinusIndex = (int) myRotationAngleInSinusSize;
        final int width = (int) myOwningPlayer.sizeInWorld.width;
        final int height = (int) myOwningPlayer.sizeInWorld.height;
        final int xOffset = theSinus.cos( rotationSinusIndex, width );
        final int yOffset = theSinus.sin( rotationSinusIndex, height );

        worldPos.setTo( myOwningPlayer.worldPos );
        worldPos.x += xOffset;
        worldPos.y += yOffset;

        updateBoundingBox();

        while ( myRotationAngleInSinusSize >= sinTableSize )
            {
            myRotationAngleInSinusSize -= sinTableSize;
            }

        if ( Math.abs( yOffset ) == 0 && !myHaveFiredFlag )
            {
            final float gunShotSpeed = GameObject.model.level.getGunShotSpeed();
            final GunShot shot = GameObject.model.gunShots.getAvailableGunShot();
            if ( shot != null )
                {
                shot.init( worldPos, gunShotSpeed );
                myHaveFiredFlag = true;
                }
            }
        else myHaveFiredFlag = false;
        }

    private boolean myHaveFiredFlag;

    private void tickRotation()
        {
        myRotationAngleInSinusSize += getRotationDeltaInSinusSize();
        }

    private int getRotationDeltaInSinusSize()
        {
        return Sinus.SIN_TABLE_SIZE / GameObject.timing.ticksPerSecond / 2;
        }

    public final void explode()
        {
        GameObject.model.explosions.addNormal( worldPos );
        myOwningPlayer.removeSatellite( this );
        active = false;
        }

    public final boolean isCrash( final RectangleF aRectangle )
        {
        return boundingBox.intersectsWith( aRectangle );
        }


    private int myIndex;

    private Player myOwningPlayer;

    private int myNumberOfSatellites;

    private Satellite myPreviousSatellite;

    private float myRotationAngleInSinusSize;

    private static Sinus theSinus = Sinus.instance();
    }
