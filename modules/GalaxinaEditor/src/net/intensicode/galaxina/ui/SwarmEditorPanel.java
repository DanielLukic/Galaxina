package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.*;
import net.intensicode.galaxina.ui.layers.*;
import net.intensicode.galaxina.ui.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public final class SwarmEditorPanel extends JPanel implements Identifiers, EditorStateListener
    {
    public SwarmEditorPanel( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;

        final EditorState state = aCoreAPI.state();
        state.add( PATH_EDITOR_OFFSET, this );
        state.add( ZOOM_FACTOR, this );
        state.add( ZOOM_MODE, this );
        state.add( SHOW_GRID, this );
        state.add( SHOW_PATHES, this );
        state.add( SMOOTH_PATHES, this );
        state.add( PROJECT, this );
        state.add( SELECTED_SWARM, this );
        state.add( SELECTED_SWARM_MARKER, this );
        state.add( SELECTED_LEVEL, this );

        myTransformer = new ZoomAwareCoordinateTransformer( aCoreAPI, this );

        final SwarmEditorLogic logic = new SwarmEditorLogic( aCoreAPI, myTransformer );
        logic.setComponent( this );

        new MouseBasedFocusHandler( this );

        addMouseWheelListener( new MouseWheelZoomer( aCoreAPI ) );
        addKeyListener( new SwarmEditorKeyHandler( aCoreAPI ) );
        setFocusable( true );

        myLayers.add( new SimpleGridLayer( aCoreAPI ) );
        myLayers.add( new ScreenBorderLayer( aCoreAPI ) );

        myLayers.add( new SelectedSwarmLayer( myCoreAPI, myTransformer ) );
        myLayers.add( new SelectedSwarmMarkerLayer( myCoreAPI, myTransformer ) );

        myPathManager = new PathManager();
        mySwarmManager = new SwarmManager();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == SELECTED_SWARM )
            {
            detachFrom( (Swarm) aOldValue );
            attachTo( (Swarm) aNewValue );
            updateUI();
            }
        else if ( aEventID == SELECTED_LEVEL )
            {
            detachFrom( (Level) aOldValue );
            attachTo( (Level) aNewValue );
            }
        else
            {
            updateUI();
            }
        }

    // From JComponent

    protected final void paintComponent( final Graphics g )
        {
        super.paintComponent( g );

        if ( !myCoreAPI.project().isOpen() ) return;

        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform at = g2d.getTransform();

        final double zoomFactor = getZoomFactor();
        myTransformer.setZoomFactor( zoomFactor );
        myTransformer.prepare( g2d );

        for ( final VisualLayer layer : myLayers ) layer.paintInto( g2d );

        g2d.setTransform( at );
        }

    // Implementation

    private void detachFrom( final Swarm aOldSwarm )
        {
        if ( aOldSwarm == null ) return;

        aOldSwarm.pathes().remove( myPathManager );
        for ( final Path path : aOldSwarm.pathes() )
            {
            myPathManager.onRemoved( null, path );
            }
        }

    private void attachTo( final Swarm aNewSwarm )
        {
        if ( aNewSwarm == null ) return;

        for ( final Path path : aNewSwarm.pathes() )
            {
            myPathManager.onAdded( null, path );
            }
        aNewSwarm.pathes().add( myPathManager );
        }

    private void detachFrom( final Level aOldLevel )
        {
        if ( aOldLevel == null ) return;

        aOldLevel.swarms().remove( mySwarmManager );
        for ( final Swarm swarm : aOldLevel.swarms() )
            {
            mySwarmManager.onRemoved( null, swarm );
            }
        }

    private void attachTo( final Level aNewLevel )
        {
        if ( aNewLevel == null ) return;

        for ( final Swarm swarm : aNewLevel.swarms() )
            {
            mySwarmManager.onAdded( null, swarm );
            }
        aNewLevel.swarms().add( mySwarmManager );
        }

    private double getZoomFactor()
        {
        final ZoomMode zoomMode = myCoreAPI.state().getZoomMode();
        if ( zoomMode == ZoomMode.ZOOM_EXACT )
            {
            return myCoreAPI.state().getZoomFactor();
            }
        else if ( zoomMode == ZoomMode.ZOOM_TO_FIT )
            {
            final double xZoom = getWidth() * 0.8 / myCoreAPI.gameScreenWidth();
            final double yZoom = getHeight() * 0.8 / myCoreAPI.gameScreenHeight();
            final double zoom = Math.min( xZoom, yZoom );
            myCoreAPI.state().setZoomFactor( zoom );
            return myCoreAPI.state().getZoomFactor();
            }
        else if ( zoomMode == ZoomMode.ZOOM_FREE )
                {
                return myCoreAPI.state().getZoomFactor();
                }
            else
                {
                throw new IllegalArgumentException();
                }
        }



    private final EditorCoreAPI myCoreAPI;

    private final PathManager myPathManager;

    private final SwarmManager mySwarmManager;

    private final ZoomAwareCoordinateTransformer myTransformer;

    private final ArrayList<VisualLayer> myLayers = new ArrayList<VisualLayer>();

    private final HashMap<Path, PathLayer> myPathLayers = new HashMap<Path, PathLayer>();

    private final HashMap<Swarm, SwarmLayer> mySwarmLayers = new HashMap<Swarm, SwarmLayer>();



    public final class SwarmManager implements GroupListener<Swarm>
        {
        public final void onAdded( final Group<Swarm> aGroup, final Swarm aNewEntry )
            {
            final SwarmLayer newLayer = new SwarmLayer( myCoreAPI, myTransformer );
            newLayer.attach( aNewEntry );
            newLayer.showMarkers = newLayer.skipIfSelected = true;
            myLayers.add( myLayers.size() - 2, newLayer );
            mySwarmLayers.put( aNewEntry, newLayer );
            updateUI();
            }

        public final void onRemoved( final Group<Swarm> aGroup, final Swarm aRemovedEntry )
            {
            final SwarmLayer oldLayer = mySwarmLayers.remove( aRemovedEntry );
            if ( oldLayer == null ) throw new IllegalArgumentException();
            if ( !myLayers.remove( oldLayer ) ) throw new IllegalArgumentException();
            updateUI();
            }

        public final void onReplaced( final Group<Swarm> aGroup, final Swarm aOldEntry, final Swarm aEntry, final Integer aIndex )
            {
            throw new RuntimeException( "nyi" );
            }

        public final void onDataChanged( final Group<Swarm> aGroup )
            {
            myLayers.removeAll( mySwarmLayers.values() );
            mySwarmLayers.clear();
            for ( final Swarm swarm : aGroup ) onAdded( aGroup, swarm );
            updateUI();
            }

        public final void onPropertyChanged( final Group<Swarm> aGroup, final String aKey, final Object aValue )
            {
            throw new RuntimeException( "nyi" );
            }
        }



    public final class PathManager implements GroupListener<Path>
        {
        public final void onAdded( final Group<Path> aPathGroup, final Path aNewEntry )
            {
            final PathLayer newLayer = new PathLayer( myCoreAPI, myTransformer );
            newLayer.attach( aNewEntry );
            newLayer.showMarkers = newLayer.skipIfSelected = false;
            myLayers.add( 2, newLayer );
            myPathLayers.put( aNewEntry, newLayer );
            updateUI();
            }

        public final void onRemoved( final Group<Path> aPathGroup, final Path aRemovedEntry )
            {
            final PathLayer oldLayer = myPathLayers.remove( aRemovedEntry );
            if ( oldLayer == null ) throw new IllegalArgumentException();
            if ( !myLayers.remove( oldLayer ) ) throw new IllegalArgumentException();
            updateUI();
            }

        public final void onReplaced( final Group<Path> aPathGroup, final Path aOldEntry, final Path aEntry, final Integer aIndex )
            {
            throw new RuntimeException( "nyi" );
            }

        public final void onDataChanged( final Group<Path> aPathGroup )
            {
            myLayers.removeAll( myPathLayers.values() );
            for ( final Path path : aPathGroup ) onAdded( aPathGroup, path );
            updateUI();
            }

        public final void onPropertyChanged( final Group<Path> aPathGroup, final String aKey, final Object aValue )
            {
            throw new RuntimeException( "nyi" );
            }
        }
    }
