package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class Edit extends RunnableAction
    {
    public Edit( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( true );
        }

    // From Runnable

    public final void run()
        {
        throw new RuntimeException();
        }
    }