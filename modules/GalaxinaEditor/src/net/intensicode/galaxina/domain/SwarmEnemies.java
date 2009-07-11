package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class SwarmEnemies extends GroupDomainObject<SwarmEnemy>
    {
    public SwarmEnemies( final Enemies aEnemies, final EditorConfiguration aConfiguration )
        {
        myEnemies = aEnemies;
        myConfiguration = aConfiguration;
        }

    public final void addEntry( final Enemy aEnemy )
        {
        final NormalSwarmEnemy swarmEnemy = new NormalSwarmEnemy( myEnemies );
        swarmEnemy.setEnemey( aEnemy );
        addEntry( swarmEnemy );
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        loadEntries( aInputStream );
        fireDataChanged();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        saveEntries( aOutputStream );
        }

    // From Object

    public final SwarmEnemies clone()
        {
        final SwarmEnemies newSwarmEnemys = new SwarmEnemies( myEnemies, myConfiguration );
        for ( final SwarmEnemy enemy : myEntries )
            {
            newSwarmEnemys.myEntries.add( enemy.clone() );
            }
        return newSwarmEnemys;
        }

    // From GroupDomainObject

    protected final SwarmEnemy loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        return NormalSwarmEnemy.loadNew( myEnemies, aInputStream );
        }



    private final Enemies myEnemies;

    private final EditorConfiguration myConfiguration;
    }
