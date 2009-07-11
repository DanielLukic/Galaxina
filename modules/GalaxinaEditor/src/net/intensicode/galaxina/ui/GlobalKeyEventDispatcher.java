package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorIdentifiers;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.Level;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class GlobalKeyEventDispatcher implements KeyEventDispatcher
    {
    public GlobalKeyEventDispatcher( final EditorCoreAPI aCoreAPI, final EditorForm aEditorForm )
        {
        myCoreAPI = aCoreAPI;
        myEditorForm = aEditorForm;
        }

    // KeyEventDispatcher

    public final boolean dispatchKeyEvent( final KeyEvent aEvent )
        {
        if ( aEvent.getID() != KeyEvent.KEY_PRESSED ) return false;
        if ( aEvent.isConsumed() ) return false;
        if ( !aEvent.isAltDown() ) return false;

        final boolean processed = process( aEvent );
        if ( processed ) aEvent.consume();
        return aEvent.isConsumed();
        }

    // Implementation

    private final boolean process( final KeyEvent aEvent )
        {
        switch ( aEvent.getKeyCode() )
            {
            case KeyEvent.VK_1:
                myEditorForm.switchTo( EditorIdentifiers.PATH_EDITOR );
                return true;
            case KeyEvent.VK_2:
                myEditorForm.switchTo( EditorIdentifiers.SWARM_EDITOR );
                return true;
            case KeyEvent.VK_3:
                myEditorForm.switchTo( EditorIdentifiers.ENEMY_EDITOR );
                return true;
            case KeyEvent.VK_4:
                myEditorForm.switchTo( EditorIdentifiers.GAME_VIEW );
                return true;
            case KeyEvent.VK_PERIOD:
                return nextLevel();
            case KeyEvent.VK_COMMA:
                return previousLevel();
            }
        return false;
        }

    private final boolean nextLevel()
        {
        final int currentIndex = myCoreAPI.state().currentLevel().levelIndex();
        final int nextLevel = currentIndex + 1;

        final Level level = myCoreAPI.project().levels().at( nextLevel );
        myCoreAPI.state().change( Identifiers.SELECTED_LEVEL, level );
        return true;
        }

    private final boolean previousLevel()
        {
        final int currentIndex = myCoreAPI.state().currentLevel().levelIndex();
        final int previousIndex = Math.max( 0, currentIndex - 1 );
        if ( currentIndex == previousIndex ) return false;

        final Level level = myCoreAPI.project().levels().at( previousIndex );
        myCoreAPI.state().change( Identifiers.SELECTED_LEVEL, level );
        return true;
        }



    private final EditorCoreAPI myCoreAPI;

    private final EditorForm myEditorForm;
    }
