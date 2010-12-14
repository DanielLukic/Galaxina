package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedSwarmMarker;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.domain.ListOfPositions;
import net.intensicode.util.*;

import java.awt.event.MouseEvent;

public class SwarmHandler implements InputHandler
    {
    public SwarmHandler( final InputHandlerContext aContext )
        {
        myContext = aContext;
        }

    public final void attach( final Swarm aSwarm )
        {
        mySwarm = aSwarm;
        }

    // From InputHandler

    public boolean isActive()
        {
        return mySwarm != null;
        }

    public final boolean mouseClicked( final MouseEvent aEvent )
        {
        final PositionF position = myContext.getTransformer().toGame( aEvent.getPoint() );
        final PositionEx selectedMarker = findMarkerFor( position );
        if ( selectedMarker != null )
            {
            myContext.getCoreApi().state().change( Identifiers.SELECTED_SWARM_MARKER, new SelectedSwarmMarker( mySwarm, selectedMarker ) );
            myContext.getCoreApi().state().change( Identifiers.SELECTED_SWARM, mySwarm );
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

        final SelectedSwarmMarker marker = (SelectedSwarmMarker) myContext.getCoreApi().state().get( Identifiers.SELECTED_SWARM_MARKER );
        if ( marker != null && marker.marker == myDraggedMarker )
            {
            final SelectedSwarmMarker newSwarmMarker = new SelectedSwarmMarker( marker.swarm, marker.marker );
            myContext.getCoreApi().state().change( Identifiers.SELECTED_SWARM_MARKER, newSwarmMarker );
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

        final ListOfPositions positions = mySwarm.positions();
        for ( int idx = positions.size() - 1; idx >= 0; idx-- )
            {
            final PositionEx position = positions.at( idx );
            final float dx = Math.abs( position.x - aPosition.x );
            final Float dy = Math.abs( position.y - aPosition.y );
            if ( dx <= halfMarkerSize && dy <= halfMarkerSize ) return position;
            }

        return null;
        }



    private Swarm mySwarm;

    private PositionF myDraggedMarker;

    private final InputHandlerContext myContext;
    }
