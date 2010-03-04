package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.GameObject;

public abstract class SecondaryWeapon extends Weapon
    {
    public static final int SHOTS_PER_STOCK = 5;

    public int remainingShots;


    public final void restock()
        {
        remainingShots += SHOTS_PER_STOCK;
        }

    public final void onInitialize()
        {
        remainingShots = SHOTS_PER_STOCK;
        }

    // Protected API

    protected void onInitializeSubclass()
        {
        }

    protected void deactivate()
        {
        GameObject.model.player.secondaryWeapon = null;
        }
    }
