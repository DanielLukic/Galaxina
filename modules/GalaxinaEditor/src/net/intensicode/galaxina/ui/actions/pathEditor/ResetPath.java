package net.intensicode.galaxina.ui.actions.pathEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.SelectedPathMarker;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ResetPath extends RunnableAction implements EditorStateListener
    {
    public ResetPath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Path path = myCoreAPI.state().currentPath();
        final SelectedPathMarker pathMarker = myCoreAPI.state().getSelectedPathMarker();
        if ( pathMarker != null && pathMarker.path == path ) myCoreAPI.state().change( Identifiers.SELECTED_PATH_MARKER, null );
        path.positions().clear();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
