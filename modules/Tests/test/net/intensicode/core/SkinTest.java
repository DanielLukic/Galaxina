package net.intensicode.core;

import junit.framework.TestCase;

public class SkinTest extends TestCase
    {
    public final void test_getCharCode()
        {
        final int gridSize = 5;
        assertEquals( 0, SkinManager.getCyclicFrameIndex( 0, gridSize ) );
        assertEquals( 1, SkinManager.getCyclicFrameIndex( 1, gridSize ) );
        assertEquals( 2, SkinManager.getCyclicFrameIndex( 2, gridSize ) );
        assertEquals( 3, SkinManager.getCyclicFrameIndex( 3, gridSize ) );

        assertEquals( 4, SkinManager.getCyclicFrameIndex( 4, gridSize ) );
        assertEquals( 9, SkinManager.getCyclicFrameIndex( 5, gridSize ) );
        assertEquals( 14, SkinManager.getCyclicFrameIndex( 6, gridSize ) );
        assertEquals( 19, SkinManager.getCyclicFrameIndex( 7, gridSize ) );

        assertEquals( 24, SkinManager.getCyclicFrameIndex( 8, gridSize ) );
        assertEquals( 23, SkinManager.getCyclicFrameIndex( 9, gridSize ) );
        assertEquals( 22, SkinManager.getCyclicFrameIndex( 10, gridSize ) );
        assertEquals( 21, SkinManager.getCyclicFrameIndex( 11, gridSize ) );

        assertEquals( 20, SkinManager.getCyclicFrameIndex( 12, gridSize ) );
        assertEquals( 15, SkinManager.getCyclicFrameIndex( 13, gridSize ) );
        assertEquals( 10, SkinManager.getCyclicFrameIndex( 14, gridSize ) );
        assertEquals( 5, SkinManager.getCyclicFrameIndex( 15, gridSize ) );

        assertEquals( 0, SkinManager.getCyclicFrameIndex( 16, gridSize ) );
        }
    }
