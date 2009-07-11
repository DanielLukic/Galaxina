package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.UiConfiguration;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.domain.ListOfPositions;
import net.intensicode.galaxina.ui.logic.CoordinateTransformer;
import net.intensicode.util.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SwarmLayer implements VisualLayer, Identifiers, Runnable
    {
    public boolean showMarkers = true;

    public boolean skipIfSelected = true;



    public SwarmLayer( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        myCoreAPI = aCoreAPI;
        myTransformer = aTransformer;

        final UiConfiguration config = aCoreAPI.ui().configuration();
        setStroke( config.swarmStroke );
        setMarkerColor( config.markerColor );

        final Thread thread = new Thread( this );
        thread.setDaemon( true );
        thread.start();
        }

    public final void attach( final Swarm aSwarm )
        {
        mySwarm = aSwarm;
        }

    public final void setStroke( final Stroke aStroke )
        {
        myStroke = aStroke;
        }

    public final void setMarkerColor( final Color aColor )
        {
        myMarkerColor = aColor;
        }

    // From VisualLayer

    public final void paintInto( final Graphics2D aGraphics2D )
        {
        if ( !isVisible() ) return;

        aGraphics2D.setStroke( myStroke );
        drawMarkers( aGraphics2D );
        }

    // From Runnable

    public final void run()
        {
        try
            {
            runUnsafe();
            }
        catch ( final InterruptedException e )
            {
            }
        }

    // Protected Interface

    protected boolean isVisible()
        {
        if ( skipIfSelected && myCoreAPI.state().currentSwarm() == mySwarm ) return false;
        return mySwarm != null;
        }

    // Implementation

    private final void drawMarkers( final Graphics2D aGraphics2D )
        {
        final int rawMarkerSize = myCoreAPI.ui().configuration().swarmMarkerSize;
        final int markerSize = (int) ( myTransformer.scaled( rawMarkerSize ) + 1 );
        final int halfMarkerSize = markerSize / 2;

        if ( myFonts.get( markerSize ) == null )
            {
            if ( myRequestedFonts.contains( markerSize ) )
                {
                requestFontFor( markerSize );
                }
            }
        else
            {
            aGraphics2D.setFont( myFonts.get( markerSize ) );
            }

        final ListOfPositions positions = mySwarm.positions();
        for ( int idx = 0; idx < positions.size(); idx++ )
            {
            final Position position = positions.at( idx );
            final int x = position.x - halfMarkerSize;
            final int y = position.y - halfMarkerSize;
            aGraphics2D.setColor( myMarkerColor );
            aGraphics2D.fillRect( x, y, markerSize, markerSize );
            if ( aGraphics2D.getFont() != null )
                {
                aGraphics2D.setColor( Color.BLACK );
                aGraphics2D.drawString( Integer.toString( idx + 1 ), x, y + markerSize );
                }
            }
        }

    private final void runUnsafe() throws InterruptedException
        {
        while ( true )
            {
            synchronized ( myRequestedFonts )
                {
                while ( myRequestedFonts.size() == 0 ) myRequestedFonts.wait();
                }

            final Integer size = myRequestedFonts.get( 0 );
            final Font font = new Font( "sans-serif", Font.BOLD, size );

            synchronized ( myRequestedFonts )
                {
                myFonts.put( size, font );
                while ( myRequestedFonts.remove( size ) ) ;
                }
            }
        }

    private final void requestFontFor( final Integer aMarkerSize )
        {
        myRequestedFonts.add( 0, aMarkerSize );
        myRequestedFonts.notify();
        }



    private Swarm mySwarm;

    private Stroke myStroke;

    private Color myMarkerColor;

    protected final EditorCoreAPI myCoreAPI;

    protected final CoordinateTransformer myTransformer;

    private final HashMap<Integer, Font> myFonts = new HashMap<Integer, Font>();

    private final ArrayList<Integer> myRequestedFonts = new ArrayList<Integer>();
    }
