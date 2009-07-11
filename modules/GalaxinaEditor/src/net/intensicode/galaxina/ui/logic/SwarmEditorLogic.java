package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;
import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.Swarm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class SwarmEditorLogic implements GroupListener<Swarm>, InputHandlerContext, EditorStateListener, Identifiers
    {
    public SwarmEditorLogic( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        myCoreAPI = aCoreAPI;
        myTransformer = aTransformer;

        aCoreAPI.state().add( SELECTED_LEVEL, this );

        myHandlers.add( new SelectedSwarmHandler( this ) );
        myHandlers.add( new AddToSwarmHandler( this ) );

        new SwarmDataListener( this );
        }

    public final void setComponent( final JComponent aComponent )
        {
        if ( myComponent != null ) throw new IllegalStateException();
        myComponent = aComponent;

        final EventDelegator delegator = new EventDelegator( myHandlers );
        myComponent.addMouseListener( delegator );
        myComponent.addMouseMotionListener( delegator );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        detachFrom( (Level) aOldValue );
        attachTo( (Level) aNewValue );
        }

    // From InputHandlerContext

    public final void updateUI()
        {
        myComponent.updateUI();
        }

    public final EditorCoreAPI getCoreApi()
        {
        return myCoreAPI;
        }

    public final CoordinateTransformer getTransformer()
        {
        return myTransformer;
        }

    // From GroupListener

    public final void onAdded( final Group<Swarm> aSwarmGroup, final Swarm aNewEntry )
        {
        final SwarmHandler newHandler = new SwarmHandler( this );
        newHandler.attach( aNewEntry );
        mySwarmHandlers.put( aNewEntry, newHandler );
        myHandlers.add( 1, newHandler );
        }

    public final void onRemoved( final Group<Swarm> aSwarmGroup, final Swarm aRemovedEntry )
        {
        final InputHandler oldHandler = mySwarmHandlers.remove( aRemovedEntry );
        if ( oldHandler == null ) throw new IllegalArgumentException();
        if ( myHandlers.remove( oldHandler ) == false ) throw new IllegalArgumentException();
        }

    public final void onReplaced( final Group<Swarm> aSwarmGroup, final Swarm aOldEntry, final Swarm aEntry, final Integer aIndex )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void onDataChanged( final Group<Swarm> aSwarmGroup )
        {
        myHandlers.removeAll( mySwarmHandlers.values() );
        mySwarmHandlers.clear();
        for ( final Swarm swarm : aSwarmGroup )
            {
            onAdded( aSwarmGroup, swarm );
            }
        }

    public final void onPropertyChanged( final Group<Swarm> aSwarmGroup, final String aKey, final Object aValue )
        {
        throw new RuntimeException( "nyi" );
        }

    // Implementation

    private final void detachFrom( final Level aOldLevel )
        {
        if ( aOldLevel == null ) return;

        aOldLevel.swarms().remove( this );
        for ( final Swarm swarm : aOldLevel.swarms() )
            {
            onRemoved( null, swarm );
            }
        }

    private final void attachTo( final Level aNewLevel )
        {
        if ( aNewLevel == null ) return;

        for ( final Swarm swarm : aNewLevel.swarms() )
            {
            onAdded( null, swarm );
            }
        aNewLevel.swarms().add( this );
        }



    private JComponent myComponent;

    private final EditorCoreAPI myCoreAPI;

    private final CoordinateTransformer myTransformer;

    private final ArrayList<InputHandler> myHandlers = new ArrayList<InputHandler>();

    private final HashMap<Swarm, InputHandler> mySwarmHandlers = new HashMap<Swarm, InputHandler>();
    }
