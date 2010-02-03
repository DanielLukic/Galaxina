package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class RunnableAction extends AbstractAction implements Runnable, Identifiers
    {
    public RunnableAction( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        myCoreAPI.configuration().init( this );
        }

    protected void runUnsafe() throws Exception
        {
        }

    // From Runnable

    public void run()
        {
        try
            {
            runUnsafe();
            }
        catch ( final Exception e )
            {
            e.printStackTrace();
            throw new RuntimeException( "nyi" );
            // log and show dialog - via centralized method
            }
        }

    // From ActionListener

    public final void actionPerformed( final ActionEvent aActionEvent )
        {
        myCoreAPI.ui().run( this );
        }



    protected final EditorCoreAPI myCoreAPI;
    }
