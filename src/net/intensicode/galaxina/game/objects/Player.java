package net.intensicode.galaxina.game.objects;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.game.enemies.Enemy;
import net.intensicode.galaxina.game.weapons.Weapon;
import net.intensicode.util.*;

public final class Player extends GameObject
    {
    public static final int NOT_HIT = 0;

    public static final int OUTER_HIT = 1;

    public static final int FULL_HIT = 2;


    public final Size sizeInWorldFixed = new Size( DEFAULT_SIZE, DEFAULT_SIZE );

    public final Position worldPosFixed = new Position();

    public final Rectangle outerBBox = new Rectangle();

    public final Rectangle innerBBox = new Rectangle();


    public Weapon primaryWeapon;

    public Weapon secondaryWeapon;

    public final DynamicArray satellites = new DynamicArray();


    public int damageInPercentFixed;

    public int invulnerableTicks;

    public int weaponUpgrades;

    public int bulletUpgrades;

    public int reloadUpgrades;

    public int score;

    public int lives;


    public boolean visible;


    public Player()
        {
        }

    public final boolean isDead()
        {
        return damageInPercentFixed == FixedMath.FIXED_100;
        }

    public final boolean isAlive()
        {
        return damageInPercentFixed < FixedMath.FIXED_100;
        }

    public final boolean isCrash( final Rectangle aRectangleFixed )
        {
        if ( invulnerableTicks > 0 ) return false;
        if ( !visible ) return false;
        return innerBBox.intersectsWith( aRectangleFixed );
        }

    public final int isHit( final Position aWorldPosFixed )
        {
        if ( invulnerableTicks > 0 ) return NOT_HIT;
        if ( myState != STATE_PLAYING || !visible ) return NOT_HIT;
        if ( !outerBBox.contains( aWorldPosFixed ) ) return NOT_HIT;
        if ( !innerBBox.contains( aWorldPosFixed ) ) return OUTER_HIT;
        return FULL_HIT;
        }

    public final void hit( final int aHitKind )
        {
        if ( aHitKind == OUTER_HIT ) damageInPercentFixed += FixedMath.FIXED_30;
        if ( aHitKind == FULL_HIT ) damageInPercentFixed += FixedMath.FIXED_50;

        final int maxValue = FixedMath.FIXED_100;
        if ( damageInPercentFixed > maxValue ) damageInPercentFixed = maxValue;
        if ( damageInPercentFixed == maxValue ) explode();
        }

    public final void explode()
        {
        myState = STATE_EXPLODING;
        damageInPercentFixed = FixedMath.FIXED_100;
        myExplodeTick = 0;
        myExplodeTicks = timing.ticksPerSecond * 2;

        for ( int idx = 0; idx < satellites.size; idx++ )
            {
            final Satellite satellite = (Satellite) satellites.get( idx );
            if ( satellite.active ) satellite.explode();
            }
        }

    public void checkForCrash( final Enemy aEnemy )
        {
        if ( isCrash( aEnemy.bbox ) )
            {
            explode();
            aEnemy.explode();
            }

        for ( int idx = 0; idx < satellites.size; idx++ )
            {
            final Satellite satellite = (Satellite) satellites.get( idx );
            if ( satellite.active && satellite.isCrash( aEnemy.bbox ) )
                {
                satellite.explode();
                aEnemy.explode();
                }
            }
        }

    public final void addSatellite( final Satellite aSatellite )
        {
        satellites.add( aSatellite );
        updateSatellites();
        }

    public final void removeSatellite( final Satellite aSatellite )
        {
        aSatellite.active = false;
        satellites.remove( aSatellite );
        updateSatellites();
        }

    private void updateSatellites()
        {
        Satellite previous = null;
        for ( int idx = 0; idx < satellites.size; idx++ )
            {
            final Satellite satellite = (Satellite) satellites.get( idx );
            satellite.init( this, idx, satellites.size, previous );
            previous = satellite;
            }
        }

    // From GameObject

    public final void onStartGame()
        {
        visible = true;
        score = 0;
        lives = 3;
        damageInPercentFixed = 0;
        myLastScoreCheck = 0;
        weaponUpgrades = 2;
        reloadUpgrades = 2;
        bulletUpgrades = 2;
        secondaryWeapon = null;
        primaryWeapon = model.weapons.simpleGun;

        //#if DEBUG
        while ( satellites.size > 0 ) removeSatellite( (Satellite) satellites.get( 0 ) );
        addSatellite( model.satellites.getAvailableInstance() );
        addSatellite( model.satellites.getAvailableInstance() );
        //#endif
        }

    public final void onStartLevel()
        {
        // TODO: This seems to occur in Weapons class already?
        if ( primaryWeapon != null ) primaryWeapon.onStartLevel();
        if ( secondaryWeapon != null ) secondaryWeapon.onStartLevel();

        damageInPercentFixed /= 2;

        final Size visibleSizeFixed = model.world.visibleSizeFixed;

        worldPosFixed.x = 0;
        worldPosFixed.y = visibleSizeFixed.height / 2;
        worldPosFixed.y -= visibleSizeFixed.height / 10;

        mySpeedStepFixed = visibleSizeFixed.width / timing.ticksPerSecond * 2 / 3;
        mySpeedLeftFixed = mySpeedRightFixed = 0;

        updateBBoxes();

        myState = STATE_PLAYING;
        }

    public final void onControlTick()
        {
        switch ( model.state )
            {
            case GameModel.STATE_PLAY_LEVEL:
            case GameModel.STATE_SHOW_LEVEL_STATS:
                onPlayLevel();
                break;
            }

        if ( myLastScoreCheck < score )
            {
            final int lastCheck = myLastScoreCheck % EXTRA_LIVE_SCORE;
            final int now = score % EXTRA_LIVE_SCORE;
            if ( now < lastCheck ) lives++;
            myLastScoreCheck = score;
            }
        }

    // Implementation

    private void updateBBoxes()
        {
        final int outerWidth = sizeInWorldFixed.width * 90 / 100;
        final int outerHeight = sizeInWorldFixed.height * 90 / 100;
        outerBBox.setCenterAndSize( worldPosFixed, outerWidth, outerHeight );

        final int innerWidth = sizeInWorldFixed.width * 50 / 100;
        final int innerHeight = sizeInWorldFixed.height * 50 / 100;
        innerBBox.setCenterAndSize( worldPosFixed, innerWidth, innerHeight );
        }

    private void onPlayLevel()
        {
        switch ( myState )
            {
            case STATE_PLAYING:
                onPlaying();
                break;
            case STATE_EXPLODING:
                onExploding();
                break;
            case STATE_GAME_OVER:
                break;
            default:
                throw new IllegalStateException();
            }

        if ( visible && damageInPercentFixed > FixedMath.FIXED_25 )
            {
            if ( mySmokeTicks > 0 )
                {
                mySmokeTicks--;
                }
            else
                {
                myTempPos.setTo( worldPosFixed );
                myTempPos.x += myRandom.nextInt( sizeInWorldFixed.width ) - sizeInWorldFixed.width / 2;
                model.smokes.add( myTempPos );
                final int maxDamage = Math.min( 75, FixedMath.toInt( damageInPercentFixed ) );
                mySmokeTicks = timing.ticksPerSecond * ( 100 - maxDamage ) / 100;
                }
            }
        }

    private void onPlaying()
        {
        tickWeapons();

        visible = true;

        if ( invulnerableTicks > 0 ) invulnerableTicks--;

        damageInPercentFixed -= FixedMath.FIXED_1 / timing.ticksPerSecond;
        if ( damageInPercentFixed < 0 ) damageInPercentFixed = 0;

        final KeysHandler keys = system.keys;
        if ( keys.check( KeysHandler.LEFT ) )
            {
            mySpeedLeftFixed += mySpeedStepFixed / 6;
            mySpeedRightFixed = mySpeedRightFixed * 60 / 100;
            }
        else
            {
            mySpeedLeftFixed = mySpeedLeftFixed * 80 / 100;
            }
        if ( keys.check( KeysHandler.RIGHT ) )
            {
            mySpeedRightFixed += mySpeedStepFixed / 6;
            mySpeedLeftFixed = mySpeedLeftFixed * 60 / 100;
            }
        else
            {
            mySpeedRightFixed = mySpeedRightFixed * 80 / 100;
            }

        mySpeedLeftFixed = Math.min( mySpeedStepFixed, mySpeedLeftFixed );
        mySpeedRightFixed = Math.min( mySpeedStepFixed, mySpeedRightFixed );

        worldPosFixed.x -= mySpeedLeftFixed;
        worldPosFixed.x += mySpeedRightFixed;

        final Size visibleSizeFixed = model.world.visibleSizeFixed;
        final int maxRightPos = ( visibleSizeFixed.width - sizeInWorldFixed.width ) / 2;
        final int maxLeftPos = -maxRightPos;

        worldPosFixed.x = Math.max( maxLeftPos, worldPosFixed.x );
        worldPosFixed.x = Math.min( maxRightPos, worldPosFixed.x );

        updateBBoxes();
        }

    private void onExploding()
        {
        tickWeapons();

        invulnerableTicks = 0;

        worldPosFixed.x -= mySpeedLeftFixed / 6;
        worldPosFixed.x += mySpeedRightFixed / 6;

        if ( myExplodeTick < myExplodeTicks )
            {
            visible = false;

            myExplodeTick++;
            if ( myExplodeTick == 1 )
                {
                model.explosions.addBigPlayer( worldPosFixed );
                }
            if ( myExplodeTick < myExplodeTicks / 2 && myExplodeTick % ( myExplodeTicks / 10 ) == 0 )
                {
                myTempPos.x = worldPosFixed.x + myRandom.nextInt( sizeInWorldFixed.width ) - sizeInWorldFixed.width / 2;
                myTempPos.y = worldPosFixed.y + myRandom.nextInt( sizeInWorldFixed.height ) - sizeInWorldFixed.height / 2;
                model.explosions.addForPlayer( myTempPos );
                }
            }
        else
            {
            mySpeedLeftFixed = mySpeedRightFixed = 0;

            onLiveLost();
            }
        }

    private void onLiveLost()
        {
        if ( lives > 0 )
            {
            lives--;
            damageInPercentFixed = 0;
            worldPosFixed.x = 0;
            myState = STATE_PLAYING;
            invulnerableTicks = timing.ticksPerSecond;
            }
        else
            {
            myState = STATE_GAME_OVER;
            model.onGameOver();
            }
        }

    private void tickWeapons()
        {
        if ( primaryWeapon != null ) primaryWeapon.onControlTick( isAlive() );
        if ( secondaryWeapon != null ) secondaryWeapon.onControlTick( isAlive() );
        }


    private int myState;

    private int mySmokeTicks;

    private int myExplodeTick;

    private int myExplodeTicks;

    private int myLastScoreCheck;

    private int mySpeedStepFixed;

    private int mySpeedLeftFixed;

    private int mySpeedRightFixed;


    private final Random myRandom = new Random( 17 );

    private final Position myTempPos = new Position();


    private static final int STATE_PLAYING = 0;

    private static final int STATE_EXPLODING = 1;

    private static final int STATE_GAME_OVER = 2;

    private static final int DEFAULT_SIZE = FixedMath.toFixed( 16 );

    private static final int EXTRA_LIVE_SCORE = 10000;
    }
