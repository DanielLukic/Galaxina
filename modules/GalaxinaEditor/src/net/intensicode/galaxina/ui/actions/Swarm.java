package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class Swarm extends RunnableAction
    {
    public Swarm( final EditorCoreAPI aCoreAPI )
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