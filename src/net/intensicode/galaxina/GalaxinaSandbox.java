package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.core.*;
import net.intensicode.galaxina.sandbox.*;
import net.intensicode.screens.ScreenBase;

public final class GalaxinaSandbox extends IntensiGame
    {
    public GalaxinaSandbox() throws Exception
        {
        super();
        }

    // From SystemContext

    public final boolean useOpenglIfPossible()
        {
        return false;
        }

    public final ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        final SkinManager skin = aGameSystem.skin;
        final SandboxContext context = new SandboxContext( this, skin );
        return new SandboxMenu( context, context.menuFont() );
        }
    }
