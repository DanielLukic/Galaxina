package net.intensicode.galaxina;

import net.intensicode.galaxina.domain.*;
import net.intensicode.galaxina.util.Log;
import net.intensicode.runme.MIDletContainer;

import java.util.ArrayList;
import java.util.HashMap;

public final class EditorState implements Identifiers
    {
    public EditorState()
        {
        setDefaults();
        }

    public final void setDefaults()
        {
        setZoomMode( ZoomMode.ZOOM_TO_FIT );
        setZoomFactor( 1.0 );
        change( SHOW_GRID, true );
        change( ALIGN_TO_GRID, true );
        change( SHOW_PATHES, false );
        change( SMOOTH_PATHES, true );
        change( MIDLET_ZOOM, ZoomMode.ZOOM_TO_FIT );
        }

    public final MIDletContainer container()
        {
        return (MIDletContainer) get( MIDLET_CONTAINER );
        }

    public final SelectedPathMarker getSelectedPathMarker()
        {
        return (SelectedPathMarker) get( SELECTED_PATH_MARKER );
        }

    public final SelectedSwarmMarker getSelectedSwarmMarker()
        {
        return (SelectedSwarmMarker) get( SELECTED_SWARM_MARKER );
        }

    public final Level currentLevel()
        {
        return (Level) get( SELECTED_LEVEL );
        }

    public final Enemy currentEnemy()
        {
        return (Enemy) get( SELECTED_ENEMY );
        }

    public final Path currentPath()
        {
        return (Path) get( SELECTED_PATH );
        }

    public final Swarm currentSwarm()
        {
        return (Swarm) get( SELECTED_SWARM );
        }

    public final Path currentSwarmPath()
        {
        return (Path) get( SELECTED_SWARM_PATH );
        }

    public final SwarmEnemy currentSwarmEnemy()
        {
        return (SwarmEnemy) get( SELECTED_SWARM_ENEMY );
        }

    public final ZoomMode getZoomMode()
        {
        return (ZoomMode) get( ZOOM_MODE );
        }

    public final void setZoomMode( final ZoomMode aNewMode )
        {
        change( ZOOM_MODE, aNewMode );
        }

    public final Double getZoomFactor()
        {
        return (Double) get( ZOOM_FACTOR );
        }

    public final void setZoomFactor( final Double aNewFactor )
        {
        change( ZOOM_FACTOR, aNewFactor );
        }

    public final void add( final String aEventID, final EditorStateListener aListener )
        {
        if ( !myListeners.containsKey( aEventID ) )
            {
            myListeners.put( aEventID, new ArrayList<EditorStateListener>() );
            }
        myListeners.get( aEventID ).add( aListener );
        }

    public final Object get( final String aEventID )
        {
        return myStates.get( aEventID );
        }

    public final Boolean getBoolean( final String aEventID )
        {
        return (Boolean) myStates.get( aEventID );
        }

    public final Integer getInteger( final String aEventID )
        {
        return (Integer) myStates.get( aEventID );
        }

    public final void change( final String aEventID, final Object aNewValue )
        {
        final Object oldValue = myStates.get( aEventID );
        if ( oldValue == aNewValue ) return;
        if ( oldValue != null && aNewValue != null && oldValue.equals( aNewValue ) ) return;

        myStates.put( aEventID, aNewValue );

        LOG.debug( "change %s from %s to %s", aEventID, oldValue, aNewValue );

        final ArrayList<EditorStateListener> listeners = myListeners.get( aEventID );
        if ( listeners == null ) return;

        for ( final EditorStateListener listener : listeners )
            {
            listener.onStateChanged( aEventID, oldValue, aNewValue );
            }
        }



    private final HashMap<String, Object> myStates = new HashMap<String, Object>();

    private final HashMap<String, ArrayList<EditorStateListener>> myListeners = new HashMap<String, ArrayList<EditorStateListener>>();


    private static final Log LOG = Log.get();
    }
