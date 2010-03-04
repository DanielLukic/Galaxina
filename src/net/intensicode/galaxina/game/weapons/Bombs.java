package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.GameObject;

public final class Bombs extends GameObject
    {
    public int activeCount;

    public Bomb[] bombs;



    public Bombs()
        {
        }

    public final Bomb getAvailableBomb()
        {
        for ( int idx = 0; idx < bombs.length; idx++ )
            {
            final Bomb bomb = bombs[ idx ];
            if ( !bomb.active ) return bomb;
            }
        //#if DEBUG
        throw new RuntimeException();
        //#else
        //# return new Bomb();
        //#endif
        }

    // From GameObject

    protected final void onInitOnce() throws Exception
        {
        final int maxBombs = model.configuration.readInt( "Weapons.Bombs.MAX", MAX_BOMBS );
        bombs = new Bomb[maxBombs];
        for ( int idx = 0; idx < bombs.length; idx++ ) bombs[ idx ] = new Bomb();
        }

    public final void onStartGame()
        {
        }

    public final void onStartLevel()
        {
        for ( int idx = 0; idx < bombs.length; idx++ )
            {
            bombs[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        activeCount = 0;
        for ( int idx = 0; idx < bombs.length; idx++ )
            {
            final Bomb bomb = bombs[ idx ];
            if ( !bomb.active ) continue;
            bomb.onControlTick( model );
            if ( bomb.active ) activeCount++;
            }
        }



    private static final int MAX_BOMBS = 32;
    }
