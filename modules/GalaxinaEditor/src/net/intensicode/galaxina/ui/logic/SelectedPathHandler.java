package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;

public final class SelectedPathHandler extends PathHandler implements EditorStateListener
    {
    public SelectedPathHandler( final InputHandlerContext aContext )
        {
        super( aContext );

        aContext.getCoreApi().state().add( Identifiers.SELECTED_PATH, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        attach( (Path) aNewValue );
        }
    }
