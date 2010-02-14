package net.intensicode.galaxina.game.data;

import net.intensicode.galaxina.game.extras.ExtraTypes;

import java.io.DataInputStream;
import java.io.IOException;

public final class EnemyConfiguration
    {
    public static final int SYNC_INDEPENDANT = 0;

    public static final int SYNC_BY_PATH = 1;

    public static final int SYNC_FOLLOW_LEFT_SIDE = 2;

    public static final int SYNC_FOLLOW_RIGHT_SIDE = 3;

    public static final int SYNC_SPEED = 4;

    public int typeID;

    public int extraID;

    public boolean zeroDelay;

    public boolean syncSpeed;

    public boolean syncByPath;

    public boolean followLeftSide;

    public boolean followRightSide;



    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        typeID = aInputStream.readInt();
        extraID = ExtraTypes.RANDOM;

        final int syncMode = aInputStream.readInt();
        switch ( syncMode )
            {
            case SYNC_SPEED:
                syncSpeed = true;
                break;
            case SYNC_BY_PATH:
                syncByPath = true;
                break;
            case SYNC_FOLLOW_LEFT_SIDE:
                followLeftSide = true;
                break;
            case SYNC_FOLLOW_RIGHT_SIDE:
                followRightSide = true;
                break;
            }

        zeroDelay = aInputStream.readBoolean();
        }
    }
