package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;

public final class NoExtra extends ExtraType
    {
    public NoExtra( final int aID )
        {
        super( aID, "NO EXTRA" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        return false;
        }
    }
