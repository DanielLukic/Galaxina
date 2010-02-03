package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.*;

import javax.swing.*;
import javax.swing.event.*;

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
            myCoreAPI.engine().slowDownInTicks = value;
            mySlowDownField.setText( Integer.toString( value ) );
            }
        } );

        myCoreAPI.state().add( PROJECT, this );
        myCoreAPI.state().add( MIDLET_CONTAINER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == MIDLET_RUNNING )
            {
            final boolean running = aNewValue != null && (Boolean) aNewValue;
            if ( running )
                {
                mySlowDownSlider.setMinimum( -myCoreAPI.timing().ticksPerSecond / 6 );
                mySlowDownSlider.setMaximum( myCoreAPI.timing().ticksPerSecond / 4 );
                mySlowDownSlider.setValue( 0 );
                }
            }

        if ( aEventID == MIDLET_CONTAINER )
            {
            final boolean enabled = aNewValue != null;
            mySlowDownSlider.setEnabled( enabled );
            mySlowDownField.setEnabled( enabled );
            }
        }

    // Implementation

    private void createUIComponents()
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
