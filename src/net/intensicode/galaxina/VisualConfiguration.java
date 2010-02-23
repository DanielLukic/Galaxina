package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.util.*;
import net.intensicode.screens.EngineStats;

public final class VisualConfiguration
    {
    public final SkinManager skin;

    public boolean showStars = true;

    //#if TOUCH_SUPPORTED

    public int touchButtonSize = 48;

    public int touchButtonAlpha = 200;

    public boolean touchButtonImages = false;

    public boolean touchShowArrows = false;

    public final Position touchPrimaryFire = new Position( 0, 272 );

    public final Position touchSecondaryFire = new Position( 192, 272 );

    public final Position touchLeft = new Position( 48, 272 );

    public final Position touchRight = new Position( 144, 272 );

    public final Position touchUp = new Position( 96, 224 );

    public final Position touchDown = new Position( 96, 272 );

    //#endif


    public static VisualConfiguration fromConfigurationResource( final ResourcesManager aResourcesManager, final SkinManager aSkinManager )
        {
        final Configuration configuration = aResourcesManager.loadConfigurationOrUseDefaults( "visual.properties" );
        return new VisualConfiguration( configuration, aSkinManager );
        }

    // Implementation

    private VisualConfiguration( final Configuration aConfiguration, final SkinManager aSkin )
        {
        //#if DEBUG
        Log.debug( "initializing visual configuration from properties" );
        //#endif

        myConfiguration = aConfiguration;
        skin = aSkin;

        EngineStats.show = aConfiguration.readBoolean( "showStats", EngineStats.show );
        showStars = aConfiguration.readBoolean( "showStars", showStars );

        touchButtonSize = aConfiguration.readInt( "touchButtonSize", touchButtonSize );
        touchButtonAlpha = aConfiguration.readInt( "touchButtonAlpha", touchButtonAlpha );
        touchButtonImages = aConfiguration.readBoolean( "touchButtonImages", touchButtonImages );
        touchShowArrows = aConfiguration.readBoolean( "touchShowArrows", touchShowArrows );

        readPosition( touchPrimaryFire, "touchPrimaryFire" );
        readPosition( touchSecondaryFire, "touchSecondaryFire" );
        readPosition( touchLeft, "touchLeft" );
        readPosition( touchRight, "touchRight" );
        readPosition( touchDown, "touchDown" );
        readPosition( touchUp, "touchUp" );

        //#if DEBUG
        EngineStats.show = true;
        //#endif
        }

    private void readSize( final Size aSize, final String aName )
        {
        aSize.width = myConfiguration.readInt( aName + ".width", aSize.width );
        aSize.height = myConfiguration.readInt( aName + ".height", aSize.height );
        }

    private void readPosition( final Position aPosition, final String aName )
        {
        aPosition.x = myConfiguration.readInt( aName + ".x", aPosition.x );
        aPosition.y = myConfiguration.readInt( aName + ".y", aPosition.y );
        }

    private Configuration myConfiguration;
    }
