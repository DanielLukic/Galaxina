package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.UiConfiguration;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.ui.logic.CoordinateTransformer;

public final class SelectedPathLayer extends PathLayer implements EditorStateListener
    {
    public SelectedPathLayer( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        super( aCoreAPI, aTransformer );
        aCoreAPI.state().add( Identifiers.SELECTED_PATH, this );

        final UiConfiguration config = aCoreAPI.ui().configuration();
        setStroke( config.selectedPathStroke );
        setSmoothPathColor( config.selectedSmoothPathColor );
        setPathColor( config.selectedPathColor );
        setMarkerColor( config.selectedMarkerColor );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        attach( (Path) aNewValue );
        }

    // From PathLayer

    protected final boolean isVisible()
        {
        final Path path = myCoreAPI.state().currentPath();
        return ( path != null );
        }
    }
