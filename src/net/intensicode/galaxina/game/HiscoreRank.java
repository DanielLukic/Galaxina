package net.intensicode.galaxina.game;

/**
 * TODO: Describe this!
 */
final class HiscoreRank
{
    int score;

    int level;

    String name;



    HiscoreRank( final int aScore, final int aLevel, final String aName )
    {
        score = aScore;
        level = aLevel;
        name = aName;
    }

    public final String toString()
    {
        final StringBuffer buffer = new StringBuffer();
        buffer.append( score );
        buffer.append( ' ' );
        buffer.append( level );
        buffer.append( ' ' );
        buffer.append( name );
        return buffer.toString();
    }
}
