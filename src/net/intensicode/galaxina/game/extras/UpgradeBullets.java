package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.galaxina.game.weapons.Weapons;

public final class UpgradeBullets extends ExtraType
    {
    public UpgradeBullets( final int aID )
        {
        super( aID, "BULLET UPGRADE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        if ( player.bulletUpgrades >= Weapons.MAX_RELOAD_UPGRADES ) return false;

        player.bulletUpgrades++;
        return true;
        }
    }
