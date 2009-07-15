package net.intensicode.game.drawers;

import junit.framework.TestCase;
import net.intensicode.util.UtilitiesEx;


public final class EnemyDrawerTest extends TestCase
    {
    public final void test_getDirectionID()
        {
        assertEquals( 45, 360 / 8 );
        assertEquals( 0, UtilitiesEx.getDirectionID( 0, 8 ) );
        assertEquals( 0, UtilitiesEx.getDirectionID( 22, 8 ) );
        assertEquals( 1, UtilitiesEx.getDirectionID( 25, 8 ) );
        assertEquals( 1, UtilitiesEx.getDirectionID( 45, 8 ) );
        assertEquals( 1, UtilitiesEx.getDirectionID( 67, 8 ) );
        assertEquals( 2, UtilitiesEx.getDirectionID( 70, 8 ) );
        assertEquals( 4, UtilitiesEx.getDirectionID( 180, 8 ) );
        assertEquals( 6, UtilitiesEx.getDirectionID( 270, 8 ) );
        assertEquals( 7, UtilitiesEx.getDirectionID( 315, 8 ) );
        }
    }
