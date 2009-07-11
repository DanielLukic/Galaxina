package net.intensicode.game.enemies.behaviors;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyBehavior;

public final class FireWhileAttacking extends EnemyBehavior
    {
    public final void onAttacking( final Enemy aEnemy )
        {
        if ( !model.level.fireWhileAttacking() ) return;
        if ( !aEnemy.weapon.canFireOnRun() ) return;
        if ( !aEnemy.weapon.isTargetGood() ) return;

        aEnemy.weapon.fireIfPossible();
        }
    }
