package net.intensicode.game.data;

import junit.framework.TestCase;

import java.io.IOException;

public final class LevelConfigurationTest extends TestCase
    {
    public final void testChallengingStage() throws IOException
        {
        final LevelConfiguration config = new LevelConfiguration();

        config.load( new LevelTestData( 1 ).done() );
        assertEquals( false, config.challengingStage );

        config.load( new LevelTestData( 1 ).setChallenging().done() );
        assertEquals( true, config.challengingStage );
        }

    public final void testAppliesTo() throws IOException
        {
        final LevelConfiguration config = new LevelConfiguration();
        config.load( new LevelTestData( 1 ).done() );

        assertFalse( config.appliesTo( 0 ) );
        assertTrue( config.appliesTo( 1 ) );
        assertFalse( config.appliesTo( 2 ) );
        assertFalse( config.appliesTo( 3 ) );
        assertFalse( config.appliesTo( 5 ) );
        assertFalse( config.appliesTo( 10 ) );
        }

    public final void testAppliesToRepeated() throws IOException
        {
        final LevelConfiguration config = new LevelConfiguration();
        config.load( new LevelTestData( 4 ).setRepeated( 3 ).done() );

        assertFalse( config.appliesTo( 0 ) );
        assertFalse( config.appliesTo( 1 ) );
        assertFalse( config.appliesTo( 2 ) );
        assertFalse( config.appliesTo( 3 ) );
        assertTrue( config.appliesTo( 4 ) );
        assertFalse( config.appliesTo( 5 ) );
        assertFalse( config.appliesTo( 6 ) );
        assertTrue( config.appliesTo( 7 ) );
        assertFalse( config.appliesTo( 8 ) );
        assertFalse( config.appliesTo( 8 ) );
        assertTrue( config.appliesTo( 10 ) );
        }
    }
