package net.intensicode.game.enemies;

import net.intensicode.core.Engine;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Random;

public abstract class EnemyBehavior
    {
    public static GameModel model;

    public static Engine engine;



    public boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.active;
        }

    public void onEntering( final Enemy aEnemy )
        {
        }

    public void onWaiting( final Enemy aEnemy )
        {
        }

    public void onAttacking( final Enemy aEnemy )
        {
        }



    protected static final Random theRandom = new Random( 4711 );
    }
