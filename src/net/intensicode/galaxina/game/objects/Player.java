package net.intensicode.galaxina.game.objects;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.weapons.*;
import net.intensicode.util.*;

public final class Player extends GameObject
    {
    public static final int NOT_HIT = 0;

    public static final int OUTER_HIT = 1;

    public static final int FULL_HIT = 2;

    private static final float ONE_HUNDRED_PERCENT = 100;


    public final SizeF sizeInWorld = new SizeF( DEFAULT_SIZE, DEFAULT_SIZE );

    public final PositionF worldPos = new PositionF();

    public final RectangleF outerBBox = new RectangleF();

    public final RectangleF innerBBox = new RectangleF();


    public Weapon primaryWeapon;

    public SecondaryWeapon secondaryWeapon;

    public final DynamicArray satellites = new DynamicArray();


    public float damageInPercent;

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
        return damageInPercent == ONE_HUNDRED_PERCENT;
        }

    public final boolean isAlive()
        {
        return damageInPercent < ONE_HUNDRED_PERCENT;
        }

    public final boolean isCrash( final RectangleF aRectangle )
        {
        if ( invulnerableTicks > 0 ) return false;
        if ( !visible ) return false;
        return innerBBox.intersectsWith( aRectangle );
        }

    public final int isHit( final PositionF aWorldPos )
        {
        if ( invulnerableTicks > 0 ) return NOT_HIT;
        if ( myState != STATE_PLAYING || !visible ) return NOT_HIT;
        if ( !outerBBox.contains( aWorldPos ) ) return NOT_HIT;
        if ( !innerBBox.contains( aWorldPos ) ) return OUTER_HIT;
        return FULL_HIT;
        }

    public final void hit( final int aHitKind )
        {
        if ( aHitKind == OUTER_HIT ) damageInPercent += 30;
        if ( aHitKind == FULL_HIT ) damageInPercent += 50;

        final float maxValue = ONE_HUNDRED_PERCENT;
        if ( damageInPercent > maxValue ) damageInPercent = maxValue;
        if ( damageInPercent == maxValue ) explode();
        }

    public final void explode()
        {
        myState = STATE_EXPLODING;
        damageInPercent = ONE_HUNDRED_PERCENT;
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
        if ( isCrash( aEnemy.boundingBox ) )
            {
            explode();
            aEnemy.explode();
            }

        for ( int idx = 0; idx < satellites.size; idx++ )
            {
            final Satellite satellite = (Satellite) satellites.get( idx );
            if ( satellite.active && satellite.isCrash( aEnemy.boundingBox ) )
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
        damageInPercent = 0;
        myLastScoreCheck = 0;
        weaponUpgrades = 0;
        reloadUpgrades = 0;
        bulletUpgrades = 0;
        satellites.clear();
        secondaryWeapon = null;
        primaryWeapon = model.weapons.simpleGun;

        //#if DEBUG
        weaponUpgrades = 2;
        reloadUpgrades = 2;
        bulletUpgrades = 2;
        secondaryWeapon = model.weapons.homingMissile;
        addSatellite( model.satellites.getAvailableInstance() );
        //#endif
        }

    public final void onStartLevel()
        {
        // TODO: This seems to occur in Weapons class already?
        if ( primaryWeapon != null ) primaryWeapon.onStartLevel();
        if ( secondaryWeapon != null ) secondaryWeapon.onStartLevel();

        damageInPercent /= 2;

        final SizeF visibleSize = model.world.visibleSize;

        worldPos.x = 0;
        worldPos.y = visibleSize.height / 2;
        worldPos.y -= visibleSize.height / 10;

        mySpeedStep = visibleSize.width / timing.ticksPerSecond * 2 / 3;
        mySpeedLeft = mySpeedRight = 0;

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
        final float outerWidth = sizeInWorld.width * 90 / ONE_HUNDRED_PERCENT;
        final float outerHeight = sizeInWorld.height * 90 / ONE_HUNDRED_PERCENT;
        outerBBox.setCenterAndSize( worldPos, outerWidth, outerHeight );

        final float innerWidth = sizeInWorld.width * 50 / ONE_HUNDRED_PERCENT;
        final float innerHeight = sizeInWorld.height * 50 / ONE_HUNDRED_PERCENT;
        innerBBox.setCenterAndSize( worldPos, innerWidth, innerHeight );
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

        if ( visible && damageInPercent > 25 )
            {
            if ( mySmokeTicks > 0 )
                {
                mySmokeTicks--;
                }
            else
                {
                myTempPos.setTo( worldPos );
                myTempPos.x += myRandom.nextFloat( sizeInWorld.width ) - sizeInWorld.width / 2;
                model.smokes.add( myTempPos );
                final int maxDamage = (int) Math.min( 75, damageInPercent );
                mySmokeTicks = (int) (timing.ticksPerSecond * ( ONE_HUNDRED_PERCENT - maxDamage ) / ONE_HUNDRED_PERCENT);
                }
            }
        }

    private void onPlaying()
        {
        tickWeapons();

        visible = true;

        if ( invulnerableTicks > 0 ) invulnerableTicks--;

        damageInPercent -= 1f / timing.ticksPerSecond;
        if ( damageInPercent < 0 ) damageInPercent = 0;

        final KeysHandler keys = system.keys;
        if ( keys.check( KeysHandler.LEFT ) )
            {
            mySpeedLeft += mySpeedStep / 6;
            mySpeedRight = mySpeedRight * 60 / ONE_HUNDRED_PERCENT;
            }
        else
            {
            mySpeedLeft = mySpeedLeft * 80 / ONE_HUNDRED_PERCENT;
            }
        if ( keys.check( KeysHandler.RIGHT ) )
            {
            mySpeedRight += mySpeedStep / 6;
            mySpeedLeft = mySpeedLeft * 60 / ONE_HUNDRED_PERCENT;
            }
        else
            {
            mySpeedRight = mySpeedRight * 80 / ONE_HUNDRED_PERCENT;
            }

        mySpeedLeft = Math.min( mySpeedStep, mySpeedLeft );
        mySpeedRight = Math.min( mySpeedStep, mySpeedRight );

        worldPos.x -= mySpeedLeft;
        worldPos.x += mySpeedRight;

        final SizeF visibleSize = model.world.visibleSize;
        final float maxRightPos = ( visibleSize.width - sizeInWorld.width ) / 2;
        final float maxLeftPos = -maxRightPos;

        worldPos.x = Math.max( maxLeftPos, worldPos.x );
        worldPos.x = Math.min( maxRightPos, worldPos.x );

        updateBBoxes();
        }

    private void onExploding()
        {
        tickWeapons();

        invulnerableTicks = 0;

        worldPos.x -= mySpeedLeft / 6;
        worldPos.x += mySpeedRight / 6;

        if ( myExplodeTick < myExplodeTicks )
            {
            visible = false;

            myExplodeTick++;
            if ( myExplodeTick == 1 )
                {
                model.explosions.addBigPlayer( worldPos );
                }
            if ( myExplodeTick < myExplodeTicks / 2 && myExplodeTick % ( myExplodeTicks / 10 ) == 0 )
                {
                myTempPos.x = worldPos.x + myRandom.nextFloat( sizeInWorld.width ) - sizeInWorld.width / 2;
                myTempPos.y = worldPos.y + myRandom.nextFloat( sizeInWorld.height ) - sizeInWorld.height / 2;
                model.explosions.addForPlayer( myTempPos );
                }
            }
        else
            {
            mySpeedLeft = mySpeedRight = 0;

            onLiveLost();
            }
        }

    private void onLiveLost()
        {
        if ( lives > 0 )
            {
            lives--;
            damageInPercent = 0;
            worldPos.x = 0;
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
        if ( secondaryWeapon != myPreviousSecondaryWeapon )
            {
            if ( secondaryWeapon != null ) secondaryWeapon.onInitialize();
            myPreviousSecondaryWeapon = secondaryWeapon;
            }

        if ( primaryWeapon != null ) primaryWeapon.onControlTick( isAlive() );
        if ( secondaryWeapon != null ) secondaryWeapon.onControlTick( isAlive() );
        }


    private int myState;

    private int mySmokeTicks;

    private int myExplodeTick;

    private int myExplodeTicks;

    private int myLastScoreCheck;

    private float mySpeedStep;

    private float mySpeedLeft;

    private float mySpeedRight;

    private Weapon myPreviousSecondaryWeapon;


    private final Random myRandom = new Random( 17 );

    private final PositionF myTempPos = new PositionF();


    private static final int STATE_PLAYING = 0;

    private static final int STATE_EXPLODING = 1;

    private static final int STATE_GAME_OVER = 2;

    private static final int DEFAULT_SIZE = 16;

    private static final int EXTRA_LIVE_SCORE = 10000;
    }
