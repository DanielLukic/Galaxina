package net.intensicode.game.data;

import java.io.DataInputStream;
import java.io.IOException;

public class LevelConfiguration
    {
    public boolean challengingStage;

    public final SwarmsConfiguration swarms = new SwarmsConfiguration();



    public void load( final DataInputStream aInputStream ) throws IOException
        {
        myLevelIndex = aInputStream.readInt();
        myRepeatInterval = aInputStream.readInt();
        myRepeatedFlag = aInputStream.readBoolean();
        challengingStage = aInputStream.readBoolean();
        swarms.load( aInputStream );
        }

    public final boolean appliesTo( final int aLevelNumberStartingAt1 )
        {
        if ( aLevelNumberStartingAt1 >= myLevelIndex && myRepeatedFlag )
            {
            return ( ( aLevelNumberStartingAt1 - myLevelIndex ) % myRepeatInterval ) == 0;
            }
        else
            {
            return myLevelIndex == aLevelNumberStartingAt1;
            }
        }



    private int myLevelIndex;

    private int myRepeatInterval;

    private boolean myRepeatedFlag;
    }
