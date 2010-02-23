package net.intensicode.galaxina.game.enemies;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameModel;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.Random;

public abstract class EnemyBehavior
    {
    public static GameModel model;

    public static GameEngine engine;

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
