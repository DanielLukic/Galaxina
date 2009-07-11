package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedPathMarker;

import java.awt.event.MouseEvent;

public final class AddToPathHandler implements InputHandler, EditorStateListener
    {
    public AddToPathHandler( final InputHandlerContext aContext )
        {
        myContext = aContext;
        myContext.getCoreApi().state().add( Identifiers.SELECTED_PATH, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        mySelectedPath = myContext.getCoreApi().state().currentPath();
        }

    // From InputHandler

    public final boolean isActive()
        {
        return ( mySelectedPath != null );
        }

    public boolean mouseClicked( final MouseEvent aEvent )
        {
        final PositionEx position = myContext.getTransformer().toGame( aEvent.getPoint() );
        mySelectedPath.positions().addEntry( position );

        myContext.getCoreApi().state().change( Identifiers.SELECTED_PATH_MARKER, new SelectedPathMarker( mySelectedPath, position ) );
        myContext.updateUI();

        return true;
        }

    public final boolean mousePressed( final MouseEvent aEvent )
        {
        return false;
        }

    public final boolean mouseReleased( final MouseEvent aEvent )
        {
        return false;
        }

    public final boolean mouseDragged( final MouseEvent aEvent )
        {
        return false;
        }



    private Path mySelectedPath;

    private final InputHandlerContext myContext;
    }
