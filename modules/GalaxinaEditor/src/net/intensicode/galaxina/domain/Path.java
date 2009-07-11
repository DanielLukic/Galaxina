package net.intensicode.galaxina.domain;

public interface Path extends GroupEntryEx
    {
    static String NAME = "NAME";

    void move( final int aDeltaX, final int aDeltaY );

    ListOfPositions positions();

    Path clone();
    }
