package net.intensicode.game.data;

import java.io.*;

public final class LevelTestData
    {
    public LevelTestData( final int aLevelIndex )
        {
        myLevelIndex = aLevelIndex;
        }

    public final LevelTestData setChallenging()
        {
        myChallengingStage = true;
        return this;
        }

    public final LevelTestData setRepeated( final int aInterval )
        {
        myRepeatFlag = true;
        myRepeatInterval = aInterval;
        return this;
        }

    public final DataInputStream done() throws IOException
        {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final DataOutputStream stream = new DataOutputStream( outputStream );
        stream.writeInt( myLevelIndex ); // level index
        stream.writeInt( myRepeatInterval ); // repeat interval
        stream.writeBoolean( myRepeatFlag ); // repeat?
        stream.writeBoolean( myChallengingStage ); // challenging?
        stream.writeInt( 0 ); // number of swarms
        stream.flush();
        stream.close();

        final byte[] data = outputStream.toByteArray();
        return new DataInputStream( new ByteArrayInputStream( data ) );
        }



    private int myRepeatInterval;

    private boolean myRepeatFlag;

    private final int myLevelIndex;

    private boolean myChallengingStage;
    }
