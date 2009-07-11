package net.intensicode.game.data;

import java.io.DataInputStream;
import java.io.IOException;

public final class SwarmsConfiguration
    {
    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        final int size = aInputStream.readInt();
        mySwarms = new SwarmConfiguration[size];
        for ( int idx = 0; idx < size; idx++ )
            {
            final SwarmConfiguration config = new SwarmConfiguration();
            config.load( aInputStream );
            mySwarms[ idx ] = config;
            }
        }

    public final void applyTo( final FormationConfiguration aConfiguration )
        {
        aConfiguration.numberOfSwarms = mySwarms.length;
        aConfiguration.numberOfEnemies = numberOfEnemies();
        aConfiguration.swarms = mySwarms;
        }

    public final int numberOfEnemies()
        {
        int count = 0;
        for ( int idx = 0; idx < mySwarms.length; idx++ )
            {
            count += mySwarms[ idx ].size;
            }
        return count;
        }



    private SwarmConfiguration[] mySwarms;
    }
