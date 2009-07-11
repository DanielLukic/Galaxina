package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.SelectedPathMarker;

public final class DeletePath extends RunnableAction implements EditorStateListener
    {
    public DeletePath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        setEnabled( false );

        myCoreAPI.state().add( EditorState.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Path currentPath = (Path) myCoreAPI.state().get( EditorState.SELECTED_PATH );
        myCoreAPI.state().change( EditorState.SELECTED_PATH, null );

        myCoreAPI.project().pathes().deleteEntry( currentPath );

        final SelectedPathMarker pathMarker = myCoreAPI.state().getSelectedPathMarker();
        if ( pathMarker != null && pathMarker.path == currentPath )
            {
            myCoreAPI.state().change( SELECTED_PATH_MARKER, null );
            }
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }