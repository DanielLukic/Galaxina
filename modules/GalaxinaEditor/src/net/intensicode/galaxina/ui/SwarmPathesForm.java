package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.*;
import net.intensicode.galaxina.ui.models.ComboBoxGroupModel;
import net.intensicode.galaxina.ui.models.ListGroupModel;

import javax.swing.*;
import java.awt.*;

public class SwarmPathesForm extends FormBase implements EditorStateListener, GroupListener<PositionEx>
    {
    public SwarmPathesForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        myModel = new ListGroupModel<Path>( aCoreAPI, mySwarmPathesList, Identifiers.SELECTED_SWARM_PATH );

        init( myReassignButton, "ReassignSwarmPath" );
        init( myAddButton, "AddSwarmPath" );
        init( myDeleteButton, "DeleteSwarmPath" );

        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );
        aCoreAPI.state().add( Identifiers.SELECTED_PATH, this );

        new ComboBoxGroupModel<Path>( aCoreAPI, myPathesComboBox, Identifiers.SELECTED_PATH ).attach( myCoreAPI.project().pathes() );
        myPathesComboBox.setAction( myCoreAPI.ui().action( "SelectPath" ) );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == Identifiers.SELECTED_SWARM )
            {
            onSwarmSelected( aOldValue, aNewValue );
            }
        else if ( aEventID == Identifiers.SELECTED_PATH )
            {
            onPathSelected( aOldValue, aNewValue );
            }
        }

    // From GroupListener

    public final void onAdded( final Group<PositionEx> aPositionExGroup, final PositionEx aNewEntry )
        {
        }

    public final void onRemoved( final Group<PositionEx> aPositionExGroup, final PositionEx aRemovedEntry )
        {
        }

    public final void onReplaced( final Group<PositionEx> aPositionExGroup, final PositionEx aOldEntry, final PositionEx aNewEntry, final Integer aIndex )
        {
        }

    public final void onDataChanged( final Group<PositionEx> aPositionExGroup )
        {
        }

    public final void onPropertyChanged( final Group<PositionEx> aPositionExGroup, final String aKey, final Object aValue )
        {
        mySwarmPathesList.updateUI();
        }

    // Implementation

    private final void onSwarmSelected( final Object aOldValue, final Object aNewValue )
        {
        final Swarm oldSwarm = (Swarm) aOldValue;
        if ( oldSwarm != null ) myModel.detach( oldSwarm.pathes() );

        final Swarm newSwarm = (Swarm) aNewValue;
        if ( newSwarm != null ) myModel.attach( newSwarm.pathes() );

        mySwarmPathesList.setEnabled( aNewValue != null );

        myModel.initSelection();
        mySwarmPathesList.updateUI();
        }

    private final void onPathSelected( final Object aOldValue, final Object aNewValue )
        {
        final Path oldPath = (Path) aOldValue;
        if ( oldPath != null ) oldPath.positions().remove( this );

        final Path newPath = (Path) aNewValue;
        if ( newPath != null ) newPath.positions().add( this );
        }



    private JPanel mySwarmPathesPanel;

    private JList mySwarmPathesList;

    private JButton myReassignButton;

    private JButton myAddButton;

    private JButton myDeleteButton;

    private JComboBox myPathesComboBox;

    private final ListGroupModel<Path> myModel;
    }
