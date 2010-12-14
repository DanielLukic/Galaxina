package net.intensicode.galaxina.game.enemies;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameModel;
import net.intensicode.util.*;

public abstract class EnemyWeapon
    {
    public static GameTiming timing;

    public static GameEngine engine;

    public static GameModel model;

    public abstract void onControlTick();

    public abstract void attachTo( final PositionF aParentPosition );

    public abstract boolean canFire();

    public abstract boolean canFireOnRun();

    public abstract boolean isTargetGood();

    public abstract void fireIfPossible();
    }
