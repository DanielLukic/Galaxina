package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.galaxina.VisualContext;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;

import java.io.IOException;

public final class ConfigurableVisualContext implements VisualContext
    {
    public ConfigurableVisualContext( final SkinManager aSkinManager, final AutohideSoftkeysScreen aSoftkeys )
        {
        mySkinManager = aSkinManager;
        mySharedSoftkeys = aSoftkeys;
        }

    public final void initialize( final ResourcesManager aResourcesManager ) throws IOException
        {
        mySharedBackground = new ImageScreen( mySkinManager.image( "title_background" ) );
        myTitleFont = skinManager().font( "menufont" );
        myMenuFont = skinManager().font( "menufont" );
        myTextFont = skinManager().font( "textfont" );
        myConfiguration = VisualConfiguration.fromConfigurationResource( aResourcesManager, mySkinManager );
        }

    // From VisualContext

    public final SkinManager skinManager()
        {
        return mySkinManager;
        }

    public final ScreenBase sharedStars()
        {
        throw new RuntimeException( "nyi" );
        }

    public final ScreenBase sharedBackground()
        {
        if ( mySharedBackground == null ) throw new IllegalStateException();
        return mySharedBackground;
        }

    public final AutohideSoftkeysScreen sharedSoftkeys()
        {
        return mySharedSoftkeys;
        }

    public final BitmapFontGenerator titleFont()
        {
        return myTitleFont;
        }

    public final BitmapFontGenerator menuFont()
        {
        return myMenuFont;
        }

    public final BitmapFontGenerator textFont()
        {
        return myTextFont;
        }

    public final VisualConfiguration configuration()
        {
        return myConfiguration;
        }


    private VisualConfiguration myConfiguration;

    private ScreenBase mySharedBackground;

    private BitmapFontGenerator myTitleFont;

    private BitmapFontGenerator myMenuFont;

    private BitmapFontGenerator myTextFont;

    private final SkinManager mySkinManager;

    private final AutohideSoftkeysScreen mySharedSoftkeys;
    }
