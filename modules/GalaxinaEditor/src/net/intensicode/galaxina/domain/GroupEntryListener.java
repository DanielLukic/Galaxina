package net.intensicode.galaxina.domain;

public interface GroupEntryListener
    {
    void onPropertyChanged( GroupEntry aEntry, String aName, Object aOldValue, Object aNewValue );
    }
