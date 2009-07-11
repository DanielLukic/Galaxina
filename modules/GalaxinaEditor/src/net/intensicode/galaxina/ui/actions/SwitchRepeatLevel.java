package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Level;

public final class SwitchRepeatLevel extends RunnableAction implements EditorStateListener
    {
    public SwitchRepeatLevel( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        myCoreAPI.state().add( EditorState.SELECTED_LEVEL, this );
        }

    // From Runnable

    public final void run()
        {
        currentLevel().setRepeated( !currentLevel().isRepeated() );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final Level currentLevel = currentLevel();
        setEnabled( currentLevel != null && currentLevel.canChangeRepeatMode() );
        }

    // Implementation

    private final Level currentLevel()
        {
        return (Level) myCoreAPI.state().get( EditorState.SELECTED_LEVEL );
        }
    }