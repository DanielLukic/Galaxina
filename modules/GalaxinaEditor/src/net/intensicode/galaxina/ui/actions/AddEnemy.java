package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemies;
import net.intensicode.galaxina.domain.NormalEnemy;

public final class AddEnemy extends RunnableAction implements EditorStateListener
    {
    public AddEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        final NormalEnemy newEnemy = new NormalEnemy( "Unnamed enemy" );

        final Enemies enemies = myCoreAPI.project().enemies();
        enemies.addEntry( newEnemy );

        myCoreAPI.state().change( SELECTED_ENEMY, newEnemy );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
