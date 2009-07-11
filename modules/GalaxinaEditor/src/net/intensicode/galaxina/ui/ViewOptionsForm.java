package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;

import javax.swing.*;
import java.awt.*;

public final class ViewOptionsForm extends FormBase implements Identifiers, EditorStateListener
    {
    public ViewOptionsForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        init( myShowGridCheckBox, "editors.ShowGrid" );
        init( myAlignToGridCheckBox, "editors.AlignToGrid" );
        init( myShowPathesCheckBox, "editors.ShowPathes" );
        init( mySmoothPathesCheckBox, "editors.SmoothPathes" );

        myCoreAPI.state().add( SHOW_GRID, this );
        myCoreAPI.state().add( ALIGN_TO_GRID, this );
        myCoreAPI.state().add( SHOW_PATHES, this );
        myCoreAPI.state().add( SMOOTH_PATHES, this );

        onStateChanged( null, null, null );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final Boolean showGrid = (Boolean) myCoreAPI.state().get( SHOW_GRID );
        myShowGridCheckBox.setSelected( showGrid );
        final Boolean alignToGrid = (Boolean) myCoreAPI.state().get( ALIGN_TO_GRID );
        myAlignToGridCheckBox.setSelected( alignToGrid );
        final Boolean showPathes = (Boolean) myCoreAPI.state().get( SHOW_PATHES );
        myShowPathesCheckBox.setSelected( showPathes );
        final Boolean smoothPathes = (Boolean) myCoreAPI.state().get( SMOOTH_PATHES );
        mySmoothPathesCheckBox.setSelected( smoothPathes );
        }



    private JPanel myViewOptionsPanel;

    private JCheckBox myShowGridCheckBox;

    private JCheckBox myAlignToGridCheckBox;

    private JCheckBox myShowPathesCheckBox;

    private JCheckBox mySmoothPathesCheckBox;
    }
