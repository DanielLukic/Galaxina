package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.Swarm;

public final class SwarmDataListener implements EditorStateListener, GroupListener<PositionEx>
    {
    public SwarmDataListener( final InputHandlerContext aContext )
        {
        myContext = aContext;

        myContext.getCoreApi().state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final Swarm oldSwarm = (Swarm) aOldValue;
        if ( oldSwarm != null ) oldSwarm.positions().remove( this );

        final Swarm newSwarm = (Swarm) aNewValue;
        if ( newSwarm != null ) newSwarm.positions().add( this );
        }

    // From GroupListener

    public final void onAdded( final Group<PositionEx> aPositionExGroup, final PositionEx aNewEntry )
        {
        myContext.updateUI();
        }

    public final void onRemoved( final Group<PositionEx> aPositionExGroup, final PositionEx aRemovedEntry )
        {
        myContext.updateUI();
        }

    public final void onReplaced( final Group<PositionEx> aPositionExGroup, final PositionEx aOldEntry, final PositionEx aNewEntry, final Integer aIndex )
        {
        myContext.updateUI();
        }

    public final void onDataChanged( final Group<PositionEx> aPositionExGroup )
        {
        myContext.updateUI();
        }

    public final void onPropertyChanged( final Group<PositionEx> aPositionExGroup, final String aKey, final Object aValue )
        {
        }

    private final InputHandlerContext myContext;
    }
