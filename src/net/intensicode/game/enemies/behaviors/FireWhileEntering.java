package net.intensicode.game.enemies.behaviors;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyBehavior;

public final class FireWhileEntering extends EnemyBehavior
    {
    public final void onEntering( final Enemy aEnemy )
        {
        if ( !model.level.fireWhileEntering() ) return;
        if ( !aEnemy.weapon.canFire() ) return;
        if ( !aEnemy.weapon.isTargetGood() ) return;

        aEnemy.weapon.fireIfPossible();
        }
    }
