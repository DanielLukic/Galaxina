package net.intensicode.galaxina.game.weapons;

import net.intensicode.core.GameTiming;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;

public final class Missile extends WorldObjectWithSize
    {
    public final PositionF direction = new PositionF();

    public static GameTiming timing;

    public float speed;


    public boolean playerOwned;

    public boolean homing;

    public boolean visible;


    public final void init( final PositionF aPosition )
        {
        worldPos.setTo( aPosition );

        direction.x = direction.y = 0;
        myState = STATE_PREPARED;
        myDebrisCount = 0;
        speed = 0;

        final float worldHeight = GameObject.model.world.visibleSize.height;
        myMaxSpeed = 75 * worldHeight / 100 / timing.ticksPerSecond;
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
        myTempPosition.setTo( direction );
        myTempPosition.scale( speed );
        worldPos.translate( myTempPosition );

        if ( !GameObject.model.world.isInside( worldPos ) )
            {
            active = false;
            return;
            }

        updateBoundingBox();
        boundingBox.applyOutsets( sizeInWorld.width / 3 );

        if ( myStateTicks < myRandom.nextInt( timing.ticksPerSecond / 4 ) )
            {
            myStateTicks++;
            }
        else
            {
            myStateTicks = 0;
            myTempPosition.setTo( direction );
            myTempPosition.x = -myTempPosition.x;
            myTempPosition.y = -myTempPosition.y;
            if ( speed > 0 ) myTempPosition.scale( myRandom.nextFloat( speed ) );
            myTempPosition.translate( worldPos );
            GameObject.model.smokes.add( myTempPosition );
            }
        }

    private void onLaunched()
        {
        if ( speed < myMaxSpeed )
            {
            speed += myMaxSpeed / timing.ticksPerSecond;
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

            if ( enemy.isHit( worldPos ) )
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

            GameObject.model.explosions.addBig( worldPos );

            for ( int idx = 0; idx < DEBRIS_COUNT; idx++ )
                {
                final int sinIndex = idx * Sinus.SIN_TABLE_SIZE / DEBRIS_COUNT;
                addBomb( speed, sinIndex );
                }
            }

        final int quarterSecond = timing.ticksPerSecond / 4;
        if ( myExplodeTicks <= quarterSecond )
            {
            final int debrisSoFar = myExplodeTicks * DEBRIS_COUNT / quarterSecond;
            while ( myDebrisCount < debrisSoFar )
                {
                final float debrisSpeed = myRandom.nextFloat( speed / 4 ) + speed / 4;
                final int sinIndex = myRandom.nextInt( Sinus.SIN_TABLE_SIZE );
                addDebris( debrisSpeed / 5, sinIndex );
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
            myTempPosition.setTo( worldPos );
            final float refSize = GameObject.model.player.sizeInWorld.width;
            myTempPosition.x += myRandom.nextFloat( refSize ) - refSize / 2;
            myTempPosition.y += myRandom.nextFloat( refSize ) - refSize / 2;
            GameObject.model.explosions.addNormal( myTempPosition );
            }
        }

    private void addDebris( final float speed, final int sinIndex )
        {
        final int dx = (int) ( Sinus.instance().sin( sinIndex, (int) (speed * 256) ) / 256f);
        final int dy = (int) ( Sinus.instance().cos( sinIndex, (int) (speed *256) ) / 256f);

        final Debris debris = GameObject.model.debrises.getInstance();
        debris.init( worldPos, dx, dy );
        debris.type = Debris.TYPE_SMALL;
        debris.timeOut = timing.ticksPerSecond * 2;
        }

    private void addBomb( final float speed, final int sinIndex )
        {
        final int dx = (int) ( Sinus.instance().sin( sinIndex, (int) (speed * 256) ) / 256f);
        final int dy = (int) ( Sinus.instance().cos( sinIndex, (int) (speed *256) ) / 256f);

        final Bomb bomb = GameObject.model.bombs.getAvailableBomb();
        bomb.init( worldPos, dx, dy );
        bomb.start( Bomb.FROM_MISSILE );
        }


    private int myStateTicks;

    private int myDebrisCount;

    private int myExplodeTicks;

    private float myMaxSpeed;

    private int myState = STATE_PREPARED;

    private final Random myRandom = Random.INSTANCE;

    private final PositionF myTempPosition = new PositionF();


    private static final int STATE_PREPARED = 0;

    private static final int STATE_LAUNCHED = 1;

    private static final int STATE_HOMING = 2;

    private static final int STATE_WAITING = 3;

    private static final int STATE_EXPLODING = 4;

    private static final int DEBRIS_COUNT = 8;
    }
