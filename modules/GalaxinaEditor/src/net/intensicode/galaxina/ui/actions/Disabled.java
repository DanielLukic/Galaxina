package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class Disabled extends RunnableAction
    {
    public Disabled( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        putValue( NAME, "DISABLED" );
        putValue( SHORT_DESCRIPTION, "ALWAYS DISABLED - FOR DEVELOPMENT ONLY" );

        setEnabled( false );
        }

    // From Runnable

    public final void run()
        {
        throw new RuntimeException( "SHOULD NEVER RUN" );
        }
    }
