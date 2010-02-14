package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.galaxina.game.objects.Weapons;

public final class UpgradeWeapon extends ExtraType
    {
    public UpgradeWeapon( final int aID )
        {
        super( aID, "WEAPON UPGRADE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;

        if ( player.weaponUpgrades < Weapons.MAX_WEAPON_UPGRADES )
            {
            player.weaponUpgrades++;
            return true;
            }

        return false;
        }
    }
