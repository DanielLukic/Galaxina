package net.intensicode.galaxina.domain;

public interface Swarm extends GroupEntryEx
    {
    String NAME = "NAME";

    String SIZE = "SIZE";

    String DELAY = "DELAY";

    void move( final int aDeltaX, final int aDeltaY );

    Integer getSize();

    void setSize( int aNewSize );

    SwarmPathes pathes();

    SwarmEnemies enemies();

    ListOfPositions positions();

    Swarm clone();
    }
