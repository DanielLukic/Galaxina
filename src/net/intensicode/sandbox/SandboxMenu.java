package net.intensicode.sandbox;

import net.intensicode.game.GameContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class SandboxMenu extends MenuBase
    {
    public SandboxMenu( final GameContext aGameContext, final FontGenerator aMenuFont )
        {
        super( aMenuFont );
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( new ClearScreen( 0 ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );

        addMenuEntry( IMAGE_DATA_TEST, "IMAGE DATA TEST" );
        addMenuEntry( ROTATION_TEST, "ROTATION TEST" );
        addMenuEntry( PATH_TEST, "PATH TEST" );

        updateSelectedEntry( 0 );
        }

    public final void onInitEverytime() throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "SELECT", "EXIT", false );
        }

    // From AbstractMenu

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        switch ( aSelectedEntry.id )
            {
            case ROTATION_TEST:
                stack().pushOnce( new RotationTestScreen( myGameContext ) );
                break;
            case PATH_TEST:
                stack().pushOnce( new PathTestScreen( myGameContext ) );
                break;
            case IMAGE_DATA_TEST:
                stack().pushOnce( new ImageDataTest( myGameContext ) );
                break;
            }
        }

    private final GameContext myGameContext;

    private static final int ROTATION_TEST = 0;

    private static final int PATH_TEST = 1;

    private static final int IMAGE_DATA_TEST = 2;
    }
