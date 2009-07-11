package net.intensicode.galaxina.domain;

public interface GroupListener<T>
    {
    void onAdded( Group<T> aGroup, T aNewEntry );

    void onRemoved( Group<T> aGroup, T aRemovedEntry );

    void onReplaced( Group<T> aGroup, T aOldEntry, T aNewEntry, Integer aIndex );

    void onDataChanged( Group<T> aGroup );
    }
