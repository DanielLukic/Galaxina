package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.*;
import net.intensicode.galaxina.ui.models.LevelIndexModel;
import net.intensicode.galaxina.util.Log;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class LevelConfigurationForm extends FormBase implements ChangeListener, ProjectListener, GroupListener<Level>, EditorStateListener
    {
    public LevelConfigurationForm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        init( myRepeatCheckBox, "SwitchRepeatLevel" );
        init( myCloneButton, "CloneLevel" );
        init( myDeleteButton, "DeleteLevel" );

        final LevelInfoUpdater levelInfoUpdater = new LevelInfoUpdater( myCoreAPI, myLevelInfo );
        myLevelInfo.addActionListener( levelInfoUpdater );
        myLevelInfo.addFocusListener( levelInfoUpdater );

        final LevelRepeatUpdater levelRepeatUpdater = new LevelRepeatUpdater( myCoreAPI, myRepeatInterval.getDocument() );
        myRepeatInterval.addActionListener( levelRepeatUpdater );
        myRepeatInterval.addFocusListener( levelRepeatUpdater );

        new BooleanPropertyUpdater( aCoreAPI, myChallengingStage, Level.CHALLENGING_STAGE, Identifiers.SELECTED_LEVEL );

        final Project project = myCoreAPI.project();
        myIndexModel = new LevelIndexModel( project.levels() );
        myIndexModel.addChangeListener( this );

        project.add( this );
        project.levels().add( this );

        aCoreAPI.state().add( SELECTED_LEVEL, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aNewValue == null ) return;
        myIndexModel.setValue( aNewValue );
        }

    // From ChangeListener

    public final void stateChanged( final ChangeEvent e )
        {
        fireCurrentLevelChanged();
        }

    // From ProjectListener

    public final void onProjectOpened( final Project aProject )
        {
        myIndexModel.reset();
        myLevelIndex.setModel( myIndexModel );
        myLevelIndex.setEnabled( true );

        fireCurrentLevelChanged();
        }

    public final void onProjectClosed()
        {
        myLevelIndex.setEnabled( false );
        myLevelIndex.setModel( new SpinnerNumberModel() );

        myCoreAPI.state().change( Identifiers.SELECTED_LEVEL, null );
        }

    // From LevelsListener

    public final void onAdded( final Group<Level> aLevelGroup, final Level aNewEntry )
        {
        }

    public final void onRemoved( final Group<Level> aLevelGroup, final Level aRemovedEntry )
        {
        }

    public final void onReplaced( final Group<Level> aLevelGroup, final Level aOldLevel, final Level aLevel, final Integer aIndex )
        {
        final Level currentLevel = (Level) myIndexModel.getValue();
        if ( currentLevel.levelIndex() == aIndex.intValue() )
            {
            if ( myIndexModel.getValue() != aLevel )
                {
                myIndexModel.switchTo( aLevel );
                }
            }
        fireCurrentLevelChanged();
        }

    public final void onDataChanged( final Group<Level> aLevelGroup )
        {
        LOG.debug( "React to this?" );
        }

    // Implementation

    private final void fireCurrentLevelChanged()
        {
        final Level currentLevel = (Level) myLevelIndex.getModel().getValue();
        fireCurrentLevelChanged( currentLevel );
        }

    private final void fireCurrentLevelChanged( final Level aNewLevel )
        {
        myCoreAPI.state().change( EditorState.SELECTED_LEVEL, aNewLevel );

        if ( aNewLevel == null ) return;

        myRepeatCheckBox.setSelected( aNewLevel.isRepeated() );
        myRepeatInterval.setText( Integer.toString( aNewLevel.repeatInterval() ) );
        myRepeatInterval.setEnabled( aNewLevel.canChangeRepeatMode() );
        myRepeatInterval.setEditable( aNewLevel.canChangeRepeatMode() );
        myLevelInfo.setText( aNewLevel.levelInfo() );
        myLevelInfo.setEditable( aNewLevel.canChangeLevelInfo() );
        myChallengingStage.setEnabled( true );
        }



    private JPanel myLevelConfigurationPanel;

    private JSpinner myLevelIndex;

    private JCheckBox myRepeatCheckBox;

    private JFormattedTextField myRepeatInterval;

    private JTextField myLevelInfo;

    private JButton myCloneButton;

    private JCheckBox myChallengingStage;

    private JButton myDeleteButton;

    private final LevelIndexModel myIndexModel;

    private static final Log LOG = Log.get();
    }
