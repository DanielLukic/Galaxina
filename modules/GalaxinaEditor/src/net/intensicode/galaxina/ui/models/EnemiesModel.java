package net.intensicode.galaxina.ui.models;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.domain.Enemies;
import net.intensicode.galaxina.domain.Enemy;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class EnemiesModel extends AbstractListModel implements ListSelectionListener, GroupListener<Enemy>
    {
    public EnemiesModel( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        myEnemies = aCoreAPI.project().enemies();
        myEnemies.add( this );
        }

    public final int findIndexOf( final Enemy aEnemy )
        {
        return myEnemies.indexOf( aEnemy );
        }

    // From ListSelectionListener

    public final void valueChanged( final ListSelectionEvent aEvent )
        {
        if ( aEvent.getValueIsAdjusting() ) return;
        final JList source = (JList) aEvent.getSource();
        final int index = source.getSelectedIndex();
        final Object value = index >= 0 ? getElementAt( index ) : null;
        myCoreAPI.state().change( EditorState.SELECTED_ENEMY, value );
        }

    // From ListModel

    public final int getSize()
        {
        return myEnemies.size();
        }

    public final Object getElementAt( final int aIndex )
        {
        return myEnemies.at( aIndex );
        }

    // From GroupListener

    public final void onAdded( final Group<Enemy> aEnemyGroup, final Enemy aNewEntry )
        {
        fireContentsChanged( this, 0, myEnemies.size() );
        }

    public final void onRemoved( final Group<Enemy> aEnemyGroup, final Enemy aRemovedEntry )
        {
        fireContentsChanged( this, 0, myEnemies.size() );
        }

    public final void onReplaced( final Group<Enemy> aEnemyGroup, final Enemy aOldEntry, final Enemy aNewEntry, final Integer aIndex )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void onDataChanged( final Group<Enemy> aEnemyGroup )
        {
        fireContentsChanged( this, 0, myEnemies.size() );
        }

    public final void onPropertyChanged( final Group<Enemy> aEnemyGroup, final String aKey, final Object aValue )
        {
        throw new RuntimeException( "nyi" );
        }



    private final Enemies myEnemies;

    private final EditorCoreAPI myCoreAPI;
    }
