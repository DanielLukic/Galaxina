package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.GameObject;
import net.intensicode.util.*;

public final class Level extends GameObject
    {
    public final DynamicArray activeEnemies = new DynamicArray();


    public int numberStartingAt1;

    public boolean levelComplete;

    public int bonusApplies;

    public boolean perfect;


    public final void advance()
        {
        numberStartingAt1++;
        }

    public final boolean anotherEnemyBombAllowed()
        {
        if ( myBombWaitTicks > 0 ) return false;
        return myBombCount < myMaxBombs;
        }

    public final void onEnemyBombLaunched()
        {
        myBombWaitTicks += timing.ticksPerSecond / 3;
        myBombCount++;
        }

    public final void onEnemyBombDone()
        {
        myBombWaitTicks -= timing.ticksPerSecond / 5;
        if ( myBombWaitTicks < 0 ) myBombWaitTicks = 0;
        myBombCount--;
        }

    public final boolean anotherAttackerAllowed()
        {
        final int activeAttackers = model.enemySpawner.numberOfAttackers();
        return activeAttackers < myMaxAttackers;
        }

    public final boolean isChallengingStage()
        {
        return model.enemySpawner.isChallengingStage();
        }

    public final boolean fireWhileAttacking()
        {
        return !isChallengingStage() && numberStartingAt1 > 5;
        }

    public final boolean fireWhileEntering()
        {
        return !isChallengingStage();
        }

    public final boolean fireWhileWaiting()
        {
        return !isChallengingStage();
        }

    public final float getGunShotSpeed()
        {
        return 2 * GameObject.model.world.visibleSize.height / GameObject.timing.ticksPerSecond;
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        numberStartingAt1 = 1;
        }

    public final void onStartLevel() throws Exception
        {
        activeEnemies.clear();
        levelComplete = false;
        bonusApplies = 0;

        myMaxBombs = Math.min( MAX_BOMBS, 4 + ( numberStartingAt1 - 1 ) * 2 / 5 );
        myMaxAttackers = Math.min( MAX_ATTACKERS, 1 + ( numberStartingAt1 - 1 ) / 5 );

        myBombCount = 0;
        }

    public final void onControlTick()
        {
        if ( myBombWaitTicks > 0 ) myBombWaitTicks--;

        if ( activeEnemies.size == model.enemySpawner.numberOfEnemies() )
            {
            boolean levelComplete = true;
            boolean perfectRun = activeEnemies.size > 0;
            for ( int idx = activeEnemies.size - 1; idx >= 0; idx-- )
                {
                final Enemy enemy = (Enemy) activeEnemies.objects[ idx ];
                if ( enemy.active ) levelComplete = false;
                if ( enemy.isReady() ) perfectRun = false;
                }

            if ( levelComplete ) onLevelComplete( perfectRun );
            }
        }

    // Implementation

    private void onLevelComplete( final boolean aPerfectRun )
        {
        activeEnemies.clear();
        levelComplete = true;
        perfect = aPerfectRun;

        final int perfectBonus = 1000;
        bonusApplies = aPerfectRun ? perfectBonus : 0;

        model.onLevelComplete();
        model.player.score += bonusApplies;
        }


    private int myMaxBombs;

    private int myBombCount;

    private int myMaxAttackers;

    private int myBombWaitTicks;

    private static final int MAX_ATTACKERS = 8;

    private static final int MAX_BOMBS = 20;
    }
