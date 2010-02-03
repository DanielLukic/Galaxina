package net.intensicode.game.objects;

import net.intensicode.core.*;

public abstract class GameObject
    {
    public static GameSystem system;

    public static GameEngine engine;

    public static GameTiming timing;

    public static GameModel model;



    public final void onInitialize( final GameSystem aGameSystem, final GameModel aGameModel ) throws Exception
        {
        //#if DEBUG
        if ( system != null && system != aGameSystem ) throw new IllegalArgumentException();
        if ( model != null && model != aGameModel ) throw new IllegalArgumentException();
        //#endif

        if ( system == null ) system = aGameSystem;
        if ( model == null ) model = aGameModel;

        engine = aGameSystem.engine;
        timing = aGameSystem.timing;

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
