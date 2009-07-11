package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.NormalSwarm;

public final class AddSwarm extends RunnableAction implements EditorStateListener
    {
    public AddSwarm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( EditorState.SELECTED_LEVEL, this );
        }

    // From Runnable

    public final void run()
        {
        final Level level = myCoreAPI.state().currentLevel();
        level.swarms().addEntry( new NormalSwarm( myCoreAPI ) );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
