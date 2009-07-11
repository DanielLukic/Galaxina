package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.SelectedSwarmMarker;
import net.intensicode.galaxina.ui.logic.CoordinateTransformer;
import net.intensicode.util.Position;

import java.awt.*;

public class SelectedSwarmMarkerLayer implements VisualLayer, EditorStateListener, Identifiers
    {
    public SelectedSwarmMarkerLayer( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        myCoreAPI = aCoreAPI;
        myTransformer = aTransformer;
        myCoreAPI.state().add( SELECTED_SWARM_MARKER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        mySelectedSwarmMarker = (SelectedSwarmMarker) aNewValue;
        }

    // From VisualLayer

    public final void paintInto( final Graphics2D aGraphics2D )
        {
        if ( !isVisible() ) return;

        final int rawMarkerSize = myCoreAPI.ui().configuration().selectedMarkerSize * 2;
        final int markerSize = (int) ( myTransformer.scaled( rawMarkerSize ) + 1 );
        final int halfMarkerSize = markerSize / 2;

        aGraphics2D.setStroke( myCoreAPI.ui().configuration().activeMarkerStroke );
        aGraphics2D.setColor( myCoreAPI.ui().configuration().activeMarkerColor );

        final Position position = mySelectedSwarmMarker.marker;
        final int x = position.x - halfMarkerSize;
        final int y = position.y - halfMarkerSize;
        aGraphics2D.drawRect( x, y, markerSize, markerSize );
        }

    // Protected Interface

    protected boolean isVisible()
        {
        return mySelectedSwarmMarker != null && mySelectedSwarmMarker.isValid();
        }



    private SelectedSwarmMarker mySelectedSwarmMarker;

    protected final EditorCoreAPI myCoreAPI;

    protected final CoordinateTransformer myTransformer;
    }
