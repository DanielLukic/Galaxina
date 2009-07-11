package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Enemy;
import net.intensicode.galaxina.domain.Project;
import net.intensicode.galaxina.domain.ProjectListener;
import net.intensicode.galaxina.ui.models.EnemiesModel;

import javax.swing.*;
import java.io.File;

public final class EnemiesForm extends FormBase implements ProjectListener, EditorStateListener
    {
    public EnemiesForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        myEnemiesModel = new EnemiesModel( aCoreAPI );
        myEnemiesList.setModel( myEnemiesModel );
        myEnemiesList.addListSelectionListener( myEnemiesModel );
        myEnemiesList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        aCoreAPI.state().add( Identifiers.SELECTED_ENEMY, this );

        init( myFileNameButton, "GetEnemyFileName" );
        init( myAddButton, "AddEnemy" );
        init( myRemoveButton, "RemoveEnemy" );
        init( myDefaultsButton, "SetDefaultEnemies" );

        final String enemyID = Identifiers.SELECTED_ENEMY;
        new PropertyUpdater( aCoreAPI, myNameField, Enemy.NAME, enemyID ).attach( myEnemiesList );
        new PropertyUpdater( aCoreAPI, myFileNameField, Enemy.FILENAME, enemyID );
        new IntegerPropertyUpdater( aCoreAPI, myHitsField, Enemy.HITS, enemyID );
        new IntegerPropertyUpdater( aCoreAPI, myBaseScoreField, Enemy.BASESCORE, enemyID );

        aCoreAPI.project().add( this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aNewValue != null )
            {
            final Enemy enemy = (Enemy) aNewValue;
            final int index = myCoreAPI.project().enemies().indexOf( enemy );
            if ( index == -1 ) throw new IllegalArgumentException();

            myEnemiesList.setSelectedIndex( index );

            try
                {
                final String filename = (String) enemy.getProperty( Enemy.FILENAME );
                final File file = new File( myCoreAPI.project().folder(), filename );
                final ImageIcon icon = new ImageIcon( file.getPath() );
                final ImagePanel panel = (ImagePanel) myImagePanel;
                panel.setIcon( icon );
                }
            catch ( final Throwable t )
                {
                throw new RuntimeException( "nyi" );
                }
            }
        else
            {
            myEnemiesList.setSelectedIndices( new int[0] );
            }
        }

    // From ProjectListener

    public final void onProjectOpened( final Project aProject )
        {
        if ( aProject.enemies().size() > 0 ) myEnemiesList.setSelectedIndex( 0 );
        myEnemiesList.setEnabled( true );
        }

    public final void onProjectClosed()
        {
        myEnemiesList.setEnabled( false );
        }

    // Implementation

    private final void createUIComponents()
        {
        myImagePanel = new ImagePanel();
        }



    private JPanel myEnemiesPanel;

    private JList myEnemiesList;

    private JFormattedTextField myNameField;

    private JFormattedTextField myFileNameField;

    private JButton myFileNameButton;

    private JFormattedTextField myHitsField;

    private JFormattedTextField myBaseScoreField;

    private JPanel myImagePanel;

    private JButton myAddButton;

    private JButton myRemoveButton;

    private JButton myDefaultsButton;

    private final EnemiesModel myEnemiesModel;
    }
