package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.GameModel;

public final class AddSatellite extends ExtraType
    {
    public AddSatellite( final int aID )
        {
        super( aID, "SATELLITE" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final Player player = aModel.player;
        if ( !aModel.satellites.hasAvailableInstance() ) return false;
        final Satellite satellite = aModel.satellites.getAvailableInstance();
        player.addSatellite( satellite );
        return true;
        }
    }
