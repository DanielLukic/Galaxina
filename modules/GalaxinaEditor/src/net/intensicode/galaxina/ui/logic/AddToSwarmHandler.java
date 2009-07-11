package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedSwarmMarker;
import net.intensicode.galaxina.domain.Swarm;

import java.awt.event.MouseEvent;

public final class AddToSwarmHandler implements InputHandler, EditorStateListener
    {
    public AddToSwarmHandler( final InputHandlerContext aContext )
        {
        myContext = aContext;
        myContext.getCoreApi().state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        mySelectedSwarm = myContext.getCoreApi().state().currentSwarm();
        }

    // From InputHandler

    public final boolean isActive()
        {
        return ( mySelectedSwarm != null );
        }

    public boolean mouseClicked( final MouseEvent aEvent )
        {
        final PositionEx position = myContext.getTransformer().toGame( aEvent.getPoint() );
        if ( mySelectedSwarm.positions().size() == mySelectedSwarm.getSize() )
            {
            mySelectedSwarm.setSize( mySelectedSwarm.getSize() + 1 );
            }
        mySelectedSwarm.positions().addEntry( position );

        myContext.getCoreApi().state().change( Identifiers.SELECTED_SWARM_MARKER, new SelectedSwarmMarker( mySelectedSwarm, position ) );
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



    private Swarm mySelectedSwarm;

    private final InputHandlerContext myContext;
    }
