/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.sandbox;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.GameContext;
import net.intensicode.screens.AbstractMenu;
import net.intensicode.screens.ClearScreen;
import net.intensicode.screens.MenuEntry;
import net.intensicode.util.FontGen;


/**
 * TODO: Describe this!
 */
public final class SandboxMenu extends AbstractMenu
    {
    public SandboxMenu( final GameContext aGameContext, final FontGen aMenuFont )
        {
        super( aMenuFont );
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        addScreen( new ClearScreen( 0 ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );

        addMenuEntry( IMAGE_DATA_TEST, "IMAGE DATA TEST" );
        addMenuEntry( ROTATION_TEST, "ROTATION TEST" );
        addMenuEntry( PATH_TEST, "PATH TEST" );

        updateSelectedEntry( 0 );
        }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "SELECT", "EXIT", false );
        }

    // From AbstractMenu

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        switch ( aSelectedEntry.id )
            {
            case ROTATION_TEST:
                engine().pushOnce( new RotationTestScreen( myGameContext ) );
                break;
            case PATH_TEST:
                engine().pushOnce( new PathTestScreen( myGameContext ) );
                break;
            case IMAGE_DATA_TEST:
                engine().pushOnce( new ImageDataTest( myGameContext ) );
                break;
            }
        }

    private final GameContext myGameContext;

    private static final int ROTATION_TEST = 0;

    private static final int PATH_TEST = 1;

    private static final int IMAGE_DATA_TEST = 2;
    }