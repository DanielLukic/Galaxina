package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;

public final class SaveChanges extends RunnableAction implements EditorStateListener, Identifiers
    {
    public SaveChanges( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( PROJECT, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From RunnableAction

    protected final void runUnsafe() throws Exception
        {
        myCoreAPI.project().save();
        }
    }
