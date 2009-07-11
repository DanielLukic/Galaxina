package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class ReassignSwarmEnemy extends RunnableAction
    {
    public ReassignSwarmEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( false );
        }

    // From Runnable

    public final void run()
        {
        throw new RuntimeException( "nyi" );
        }
    }
