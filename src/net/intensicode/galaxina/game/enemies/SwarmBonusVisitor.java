package net.intensicode.galaxina.game.enemies;

import net.intensicode.util.Visitor;

public final class SwarmBonusVisitor extends Visitor
    {
    public SwarmBonusVisitor( final Swarm aSwarm )
        {
        mySwarm = aSwarm;
        }

    // From Visitor

    public final void init()
        {
        myBonusScore = 0;
        myBonusApplies = true;
        }

    public final void visit( final Object aObject )
        {
        final Enemy enemy = (Enemy) aObject;
        if ( enemy.isReady() ) myBonusApplies = false;
        else myBonusScore += enemy.getScore();
        }

    public final void done()
        {
        if ( myBonusApplies ) mySwarm.onScoreSwarmBonus( myBonusScore );
        }



    private int myBonusScore;

    private boolean myBonusApplies;

    private final Swarm mySwarm;
    }
