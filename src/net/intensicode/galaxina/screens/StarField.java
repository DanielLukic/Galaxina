package net.intensicode.galaxina.screens;

import net.intensicode.core.GameSystem;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;


public final class StarField extends ScreenBase
    {
    public int xMoveFixed;

    public int yMoveFixed;

    public int zMoveFixed;


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
        myStarsX = new int[myNumberOfStars];
        myStarsY = new int[myNumberOfStars];
        myStarsZ = new int[myNumberOfStars];

        final Random random = new Random();
        for ( int idx = 0; idx < myNumberOfStars; idx++ )
            {
            myStarsX[ idx ] = random.nextInt() % UNIVERSE_HALF;
            myStarsY[ idx ] = random.nextInt() % UNIVERSE_HALF;
            myStarsZ[ idx ] = random.nextInt() % UNIVERSE_HALF;
            }
        }

    public final void changeMovement( final int aFixedX, final int aFixedY, final int aFixedZ )
        {
        myState = CHANGE_MOVEMENT;
        myNewFixedX = aFixedX;
        myNewFixedY = aFixedY;
        myNewFixedZ = aFixedZ;

        final int ticksForChange = timing().ticksPerSecond * 2;
        myChangeFixedX = ( myNewFixedX - xMoveFixed ) / ticksForChange;
        myChangeFixedY = ( myNewFixedY - yMoveFixed ) / ticksForChange;
        myChangeFixedZ = ( myNewFixedZ - zMoveFixed ) / ticksForChange;
        if ( myChangeFixedX == 0 && myNewFixedX < xMoveFixed ) myChangeFixedX = -1;
        if ( myChangeFixedX == 0 && myNewFixedX > xMoveFixed ) myChangeFixedX = +1;
        if ( myChangeFixedY == 0 && myNewFixedY < yMoveFixed ) myChangeFixedY = -1;
        if ( myChangeFixedY == 0 && myNewFixedY > yMoveFixed ) myChangeFixedY = +1;
        if ( myChangeFixedZ == 0 && myNewFixedZ < zMoveFixed ) myChangeFixedZ = -1;
        if ( myChangeFixedZ == 0 && myNewFixedZ > zMoveFixed ) myChangeFixedZ = +1;
        }

    private int myNewFixedX;

    private int myNewFixedY;

    private int myNewFixedZ;

    private int myChangeFixedX;

    private int myChangeFixedY;

    private int myChangeFixedZ;

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
            myStarsX[ idx ] = move( myStarsX[ idx ], xMoveFixed );
            myStarsY[ idx ] = move( myStarsY[ idx ], yMoveFixed );
            myStarsZ[ idx ] = move( myStarsZ[ idx ], zMoveFixed );
            }
        }

    private void onChangeMovement()
        {
        xMoveFixed = update( xMoveFixed, myNewFixedX, myChangeFixedX );
        yMoveFixed = update( yMoveFixed, myNewFixedY, myChangeFixedY );
        zMoveFixed = update( zMoveFixed, myNewFixedZ, myChangeFixedZ );
        onMoving();
        if ( !closeEnough( xMoveFixed, myNewFixedX, myChangeFixedX ) ) return;
        if ( !closeEnough( yMoveFixed, myNewFixedY, myChangeFixedY ) ) return;
        if ( !closeEnough( zMoveFixed, myNewFixedZ, myChangeFixedZ ) ) return;
        xMoveFixed = myNewFixedX;
        yMoveFixed = myNewFixedY;
        zMoveFixed = myNewFixedZ;
        myState = MOVING;
        }

    private boolean closeEnough( final int aCurrent, final int aTarget, final int aDelta )
        {
        if ( Math.abs( aTarget - aCurrent ) > Math.abs( aDelta ) ) return false;
        return true;
        }

    private int update( final int aCurrent, final int aTarget, final int aDelta )
        {
        if ( Math.abs( aTarget - aCurrent ) >= Math.abs( aDelta ) ) return aCurrent + aDelta;
        return aCurrent;
        }

    private void onTumbling()
        {
        final Random random = Random.INSTANCE;
        if ( random.nextInt( 32 ) > 30 )
            {
            final int maxSpeedFixed = FixedMath.FIXED_10 / timing().ticksPerSecond;
            final int x = random.nextInt( maxSpeedFixed ) - maxSpeedFixed / 2;
            final int y = random.nextInt( maxSpeedFixed ) - maxSpeedFixed / 2;
            final int z = random.nextInt( maxSpeedFixed ) - maxSpeedFixed / 2;
            changeMovement( x, y, z );
            }

        onChangeMovement();

        myState = TUMBLING;
        }

    private int move( final int aValueFixed, final int aDeltaFixed )
        {
        final int newFixed = aValueFixed + aDeltaFixed;
        return newFixed & ( UNIVERSE_MASK );
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

            final int[] xyz2d = doProjection( myPosition3d );

            final int x2d = xyz2d[ 0 ];
            final int y2d = xyz2d[ 1 ];
            if ( x2d < 0 || x2d >= screenWidth ) continue;
            if ( y2d < 0 || y2d >= screenHeight ) continue;

            final int zNew = xyz2d[ 2 ];
            final int starIntensity = ( zNew * maxIntensity ) >> UNIVERSE_SHIFT;
            final int shiftedIntensity = maxIntensity / 3 + starIntensity;
            final int intensity = Math.max( 0, Math.min( maxIntensity, shiftedIntensity ) );
            myStars[ intensity ].blit( graphics(), x2d, y2d );

            maxInt = Math.max( maxInt, intensity );
            minInt = Math.min( minInt, intensity );
            }
        }

    // Implementation

    private int[] doProjection( final int[] aPosition3D )
        {
        final int xTemp = aPosition3D[ 0 ] & UNIVERSE_MASK;
        final int yTemp = aPosition3D[ 1 ] & UNIVERSE_MASK;
        final int zTemp = aPosition3D[ 2 ] & UNIVERSE_MASK;

        int z = ( zTemp - UNIVERSE_HALF ) - VIEW_PLANE;
        if ( z == 0 ) z = 1;

        final int x = ( xTemp - UNIVERSE_HALF ) * myScreenWidth;
        final int y = ( yTemp - UNIVERSE_HALF ) * myScreenHeight;
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

    private final int[] myStarsX;

    private final int[] myStarsY;

    private final int[] myStarsZ;

    private final CharData[] myStars;

    private final int[] myPosition3d = new int[3];

    private final int[] myPosition2dPlusNewZ = new int[3];

    private static final int UNIVERSE_SHIFT = FixedMath.FIXED_SHIFT + 4;

    private static final int UNIVERSE_SIZE = 1 << UNIVERSE_SHIFT;

    private static final int UNIVERSE_HALF = UNIVERSE_SIZE >> 1;

    private static final int UNIVERSE_MASK = UNIVERSE_SIZE - 1;

    private static final int VIEW_PLANE = UNIVERSE_SIZE * 4 / 5;
    }
