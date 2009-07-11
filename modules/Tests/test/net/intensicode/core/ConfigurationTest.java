package net.intensicode.core;

import junit.framework.TestCase;
import net.intensicode.util.DynamicArray;

import java.io.IOException;



public final class ConfigurationTest extends TestCase
    {
    public final void testFindDelimiter()
        {
        assertEquals( -1, Configuration.findDelimiter( "abc123", "\n\r!", 0 ) );
        assertEquals( 3, Configuration.findDelimiter( "abc\n123", "\n\r!", 0 ) );
        assertEquals( 1, Configuration.findDelimiter( "a!b\rc\n123", "\n\r!", 0 ) );
        assertEquals( -1, Configuration.findDelimiter( "a!b\rc\n123", "", 0 ) );
        assertEquals( -1, Configuration.findDelimiter( "", "", 0 ) );
        }

    public final void testSplitString() throws IOException
        {
        final String dataString = new String( TEST_CONFIGURATION );

        final DynamicArray lines = Configuration.splitString( dataString, true );
        assertEquals( 4, lines.size );
        assertEquals( "Skin.allowResize = false", lines.objects[ 0 ] );
        assertEquals( "BitmapFontGen.buffered = false", lines.objects[ 1 ] );
        assertEquals( "TargetScreenSize.width = 0", lines.objects[ 2 ] );
        assertEquals( "TargetScreenSize.height = 0", lines.objects[ 3 ] );
        }

    private static final String TEST_CONFIGURATION = "\n" +
            "Skin.allowResize = false\n" +
            "\n" +
            "BitmapFontGen.buffered = false\n" +
            "\n" +
            "TargetScreenSize.width = 0\n" +
            "TargetScreenSize.height = 0";
    }
