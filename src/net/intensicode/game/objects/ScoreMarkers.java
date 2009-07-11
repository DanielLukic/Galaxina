package net.intensicode.game.objects;

import net.intensicode.core.Engine;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.Position;


/**
 * TODO: Describe this!
 */
public final class ScoreMarkers extends GameObject
    {
    public final DynamicArray scoreTags = new DynamicArray();


    public final void add( final Position aPosition, final int aScore )
        {
        scoreTags.add( new ScoreTag( aPosition, aScore, Engine.ticksPerSecond ) );
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
            scoreTag.onControlTick( engine, model.world );
            if ( scoreTag.active == false ) scoreTags.remove( idx );
            }
        }
    }
