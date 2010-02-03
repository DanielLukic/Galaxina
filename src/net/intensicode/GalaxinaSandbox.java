package net.intensicode;

import net.intensicode.core.GameSystem;
import net.intensicode.core.SkinManager;
import net.intensicode.sandbox.SandboxContext;
import net.intensicode.sandbox.SandboxMenu;
import net.intensicode.screens.ScreenBase;

public final class GalaxinaSandbox extends IntensiGame
    {
    public GalaxinaSandbox() throws Exception
        {
        super();
        }

    // From SystemContext

    public ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        final SkinManager skin = aGameSystem.skin;
        final SandboxContext context = new SandboxContext( this, skin );
        return new SandboxMenu( context, context.menuFont() );
        }
    }
