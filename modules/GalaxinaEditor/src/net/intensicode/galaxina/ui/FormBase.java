package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;

import javax.swing.*;

public abstract class FormBase
    {
    public FormBase( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // Protected Interface

    protected final void init( final AbstractButton aButton, final String aActionID )
        {
        aButton.setAction( myCoreAPI.ui().action( aActionID ) );
        aButton.setHideActionText( aButton.getIcon() != null );
        }

    protected final void init( final JTextField aTextField, final String aActionID )
        {
        aTextField.setAction( myCoreAPI.ui().action( aActionID ) );
        }

    protected final EditorCoreAPI myCoreAPI;
    }
