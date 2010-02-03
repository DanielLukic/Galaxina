package net.intensicode.game.objects;

import net.intensicode.core.GameTiming;
import net.intensicode.util.Position;

public final class ScoreTag
    {
    public final Position worldPosFixed;

    public final int score;

    public boolean active;


    public ScoreTag( final Position aPosition, final int aScore, final int aTicksVisible )
        {
        active = true;
        worldPosFixed = aPosition;
        score = aScore;
        myTicksVisible = aTicksVisible;
        }

    public final void onControlTick( final GameTiming aGameTiming, final World aWorld )
        {
        if ( myTicksVisible > 0 )
            {
            myTicksVisible--;
            }
        else
            {
            active = false;
            }

        worldPosFixed.y -= aWorld.visibleSizeFixed.height / 6 / aGameTiming.ticksPerSecond;
        }


    private int myTicksVisible;
    }
