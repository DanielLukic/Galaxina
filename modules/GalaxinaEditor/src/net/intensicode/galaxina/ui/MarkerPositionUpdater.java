package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.SelectedMarker;

import javax.swing.*;

public final class MarkerPositionUpdater extends TextFieldUpdater implements EditorStateListener, Identifiers
    {
    public static final int ORDINATE_X = 1;

    public static final int ORDINATE_Y = 2;



    public MarkerPositionUpdater( final EditorCoreAPI aCoreAPI, final JTextField aTextField, final int aOrdinateID, final String aStateID )
        {
        super( aCoreAPI, aTextField );

        myTextField = aTextField;

        myOrdinateID = aOrdinateID;

        aCoreAPI.state().add( aStateID, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        myMarker = (SelectedMarker) aNewValue;
        if ( myMarker != null )
            {
            myTextField.setText( Integer.toString( getRawValue() ) );
            myTextField.setEditable( true );
            myTextField.setEnabled( true );
            }
        else
            {
            myTextField.setEditable( false );
            myTextField.setEnabled( false );
            }
        }

    // From TextFieldUpdater

    protected final String getOldValue()
        {
        if ( myMarker == null ) return null;
        return Integer.toString( getRawValue() );
        }

    protected final void setNewValue( final String aNewValue )
        {
        if ( myMarker == null ) return;
        setRawValue( Integer.parseInt( aNewValue ) );
        }

    // Implementation

    private final int getRawValue()
        {
        if ( myOrdinateID == ORDINATE_X ) return myMarker.marker.x;
        if ( myOrdinateID == ORDINATE_Y ) return myMarker.marker.y;
        throw new IllegalStateException();
        }

    private final void setRawValue( int aRawValue )
        {
        if ( myOrdinateID == ORDINATE_X ) myMarker.marker.x = aRawValue;
        if ( myOrdinateID == ORDINATE_Y ) myMarker.marker.y = aRawValue;
        throw new IllegalStateException();
        }



    private SelectedMarker myMarker;

    private final int myOrdinateID;

    private final JTextField myTextField;
    }
