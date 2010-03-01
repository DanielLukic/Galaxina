package net.intensicode.util;

import junit.framework.TestCase;

public class Rectangle_uniteWith_Test extends TestCase
    {
    public final void test_same()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 10, 10, 10, 10 );
        a.uniteWith( b );
        assertEquals( "a unchanged", new Rectangle( 10, 10, 10, 10 ), a );
        }

    public final void test_contains()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 12, 12, 6, 6 );
        a.uniteWith( b );
        assertEquals( "a unchanged", new Rectangle( 10, 10, 10, 10 ), a );
        }

    public final void test_contained()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 5, 5, 15, 15 );
        a.uniteWith( b );
        assertEquals( "b unchanged", new Rectangle( 5, 5, 15, 15 ), b );
        }

    public final void test_besides()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 10, 10, 10 );
        a.uniteWith( b );
        assertEquals( "a+b merged", new Rectangle( 10, 10, 20, 10 ), a );
        }

    public final void test_below()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 10, 20, 10, 10 );
        a.uniteWith( b );
        assertEquals( "a+b merged", new Rectangle( 10, 10, 10, 20 ), a );
        }

    public final void test_touching()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 20, 20, 10, 10 );
        a.uniteWith( b );
        assertEquals( "a+b merged convex", new Rectangle( 10, 10, 20, 20 ), a );
        }

    public final void test_distant()
        {
        final Rectangle a = new Rectangle( 10, 10, 10, 10 );
        final Rectangle b = new Rectangle( 30, 30, 10, 10 );
        a.uniteWith( b );
        assertEquals( "a+b merged convex", new Rectangle( 10, 10, 30, 30 ), a );
        }
    }
