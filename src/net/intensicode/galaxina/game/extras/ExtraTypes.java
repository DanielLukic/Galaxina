package net.intensicode.galaxina.game.extras;

public final class ExtraTypes
    {
    public static final int NO_EXTRA = 0;

    public static final int SCORE_BONUS = 1;

    public static final int UPGRADE_RELOAD = 2;

    public static final int UPGRADE_BULLETS = 3;

    public static final int UPGRADE_WEAPON = 4;

    public static final int SPREAD_BOMBS = 5;

    public static final int HOMING_MISSILE = 6;

    public static final int SMART_BOMB = 7;

    public static final int SATELLITE = 8;

    public static final int REPAIR = 9;

    public static final int INDESTRUCTIBLE = 10;

    public static final int RANDOM = 11;

    public static final int RANDOM_WEAPON = 12;

    public static final int NUMBER_OF_EXTRA_TYPES = 13;

    public final ExtraType[] all;


    public ExtraTypes()
        {
        all = new ExtraType[NUMBER_OF_EXTRA_TYPES];
        add( new NoExtra( NO_EXTRA ) );
        add( new ScoreBonus( SCORE_BONUS ) );
        add( new UpgradeReload( UPGRADE_RELOAD ) );
        add( new UpgradeBullets( UPGRADE_BULLETS ) );
        add( new UpgradeWeapon( UPGRADE_WEAPON ) );
        add( new SetSpreadBombs( SPREAD_BOMBS ) );
        add( new SetHomingMissile( HOMING_MISSILE ) );
        add( new SetSmartBomb( SMART_BOMB ) );
        add( new AddSatellite( SATELLITE ) );
        add( new RepairPlayer( REPAIR ) );
        add( new Indestructible( INDESTRUCTIBLE ) );
        add( new RandomExtra( RANDOM ) );
        add( new RandomExtra( RANDOM_WEAPON ) );
        }

    public static boolean isWeaponId( final int aId )
        {
        if ( aId == SMART_BOMB ) return true;
        if ( aId == HOMING_MISSILE ) return true;
        if ( aId == SPREAD_BOMBS ) return true;
        return false;
        }

    // Implementation

    private void add( final ExtraType aExtraType )
        {
        if ( all[ aExtraType.id ] != null ) throw new IllegalArgumentException();
        all[ aExtraType.id ] = aExtraType;
        }
    }
