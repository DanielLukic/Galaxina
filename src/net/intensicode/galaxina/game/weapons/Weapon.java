package net.intensicode.galaxina.game.weapons;

public abstract class Weapon
    {
    public abstract void onInitialize();

    public abstract void onStartLevel();

    public abstract void onControlTick( final boolean aAllowControl );

    public abstract void onProjectileDone( final Object aObject );
    }
