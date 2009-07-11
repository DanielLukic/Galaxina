package net.intensicode.game.drawers;

import junit.framework.TestCase;
import net.intensicode.util.Utilities;


public final class EnemyDrawerTest extends TestCase
    {
    public final void test_getDirectionID()
        {
        assertEquals( 45, 360 / 8 );
        assertEquals( 0, Utilities.getDirectionID( 0, 8 ) );
        assertEquals( 0, Utilities.getDirectionID( 22, 8 ) );
        assertEquals( 1, Utilities.getDirectionID( 25, 8 ) );
        assertEquals( 1, Utilities.getDirectionID( 45, 8 ) );
        assertEquals( 1, Utilities.getDirectionID( 67, 8 ) );
        assertEquals( 2, Utilities.getDirectionID( 70, 8 ) );
        assertEquals( 4, Utilities.getDirectionID( 180, 8 ) );
        assertEquals( 6, Utilities.getDirectionID( 270, 8 ) );
        assertEquals( 7, Utilities.getDirectionID( 315, 8 ) );
        }
    }
