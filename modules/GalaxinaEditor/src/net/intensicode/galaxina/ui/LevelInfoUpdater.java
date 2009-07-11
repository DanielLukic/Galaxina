package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.domain.Level;

import javax.swing.*;

public final class LevelInfoUpdater extends TextFieldUpdater
    {
    public LevelInfoUpdater( final EditorCoreAPI aCoreAPI, final JTextField aTextField )
        {
        super( aCoreAPI, aTextField );
        }

    // From TextFieldUpdater

    protected final String getOldValue()
        {
        final Level level = coreAPI().state().currentLevel();
        if ( level == null ) return null;
        return level.levelInfo();
        }

    protected final void setNewValue( final String aNewValue )
        {
        final Level level = coreAPI().state().currentLevel();
        if ( level == null ) throw new IllegalStateException();
        level.setLevelInfo( aNewValue );
        }
    }
