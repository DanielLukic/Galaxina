package net.intensicode.galaxina.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface GroupEntry
    {
    void load( DataInputStream aInputStream ) throws IOException;

    void save( DataOutputStream aOutputStream ) throws IOException;
    }
