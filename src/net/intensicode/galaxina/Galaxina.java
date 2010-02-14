package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.screens.ScreenBase;
import net.intensicode.core.GameSystem;

public final class Galaxina extends IntensiGame
    {
    public Galaxina() throws Exception
        {
        super();
        }

    // From SystemContext

    public final boolean useOpenglIfPossible()
        {
        return true;
        }

    public ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        return new MainController();
        }
    }
