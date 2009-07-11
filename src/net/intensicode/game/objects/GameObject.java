package net.intensicode.game.objects;

import net.intensicode.core.Engine;



/**
 * TODO: Describe this!
 */
public abstract class GameObject
    {
    public static Engine engine;

    public static GameModel model;



    public final void onInitialize( final Engine aEngine, final GameModel aGameModel ) throws Exception
        {
        //#if DEBUG
        if ( engine != null && engine != aEngine ) throw new IllegalArgumentException();
        if ( model != null && model != aGameModel ) throw new IllegalArgumentException();
        //#endif

        if ( engine == null ) engine = aEngine;
        if ( model == null ) model = aGameModel;

        if ( !myInitializedFlag ) onInitOnce();
        myInitializedFlag = true;
        }

    // Abstract Interface

    public abstract void onStartGame() throws Exception;

    public abstract void onStartLevel() throws Exception;

    public abstract void onControlTick();

    // Protected Interface

    protected void onInitOnce() throws Exception
        {
        }



    private boolean myInitializedFlag;
    }
