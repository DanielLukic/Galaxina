package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.enemies.Enemy;
import net.intensicode.galaxina.game.weapons.Weapon;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
public final class GunShot
    {
    public final Position worldPosFixed = new Position();

    public boolean active;

    public int speedFixed;

    public Weapon source;



    public final GunShot init( final Position aStartPosition, final int aFixedSpeed )
        {
        worldPosFixed.setTo( aStartPosition );
        speedFixed = aFixedSpeed;
        active = true;
        return this;
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        if ( !active ) return;

        worldPosFixed.y -= speedFixed;

        final World world = aGameModel.world;
        active = world.isInside( worldPosFixed );
        if ( !active )
            {
            if ( source != null ) source.onProjectileDone( this );
            return;
            }

        final DynamicArray enemies = aGameModel.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( enemy.active == false ) continue;

            final boolean hit = enemy.isHit( worldPosFixed );
            if ( hit == false ) continue;

            enemy.hit();

            active = false;
            if ( source != null ) source.onProjectileDone( this );
            break;
            }
        }
    }
