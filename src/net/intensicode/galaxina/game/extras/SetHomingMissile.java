package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.objects.Weapon;

public final class SetHomingMissile extends ExtraType
    {
    public SetHomingMissile( final int aID )
        {
        super( aID, "HOMING MISSILE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        final Weapon newWeapon = aModel.weapons.homingMissile;
        if ( player.secondaryWeapon == newWeapon ) return false;
        player.secondaryWeapon = newWeapon;
        return true;
        }
    }
