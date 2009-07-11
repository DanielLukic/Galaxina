package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Level;

public final class DeleteLevel extends RunnableAction implements EditorStateListener
    {
    public DeleteLevel( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        myCoreAPI.state().add( EditorState.SELECTED_LEVEL, this );
        }

    // From Runnable

    public final void run()
        {
        final Level level = myCoreAPI.state().currentLevel();
        myCoreAPI.project().levels().deleteEntry( level );

        final Level proxy = myCoreAPI.project().levels().at( level.levelIndex() );
        myCoreAPI.state().change( SELECTED_LEVEL, proxy );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final Level newLevel = (Level) aNewValue;
        if ( newLevel != null )
            {
            final boolean isProxy = newLevel.canBeReplacedByClonedSelf();
            final boolean isDefault = newLevel.levelIndex() == 0;
            setEnabled( newLevel != null && !isProxy && !isDefault );
            }
        else
            {
            setEnabled( false );
            }
        }
    }
