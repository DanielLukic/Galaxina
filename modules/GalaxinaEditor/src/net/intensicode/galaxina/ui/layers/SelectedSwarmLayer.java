package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.UiConfiguration;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.logic.CoordinateTransformer;

public final class SelectedSwarmLayer extends SwarmLayer implements EditorStateListener
    {
    public SelectedSwarmLayer( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        super( aCoreAPI, aTransformer );
        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );

        final UiConfiguration config = aCoreAPI.ui().configuration();
        setStroke( config.selectedSwarmStroke );
        setMarkerColor( config.selectedMarkerColor );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        attach( (Swarm) aNewValue );
        }

    // From PathLayer

    protected final boolean isVisible()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        return ( swarm != null );
        }
    }
