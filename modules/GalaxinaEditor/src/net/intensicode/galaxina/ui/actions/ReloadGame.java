package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.EmbeddedGalaxina;

public final class ReloadGame extends RunnableAction implements EditorStateListener
    {
    public ReloadGame( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( MIDLET_RUNNING, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From RunnableAction

    protected final void runUnsafe() throws Exception
        {
        final EmbeddedGalaxina galaxina = myCoreAPI.project().galaxina();
        if ( galaxina == null ) return;
        galaxina.reloadGame();

        final int level = myCoreAPI.state().currentLevel().levelIndex();
        galaxina.switchToLevel( Math.max( 1, level ) );

        myCoreAPI.clearGameEnginePauseAndSingleStep();
        }
    }
