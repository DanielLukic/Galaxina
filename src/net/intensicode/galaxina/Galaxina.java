package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.util.Log;
import net.intensicode.screens.ScreenBase;
import net.intensicode.core.GameSystem;

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

    public final void onFramesDropped( final GameSystem aGameSystem )
        {
        }
    }
