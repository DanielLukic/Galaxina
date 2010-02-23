package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyBehavior;

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
