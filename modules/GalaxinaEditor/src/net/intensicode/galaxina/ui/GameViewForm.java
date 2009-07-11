package net.intensicode.galaxina.ui;

import net.intensicode.core.Engine;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class GameViewForm extends FormBase implements EditorStateListener
    {
    public GameViewForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        init( myZoomExactButton, "ZoomGameExact" );
        init( myZoomToFitButton, "ZoomGameToFit" );
        init( myStepButton, "StepGame" );
        init( myPauseButton, "PauseGame" );
        init( myReloadButton, "ReloadGame" );

        mySlowDownSlider.addChangeListener( new ChangeListener()
        {
        public final void stateChanged( final ChangeEvent aEvent )
            {
            final int value = mySlowDownSlider.getValue();
            Engine.slowDownInTicks = value;
            mySlowDownField.setText( Integer.toString( value ) );
            }
        } );

        myCoreAPI.state().add( PROJECT, this );
        myCoreAPI.state().add( MIDLET_CONTAINER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final boolean enabled = aNewValue != null;
        if ( aEventID == PROJECT )
            {
            if ( enabled )
                {
                mySlowDownSlider.setMinimum( -Engine.ticksPerSecond / 6 );
                mySlowDownSlider.setMaximum( Engine.ticksPerSecond / 4 );
                mySlowDownSlider.setValue( 0 );
                }
            }
        if ( aEventID == MIDLET_CONTAINER )
            {
            mySlowDownSlider.setEnabled( enabled );
            mySlowDownField.setEnabled( enabled );
            }
        }

    // Implementation

    private final void createUIComponents()
        {
        myGameView = new GamePanel( myCoreAPI );
        }



    private JPanel myGameViewPanel;

    private JPanel myGameView;

    private JButton myZoomExactButton;

    private JButton myZoomToFitButton;

    private JButton myReloadButton;

    private JButton myPauseButton;

    private JButton myStepButton;

    private JSlider mySlowDownSlider;

    private JTextField mySlowDownField;
    }
