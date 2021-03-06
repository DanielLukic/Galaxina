package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.extras.ExtraType;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.util.*;

public final class Extras extends GameObject
    {
    public final SizeF sizeInWorld = new SizeF();

    public final Extra[] extras = new Extra[MAX_EXTRAS];



    public Extras()
        {
        }

    public final void deploy( final PositionF aPosition, final ExtraType aExtraType )
        {
        final Extra extra = getInstance();
        if ( extra == null ) return;

        final float dropSpeed = model.world.visibleSize.height / timing.ticksPerSecond / 3;
        extra.init( aPosition, dropSpeed );
        extra.type = aExtraType;

        model.triggerSound( model.soundExtra );
        }

    // From GameObject

    public void onStartGame()
        {
        Extra.timing = timing;
        }

    public void onStartLevel()
        {
        for ( int idx = 0; idx < extras.length; idx++ )
            {
            if ( extras[ idx ] == null ) continue;
            extras[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < extras.length; idx++ )
            {
            final Extra extra = extras[ idx ];
            if ( extra == null || !extra.active ) continue;
            extra.onControlTick( model );
            }
        }

    // Implementation

    private Extra getInstance()
        {
        for ( int idx = 0; idx < extras.length; idx++ )
            {
            final Extra extra = extras[ idx ];
            if ( extra == null ) return extras[ idx ] = new Extra();
            if ( !extra.active ) return extra;
            }
        return null;
        }



    private static final int MAX_EXTRAS = 8;
    }
