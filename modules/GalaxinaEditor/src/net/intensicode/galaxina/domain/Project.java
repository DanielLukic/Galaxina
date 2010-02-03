package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.util.Log;

import java.io.*;

public final class Project extends AbstractDomainObject<ProjectListener>
    {
    public Project( final EditorCoreAPI aCoreAPI ) throws Exception
        {
        myCoreAPI = aCoreAPI;

        myLevels = new Levels( myCoreAPI );
        myPathes = new Pathes( myCoreAPI );
        myEnemies = new Enemies();
        }

    public final File folder()
        {
        return myProjectDirectory;
        }

    public final boolean isOpen()
        {
        return myProjectDirectory != null;
        }

    public final void open( final File aProjectDirectory ) throws Exception
        {
        if ( myProjectDirectory != null ) throw new IllegalStateException();

        final EditorConfiguration config = myCoreAPI.configuration();
        final File enemiesFile = new File( aProjectDirectory, config.enemiesFileName() );
        final File pathesFile = new File( aProjectDirectory, config.pathesFileName() );
        final File levelsFile = new File( aProjectDirectory, config.levelsFileName() );
        if ( enemiesFile.exists() && pathesFile.exists() && levelsFile.exists() )
            {
            LOG.debug( "Loading project (%s)", enemiesFile );
            final DataInputStream enemies = openStream( enemiesFile );
            myEnemies.load( enemies );
            enemies.close();

            LOG.debug( "Loading project (%s)", pathesFile );
            final DataInputStream pathes = openStream( pathesFile );
            myPathes.load( pathes );
            pathes.close();

            LOG.debug( "Loading project (%s)", levelsFile );
            final DataInputStream levels = openStream( levelsFile );
            myLevels.load( levels );
            levels.close();
            }
        else
            {
            myEnemies.open();
            myPathes.open();
            myLevels.open();
            }

        myProjectDirectory = aProjectDirectory;

        myGalaxina = new EmbeddedGalaxina();

        fire( "onProjectOpened", this );

        myCoreAPI.state().change( EditorState.PROJECT, this );
        }

    public final void save() throws IOException
        {
        if ( myProjectDirectory == null ) throw new IllegalStateException();

        final EditorConfiguration config = myCoreAPI.configuration();

        final File enemiesFile = new File( myProjectDirectory, config.enemiesFileName() );
        LOG.debug( "Saving project (%s)", enemiesFile );
        final DataOutputStream enemies = openOutput( enemiesFile );
        myEnemies.save( enemies );
        enemies.close();

        final File pathesFile = new File( myProjectDirectory, config.pathesFileName() );
        LOG.debug( "Saving project (%s)", pathesFile );
        final DataOutputStream pathes = openOutput( pathesFile );
        myPathes.save( pathes );
        pathes.close();

        final File levelsFile = new File( myProjectDirectory, config.levelsFileName() );
        LOG.debug( "Saving project (%s)", levelsFile );
        final DataOutputStream levels = openOutput( levelsFile );
        myLevels.save( levels );
        levels.close();
        }

    public final void close()
        {
        myCoreAPI.state().change( EditorState.PROJECT, null );

        fire( "onProjectClosed" );

        myGalaxina = null;

        myProjectDirectory = null;

        myLevels.close();
        myPathes.close();
        myEnemies.close();
        }

    public final Levels levels()
        {
        return myLevels;
        }

    public final Pathes pathes()
        {
        return myPathes;
        }

    public final Enemies enemies()
        {
        return myEnemies;
        }

    public final EmbeddedGalaxina galaxina()
        {
        return myGalaxina;
        }

    // Implementation

    private static DataOutputStream openOutput( final File aProjectFile ) throws FileNotFoundException
        {
        final FileOutputStream outputStream = new FileOutputStream( aProjectFile );
        final DataOutputStream stream = new DataOutputStream( outputStream );
        return stream;
        }

    private final DataInputStream openStream( final File aProjectFile ) throws FileNotFoundException
        {
        final FileInputStream inputStream = new FileInputStream( aProjectFile );
        return new DataInputStream( inputStream );
        }



    private EmbeddedGalaxina myGalaxina;

    private File myProjectDirectory;


    private final Levels myLevels;

    private final Pathes myPathes;

    private final Enemies myEnemies;

    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
