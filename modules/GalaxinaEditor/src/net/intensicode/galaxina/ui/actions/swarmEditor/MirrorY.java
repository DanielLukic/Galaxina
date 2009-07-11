package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;
import net.intensicode.util.Position;

public final class MirrorY extends RunnableAction implements EditorStateListener
    {
    public MirrorY( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        if ( swarm == null ) return;
        for ( final Position pos : swarm.positions() )
            {
            pos.y = myCoreAPI.project().engine().screen.height() - pos.y;
            }
        swarm.positions().fireDataChanged();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
