package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.core.GameSystem;
import net.intensicode.screens.ScreenBase;

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

    public void onDebugTriggered()
        {
        throw new RuntimeException( "debug" );
        }

    public void onCheatTriggered()
        {
        throw new RuntimeException( "cheat" );
        }
    }
