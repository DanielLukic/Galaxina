package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;
import net.intensicode.util.Position;

public final class ShrinkX extends RunnableAction implements EditorStateListener
    {
    public ShrinkX( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final int width = myCoreAPI.project().engine().screen.width();
        final int halfWidth = width / 2;

        final Swarm swarm = myCoreAPI.state().currentSwarm();
        for ( final Position pos : swarm.positions() )
            {
            final int delta = pos.x - halfWidth;
            pos.x = halfWidth + delta * 9 / 10;
            }
        swarm.positions().fireDataChanged();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
