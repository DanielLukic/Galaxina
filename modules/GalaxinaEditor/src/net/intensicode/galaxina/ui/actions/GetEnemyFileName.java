package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemy;
import net.intensicode.galaxina.domain.GroupEntryEx;

import javax.swing.*;
import java.io.File;

public final class GetEnemyFileName extends RunnableAction implements EditorStateListener
    {
    public GetEnemyFileName( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        myChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        myChooser.setMultiSelectionEnabled( false );
        myChooser.setVisible( true );

        aCoreAPI.state().add( SELECTED_ENEMY, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From RunnableAction

    public final void runUnsafe() throws Exception
        {
        final File baseFolder = myCoreAPI.project().folder();
        myChooser.setCurrentDirectory( baseFolder );

        final int choice = myChooser.showOpenDialog( myCoreAPI.ui().frame() );
        if ( choice == JFileChooser.APPROVE_OPTION )
            {
            final String enemyFileName = myChooser.getSelectedFile().getName();
            final File checkFile = new File( baseFolder, enemyFileName );
            if ( checkFile.exists() == false || checkFile.isFile() == false )
                {
                throw new IllegalArgumentException( checkFile.toString() );
                }

            final GroupEntryEx entry = (GroupEntryEx) myCoreAPI.state().get( SELECTED_ENEMY );
            entry.setProperty( Enemy.FILENAME, enemyFileName );
            }
        }

    private final JFileChooser myChooser = new JFileChooser();
    }
