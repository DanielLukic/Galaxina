package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemy;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.domain.SwarmEnemy;
import net.intensicode.galaxina.ui.models.ComboBoxGroupModel;
import net.intensicode.galaxina.ui.models.ListGroupModel;

import javax.swing.*;

public class SwarmEnemiesForm extends FormBase implements EditorStateListener
    {
    public SwarmEnemiesForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        final String stateID = EditorState.SELECTED_SWARM_ENEMY;
        myModel = new ListGroupModel<SwarmEnemy>( aCoreAPI, mySwarmEnemiesList, stateID );

        init( myReassignButton, "ReassignSwarmEnemy" );
        init( myAddButton, "AddSwarmEnemy" );
        init( myDeleteButton, "DeleteSwarmEnemy" );

        aCoreAPI.state().add( SELECTED_SWARM, this );
        aCoreAPI.state().add( SELECTED_SWARM_ENEMY, this );

        new ComboBoxGroupModel<Enemy>( aCoreAPI, myEnemyComboBox, SELECTED_ENEMY ).attach( myCoreAPI.project().enemies() );
        myEnemyComboBox.setAction( myCoreAPI.ui().action( "SelectEnemy" ) );

        mySyncModeBox.setAction( myCoreAPI.ui().action( "SetSwarmEnemySyncMode" ) );
        mySyncModeBox.addItem( SwarmEnemy.INDEPENDANT_MODE );
        mySyncModeBox.addItem( SwarmEnemy.SYNC_BY_PATH );
        mySyncModeBox.addItem( SwarmEnemy.FOLLOW_LEFT_SIDE );
        mySyncModeBox.addItem( SwarmEnemy.FOLLOW_RIGHT_SIDE );
        mySyncModeBox.addItem( SwarmEnemy.SYNC_SPEED );

        new BooleanPropertyUpdater( myCoreAPI, myZeroDelayCheckBox, SwarmEnemy.ZERO_DELAY, SELECTED_SWARM_ENEMY );
        }

    public final JPanel getComponent()
        {
        return mySwarmEnemiesPanel;
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == SELECTED_SWARM ) onSwarmChanged( aOldValue, aNewValue );
        if ( aEventID == SELECTED_SWARM_ENEMY ) onSwarmEnemyChanged( aNewValue );
        }

    // Implementation

    private final void onSwarmChanged( final Object aOldValue, final Object aNewValue )
        {
        final Swarm oldSwarm = (Swarm) aOldValue;
        if ( aOldValue != null ) myModel.detach( oldSwarm.enemies() );

        final Swarm newSwarm = (Swarm) aNewValue;
        if ( aNewValue != null ) myModel.attach( newSwarm.enemies() );

        mySwarmEnemiesList.setEnabled( aNewValue != null );

        myModel.initSelection();
        mySwarmEnemiesList.updateUI();
        }

    private final void onSwarmEnemyChanged( final Object aNewValue )
        {
        final SwarmEnemy swarmEnemy = (SwarmEnemy) aNewValue;
        if ( swarmEnemy == null ) return;
        mySyncModeBox.setSelectedItem( swarmEnemy.getSyncMode() );
        }



    private JPanel mySwarmEnemiesPanel;

    private JList mySwarmEnemiesList;

    private JButton myReassignButton;

    private JButton myAddButton;

    private JButton myDeleteButton;

    private JComboBox myEnemyComboBox;

    private JComboBox mySyncModeBox;

    private JCheckBox myZeroDelayCheckBox;

    private final ListGroupModel<SwarmEnemy> myModel;
    }
