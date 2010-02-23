package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.objects.Weapon;

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
