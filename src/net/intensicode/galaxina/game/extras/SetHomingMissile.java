package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.galaxina.game.weapons.SecondaryWeapon;

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
        final SecondaryWeapon newWeapon = aModel.weapons.homingMissile;
        player.secondaryWeapon = newWeapon;
        newWeapon.restock();
        return true;
        }
    }
