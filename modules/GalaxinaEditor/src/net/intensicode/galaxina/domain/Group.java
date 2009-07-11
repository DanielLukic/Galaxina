package net.intensicode.galaxina.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Group<T> extends Iterable<T>
    {
    void add( GroupListener<T> aListener );

    void remove( GroupListener<T> aListener );

    void load( DataInputStream aInputStream ) throws IOException;

    void save( DataOutputStream aOutputStream ) throws IOException;

    int indexOf( T aPosition );

    void addEntry( T aEntry );

    void deleteEntry( T aEntry );

    void fireDataChanged();

    void clear();

    int size();

    T at( int aIndex );
    }
