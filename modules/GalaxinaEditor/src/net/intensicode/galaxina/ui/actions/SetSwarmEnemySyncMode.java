package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.SwarmEnemy;
import net.intensicode.galaxina.domain.SyncMode;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class SetSwarmEnemySyncMode extends BasicAction implements EditorStateListener
    {
    public SetSwarmEnemySyncMode( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( SELECTED_SWARM_ENEMY, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From RunnableAction

    public final void actionPerformed( final ActionEvent aActionEvent )
        {
        final JComboBox comboBox = (JComboBox) aActionEvent.getSource();
        final SyncMode syncMode = (SyncMode) comboBox.getModel().getSelectedItem();
        final SwarmEnemy swarmEnemy = core().state().currentSwarmEnemy();
        if ( swarmEnemy != null ) swarmEnemy.setSyncMode( syncMode );
        }
    }
