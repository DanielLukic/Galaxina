/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.Configuration;
import net.intensicode.core.ResourceLoader;
import net.intensicode.core.Skin;
import net.intensicode.sandbox.SandboxContext;
import net.intensicode.sandbox.SandboxMenu;


/**
 * TODO: Describe this!
 */
public final class GalaxinaSandbox extends IntensiGame
    {
    public GalaxinaSandbox() throws Exception
        {
        super();
        }

    // From SystemContext

    public final AbstractScreen initMainController() throws Exception
        {
        final ResourceLoader loader = myEngine.loader;
        final Configuration config = loader.loadConfiguration( "/skin.properties" );
        final Skin skin = new Skin( loader, config );
        final SandboxContext context = new SandboxContext( this, skin );
        return new SandboxMenu( context, context.menuFont() );
        }
    }