package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyBehavior;

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
