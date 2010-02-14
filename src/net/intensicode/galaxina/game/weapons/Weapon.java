package net.intensicode.galaxina.game.weapons;

/**
 * TODO: Describe this!
 */
public abstract class Weapon
    {
    public abstract boolean isSecondary();

    public abstract void onInitialize();

    public abstract void onStartLevel();

    public abstract void onControlTick( final boolean aAllowControl );

    public abstract void onProjectileDone( final Object aObject );
    }
