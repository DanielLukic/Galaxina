package net.intensicode.galaxina.ui.actions.pathEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedPathMarker;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class RemovePathNode extends RunnableAction implements EditorStateListener
    {
    public RemovePathNode( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( SELECTED_PATH_MARKER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From Runnable

    public final void run()
        {
        final SelectedPathMarker pathMarker = (SelectedPathMarker) myCoreAPI.state().get( SELECTED_PATH_MARKER );
        if ( pathMarker == null ) throw new IllegalStateException();

        final Path selectedPath = pathMarker.path;
        final int deletedIndex = selectedPath.positions().indexOf( pathMarker.marker );
        selectedPath.positions().deleteEntry( pathMarker.marker );

        if ( selectedPath.positions().size() == 0 )
            {
            myCoreAPI.state().change( SELECTED_PATH_MARKER, null );
            }
        else
            {
            final int newIndex = Math.min( deletedIndex, selectedPath.positions().size() - 1 );
            final PositionEx lastPosition = selectedPath.positions().at( newIndex );
            myCoreAPI.state().change( SELECTED_PATH_MARKER, new SelectedPathMarker( selectedPath, lastPosition ) );
            }
        }
    }
