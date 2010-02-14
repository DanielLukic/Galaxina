package net.intensicode.galaxina.game.enemies;

import junit.framework.TestCase;
import net.intensicode.util.FixedMath;

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
        final int toFixed = FixedMath.toFixed( aTo );
        final int fromFixed = FixedMath.toFixed( aFrom );
        final int delta = Enemy.getDirectionDelta( toFixed, fromFixed );
        return FixedMath.toIntRounded( delta );
        }
    }
