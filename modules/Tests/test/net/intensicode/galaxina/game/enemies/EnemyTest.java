package net.intensicode.galaxina.game.enemies;

import junit.framework.TestCase;
import net.intensicode.galaxina.game.objects.Enemy;

public final class EnemyTest extends TestCase
    {
    public final void testGetDirectionDelta()
        {
        assertEquals( 10, getDelta( 10, 0 ) );
        assertEquals( 90, getDelta( 90, 0 ) );
        assertEquals( 180, getDelta( 180, 0 ) );
        assertEquals( -160, getDelta( 200, 0 ) );
        assertEquals( -60, getDelta( 300, 0 ) );

        assertEquals( -20, getDelta( 300, 320 ) );
        assertEquals( -90, getDelta( -45, 45 ) );
        assertEquals( 90, getDelta( 45, -45 ) );
        assertEquals( 90, getDelta( 45, 315 ) );
        assertEquals( -90, getDelta( 315, 45 ) );
        }

    private final int getDelta( final int aTo, final int aFrom )
        {
        final float to = ( aTo );
        final float from = ( aFrom );
        final float delta = Enemy.getDirectionDelta( to, from );
        return Math.round( delta );
        }
    }
