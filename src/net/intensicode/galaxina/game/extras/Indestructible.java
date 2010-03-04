package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Player;

public final class Indestructible extends ExtraType
    {
    public Indestructible( final int aID )
        {
        super( aID, "INDESTRUCTIBLE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        player.invulnerableTicks = GameObject.timing.ticksPerSecond * 3;
        return true;
        }
    }
