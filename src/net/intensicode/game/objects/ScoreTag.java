package net.intensicode.game.objects;

import net.intensicode.core.Engine;
import net.intensicode.util.Position;


/**
 * TODO: Describe this!
 */
public final class ScoreTag extends Object
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

    public final void onControlTick( final Engine aEngine, final World aWorld )
        {
        if ( myTicksVisible > 0 ) myTicksVisible--;
        else active = false;

        worldPosFixed.y -= aWorld.visibleSizeFixed.height / 6 / Engine.ticksPerSecond;
        }


    private int myTicksVisible;
    }
