package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;

public final class ClonePath extends RunnableAction implements EditorStateListener
    {
    public ClonePath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( false );
        myCoreAPI.state().add( EditorState.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Path currentPath = (Path) myCoreAPI.state().get( EditorState.SELECTED_PATH );
        myCoreAPI.project().pathes().addEntry( currentPath.clone() );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }