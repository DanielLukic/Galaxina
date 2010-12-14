package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.GameObject;

public final class ScoreMarkers extends GameObject
    {
    public final DynamicArray scoreTags = new DynamicArray();


    public final void add( final PositionF aPosition, final int aScore )
        {
        scoreTags.add( new ScoreTag( aPosition, aScore, timing.ticksPerSecond ) );
        }

    // From GameObject

    public final void onStartGame()
        {
        }

    public final void onStartLevel()
        {
        scoreTags.clear();
        }

    public final void onControlTick()
        {
        for ( int idx = scoreTags.size - 1; idx >= 0; idx-- )
            {
            final ScoreTag scoreTag = (ScoreTag) scoreTags.objects[ idx ];
            scoreTag.onControlTick( timing, model.world );
            if ( !scoreTag.active ) scoreTags.remove( idx );
            }
        }
    }
