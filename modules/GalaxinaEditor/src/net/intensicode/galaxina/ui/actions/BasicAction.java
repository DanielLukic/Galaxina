package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;

import javax.swing.*;

public abstract class BasicAction extends AbstractAction implements Identifiers
    {
    public BasicAction( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        myCoreAPI.configuration().init( this );
        }

    // Protected Interface

    protected final EditorCoreAPI core()
        {
        return myCoreAPI;
        }



    protected final EditorCoreAPI myCoreAPI;
    }
