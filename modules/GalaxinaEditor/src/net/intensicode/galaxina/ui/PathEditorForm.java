package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;

import javax.swing.*;

public final class PathEditorForm extends FormBase
    {
    public PathEditorForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        init( myZoomExactButton, "editors.ZoomExact" );
        init( myZoomToFitButton, "editors.ZoomToFit" );
        init( myZoomInButton, "editors.ZoomIn" );
        init( myZoomOutButton, "editors.ZoomOut" );

        init( myAddNodeButton, "pathEditor.AddPathNode" );
        init( myRemoveNodeButton, "pathEditor.RemovePathNode" );
        init( mySetDefaultButton, "pathEditor.SetDefaultPath" );
        init( myResetPathButton, "pathEditor.ResetPath" );

        init( myMirrorXButton, "pathEditor.MirrorX" );
        init( myMirrorYButton, "pathEditor.MirrorY" );

        new MarkerPositionUpdater( myCoreAPI, myXTextField, MarkerPositionUpdater.ORDINATE_X, Identifiers.SELECTED_PATH_MARKER );
        new MarkerPositionUpdater( myCoreAPI, myYTextField, MarkerPositionUpdater.ORDINATE_Y, Identifiers.SELECTED_PATH_MARKER );
        }

    // Implementation

    private final void createUIComponents()
        {
        myPathEditor = new PathEditorPanel( myCoreAPI );
        }



    private JPanel myPathEditor;

    private JPanel myPathEditorPanel;

    private JButton myZoomExactButton;

    private JButton myZoomToFitButton;

    private JButton myZoomInButton;

    private JButton myZoomOutButton;

    private JButton myAddNodeButton;

    private JButton myRemoveNodeButton;

    private JButton mySetDefaultButton;

    private JTextField myXTextField;

    private JTextField myYTextField;

    private JButton myResetPathButton;

    private JButton myMirrorXButton;

    private JButton myMirrorYButton;
    }
