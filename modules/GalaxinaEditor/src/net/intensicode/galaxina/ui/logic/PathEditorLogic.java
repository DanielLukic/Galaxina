package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;
import net.intensicode.galaxina.domain.Path;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class PathEditorLogic implements GroupListener<Path>, InputHandlerContext
    {
    public PathEditorLogic( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        myCoreAPI = aCoreAPI;
        myTransformer = aTransformer;

        aCoreAPI.project().pathes().add( this );

        myHandlers.add( new SelectedPathHandler( this ) );
        myHandlers.add( new AddToPathHandler( this ) );

        new PathDataListener( this );
        }

    public final void setComponent( final JComponent aComponent )
        {
        if ( myComponent != null ) throw new IllegalStateException();
        myComponent = aComponent;

        final EventDelegator delegator = new EventDelegator( myHandlers );
        myComponent.addMouseListener( delegator );
        myComponent.addMouseMotionListener( delegator );
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

    public final void onAdded( final Group<Path> aPathGroup, final Path aNewEntry )
        {
        final PathHandler pathHandler = new PathHandler( this );
        pathHandler.attach( aNewEntry );
        myPathHandlers.put( aNewEntry, pathHandler );
        myHandlers.add( 1, pathHandler );
        }

    public final void onRemoved( final Group<Path> aPathGroup, final Path aRemovedEntry )
        {
        final InputHandler handler = myPathHandlers.remove( aRemovedEntry );
        if ( handler == null ) throw new IllegalArgumentException();
        if ( myHandlers.remove( handler ) == false ) throw new IllegalArgumentException();
        }

    public final void onReplaced( final Group<Path> aPathGroup, final Path aOldEntry, final Path aEntry, final Integer aIndex )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void onDataChanged( final Group<Path> aPathGroup )
        {
        myHandlers.removeAll( myPathHandlers.values() );
        myPathHandlers.clear();
        for ( final Path path : aPathGroup )
            {
            onAdded( aPathGroup, path );
            }
        }

    public final void onPropertyChanged( final Group<Path> aPathGroup, final String aKey, final Object aValue )
        {
        throw new RuntimeException( "nyi" );
        }



    private JComponent myComponent;

    private final EditorCoreAPI myCoreAPI;

    private final CoordinateTransformer myTransformer;

    private final ArrayList<InputHandler> myHandlers = new ArrayList<InputHandler>();

    private final HashMap<Path, InputHandler> myPathHandlers = new HashMap<Path, InputHandler>();
    }
