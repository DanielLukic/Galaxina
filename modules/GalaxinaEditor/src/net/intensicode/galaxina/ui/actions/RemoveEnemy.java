package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemies;
import net.intensicode.galaxina.domain.Enemy;

public final class RemoveEnemy extends RunnableAction implements EditorStateListener
    {
    public RemoveEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( SELECTED_ENEMY, this );
        }

    // From Runnable

    public final void run()
        {
        final Enemy enemy = myCoreAPI.state().currentEnemy();
        if ( enemy == null ) throw new IllegalStateException();

        final Enemies enemies = myCoreAPI.project().enemies();
        enemies.deleteEntry( enemy );

        if ( myCoreAPI.state().get( SELECTED_ENEMY ) == enemy )
            {
            myCoreAPI.state().change( SELECTED_ENEMY, null );
            }
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
