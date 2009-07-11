package net.intensicode.galaxina.domain;

public interface Enemy extends GroupEntryEx
    {
    String NAME = "NAME";

    String FILENAME = "FILENAME";

    String HITS = "HITS";

    String BASESCORE = "BASESCORE";

    Enemy clone();
    }
