package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.PositionEx;

public final class PathDataListener implements EditorStateListener, GroupListener<PositionEx>
    {
    public PathDataListener( final InputHandlerContext aContext )
        {
        myContext = aContext;

        myContext.getCoreApi().state().add( Identifiers.SELECTED_PATH, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final Path oldPath = (Path) aOldValue;
        if ( oldPath != null ) oldPath.positions().remove( this );

        final Path newPath = (Path) aNewValue;
        if ( newPath != null ) newPath.positions().add( this );
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
