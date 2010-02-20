package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;

public final class Satellite extends SimpleObject
    {
    public final void init( final Player aOwningPlayer, final int aIndex, final int aCount, final Satellite aPrevious )
        {
        myOwningPlayer = aOwningPlayer;
        myIndex = aIndex;
        myNumberOfSatellites = aCount;
        myPreviousSatellite = aPrevious;
        active = true;

        animTicks = GameObject.timing.ticksPerSecond;
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

        if ( myPreviousSatellite == null )
            {
            tickRotation();
            }
        else
            {
            final int rotationAngleOffsetInFixedSinusSize = FixedMath.toFixed( Sinus.SIN_TABLE_SIZE ) / myNumberOfSatellites;
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

        updateBBoxes();
        }

    private void tickRotation()
        {
        final int rotationDeltaInFixedSinusSize = FixedMath.toFixed( Sinus.SIN_TABLE_SIZE ) / GameObject.timing.ticksPerSecond / 2;
        myRotationAngleInFixedSinusSize += rotationDeltaInFixedSinusSize;
        }

    public final void explode()
        {
        GameObject.model.explosions.addNormal( worldPosFixed );
        myOwningPlayer.removeSatellite( this );
        active = false;
        }

    public final boolean isCrash( final Rectangle aRectangleFixed )
        {
        return innerBBox.intersectsWith( aRectangleFixed );
        }


    private int myIndex;

    private Player myOwningPlayer;

    private int myNumberOfSatellites;

    private Satellite myPreviousSatellite;

    private int myRotationAngleInFixedSinusSize;

    private static Sinus theSinus = Sinus.instance();
    }
