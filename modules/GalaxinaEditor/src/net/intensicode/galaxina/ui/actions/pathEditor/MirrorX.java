package net.intensicode.galaxina.ui.actions.pathEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.ui.actions.RunnableAction;
import net.intensicode.util.Position;

public final class MirrorX extends RunnableAction implements EditorStateListener
    {
    public MirrorX( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Path path = myCoreAPI.state().currentPath();
        for ( final Position pos : path.positions() )
            {
            pos.x = myCoreAPI.project().engine().screen.width() - pos.x;
            }
        path.positions().fireDataChanged();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
