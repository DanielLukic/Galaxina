package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;
import net.intensicode.util.*;

public final class MirrorX extends RunnableAction implements EditorStateListener
    {
    public MirrorX( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        for ( final PositionF pos : swarm.positions() )
            {
            pos.x = myCoreAPI.gameScreenWidth() - pos.x;
            }
        swarm.positions().fireDataChanged();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
