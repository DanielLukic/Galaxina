package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;

import javax.swing.*;

public final class SwarmEditorKeyHandler extends AbstractEditorKeyHandler
    {
    public SwarmEditorKeyHandler( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        }

    // Protected Interface

    protected final void removeNode()
        {
        final Action action = myCoreAPI.ui().action( "swarmEditor.RemoveSwarmNode" );
        if ( action.isEnabled() ) action.actionPerformed( null );
        }

    protected final void moveData( final int aDeltaX, final int aDeltaY )
        {
        final EditorState state = myCoreAPI.state();
        if ( state.currentSwarm() == null ) return;
        state.currentSwarm().move( aDeltaX, aDeltaY );
        }
    }
