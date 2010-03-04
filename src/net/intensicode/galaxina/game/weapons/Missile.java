package net.intensicode.galaxina.game.weapons;

import net.intensicode.core.GameTiming;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;

public final class Missile extends WorldObjectWithSize
    {
    public final Position directionFixed = new Position();

    public static GameTiming timing;

    public int speedFixed;


    public boolean playerOwned;

    public boolean homing;

    public boolean visible;


    public final void init( final Position aPosition )
        {
        worldPosFixed.setTo( aPosition );

        directionFixed.x = directionFixed.y = 0;
        myState = STATE_PREPARED;
        myDebrisCount = 0;
        speedFixed = 0;

        final int worldHeightFixed = GameObject.model.world.visibleSizeFixed.height;
        myMaxSpeedFixed = 75 * worldHeightFixed / 100 / timing.ticksPerSecond;
        }

    public final void launch()
        {
        active = visible = true;
        myState = STATE_LAUNCHED;
        }

    public final void explode()
        {
        myState = STATE_EXPLODING;
        myExplodeTicks = 0;
        visible = false;
        }

    public final void onControlTick()
        {
        switch ( myState )
            {
            case STATE_LAUNCHED:
                onLaunched();
                onSearching();
                moveAhead();
                break;
            case STATE_HOMING:
                onHoming();
                onSearching();
                moveAhead();
                break;
            case STATE_WAITING:
                onSearching();
                moveAhead();
                break;
            case STATE_EXPLODING:
                onExploding();
                break;
            default:
                //#if DEBUG
                throw new IllegalStateException();
                //#endif
            }
        }

    // Implementation

    private void moveAhead()
        {
        myTempPosition.setTo( directionFixed );
        myTempPosition.scaleFixed( speedFixed );
        worldPosFixed.translate( myTempPosition );

        if ( !GameObject.model.world.isInside( worldPosFixed ) )
            {
            active = false;
            return;
            }

        updateBoundingBox();
        boundingBox.applyOutsets( sizeInWorldFixed.width / 3 );

        if ( myStateTicks < myRandom.nextInt( timing.ticksPerSecond / 4 ) )
            {
            myStateTicks++;
            }
        else
            {
            myStateTicks = 0;
            myTempPosition.setTo( directionFixed );
            myTempPosition.x = -myTempPosition.x;
            myTempPosition.y = -myTempPosition.y;
            if ( speedFixed > 0 ) myTempPosition.scaleFixed( myRandom.nextInt( speedFixed ) );
            myTempPosition.translate( worldPosFixed );
            GameObject.model.smokes.add( myTempPosition );
            }
        }

    private void onLaunched()
        {
        if ( speedFixed < myMaxSpeedFixed )
            {
            speedFixed += myMaxSpeedFixed / timing.ticksPerSecond;
            }
        else
            {
            myState = homing ? STATE_HOMING : STATE_WAITING;
            }
        }

    private void onHoming()
        {
        onSearching();
        }

    private void onSearching()
        {
        if ( playerOwned ) onPlayerOwned();
        else throw new RuntimeException( "nyi" );
        }

    private void onPlayerOwned()
        {
        final DynamicArray enemies = GameObject.model.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( !enemy.active ) continue;

            if ( enemy.isHit( worldPosFixed ) )
                {
                enemy.hit();
                explode();
                }

            if ( myState == STATE_LAUNCHED ) continue;
            if ( enemy.boundingBox.intersectsWith( boundingBox ) ) explode();
            }
        }

    private void onExploding()
        {
        if ( myExplodeTicks == 0 )
            {
            myStateTicks = 0;

            GameObject.model.explosions.addBig( worldPosFixed );

            for ( int idx = 0; idx < DEBRIS_COUNT; idx++ )
                {
                final int sinIndex = idx * Sinus.SIN_TABLE_SIZE / DEBRIS_COUNT;
                addBomb( speedFixed, sinIndex );
                }
            }

        final int quarterSecond = timing.ticksPerSecond / 4;
        if ( myExplodeTicks <= quarterSecond )
            {
            final int debrisSoFar = myExplodeTicks * DEBRIS_COUNT / quarterSecond;
            while ( myDebrisCount < debrisSoFar )
                {
                final int speed = myRandom.nextInt( speedFixed / 4 ) + speedFixed / 4;
                final int sinIndex = myRandom.nextInt( Sinus.SIN_TABLE_SIZE );
                addDebris( speed / 5, sinIndex );
                myDebrisCount++;
                }
            }

        if ( myExplodeTicks < timing.ticksPerSecond )
            {
            myExplodeTicks++;
            }
        else
            {
            active = false;
            }

        if ( myStateTicks < quarterSecond )
            {
            myStateTicks++;
            }
        else
            {
            myStateTicks = 0;
            myTempPosition.setTo( worldPosFixed );
            final int refSize = GameObject.model.player.sizeInWorldFixed.width;
            myTempPosition.x += myRandom.nextInt( refSize ) - refSize / 2;
            myTempPosition.y += myRandom.nextInt( refSize ) - refSize / 2;
            GameObject.model.explosions.addNormal( myTempPosition );
            }
        }

    private void addDebris( final int speed, final int sinIndex )
        {
        final int dx = Sinus.instance().sin( sinIndex, speed );
        final int dy = Sinus.instance().cos( sinIndex, speed );

        final Debris debris = GameObject.model.debrises.getInstance();
        debris.init( worldPosFixed, dx, dy );
        debris.type = Debris.TYPE_SMALL;
        debris.timeOut = timing.ticksPerSecond * 2;
        }

    private void addBomb( final int speed, final int sinIndex )
        {
        final int dx = Sinus.instance().sin( sinIndex, speed );
        final int dy = Sinus.instance().cos( sinIndex, speed );

        final Bomb bomb = GameObject.model.bombs.getAvailableBomb();
        bomb.init( worldPosFixed, dx, dy );
        bomb.start( Bomb.FROM_MISSILE );
        }


    private int myStateTicks;

    private int myDebrisCount;

    private int myExplodeTicks;

    private int myMaxSpeedFixed;

    private int myState = STATE_PREPARED;

    private final Random myRandom = Random.INSTANCE;

    private final Position myTempPosition = new Position();


    private static final int STATE_PREPARED = 0;

    private static final int STATE_LAUNCHED = 1;

    private static final int STATE_HOMING = 2;

    private static final int STATE_WAITING = 3;

    private static final int STATE_EXPLODING = 4;

    private static final int DEBRIS_COUNT = 8;
    }
