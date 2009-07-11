package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class Project extends RunnableAction
    {
    public Project( final EditorCoreAPI aCoreAPI )
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