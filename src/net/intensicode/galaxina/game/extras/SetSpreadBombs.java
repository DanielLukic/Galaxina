package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.Player;
import net.intensicode.galaxina.game.weapons.SecondaryWeapon;

public final class SetSpreadBombs extends ExtraType
    {
    public SetSpreadBombs( final int aID )
        {
        super( aID, "SPREAD BOMBS" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        final SecondaryWeapon newWeapon = aModel.weapons.spreadBombs;
        player.secondaryWeapon = newWeapon;
        newWeapon.restock();
        return true;
        }
    }
