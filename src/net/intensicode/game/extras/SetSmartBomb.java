package net.intensicode.game.extras;

import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.Player;
import net.intensicode.game.weapons.Weapon;

public final class SetSmartBomb extends ExtraType
    {
    public SetSmartBomb( final int aID )
        {
        super( aID, "SMART BOMB" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        final Weapon newWeapon = aModel.weapons.smartBomb;
        if ( player.secondaryWeapon == newWeapon ) return false;
        player.secondaryWeapon = newWeapon;
        return true;
        }
    }
