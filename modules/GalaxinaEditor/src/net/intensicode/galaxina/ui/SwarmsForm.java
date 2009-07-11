package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.Project;
import net.intensicode.galaxina.domain.ProjectListener;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.models.ListGroupModel;

import javax.swing.*;

public final class SwarmsForm extends FormBase implements ProjectListener, EditorStateListener, Identifiers
    {
    public SwarmsForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        final String stateID = EditorState.SELECTED_SWARM;
        myModel = new ListGroupModel<Swarm>( aCoreAPI, mySwarmsList, stateID );

        init( myAddButton, "AddSwarm" );
        init( myDeleteButton, "DeleteSwarm" );
        init( myCloneButton, "CloneSwarm" );
        init( myMoveUpButton, "MoveSwarmUp" );
        init( myMoveDownButton, "MoveSwarmDown" );

        init( myNameField, "SetSwarmName" );
        init( mySizeField, "SetSwarmSize" );
        init( myDelayField, "SetSwarmDelay" );

        final String enemyID = Identifiers.SELECTED_SWARM;
        new PropertyUpdater( aCoreAPI, myNameField, Swarm.NAME, enemyID ).attach( mySwarmsList );
        new IntegerPropertyUpdater( aCoreAPI, mySizeField, Swarm.SIZE, enemyID );
        new IntegerPropertyUpdater( aCoreAPI, myDelayField, Swarm.DELAY, enemyID );

        aCoreAPI.project().add( this );

        aCoreAPI.state().add( SELECTED_LEVEL, this );
        }

    public final JPanel getComponent()
        {
        return mySwarmsPanel;
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == SELECTED_LEVEL )
            {
            onLevelChanged( (Level) aOldValue, (Level) aNewValue );
            }
        }

    // From ProjectListener

    public final void onProjectOpened( final Project aProject )
        {
        mySwarmsList.setEnabled( true );
        if ( mySwarmsList.getModel().getSize() > 0 ) mySwarmsList.setSelectedIndex( 0 );
        }

    public final void onProjectClosed()
        {
        mySwarmsList.setEnabled( false );
        myCoreAPI.state().change( SELECTED_SWARM, null );
        }

    // Implementation

    private final void onLevelChanged( final Level aOldLevel, final Level aNewLevel )
        {
        if ( aOldLevel != null ) myModel.detach( aOldLevel.swarms() );
        if ( aNewLevel != null ) myModel.attach( aNewLevel.swarms() );
        myModel.initSelection();
        mySwarmsList.updateUI();
        }



    private JPanel mySwarmsPanel;

    private JList mySwarmsList;

    private JButton myAddButton;

    private JButton myCloneButton;

    private JButton myDeleteButton;

    private JButton myMoveUpButton;

    private JButton myMoveDownButton;

    private JTextField myNameField;

    private JTextField myDelayField;

    private JTextField mySizeField;

    private final ListGroupModel<Swarm> myModel;
    }
