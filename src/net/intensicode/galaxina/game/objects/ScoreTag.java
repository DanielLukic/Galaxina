package net.intensicode.galaxina.game.objects;

import net.intensicode.core.GameTiming;
import net.intensicode.util.Position;
import net.intensicode.galaxina.game.*;

public final class ScoreTag extends WorldObject
    {
    public final int score;


    public ScoreTag( final Position aPosition, final int aScore, final int aTicksVisible )
        {
        active = true;
        worldPosFixed.setTo( aPosition );
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
