package net.intensicode.galaxina.ui.actions;

import net.intensicode.core.Engine;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;

public final class PauseGame extends RunnableAction implements EditorStateListener, Identifiers
    {
    public PauseGame( final EditorCoreAPI aCoreAPI )
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
        Engine.pause = !Engine.pause;
        }
    }
