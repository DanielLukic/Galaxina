package net.intensicode.galaxina.domain;

public interface ProjectListener
    {
    void onProjectOpened( Project aProject );

    void onProjectClosed();
    }
