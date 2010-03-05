package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.GameObject;
import net.intensicode.galaxina.game.extras.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;


public final class Swarm
    {
    /**
     * Shared position for showing a swarm bonus.
     */
    public final Position lastHitPosition = new Position();

    public boolean isComplete = false;

    public boolean isDestroyed = false;

    public int bonusApplies;

    public int extraID;


    public Swarm()
        {
        }

    public final int numberOfAttackers()
        {
        int attackers = 0;
        for ( int idx = 0; idx < myParticipants.size; idx++ )
            {
            final Enemy enemy = (Enemy) myParticipants.get( idx );
            if ( enemy.controller == Enemy.ATTACKING ) attackers++;
            }
        return attackers;
        }

    public final void onScoreSwarmBonus( final int aBonusScore )
        {
        bonusApplies += aBonusScore;
        if ( extraID == ExtraTypes.NO_EXTRA ) return;

        final ExtraType type = GameObject.model.extraTypes.all[ extraID ];
        GameObject.model.extras.deploy( lastHitPosition, type );
        }

    public final Enemy spawnEnemy()
        {
        final Enemy enemy = new Enemy( lastHitPosition );
        myParticipants.add( enemy );
        return enemy;
        }

    public final void onControlTick()
        {
        if ( isDestroyed ) return;

        for ( int idx = 0; idx < myParticipants.size; idx++ )
            {
            final Enemy enemy = (Enemy) myParticipants.objects[ idx ];
            if ( enemy.active ) enemy.onControlTick();
            }

        if ( !isComplete ) return;

        boolean allEnemiesDestroyed = true;
        for ( int idx = 0; idx < myParticipants.size; idx++ )
            {
            final Enemy enemy = (Enemy) myParticipants.objects[ idx ];
            if ( enemy.active ) allEnemiesDestroyed = false;
            }

        // Only accept 'allEnemiesDestroyed' if swarm is already complete..
        isDestroyed = allEnemiesDestroyed;

        // Check if completely destroyed while entering the level:
        if ( isDestroyed ) myParticipants.each( CHECK_FOR_SWARM_BONUS );
        }


    private final DynamicArray myParticipants = new DynamicArray();

    private final Visitor CHECK_FOR_SWARM_BONUS = new SwarmBonusVisitor( this );
    }
