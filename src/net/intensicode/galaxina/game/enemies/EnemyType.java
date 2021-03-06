package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.extras.ExtraTypes;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;

public final class EnemyType
    {
    public final SizeF sizeInWorld = new SizeF();

    public int extraID = ExtraTypes.NO_EXTRA;

    public Class weaponClass;

    public final int typeID;

    public String filename;

    public int baseScore;

    public int baseSpeed;

    public String name;

    public int hits;



    public EnemyType( final int aTypeID )
        {
        typeID = aTypeID;
        }

    public final void add( final EnemyBehavior aBehavior )
        {
        myBehaviors.add( aBehavior );
        }

    public final EnemyWeapon createWeapon( final PositionF aParentPosition )
        {
        try
            {
            final EnemyWeapon weapon = (EnemyWeapon) weaponClass.newInstance();
            weapon.attachTo( aParentPosition );
            return weapon;
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            Log.debug( "Failed creating weapon: {}", e.toString() );
            //#endif
            throw new RuntimeException( e.toString() );
            }
        }

    public final int getScore( final GameModel aModel )
        {
        return baseScore + ( aModel.level.numberStartingAt1 - 1 ) * baseScore / 10;
        }

    public final float getSpeed( final GameModel aModel )
        {
        return baseSpeed * aModel.world.visibleSize.height / 100 / GameObject.timing.ticksPerSecond;
        }

    public final void tickEnteringBehaviors( final Enemy aEnemy )
        {
        for ( int idx = 0; idx < myBehaviors.size; idx++ )
            {
            final EnemyBehavior behavior = (EnemyBehavior) myBehaviors.objects[ idx ];
            if ( !behavior.isActive( aEnemy ) ) continue;
            behavior.onEntering( aEnemy );
            }
        }

    public final void tickWaitingBehaviors( final Enemy aEnemy )
        {
        for ( int idx = 0; idx < myBehaviors.size; idx++ )
            {
            final EnemyBehavior behavior = (EnemyBehavior) myBehaviors.objects[ idx ];
            if ( !behavior.isActive( aEnemy ) ) continue;
            behavior.onWaiting( aEnemy );
            }
        }

    public final void tickAttackingBehaviors( final Enemy aEnemy )
        {
        for ( int idx = 0; idx < myBehaviors.size; idx++ )
            {
            final EnemyBehavior behavior = (EnemyBehavior) myBehaviors.objects[ idx ];
            if ( !behavior.isActive( aEnemy ) ) continue;
            behavior.onAttacking( aEnemy );
            }
        }



    private final DynamicArray myBehaviors = new DynamicArray();
    }
