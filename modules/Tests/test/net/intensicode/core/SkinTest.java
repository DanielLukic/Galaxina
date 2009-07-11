package net.intensicode.core;

import junit.framework.TestCase;

public class SkinTest extends TestCase
    {
    public final void test_getCharCode()
        {
        final int gridSize = 5;
        assertEquals( 0, Skin.getCyclicFrameIndex( 0, gridSize ) );
        assertEquals( 1, Skin.getCyclicFrameIndex( 1, gridSize ) );
        assertEquals( 2, Skin.getCyclicFrameIndex( 2, gridSize ) );
        assertEquals( 3, Skin.getCyclicFrameIndex( 3, gridSize ) );

        assertEquals( 4, Skin.getCyclicFrameIndex( 4, gridSize ) );
        assertEquals( 9, Skin.getCyclicFrameIndex( 5, gridSize ) );
        assertEquals( 14, Skin.getCyclicFrameIndex( 6, gridSize ) );
        assertEquals( 19, Skin.getCyclicFrameIndex( 7, gridSize ) );

        assertEquals( 24, Skin.getCyclicFrameIndex( 8, gridSize ) );
        assertEquals( 23, Skin.getCyclicFrameIndex( 9, gridSize ) );
        assertEquals( 22, Skin.getCyclicFrameIndex( 10, gridSize ) );
        assertEquals( 21, Skin.getCyclicFrameIndex( 11, gridSize ) );

        assertEquals( 20, Skin.getCyclicFrameIndex( 12, gridSize ) );
        assertEquals( 15, Skin.getCyclicFrameIndex( 13, gridSize ) );
        assertEquals( 10, Skin.getCyclicFrameIndex( 14, gridSize ) );
        assertEquals( 5, Skin.getCyclicFrameIndex( 15, gridSize ) );

        assertEquals( 0, Skin.getCyclicFrameIndex( 16, gridSize ) );
        }
    }
