package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Swarm;

public final class SelectedSwarmHandler extends SwarmHandler implements EditorStateListener
    {
    public SelectedSwarmHandler( final InputHandlerContext aContext )
        {
        super( aContext );

        aContext.getCoreApi().state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        attach( (Swarm) aNewValue );
        }
    }
