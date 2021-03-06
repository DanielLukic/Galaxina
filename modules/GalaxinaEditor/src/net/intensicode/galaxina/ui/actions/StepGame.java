package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.*;

public final class StepGame extends RunnableAction implements EditorStateListener, Identifiers
    {
    public StepGame( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( MIDLET_CONTAINER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From RunnableAction

    protected final void runUnsafe() throws Exception
        {
        myCoreAPI.doGameEngineSingleStep();
        }
    }
