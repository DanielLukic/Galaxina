package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyBehavior;

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
