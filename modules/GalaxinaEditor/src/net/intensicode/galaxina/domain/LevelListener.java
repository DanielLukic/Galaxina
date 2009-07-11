package net.intensicode.galaxina.domain;

public interface LevelListener
    {
    void onSetRepeated( Level aLevel, boolean aRepeatedFlag );

    void onSetRepeatInterval( Level aLevel, int aRepeatInterval );

    void onSetLevelInfo( Level aLevel, String aInfo );
    }
