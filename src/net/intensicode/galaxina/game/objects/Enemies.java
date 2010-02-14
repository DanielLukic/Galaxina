package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.enemies.behaviors.*;
import net.intensicode.galaxina.game.extras.ExtraTypes;
import net.intensicode.util.Log;

import java.io.*;

public final class Enemies extends GameObject
    {
    public static final EnemyBehavior FIRE_WHILE_ENTERING = new FireWhileEntering();

    public static final EnemyBehavior FIRE_WHILE_WAITING = new FireWhileWaiting();

    public static final EnemyBehavior FIRE_WHILE_ATTACKING = new FireWhileAttacking();

    public static final EnemyBehavior STRIKE_WHEN_ATTACKED = new StrikeWhenAttacked();

    public static final EnemyBehavior ATTACK_OR_CIRLE = new AttackOrCircle();

    public static final EnemyBehavior ATTACK_WHEN_CLOSE = new AttackWhenClose();

    public static final EnemyBehavior AVOID_BEING_HIT = new AvoidBeingHit();

    public static final EnemyBehavior HARD_DIVE = new HardDive();

    public EnemyType[] types;



    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        }

    public final void onControlTick()
        {
        }

    // Protected Interface

    protected final void onInitOnce() throws Exception
        {
        loadEnemyTypes();
        }

    // Implementation

    private void loadEnemyTypes() throws Exception
        {
        final InputStream stream = system.resources.openStreamChecked( DATA_FILE_NAME );
        final DataInputStream input = new DataInputStream( stream );

        final int count = input.readInt();
        //#if DEBUG
        Log.debug( "Loading {} enemy types", count );
        //#endif
        types = new EnemyType[count];

        for ( int idx = 0; idx < count; idx++ )
            {
            types[ idx ] = loadEntry( input, idx );
            }

        input.close();
        }

    private EnemyType loadEntry( final DataInputStream aInputStream, final int aTypeID ) throws Exception
        {
        final EnemyType type = new EnemyType( aTypeID );
        type.name = aInputStream.readUTF();
        type.filename = aInputStream.readUTF();
        type.hits = aInputStream.readInt();
        type.baseScore = aInputStream.readInt();

        // Hardcoded for now.. should obviously be configurable, too..
        type.extraID = ExtraTypes.RANDOM;
        type.baseSpeed = 40 - aTypeID / 2 * 5;
        type.weaponClass = Class.forName( "net.intensicode.galaxina.game.enemies.BombDropper" );

        //#if DEBUG
        Log.debug( "Enemy type: {}", aTypeID );
        Log.debug( "Enemy name: {}", type.name );
        //#endif

        //switch ( aTypeID )
        //    {
        //    case 0:
        //        type.add( ATTACK_WHEN_CLOSE );
        //        break;
        //    case 1:
        //        type.add( ATTACK_OR_CIRLE );
        //        break;
        //    case 2:
        //        type.add( STRIKE_WHEN_ATTACKED );
        //        break;
        //    case 3:
        //        type.add( HARD_DIVE );
        //        break;
        //    case 4:
        //        type.add( AVOID_BEING_HIT );
        //        break;
        //    case 5:
        //        type.add( AVOID_BEING_HIT );
        //        break;
        //    }

        type.add( FIRE_WHILE_ENTERING );
        type.add( FIRE_WHILE_WAITING );
        type.add( FIRE_WHILE_ATTACKING );

        return type;
        }



    private static final String DATA_FILE_NAME = "/enemies.dat";
    }
