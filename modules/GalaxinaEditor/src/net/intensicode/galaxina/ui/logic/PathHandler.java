package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedPathMarker;
import net.intensicode.galaxina.domain.ListOfPositions;
import net.intensicode.util.*;

import java.awt.event.MouseEvent;

public class PathHandler implements InputHandler
    {
    public PathHandler( final InputHandlerContext aContext )
        {
        myContext = aContext;
        }

    public final void attach( final Path aPath )
        {
        myPath = aPath;
        }

    // From InputHandler

    public boolean isActive()
        {
        return myPath != null;
        }

    public final boolean mouseClicked( final MouseEvent aEvent )
        {
        final PositionF position = myContext.getTransformer().toGame( aEvent.getPoint() );
        final PositionEx selectedMarker = findMarkerFor( position );
        if ( selectedMarker != null )
            {
            myContext.getCoreApi().state().change( Identifiers.SELECTED_PATH_MARKER, new SelectedPathMarker( myPath, selectedMarker ) );
            myContext.getCoreApi().state().change( Identifiers.SELECTED_PATH, myPath );
            }
        return selectedMarker != null;
        }

    public final boolean mousePressed( final MouseEvent aEvent )
        {
        final PositionF position = myContext.getTransformer().toGame( aEvent.getPoint() );
        myDraggedMarker = findMarkerFor( position );
        return myDraggedMarker != null;
        }

    public final boolean mouseReleased( final MouseEvent aEvent )
        {
        final PositionF draggedMarker = myDraggedMarker;
        myDraggedMarker = null;
        return draggedMarker != null;
        }

    public final boolean mouseDragged( final MouseEvent aEvent )
        {
        if ( myDraggedMarker == null ) return false;

        final PositionF position = myContext.getTransformer().toGame( aEvent.getPoint() );
        if ( myDraggedMarker.equals( position ) ) return true;
        myDraggedMarker.setTo( position );

        final SelectedPathMarker pathMarker = (SelectedPathMarker) myContext.getCoreApi().state().get( Identifiers.SELECTED_PATH_MARKER );
        if ( pathMarker != null && pathMarker.marker == myDraggedMarker )
            {
            final SelectedPathMarker newPathMarker = new SelectedPathMarker( pathMarker.path, pathMarker.marker );
            myContext.getCoreApi().state().change( Identifiers.SELECTED_PATH_MARKER, newPathMarker );
            }

        myContext.updateUI();
        return true;
        }

    // Implementation

    private final PositionEx findMarkerFor( final PositionF aPosition )
        {
        final int rawMarkerSize = myContext.getCoreApi().ui().configuration().markerSize;
        final int markerSize = (int) ( myContext.getTransformer().scaled( rawMarkerSize ) + 1 );
        final int halfMarkerSize = markerSize / 2;

        final ListOfPositions positions = myPath.positions();
        for ( int idx = positions.size() - 1; idx >= 0; idx-- )
            {
            final PositionEx position = positions.at( idx );
            final float dx = Math.abs( position.x - aPosition.x );
            final float dy = Math.abs( position.y - aPosition.y );
            if ( dx <= halfMarkerSize && dy <= halfMarkerSize ) return position;
            }

        return null;
        }



    private Path myPath;

    private PositionF myDraggedMarker;

    private final InputHandlerContext myContext;
    }
