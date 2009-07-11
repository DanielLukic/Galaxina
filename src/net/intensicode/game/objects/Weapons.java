package net.intensicode.game.objects;

import net.intensicode.game.weapons.*;

/**
 * TODO: Describe this!
 */
public final class Weapons extends GameObject
    {
    public static final int MAX_BULLET_UPGRADES = 8;

    public static final int MAX_WEAPON_UPGRADES = 3;

    public static final int MAX_RELOAD_UPGRADES = 3;

    public final SimpleGun simpleGun = new SimpleGun();

    public final SmartBomb smartBomb = new SmartBomb();

    public final SpreadBombs spreadBombs = new SpreadBombs();

    public final HomingMissile homingMissile = new HomingMissile();

    private final Weapon[] weapons = { simpleGun, smartBomb, spreadBombs, homingMissile };



    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        for ( int idx = 0; idx < weapons.length; idx++ )
            {
            weapons[ idx ].onStartLevel();
            }
        }

    public final void onControlTick()
        {
        // Ticked by Player - not here!
        }

    // Protected Interface

    protected final void onInitOnce() throws Exception
        {
        for ( int idx = 0; idx < weapons.length; idx++ )
            {
            weapons[ idx ].onInitialize();
            }
        }
    }
