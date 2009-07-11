package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemies;
import net.intensicode.galaxina.domain.NormalEnemy;

public final class SetDefaultEnemies extends RunnableAction implements EditorStateListener
    {
    public SetDefaultEnemies( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.project().enemies().clear();

        addEntry( "Scout", 1, 10, "enemy1.png" );
        addEntry( "Fighter", 1, 15, "enemy2.png" );
        addEntry( "Grabber", 1, 20, "enemy3.png" );
        addEntry( "Cruiser", 2, 30, "enemy4.png" );
        addEntry( "Blazer", 2, 40, "enemy5.png" );
        addEntry( "Mother", 3, 50, "enemy6.png" );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // Implementation

    private final void addEntry( final String aName, final int aHits, final int aBaseScore, final String aFileName )
        {
        final Enemies enemies = myCoreAPI.project().enemies();
        enemies.addEntry( new NormalEnemy( aName, aHits, aBaseScore ).setFileName( aFileName ) );
        }
    }
