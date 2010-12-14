package net.intensicode.galaxina.game.objects;

import net.intensicode.core.GameTiming;
import net.intensicode.util.*;
import net.intensicode.galaxina.game.*;

public final class ScoreTag extends WorldObject
    {
    public final int score;


    public ScoreTag( final PositionF aPosition, final int aScore, final int aTicksVisible )
        {
        active = true;
        worldPos.setTo( aPosition );
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

        worldPos.y -= aWorld.visibleSize.height / 6 / aGameTiming.ticksPerSecond;
        }


    private int myTicksVisible;
    }
