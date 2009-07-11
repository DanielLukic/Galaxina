package net.intensicode.game.enemies.behaviors;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyBehavior;

public final class FireWhileWaiting extends EnemyBehavior
    {
    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.fireWhileWaiting() ) return;
        if ( !aEnemy.weapon.canFire() ) return;
        if ( !aEnemy.weapon.isTargetGood() ) return;

        aEnemy.weapon.fireIfPossible();
        }
    }
