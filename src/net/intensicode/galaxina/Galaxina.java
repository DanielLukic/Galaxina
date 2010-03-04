package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Log;

public final class Galaxina extends IntensiGame
    {
    public Galaxina() throws Exception
        {
        super();
        }

    // From SystemContext

    public ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        return new MainController();
        }

    public void onStopApplication( final GameSystem aGameSystem )
        {
        Log.debug( "onStopApplication" );

        // Clean up static bull shit.. I hate myself.. Is that enough?

        GameObject.system = null;
        GameObject.engine = null;
        GameObject.timing = null;
        GameObject.model = null;

        Enemy.timing = null;
        Enemy.model = null;

        EnemyBehavior.engine = null;
        EnemyBehavior.model = null;

        EnemyWeapon.timing = null;
        EnemyWeapon.engine = null;
        EnemyWeapon.model = null;

        Extra.timing = null;
        Missile.timing = null;
        }
    }
