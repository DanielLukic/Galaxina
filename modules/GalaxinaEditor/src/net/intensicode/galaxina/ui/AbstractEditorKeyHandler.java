package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.util.Position;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class AbstractEditorKeyHandler implements KeyListener
    {
    public AbstractEditorKeyHandler( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From KeyListener

    public final void keyTyped( final KeyEvent e )
        {
        }

    public final void keyPressed( final KeyEvent aEvent )
        {
        if ( aEvent.isConsumed() ) return;

        final int modifiers = aEvent.getModifiers();
        final int xDelta = myCoreAPI.ui().configuration().gridSizeX;
        final int yDelta = myCoreAPI.ui().configuration().gridSizeY;

        switch ( aEvent.getKeyCode() )
            {
            case KeyEvent.VK_DELETE:
                removeNode();
                break;
            case KeyEvent.VK_N:
                resetOffset();
                break;
            case KeyEvent.VK_DOWN:
                move( 0, yDelta, modifiers );
                break;
            case KeyEvent.VK_UP:
                move( 0, -yDelta, modifiers );
                break;
            case KeyEvent.VK_LEFT:
                move( -xDelta, 0, modifiers );
                break;
            case KeyEvent.VK_RIGHT:
                move( +xDelta, 0, modifiers );
                break;
            default:
                break;
            }
        }

    public final void keyReleased( final KeyEvent e )
        {
        }

    // Implementation

    protected abstract void removeNode();

    protected abstract void moveData( final int aDeltaX, final int aDeltaY );

    private final void resetOffset()
        {
        final EditorState state = myCoreAPI.state();
        state.change( Identifiers.PATH_EDITOR_OFFSET, NO_OFFSET );
        }

    private final void move( final int aDeltaX, final int aDeltaY, final int aModifiers )
        {
        if ( ( aModifiers & KeyEvent.ALT_MASK ) != 0 ) moveView( aDeltaX, aDeltaY );
        else moveData( aDeltaX, aDeltaY );
        }

    private final void moveView( final int aDeltaX, final int aDeltaY )
        {
        final EditorState state = myCoreAPI.state();
        final Position newOffset = new Position();
        final Position offset = (Position) state.get( Identifiers.PATH_EDITOR_OFFSET );
        if ( offset != null ) newOffset.setTo( offset );
        newOffset.x += aDeltaX;
        newOffset.y -= aDeltaY;
        state.change( Identifiers.PATH_EDITOR_OFFSET, newOffset );
        }



    protected final EditorCoreAPI myCoreAPI;

    private static final Position NO_OFFSET = new Position();
    }
