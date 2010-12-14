package net.intensicode.galaxina.screens;

import net.intensicode.core.GameSystem;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;


public final class StarField extends ScreenBase
    {
    public float xMove;

    public float yMove;

    public float zMove;


    public StarField( final int aNumberOfStars, final CharGenerator aStarsGenerator )
        {
        final int intensitySteps = aStarsGenerator.charsPerRow * aStarsGenerator.charsPerColumn;
        //#if DEBUG
        Assert.isTrue( "stars generator should have at least one frame", intensitySteps > 0 );
        //#endif

        myStars = new CharData[intensitySteps];
        for ( int idx = 0; idx < myStars.length; idx++ )
            {
            myStars[ idx ] = aStarsGenerator.getCharData( idx );
            }

        myNumberOfStars = aNumberOfStars;
        myStarsX = new float[myNumberOfStars];
        myStarsY = new float[myNumberOfStars];
        myStarsZ = new float[myNumberOfStars];

        final Random random = new Random();
        for ( int idx = 0; idx < myNumberOfStars; idx++ )
            {
            myStarsX[ idx ] = random.nextInt() % UNIVERSE_HALF;
            myStarsY[ idx ] = random.nextInt() % UNIVERSE_HALF;
            myStarsZ[ idx ] = random.nextInt() % UNIVERSE_HALF;
            }
        }

    public final void changeMovement( final float aX, final float aY, final float aZ )
        {
        myState = CHANGE_MOVEMENT;
        myNewX = aX;
        myNewY = aY;
        myNewZ = aZ;

        final int ticksForChange = timing().ticksPerSecond * 2;
        myChangeX = ( myNewX - xMove ) / ticksForChange;
        myChangeY = ( myNewY - yMove ) / ticksForChange;
        myChangeZ = ( myNewZ - zMove ) / ticksForChange;
        if ( myChangeX == 0 && myNewX < xMove ) myChangeX = -1;
        if ( myChangeX == 0 && myNewX > xMove ) myChangeX = +1;
        if ( myChangeY == 0 && myNewY < yMove ) myChangeY = -1;
        if ( myChangeY == 0 && myNewY > yMove ) myChangeY = +1;
        if ( myChangeZ == 0 && myNewZ < zMove ) myChangeZ = -1;
        if ( myChangeZ == 0 && myNewZ > zMove ) myChangeZ = +1;
        }

    private float myNewX;

    private float myNewY;

    private float myNewZ;

    private float myChangeX;

    private float myChangeY;

    private float myChangeZ;

    private int myState = MOVING;

    private static final int MOVING = 0;

    private static final int CHANGE_MOVEMENT = 1;

    private static final int TUMBLING = 2;

    public final void startTumbling()
        {
        myState = TUMBLING;
        }

    // From ScreenBase

    public final void onInit( final GameSystem aGameSystem ) throws Exception
        {
        super.onInit( aGameSystem );

        myScreenWidth = screen().width();
        myScreenHeight = screen().height();
        myScreenCenterX = myScreenWidth / 2;
        myScreenCenterY = myScreenHeight / 2;

        myViewerX = myViewerY = myViewerZ = 0;
        }

    public final void onControlTick()
        {
        if ( myState == MOVING ) onMoving();
        else if ( myState == CHANGE_MOVEMENT ) onChangeMovement();
        else if ( myState == TUMBLING ) onTumbling();
        }

    private void onMoving()
        {
        for ( int idx = 0; idx < myNumberOfStars; idx++ )
            {
            myStarsX[ idx ] = move( myStarsX[ idx ], xMove );
            myStarsY[ idx ] = move( myStarsY[ idx ], yMove );
            myStarsZ[ idx ] = move( myStarsZ[ idx ], zMove );
            }
        }

    private void onChangeMovement()
        {
        xMove = update( xMove, myNewX, myChangeX );
        yMove = update( yMove, myNewY, myChangeY );
        zMove = update( zMove, myNewZ, myChangeZ );
        onMoving();
        if ( !closeEnough( xMove, myNewX, myChangeX ) ) return;
        if ( !closeEnough( yMove, myNewY, myChangeY ) ) return;
        if ( !closeEnough( zMove, myNewZ, myChangeZ ) ) return;
        xMove = myNewX;
        yMove = myNewY;
        zMove = myNewZ;
        myState = MOVING;
        }

    private boolean closeEnough( final float aCurrent, final float aTarget, final float aDelta )
        {
        if ( Math.abs( aTarget - aCurrent ) > Math.abs( aDelta ) ) return false;
        return true;
        }

    private float update( final float aCurrent, final float aTarget, final float aDelta )
        {
        if ( Math.abs( aTarget - aCurrent ) >= Math.abs( aDelta ) ) return aCurrent + aDelta;
        return aCurrent;
        }

    private void onTumbling()
        {
        final Random random = Random.INSTANCE;
        if ( random.nextInt( 32 ) > 30 )
            {
            final float maxSpeed = 10f / timing().ticksPerSecond;
            final float x = random.nextFloat( maxSpeed ) - maxSpeed / 2;
            final float y = random.nextFloat( maxSpeed ) - maxSpeed / 2;
            final float z = random.nextFloat( maxSpeed ) - maxSpeed / 2;
            changeMovement( x, y, z );
            }

        onChangeMovement();

        myState = TUMBLING;
        }

    private float move( final float aValue, final float aDelta )
        {
        final float changed = aValue + aDelta;
        if ( changed < UNIVERSE_SIZE ) return changed + UNIVERSE_SIZE;
        if ( changed > UNIVERSE_SIZE ) return changed - UNIVERSE_SIZE;
        return changed;
        }

    public final void onDrawFrame()
        {
        final int screenWidth = screen().width();
        final int screenHeight = screen().height();

        int maxInt = 0;
        int minInt = 0;

        final int maxIntensity = myStars.length - 1;
        for ( int idx = 0; idx < myNumberOfStars; idx++ )
            {
            myPosition3d[ 0 ] = myStarsX[ idx ] + myViewerX;
            myPosition3d[ 1 ] = myStarsY[ idx ] + myViewerY;
            myPosition3d[ 2 ] = myStarsZ[ idx ] + myViewerZ;

            final float[] xyz2d = doProjection( myPosition3d );

            final float x2d = xyz2d[ 0 ];
            final float y2d = xyz2d[ 1 ];
            if ( x2d < 0 || x2d >= screenWidth ) continue;
            if ( y2d < 0 || y2d >= screenHeight ) continue;

            final float zNew = xyz2d[ 2 ];
            final float starIntensity = ( zNew * maxIntensity ) / UNIVERSE_SIZE;
            final float shiftedIntensity = maxIntensity / 3 + starIntensity;
            final int intensity = (int) Math.max( 0, Math.min( maxIntensity, shiftedIntensity ) );
            myStars[ intensity ].blit( graphics(), (int) x2d, (int) y2d );

            maxInt = Math.max( maxInt, intensity );
            minInt = Math.min( minInt, intensity );
            }
        }

    // Implementation

    private float[] doProjection( final float[] aPosition3D )
        {
        final float xTemp = aPosition3D[ 0 ];
        final float yTemp = aPosition3D[ 1 ];
        final float zTemp = aPosition3D[ 2 ];

        float z = ( zTemp - UNIVERSE_HALF ) - VIEW_PLANE;
        if ( z == 0 ) z = 1;

        final float x = ( xTemp - UNIVERSE_HALF ) * myScreenWidth;
        final float y = ( yTemp - UNIVERSE_HALF ) * myScreenHeight;
        myPosition2dPlusNewZ[ 0 ] = x / z + myScreenCenterX;
        myPosition2dPlusNewZ[ 1 ] = y / z + myScreenCenterY;
        myPosition2dPlusNewZ[ 2 ] = zTemp;
        return myPosition2dPlusNewZ;
        }


    private int myViewerX;

    private int myViewerY;

    private int myViewerZ;

    private int myScreenWidth;

    private int myScreenHeight;

    private int myScreenCenterX;

    private int myScreenCenterY;

    private int myNumberOfStars;

    private final float[] myStarsX;

    private final float[] myStarsY;

    private final float[] myStarsZ;

    private final CharData[] myStars;

    private final float[] myPosition3d = new float[3];

    private final float[] myPosition2dPlusNewZ = new float[3];

    private static final int UNIVERSE_SHIFT = 4;

    private static final int UNIVERSE_SIZE = 1 << UNIVERSE_SHIFT;

    private static final int UNIVERSE_HALF = UNIVERSE_SIZE >> 1;

    private static final int VIEW_PLANE = UNIVERSE_SIZE * 4 / 5;
    }
