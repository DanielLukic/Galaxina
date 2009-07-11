package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

import javax.swing.*;
import java.io.File;

public final class OpenProject extends RunnableAction
    {
    public OpenProject( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( true );

        myChooser.setCurrentDirectory( getDefaultDirectory() );
        myChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        myChooser.setMultiSelectionEnabled( false );
        myChooser.setVisible( true );
        }

    // From RunnableAction

    public final void runUnsafe() throws Exception
        {
        final int choice = myChooser.showOpenDialog( myCoreAPI.ui().frame() );
        if ( choice == JFileChooser.APPROVE_OPTION )
            {
            final File projectDirectory = myChooser.getSelectedFile();
            if ( projectDirectory.exists() == false || projectDirectory.isDirectory() == false )
                {
                throw new IllegalArgumentException( projectDirectory.toString() );
                }
            myCoreAPI.open( projectDirectory );
            }
        }

    // Implementation

    private static final File getDefaultDirectory()
        {
        final File currentDirectory = new File( "." );
        final File defaultProjectDirectory = new File( currentDirectory, "res/Galaxina" );
        if ( defaultProjectDirectory.exists() && defaultProjectDirectory.isDirectory() )
            {
            return defaultProjectDirectory;
            }
        final File defaultFallbackDirectory = new File( currentDirectory, "res" );
        if ( defaultFallbackDirectory.exists() && defaultFallbackDirectory.isDirectory() )
            {
            return defaultFallbackDirectory;
            }
        return currentDirectory;
        }



    private final JFileChooser myChooser = new JFileChooser();
    }
