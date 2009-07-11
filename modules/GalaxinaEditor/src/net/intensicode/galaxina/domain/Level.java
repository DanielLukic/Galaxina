package net.intensicode.galaxina.domain;

public interface Level extends GroupEntryEx
    {
    String CHALLENGING_STAGE = "CHALLENGING_STAGE";

    void add( LevelListener aListener );

    int levelIndex();

    boolean hasChanges();

    boolean appliesTo( int aLevelIndex );

    boolean canChangeRepeatMode();

    boolean isRepeated();

    void setRepeated( boolean aRepeatedFlag );

    int repeatInterval();

    void setRepeatInterval( int aRepeatInterval );

    boolean canChangeLevelInfo();

    String levelInfo();

    void setLevelInfo( String aLevelInfo );

    boolean canBeReplacedByClonedSelf();

    Swarms swarms();
    }
