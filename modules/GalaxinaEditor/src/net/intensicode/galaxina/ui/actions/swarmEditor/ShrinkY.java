package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;
import net.intensicode.util.Position;

public final class ShrinkY extends RunnableAction implements EditorStateListener
    {
    public ShrinkY( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        for ( final Position pos : swarm.positions() )
            {
            pos.y = pos.y * 9 / 10;
            }
        swarm.positions().fireDataChanged();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
