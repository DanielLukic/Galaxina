package net.intensicode.game.objects;

import net.intensicode.core.Engine;
import net.intensicode.game.extras.ExtraType;
import net.intensicode.util.Position;
import net.intensicode.util.Size;



/**
 * TODO: Describe this!
 */
public final class FallingExtras extends GameObject
    {
    public final Size sizeInWorldFixed = new Size();

    public final Extra[] extras = new Extra[MAX_EXTRAS];



    public FallingExtras()
        {
        }

    public final void deploy( final Position aPosition, final ExtraType aExtraType )
        {
        final Extra extra = getInstance();
        if ( extra == null ) return;

        final int dropSpeed = model.world.visibleSizeFixed.height / Engine.ticksPerSecond / 3;
        extra.init( aPosition, dropSpeed );
        extra.type = aExtraType;
        }

    // From GameObject

    public void onStartGame()
        {
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
            if ( extra == null || extra.active == false ) continue;
            extra.onControlTick( model );
            }
        }

    // Implementation

    private final Extra getInstance()
        {
        for ( int idx = 0; idx < extras.length; idx++ )
            {
            final Extra extra = extras[ idx ];
            if ( extra == null ) return extras[ idx ] = new Extra();
            if ( extra.active == false ) return extra;
            }
        return null;
        }



    private static final int MAX_EXTRAS = 8;
    }
