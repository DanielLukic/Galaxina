package net.intensicode.galaxina.game.objects;

import net.intensicode.core.GameTiming;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.enemies.controllers.*;
import net.intensicode.galaxina.game.extras.*;
import net.intensicode.galaxina.util.UtilitiesEx;
import net.intensicode.path.PathWithDirection;
import net.intensicode.util.*;


public final class Enemy extends WorldObjectWithSize
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


    public static final int LOOK_AHEAD_STEPS = 3;

    public static final int LOOK_BEHIND_STEPS = 2;


    public static final int SYNC_NONE = 0;

    public static final int SYNC_SPEED = 1;

    public static final int SYNC_PATH = 2;

    public static final int SYNC_LEFT = 3;

    public static final int SYNC_RIGHT = 4;


    public static GameTiming timing;

    public static GameModel model;


    public PositionF formationPosition;


    public float pathPos;

    public PathWithDirection path;

    public Enemy syncSource;

    public float syncSpeed;

    public int syncMode;


    public EnemyController controller;

    public EnemyWeapon weapon;

    public EnemyType type;

    public int hitsRemaining;


    public float directionInDegrees;

    public float targetDirection;


    public boolean deployExtraIfDestroyed;

    public boolean inSyncWithBreath;

    public boolean active;


    public Enemy( final PositionF aLastHitPosition )
        {
        myLastHitPosition = aLastHitPosition;
        }

    public static float getDirectionDelta( final float aTo, final float aFrom )
        {
        final float delta = aTo - aFrom;
        if ( delta > 180 ) return delta - 360;
        if ( delta < -180 ) return 360 + delta;
        return delta;
        }

    public final void init( final EnemyType aEnemyType, final PathWithDirection aIncomingPath, final PositionF aFormationPosition )
        {
        type = aEnemyType;
        deployExtraIfDestroyed = type.extraID != ExtraTypes.NO_EXTRA;
        formationPosition = aFormationPosition;
        weapon = type.createWeapon( worldPos );
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

    public final float getSpeed()
        {
        return syncSpeed != 0 ? syncSpeed : type.getSpeed( model );
        }

    public final PositionF getBreathPos( final boolean aInSyncFlag )
        {
        return model.breather.getBreathPos( formationPosition, aInSyncFlag );
        }

    public final void syncSpeedWith( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_SPEED;
        syncSpeed = aEnemy.getSpeed();
        }

    public final void syncPathWith( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_PATH;
        syncSpeed = aEnemy.getSpeed();
        setController( FOLLOW_IN_SYNC );
        }

    public final void followAtLeftSideOf( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_LEFT;
        syncSpeed = aEnemy.getSpeed();
        setController( FOLLOW_BY_SIDE );
        }

    public final void followAtRightSideOf( final Enemy aEnemy )
        {
        syncSource = aEnemy;
        syncMode = SYNC_RIGHT;
        syncSpeed = aEnemy.getSpeed();
        setController( FOLLOW_BY_SIDE );
        }

    public final void updateToBreathPosition()
        {
        worldPos.setTo( getBreathPos( inSyncWithBreath ) );
        targetDirection = 0;
        }

    public final void jumpToStartOfPath()
        {
        final PositionF startPosition = path.getStartPosition();
        final float startDirection = UtilitiesEx.directionToDegrees( path.getStartDirection() );
        worldPos.setTo( startPosition );
        directionInDegrees = targetDirection = startDirection;
        }

    public final void moveByDirection( final PositionF aOffset )
        {
        aOffset.normalize();
        aOffset.scale( getSpeed() );
        worldPos.translate( aOffset );

        targetDirection = UtilitiesEx.directionToDegrees( aOffset );
        }

    public final boolean moveAlongPath()
        {
        pathPos += getSpeed();
        updateToPathPosition();
        return pathPos >= path.getPathLength();
        }

    public final void syncPathTo( final Enemy aSource )
        {
        final float pathPosInPercent = aSource.pathPos * 100 / aSource.path.getPathLength();
        pathPos = pathPosInPercent * path.getPathLength() / 100;
        updateToPathPosition();
        }

    public final void copyFrom( final Enemy aSource )
        {
        final float pathPosInPercent = aSource.pathPos * 100 / aSource.path.getPathLength();
        pathPos = pathPosInPercent * path.getPathLength() / 100;
        worldPos.setTo( aSource.worldPos );
        directionInDegrees = targetDirection = aSource.directionInDegrees;
        }

    public final boolean isCloseTo( final PositionF aPosition )
        {
        return ( worldPos.distanceTo( aPosition ) <= 1f );
        }

    public final boolean isReady()
        {
        if ( controller.readyUsesSyncSource )
            {
            return syncSource.controller.readyForAction;
            }
        else
            {
            return controller.readyForAction;
            }
        }

    public final boolean isHit( final PositionF aWorldPos )
        {
        return boundingBox.contains( aWorldPos );
        }

    public final void hit()
        {
        //#if DEBUG
        if ( hitsRemaining == 0 ) throw new IllegalStateException();
        //#endif

        hitsRemaining--;
        if ( hitsRemaining > 0 ) return;

        myLastHitPosition.setTo( worldPos );
        model.player.score += type.getScore( model );
        explode();
        }

    public final void explode()
        {
        if ( type.hits == 1 ) model.explosions.addNormal( worldPos );
        else model.explosions.addBig( worldPos );
        active = false;

        if ( deployExtraIfDestroyed )
            {
            final ExtraType extraType = model.extraTypes.all[ type.extraID ];
            model.extras.deploy( worldPos, extraType );
            }
        }

    public void onEndOfPath()
        {
        unsync();
        setPath( null );

        if ( formationPosition == null )
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
        if ( !active ) return;

        controller.onControlTick( this );

        if ( worldPos.y > 0 )
            {
            final Player player = model.player;
            player.checkForCrash( this );
            }

        if ( hitsRemaining < type.hits )
            {
            final int probability = timing.ticksPerSecond * ( type.hits - hitsRemaining ) / type.hits;
            if ( theRandom.nextInt( timing.ticksPerSecond ) < probability / 3 )
                {
                model.sparks.add( worldPos, type.sizeInWorld );
                }
            }

        final float width = type.sizeInWorld.width * 0.8f;
        final float height = type.sizeInWorld.height * 0.8f;
        boundingBox.setCenterAndSize( worldPos, width, height );

        weapon.onControlTick();

        onTickRotation();
        }

    // Implementation

    private void updateToPathPosition()
        {
        worldPos.setTo( path.getPosition( pathPos ) );

        myBeforeDirection.setTo( path.getDirection( pathPos - getSpeed() * LOOK_BEHIND_STEPS ) );
        myAfterDirection.setTo( path.getDirection( pathPos + getSpeed() * LOOK_AHEAD_STEPS ) );

        myTempPos.x = myBeforeDirection.x + myAfterDirection.x;
        myTempPos.y = myBeforeDirection.y + myAfterDirection.y;

        targetDirection = UtilitiesEx.directionToDegrees( myTempPos );
        }

    private void onTickRotation()
        {
        if ( targetDirection == directionInDegrees ) return;

        final float delta = getDirectionDelta( targetDirection, directionInDegrees );
        directionInDegrees += delta * ( timing.ticksPerSecond / 2 ) / timing.ticksPerSecond;

        while ( directionInDegrees < 0 )
            {
            directionInDegrees += 360;
            }
        while ( directionInDegrees >= 360 )
            {
            directionInDegrees -= 360;
            }
        }


    private final PositionF myLastHitPosition;

    private final PositionF myTempPos = new PositionF();

    private static final PositionF myAfterDirection = new PositionF();

    private static final PositionF myBeforeDirection = new PositionF();

    private static final Random theRandom = new Random();
    }
