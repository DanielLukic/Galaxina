package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.GameModel;

public final class RepairPlayer extends ExtraType
    {
    public RepairPlayer( final int aID )
        {
        super( aID, "REPAIR" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        player.damageInPercent = 0;
        return true;
        }
    }
