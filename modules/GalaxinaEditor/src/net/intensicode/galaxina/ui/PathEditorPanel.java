package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.*;
import net.intensicode.galaxina.ui.layers.*;
import net.intensicode.galaxina.ui.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public final class PathEditorPanel extends JPanel implements Identifiers, EditorStateListener, GroupListener<Path>
    {
    public PathEditorPanel( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;

        setVisible( false );

        aCoreAPI.state().add( PATH_EDITOR_OFFSET, this );
        aCoreAPI.state().add( SELECTED_PATH_MARKER, this );
        aCoreAPI.state().add( SELECTED_PATH, this );
        aCoreAPI.state().add( ZOOM_FACTOR, this );
        aCoreAPI.state().add( ZOOM_MODE, this );
        aCoreAPI.state().add( SHOW_GRID, this );
        aCoreAPI.state().add( SHOW_PATHES, this );
        aCoreAPI.state().add( SMOOTH_PATHES, this );
        aCoreAPI.state().add( PROJECT, this );

        aCoreAPI.project().pathes().add( this );

        myTransformer = new ZoomAwareCoordinateTransformer( aCoreAPI, this );

        final PathEditorLogic logic = new PathEditorLogic( aCoreAPI, myTransformer );
        logic.setComponent( this );

        new MouseBasedFocusHandler( this );

        addMouseWheelListener( new MouseWheelZoomer( aCoreAPI ) );
        addKeyListener( new PathEditorKeyHandler( aCoreAPI ) );
        setFocusable( true );

        myLayers.add( new SimpleGridLayer( aCoreAPI ) );
        myLayers.add( new ScreenBorderLayer( aCoreAPI ) );
        myLayers.add( new SelectedPathLayer( myCoreAPI, myTransformer ) );
        myLayers.add( new SelectedPathMarkerLayer( myCoreAPI, myTransformer ) );
        }

    // From GroupListener

    public final void onAdded( final Group<Path> aPathGroup, final Path aNewEntry )
        {
        final PathLayer pathLayer = new PathLayer( myCoreAPI, myTransformer );
        pathLayer.attach( aNewEntry );
        myLayers.add( myLayers.size() - 2, pathLayer );
        myPathLayers.put( aNewEntry, pathLayer );

        requestFocus();
        }

    public final void onRemoved( final Group<Path> aPathGroup, final Path aRemovedEntry )
        {
        final PathLayer oldLayer = myPathLayers.remove( aRemovedEntry );
        if ( oldLayer == null ) throw new IllegalArgumentException();
        if ( !myLayers.remove( oldLayer ) ) throw new IllegalArgumentException();
        }

    public final void onReplaced( final Group<Path> aPathGroup, final Path aOldEntry, final Path aEntry, final Integer aIndex )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void onDataChanged( final Group<Path> aPathGroup )
        {
        myLayers.removeAll( myPathLayers.values() );
        myPathLayers.clear();
        for ( final Path path : aPathGroup ) onAdded( aPathGroup, path );
        updateUI();
        }

    public final void onPropertyChanged( final Group<Path> aPathGroup, final String aKey, final Object aValue )
        {
        throw new RuntimeException( "nyi" );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == PROJECT )
            {
            setVisible( aNewValue != null );
            requestFocus();
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

        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform at = g2d.getTransform();

        final double zoomFactor = getZoomFactor();
        myTransformer.setZoomFactor( zoomFactor );
        myTransformer.prepare( g2d );

        for ( final VisualLayer layer : myLayers ) layer.paintInto( g2d );

        g2d.setTransform( at );
        }

    // Implementation

    private double getZoomFactor()
        {
        final EditorState.ZoomMode zoomMode = myCoreAPI.state().getZoomMode();
        if ( zoomMode == Identifiers.ZoomMode.ZOOM_EXACT )
            {
            return myCoreAPI.state().getZoomFactor();
            }
        else if ( zoomMode == Identifiers.ZoomMode.ZOOM_TO_FIT )
            {
            final double xZoom = getWidth() * 0.8 / myCoreAPI.gameScreenWidth();
            final double yZoom = getHeight() * 0.8 / myCoreAPI.gameScreenHeight();
            final double zoom = Math.min( xZoom, yZoom );
            myCoreAPI.state().setZoomFactor( zoom );
            return myCoreAPI.state().getZoomFactor();
            }
        else if ( zoomMode == Identifiers.ZoomMode.ZOOM_FREE )
                {
                return myCoreAPI.state().getZoomFactor();
                }
            else
                {
                throw new IllegalArgumentException();
                }
        }



    private final EditorCoreAPI myCoreAPI;

    private final ZoomAwareCoordinateTransformer myTransformer;

    private final ArrayList<VisualLayer> myLayers = new ArrayList<VisualLayer>();

    private final HashMap<Path, PathLayer> myPathLayers = new HashMap<Path, PathLayer>();
    }
