package net.intensicode.game.enemies;

import net.intensicode.core.Engine;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
public abstract class EnemyWeapon
    {
    public static Engine engine;

    public static GameModel model;



    public abstract void onControlTick();

    public abstract void attachTo( final Position aParentPosition );

    public abstract boolean canFire();

    public abstract boolean canFireOnRun();

    public abstract boolean isTargetGood();

    public abstract void fireIfPossible();
    }
