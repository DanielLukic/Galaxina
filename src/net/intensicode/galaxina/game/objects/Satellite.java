package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.*;

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

        worldPosFixed.setTo( myOwningPlayer.worldPosFixed );
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

        final int sinTableSizeFixed = FixedMath.toFixed( Sinus.SIN_TABLE_SIZE );
        if ( myPreviousSatellite == null )
            {
            tickRotation();
            }
        else
            {
            final int rotationAngleOffsetInFixedSinusSize = sinTableSizeFixed / myNumberOfSatellites;
            myRotationAngleInFixedSinusSize = myPreviousSatellite.myRotationAngleInFixedSinusSize + rotationAngleOffsetInFixedSinusSize;
            }

        final int rotationSinusIndex = FixedMath.toInt( myRotationAngleInFixedSinusSize );
        final int width = FixedMath.toInt( myOwningPlayer.sizeInWorldFixed.width );
        final int height = FixedMath.toInt( myOwningPlayer.sizeInWorldFixed.height );
        final int xOffset = theSinus.cos( rotationSinusIndex, width );
        final int yOffset = theSinus.sin( rotationSinusIndex, height );

        worldPosFixed.setTo( myOwningPlayer.worldPosFixed );
        worldPosFixed.x += FixedMath.toFixed( xOffset );
        worldPosFixed.y += FixedMath.toFixed( yOffset );

        updateBoundingBox();

        while ( myRotationAngleInFixedSinusSize >= sinTableSizeFixed )
            {
            myRotationAngleInFixedSinusSize -= sinTableSizeFixed;
            }

        if ( Math.abs( yOffset ) == 0 && !myHaveFiredFlag )
            {
            final int gunShotSpeed = GameObject.model.level.getGunShotSpeed();
            final GunShot shot = GameObject.model.gunShots.getAvailableGunShot();
            if ( shot != null )
                {
                shot.init( worldPosFixed, gunShotSpeed );
                myHaveFiredFlag = true;
                }
            }
        else myHaveFiredFlag = false;
        }

    private boolean myHaveFiredFlag;

    private void tickRotation()
        {
        myRotationAngleInFixedSinusSize += getRotationDeltaInFixedSinusSize();
        }

    private int getRotationDeltaInFixedSinusSize()
        {
        return FixedMath.toFixed( Sinus.SIN_TABLE_SIZE ) / GameObject.timing.ticksPerSecond / 2;
        }

    public final void explode()
        {
        GameObject.model.explosions.addNormal( worldPosFixed );
        myOwningPlayer.removeSatellite( this );
        active = false;
        }

    public final boolean isCrash( final Rectangle aRectangleFixed )
        {
        return boundingBox.intersectsWith( aRectangleFixed );
        }


    private int myIndex;

    private Player myOwningPlayer;

    private int myNumberOfSatellites;

    private Satellite myPreviousSatellite;

    private int myRotationAngleInFixedSinusSize;

    private static Sinus theSinus = Sinus.instance();
    }
