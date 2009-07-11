package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Swarm;

import java.awt.event.ActionEvent;

public final class SetSwarmSize extends BasicAction implements EditorStateListener
    {
    public SetSwarmSize( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( false );
        core().state().add( EditorState.SELECTED_SWARM, this );
        }

    // From ActionListener

    public final void actionPerformed( final ActionEvent e )
        {
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
