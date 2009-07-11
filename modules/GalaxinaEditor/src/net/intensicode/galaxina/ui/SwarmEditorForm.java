package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;

import javax.swing.*;

public final class SwarmEditorForm extends FormBase
    {
    public SwarmEditorForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        init( myZoomExactButton, "editors.ZoomExact" );
        init( myZoomToFitButton, "editors.ZoomToFit" );
        init( myZoomInButton, "editors.ZoomIn" );
        init( myZoomOutButton, "editors.ZoomOut" );

        init( myAddNodeButton, "swarmEditor.AddSwarmNode" );
        init( myRemoveNodeButton, "swarmEditor.RemoveSwarmNode" );
        init( mySetDefaultButton, "swarmEditor.SetDefaultSwarm" );
        init( myResetSwarmButton, "swarmEditor.ResetSwarm" );

        init( myMirrorXButton, "swarmEditor.MirrorX" );
        init( myMirrorYButton, "swarmEditor.MirrorY" );
        init( myShrinkXButton, "swarmEditor.ShrinkX" );
        init( myShrinkYButton, "swarmEditor.ShrinkY" );

        new MarkerPositionUpdater( myCoreAPI, myXTextField, MarkerPositionUpdater.ORDINATE_X, Identifiers.SELECTED_SWARM_MARKER );
        new MarkerPositionUpdater( myCoreAPI, myYTextField, MarkerPositionUpdater.ORDINATE_Y, Identifiers.SELECTED_SWARM_MARKER );
        }

    // Implementation

    private final void createUIComponents()
        {
        mySwarmEditor = new SwarmEditorPanel( myCoreAPI );
        }



    private JPanel mySwarmEditor;

    private JPanel mySwarmEditorPanel;

    private JButton myZoomExactButton;

    private JButton myZoomToFitButton;

    private JButton myZoomInButton;

    private JButton myZoomOutButton;

    private JButton myAddNodeButton;

    private JButton myRemoveNodeButton;

    private JButton mySetDefaultButton;

    private JButton myResetSwarmButton;

    private JButton myMirrorXButton;

    private JButton myMirrorYButton;

    private JTextField myXTextField;

    private JTextField myYTextField;

    private JButton myShrinkXButton;

    private JButton myShrinkYButton;
    }
