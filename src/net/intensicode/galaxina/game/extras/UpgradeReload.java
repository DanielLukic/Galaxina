package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.galaxina.game.objects.Weapons;

public final class UpgradeReload extends ExtraType
    {
    public UpgradeReload( final int aID )
        {
        super( aID, "RELOAD UPGRADE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        if ( player.reloadUpgrades >= Weapons.MAX_RELOAD_UPGRADES ) return false;

        player.reloadUpgrades++;
        return true;
        }
    }
