package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.GameObject;

public final class Satellites extends GameObject
    {
    public Satellite[] satellites;


    public Satellites()
        {
        }

    public final boolean hasAvailableInstance()
        {
        for ( int idx = 0; idx < satellites.length; idx++ )
            {
            if ( !satellites[ idx ].active ) return true;
            }
        return false;
        }

    public final Satellite getAvailableInstance()
        {
        for ( int idx = 0; idx < satellites.length; idx++ )
            {
            final Satellite satellite = satellites[ idx ];
            if ( !satellite.active ) return satellite;
            }
        //#if DEBUG
        throw new RuntimeException();
        //#else
        //# return new Satellite();
        //#endif
        }

    // From GameObject

    protected final void onInitOnce() throws Exception
        {
        final int maxInstances = model.configuration.readInt( "Weapons.Satellites.MAX", MAX_SATELLITES );
        satellites = new Satellite[maxInstances];
        for ( int idx = 0; idx < satellites.length; idx++ ) satellites[ idx ] = new Satellite();
        }

    public final void onStartGame() throws Exception
        {
        for ( int idx = 0; idx < satellites.length; idx++ )
            {
            satellites[ idx ].onStartGame();
            }
        }

    public final void onStartLevel() throws Exception
        {
        for ( int idx = 0; idx < satellites.length; idx++ )
            {
            satellites[ idx ].onStartLevel();
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < satellites.length; idx++ )
            {
            final Satellite satellite = satellites[ idx ];
            if ( !satellite.active ) continue;
            satellite.onControlTick();
            }
        }


    private static final int MAX_SATELLITES = 4;
    }
