package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.Pathes;
import net.intensicode.galaxina.domain.Project;
import net.intensicode.galaxina.domain.ProjectListener;
import net.intensicode.galaxina.ui.models.ListGroupModel;

import javax.swing.*;
import java.awt.*;

public final class PathesForm extends FormBase implements ProjectListener
    {
    public PathesForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        final String stateID = EditorState.SELECTED_PATH;
        final Pathes pathes = myCoreAPI.project().pathes();
        new ListGroupModel<Path>( aCoreAPI, myPathesList, stateID ).attach( pathes );

        init( myAddButton, "AddPath" );
        init( myDeleteButton, "DeletePath" );
        init( myCloneButton, "ClonePath" );
        init( myMoveUpButton, "MovePathUp" );
        init( myMoveDownButton, "MovePathDown" );

        new PropertyUpdater( aCoreAPI, myNameField, Path.NAME, Identifiers.SELECTED_PATH ).attach( myPathesList );

        aCoreAPI.project().add( this );
        }

    // From ProjectListener

    public final void onProjectOpened( final Project aProject )
        {
        myPathesList.setEnabled( true );
        if ( myPathesList.getModel().getSize() > 0 ) myPathesList.setSelectedIndex( 0 );
        }

    public final void onProjectClosed()
        {
        myPathesList.setEnabled( false );
        myCoreAPI.state().change( Identifiers.SELECTED_PATH, null );
        }



    private JPanel myPathesPanel;

    private JList myPathesList;

    private JButton myAddButton;

    private JButton myDeleteButton;

    private JButton myCloneButton;

    private JButton myMoveUpButton;

    private JButton myMoveDownButton;

    private JTextField myNameField;
    }
