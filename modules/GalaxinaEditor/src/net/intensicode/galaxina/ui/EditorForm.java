package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorIdentifiers;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class EditorForm implements EditorStateListener, ChangeListener
    {
    public EditorForm( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;

        aCoreAPI.state().add( EDITOR_TAB, this );

        myTabbedPane.addChangeListener( this );
        }

    public JComponent getComponent()
        {
        return myEditorFormPanel;
        }

    public void switchTo( final String aTabID )
        {
        myCoreAPI.state().change( Identifiers.EDITOR_TAB, aTabID );
        }

    // From ChangeListener

    public final void stateChanged( final ChangeEvent e )
        {
        switch ( myTabbedPane.getSelectedIndex() )
            {
            case 0:
                myCoreAPI.state().change( Identifiers.EDITOR_TAB, EditorIdentifiers.PATH_EDITOR );
                break;
            case 1:
                myCoreAPI.state().change( Identifiers.EDITOR_TAB, EditorIdentifiers.SWARM_EDITOR );
                break;
            case 2:
                myCoreAPI.state().change( Identifiers.EDITOR_TAB, EditorIdentifiers.ENEMY_EDITOR );
                break;
            case 3:
                myCoreAPI.state().change( Identifiers.EDITOR_TAB, EditorIdentifiers.GAME_VIEW );
                break;
            }
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aNewValue == null ) throw new IllegalArgumentException();

        if ( aNewValue == EditorIdentifiers.PATH_EDITOR ) myTabbedPane.setSelectedIndex( 0 );
        if ( aNewValue == EditorIdentifiers.SWARM_EDITOR ) myTabbedPane.setSelectedIndex( 1 );
        if ( aNewValue == EditorIdentifiers.ENEMY_EDITOR ) myTabbedPane.setSelectedIndex( 2 );
        if ( aNewValue == EditorIdentifiers.GAME_VIEW ) myTabbedPane.setSelectedIndex( 3 );
        }

    // Implementation

    private final void createUIComponents()
        {
        myLevelConfigurationForm = new LevelConfigurationForm( myCoreAPI );
        mySwarmsForm = new SwarmsForm( myCoreAPI );
        mySwarmPathesForm = new SwarmPathesForm( myCoreAPI );
        myPathesForm = new PathesForm( myCoreAPI );
        mySwarmEnemiesForm = new SwarmEnemiesForm( myCoreAPI );
        myEnemiesForm = new EnemiesForm( myCoreAPI );
        myPathEditorForm = new PathEditorForm( myCoreAPI );
        mySwarmEditorForm = new SwarmEditorForm( myCoreAPI );
        myGameViewForm = new GameViewForm( myCoreAPI );
        myViewOptionsForm = new ViewOptionsForm( myCoreAPI );
        }



    private JPanel myEditorFormPanel;

    private LevelConfigurationForm myLevelConfigurationForm;

    private SwarmsForm mySwarmsForm;

    private GameViewForm myGameViewForm;

    private PathesForm myPathesForm;

    private EnemiesForm myEnemiesForm;

    private SwarmPathesForm mySwarmPathesForm;

    private SwarmEnemiesForm mySwarmEnemiesForm;

    private JTabbedPane myTabbedPane;

    private PathEditorForm myPathEditorForm;

    private SwarmEditorForm mySwarmEditorForm;

    private ViewOptionsForm myViewOptionsForm;

    private final EditorCoreAPI myCoreAPI;
    }
