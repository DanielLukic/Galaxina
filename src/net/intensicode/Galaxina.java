/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.core.AbstractScreen;


/**
 * TODO: Describe this!
 */
public final class Galaxina extends IntensiGame
    {
    public Galaxina() throws Exception
        {
        super();
        }

    // From SystemContext

    public final AbstractScreen initMainController() throws Exception
        {
        return new MainController();
        }
    }
