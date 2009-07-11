package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ToggleButtonUpdater implements ActionListener
    {
    public ToggleButtonUpdater( final EditorCoreAPI aCoreAPI, final JToggleButton aToggleButton )
        {
        myCoreAPI = aCoreAPI;
        myToggleButton = aToggleButton;
        myToggleButton.addActionListener( this );
        }

    // From ActionListener

    public final void actionPerformed( final ActionEvent aEvent )
        {
        Log.get().debug( aEvent.toString() );

        final Boolean value = myToggleButton.isSelected();
        final Boolean old = getOldValue();
        if ( old != null && old.equals( value ) ) return;
        setNewValue( value );
        }

    // Protected Interface

    protected abstract Boolean getOldValue();

    protected abstract void setNewValue( final Boolean aNewValue );



    protected final JToggleButton myToggleButton;

    protected final EditorCoreAPI myCoreAPI;
    }
