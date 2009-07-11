package net.intensicode.game.extras;

import net.intensicode.game.objects.GameModel;

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
