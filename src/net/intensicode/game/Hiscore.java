/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.game;

import net.intensicode.SystemConfiguration;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * TODO: Describe this!
 */
public final class Hiscore
{
    public static final int MAX_NAME_LENGTH = 16;

    public static final int HISCORE_RS_ID = 1;

    public static final String HISCORE_RS_NAME = "hiscore";

    public static final String HISCORE_ID = SystemConfiguration.HISCORE_ID;



    public Hiscore()
    {
        for ( int idx = 0; idx < NUMBER_OF_ENTRIES; idx++ )
        {
            myEntries.add( new HiscoreRank( 100000 - idx * 10000, 10 - idx, "The.French.DJ" ) );
        }
    }

    public final String id()
    {
        return HISCORE_ID;
    }

    public final boolean isNewHiscore( final int aScore )
    {
        return ( aScore >= rank( 0 ).score );
    }

    public final boolean isHiscoreRank( final int aScore )
    {
        return ( aScore >= rank( myEntries.size - 1 ).score );
    }

    public final int numberOfEntries()
    {
        return myEntries.size;
    }

    public final int topScore()
    {
        return score( 0 );
    }

    public final int score( final int aRank )
    {
        return rank( aRank ).score;
    }

    public final int level( final int aRank )
    {
        return rank( aRank ).level;
    }

    public final String name( final int aRank )
    {
        return rank( aRank ).name;
    }

    public final void loadFrom( final InputStream aInput ) throws IOException
    {
        //#if DEBUG
        Log.debug( "Loading hiscore" );
        //#endif

        final String header = readLine( aInput );
        if ( header.equals( HISCORE_ID ) == false )
        {
            //#if DEBUG
            net.intensicode.util.Log.debug( "Invalid hiscore header: {}", header );
            //#endif
            throw new IOException( "Invalid hiscore header" );
        }

        for ( int idx = 0; idx < NUMBER_OF_ENTRIES; idx++ )
        {
            final HiscoreRank rank = ( HiscoreRank ) myEntries.objects[ idx ];
            rank.score = readInteger( aInput );
            rank.level = readInteger( aInput );
            rank.name = readLine( aInput );
        }
    }

    public final void saveTo( final OutputStream aOutput ) throws IOException
    {
        //#if DEBUG
        Log.debug( "Saving hiscore" );
        //#endif

        aOutput.write( HISCORE_ID.getBytes() );
        aOutput.write( 10 );

        for ( int idx = 0; idx < NUMBER_OF_ENTRIES; idx++ )
        {
            final HiscoreRank rank = ( HiscoreRank ) myEntries.objects[ idx ];
            aOutput.write( rank.toString().getBytes() );
            aOutput.write( 10 );
        }
    }

    public final void insert( final int aScore, final int aLevel, final String aName )
    {
        for ( int idx = 0; idx < myEntries.size; idx++ )
        {
            if ( aScore >= rank( idx ).score )
            {
                myEntries.insert( idx, new HiscoreRank( aScore, aLevel, aName ) );
                break;
            }
        }

        while ( myEntries.size > numberOfEntries() ) myEntries.remove( myEntries.size - 1 );
    }

    public final String rankSpec( final int aIndex )
    {
        final HiscoreRank rank = ( HiscoreRank ) myEntries.get( aIndex );
        return rank.toString();
    }

    // Implementation

    private final HiscoreRank rank( final int aIndex )
    {
        return ( HiscoreRank ) myEntries.get( aIndex );
    }

    private static final String readLine( final InputStream aInput ) throws IOException
    {
        final StringBuffer buffer = new StringBuffer();
        boolean skipEOL = true;
        while ( true )
        {
            final int newChar = aInput.read();
            if ( newChar == -1 ) break;

            if ( newChar == '\n' || newChar == '\r' )
            {
                if ( skipEOL ) continue;
                else break;
            }
            else
            {
                skipEOL = false;
                buffer.append( ( char ) newChar );
            }
        }
        return buffer.toString();
    }

    private static final int readInteger( final InputStream aInput ) throws IOException
    {
        int value = 0;
        while ( true )
        {
            final int newChar = aInput.read();
            if ( newChar == -1 ) break;
            if ( newChar == ' ' ) break;
            if ( newChar == '\n' || newChar == '\r' ) continue;
            if ( newChar < '0' || newChar > '9' ) throw new IllegalArgumentException();
            value *= 10;
            value += ( newChar - '0' );
        }
        return value;
    }



    private final DynamicArray myEntries = new DynamicArray( 10, 0 );

    private static final int NUMBER_OF_ENTRIES = 10;
}
