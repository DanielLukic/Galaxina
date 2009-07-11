package net.intensicode.galaxina;

public interface EditorStateListener extends Identifiers
    {
    void onStateChanged( String aEventID, Object aOldValue, Object aNewValue );
    }
