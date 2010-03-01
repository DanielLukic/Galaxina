package net.intensicode.util;

import junit.framework.TestCase;

public class Rectangle_isAdjacent_Test extends TestCase
    {
    public final void test_same()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 10, 10, 10, 10 );
        assertFalse( "same is not adjacent", a.isAdjacent( b ) );
        }

    public final void test_touched_at_corner()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 20, 10, 10 );
        assertFalse( "touched at corner is not adjacent", a.isAdjacent( b ) );
        }

    public final void test_intersecting()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 15, 15, 10, 10 );
        assertFalse( "intersecting is not adjacent", a.isAdjacent( b ) );
        }

    public final void test_same_rows_but_not_touching()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 30, 10, 10, 10 );
        assertFalse( "a not touching b", a.isAdjacent( b ) );
        }

    public final void test_touched_left()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 0, 10, 10, 10 );
        assertTrue( "a touched at left by b", a.isAdjacent( b ) );
        }

    public final void test_touched_top()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 10, 0, 10, 10 );
        assertTrue( "a touched at top by b", a.isAdjacent( b ) );
        }

    public final void test_touched_right()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 10, 10, 10 );
        assertTrue( "a touched at right by b", a.isAdjacent( b ) );
        }

    public final void test_touched_bottom()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 10, 20, 10, 10 );
        assertTrue( "a touched at bottom by b", a.isAdjacent( b ) );
        }

    public final void test_touched_right_but_taller_b()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 10, 10, 20 );
        assertFalse( "a touched at right by taller b", a.isAdjacent( b ) );
        }

    public final void test_touched_right_but_smaller_b()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 10, 10, 5 );
        assertFalse( "a touched at right by smaller b", a.isAdjacent( b ) );
        }
    }
