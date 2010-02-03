package net.intensicode.game.enemies;

import net.intensicode.game.enemies.controllers.*;
import net.intensicode.game.extras.ExtraType;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.GameObject;
import net.intensicode.game.objects.Player;
import net.intensicode.path.PathWithDirection;
import net.intensicode.util.UtilitiesEx;
import net.intensicode.util.*;
import net.intensicode.core.GameTiming;

public final class Enemy
    {
    public static final EnemyController ENTERING = new EnteringController();

    public static final EnemyController POSITIONING = new PositioningController();

    public static final EnemyController VANISHED = new VanishedController();

    public static final EnemyController BREATHING = new BreathingController();

    public static final EnemyController WAITING = new WaitingController();

    public static final EnemyController FOLLOW_IN_SYNC = new FollowInSyncController();

    public static final EnemyController FOLLOW_BY_SIDE = new FollowBySideController();

    public static final EnemyController ATTACKING = new AttackingController();

    public static final EnemyController RETURNING = new ReturningController();


    public static final int SYNC_NONE = 0;

    public static final int SYNC_SPEED = 1;

    public static final int SYNC_PATH = 2;

    public static final int SYNC_LEFT = 3;

    public static final int SYNC_RIGHT = 4;


    public static GameTiming timing;

    public static GameModel model;


    public final Position worldPosFixed = new Position();

    public final Rectangle bbox = new Rectangle();

    public Position formationPositionRelative;


    public int pathPos;

    public PathWithDirection path;

    public Enemy syncSource;

    public int syncSpeed;

    public int syncMode;

    
    public EnemyController controller;

    public EnemyWeapon weapon;

    public EnemyType type;

    public int hitsRemaining;


    public int directionInDegreesFixed;

    public int targetDirectionFixed;


    public boolean deployExtraIfDestroyed;

    public boolean inSyncWithBreath;

    public boolean active;



    public Enemy( final Position aLastHitPosition )
        {
        myLastHitPosition = aLastHitPosition;
        }

    public static int getDirectionDelta( final int aTo, final int aFrom )
        {
        final int delta = aTo - aFrom;
        if ( delta > FixedMath.FIXED_180 ) return delta - FixedMath.FIXED_360;
        if ( delta < -FixedMath.FIXED_180 ) return FixedMath.FIXED_360 + delta;
        return delta;
        }

    public final void init( final EnemyType aEnemyType, final PathWithDirection aIncomingPath, final Position aFormationPosition )
        {
        type = aEnemyType;
        formationPositionRelative = aFormationPosition;
        weapon = type.createWeapon( worldPosFixed );
        hitsRemaining = type.hits;

        unsync();
        setPath( aIncomingPath );
        setController( ENTERING );

        jumpToStartOfPath();

        active = true;
        }

    public final void unsync()
        {
        syncSpeed = 0;
        syncSource = null;
        syncMode = SYNC_NONE;
        }

    public final void setPath( final PathWithDirection aNewPath )
        {
        path = aNewPath;
        pathPos = 0;
        }

    public final void setController( final EnemyController aController )
        {
        controller = aController;
        controller.onInitialize( this );
        }

    public final void startAttack( final EnemyPath aPath )
        {
        setPath( aPath );
        setController( ATTACKING );
        }

    public final int getScore()
        {
        return type.getScore( GameObject.model );
        }

    public final int getSpeedFixed()
        {
        return syncSpeed != 0 ? syncSpeed : type.getSpeedFixed( model );
        }

    public final Position getBreathPos( final boolean aInSyncFlag )
        {
        return model.breather.getBreathPos( formationPositionRelative, aInSyncFlag );
        }

    public final void syncSpeedWith( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_SPEED;
        syncSpeed = aEnemy.getSpeedFixed();
        }

    public final void syncPathWith( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_PATH;
        syncSpeed = aEnemy.getSpeedFixed();
        setController( FOLLOW_IN_SYNC );
        }

    public final void followAtLeftSideOf( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_LEFT;
        syncSpeed = aEnemy.getSpeedFixed();
        setController( FOLLOW_BY_SIDE );
        }

    public final void followAtRightSideOf( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_RIGHT;
        syncSpeed = aEnemy.getSpeedFixed();
        setController( FOLLOW_BY_SIDE );
        }

    public final void updateToBreathPosition()
        {
        worldPosFixed.setTo( getBreathPos( inSyncWithBreath ) );
        targetDirectionFixed = 0;
        }

    public final void jumpToStartOfPath()
        {
        final Position startPosition = path.getStartPosition();
        final int direction = UtilitiesEx.directionToDegrees( path.getStartPosition() );
        final int startDirection = FixedMath.toFixed( direction );
        worldPosFixed.setTo( startPosition );
        directionInDegreesFixed = targetDirectionFixed = startDirection;
        }

    public final void moveByDirection( final Position aOffset )
        {
        aOffset.normalizeFixed();
        aOffset.scaleFixed( getSpeedFixed() );
        worldPosFixed.translate( aOffset );

        final int degrees = UtilitiesEx.directionToDegrees( aOffset );
        targetDirectionFixed = FixedMath.toFixed( degrees );
        }

    public final boolean moveAlongPath()
        {
        pathPos += getSpeedFixed();
        updateToPathPosition();
        return pathPos >= path.getPathLength();
        }

    public final void syncPathTo( final Enemy aSource )
        {
        final int pathPosInPercent = aSource.pathPos * 100 / aSource.path.getPathLength();
        pathPos = pathPosInPercent * path.getPathLength() / 100;
        updateToPathPosition();
        }

    public final void copyFrom( final Enemy aSource )
        {
        final int pathPosInPercent = aSource.pathPos * 100 / aSource.path.getPathLength();
        pathPos = pathPosInPercent * path.getPathLength() / 100;
        worldPosFixed.setTo( aSource.worldPosFixed );
        directionInDegreesFixed = targetDirectionFixed = aSource.directionInDegreesFixed;
        }

    public final boolean isCloseTo( final Position aPositionFixed )
        {
        return ( worldPosFixed.distanceToFixed( aPositionFixed ) <= FixedMath.FIXED_1 );
        }

    public final boolean isReady()
        {
        return controller.isReadyForAction( this );
        }

    public final boolean isHit( final Position aWorldPosFixed )
        {
        return bbox.contains( aWorldPosFixed );
        }

    public final void hit()
        {
        //#if DEBUG
        if ( hitsRemaining == 0 ) throw new IllegalStateException();
        //#endif

        hitsRemaining--;
        if ( hitsRemaining > 0 ) return;

        myLastHitPosition.setTo( worldPosFixed );
        model.player.score += type.getScore( model );
        explode();
        }

    public final void explode()
        {
        if ( type.hits == 1 ) model.explosions.addNormal( worldPosFixed );
        else model.explosions.addBig( worldPosFixed );
        active = false;

        if ( deployExtraIfDestroyed )
            {
            final ExtraType extraType = model.extraTypes.all[ type.extraID ];
            model.extras.deploy( worldPosFixed, extraType );
            }
        }

    public void onEndOfPath()
        {
        unsync();
        setPath( null );

        if ( formationPositionRelative == null )
            {
            // Challenging stage or vanish after entering..
            setController( Enemy.VANISHED );
            }
        else
            {
            // Move into formation..
            setController( Enemy.POSITIONING );
            }
        }

    public final void onControlTick()
        {
        controller.onControlTick( this );

        if ( worldPosFixed.y > 0 )
            {
            final Player player = model.player;
            if ( player.isCrash( bbox ) )
                {
                player.explode();
                this.explode();
                }
            }

        if ( !active ) return;

        if ( hitsRemaining < type.hits )
            {
            final int probability = timing.ticksPerSecond * ( type.hits - hitsRemaining ) / type.hits;
            if ( theRandom.nextInt( timing.ticksPerSecond ) < probability / 3 )
                {
                model.sparks.add( worldPosFixed, type.sizeInWorldFixed );
                }
            }

        final int width = type.sizeInWorldFixed.width * 80 / 100;
        final int height = type.sizeInWorldFixed.height * 80 / 100;
        bbox.setCenterAndSize( worldPosFixed, width, height );

        weapon.onControlTick();

        onTickRotation();
        }

    // Implementation

    private void updateToPathPosition()
        {
        worldPosFixed.setTo( path.getPosition( pathPos ) );

        myBeforeDirection.setTo( path.getDirection( pathPos - getSpeedFixed() * LOOK_BEHIND_STEPS ) );
        myAfterDirection.setTo( path.getDirection( pathPos + getSpeedFixed() * LOOK_AHEAD_STEPS ) );

        myTempPos.x = myBeforeDirection.x + myAfterDirection.x;
        myTempPos.y = myBeforeDirection.y + myAfterDirection.y;

        final int degrees = UtilitiesEx.directionToDegrees( myTempPos );
        targetDirectionFixed = FixedMath.toFixed( degrees );
        }

    private void onTickRotation()
        {
        if ( targetDirectionFixed == directionInDegreesFixed ) return;

        final int delta = getDirectionDelta( targetDirectionFixed, directionInDegreesFixed );
        directionInDegreesFixed += delta * 12 / timing.ticksPerSecond;

        while ( directionInDegreesFixed < 0 )
            {
            directionInDegreesFixed += FixedMath.FIXED_360;
            }
        while ( directionInDegreesFixed >= FixedMath.FIXED_360 )
            {
            directionInDegreesFixed -= FixedMath.FIXED_360;
            }
        }



    private final Position myLastHitPosition;

    public static final int LOOK_AHEAD_STEPS = 3;

    public static final int LOOK_BEHIND_STEPS = 2;

    private final Position myTempPos = new Position();

    private static final Position myAfterDirection = new Position();

    private static final Position myBeforeDirection = new Position();

    private static final Random theRandom = new Random();
    }
