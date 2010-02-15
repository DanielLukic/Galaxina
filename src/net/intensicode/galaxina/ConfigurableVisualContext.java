package net.intensicode.galaxina;

import net.intensicode.core.SkinManager;
import net.intensicode.galaxina.game.VisualContext;
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

    public final void initialize() throws IOException
        {
        mySharedBackground = new ImageScreen( skin().image( "title_background" ) );
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

    public final BitmapFontGenerator titleFont() throws IOException
        {
        return skinManager().font( "menufont" );
        }

    public final BitmapFontGenerator menuFont() throws IOException
        {
        return skinManager().font( "menufont" );
        }

    public final BitmapFontGenerator textFont() throws IOException
        {
        return skinManager().font( "textfont" );
        }

    // Implementation

    private SkinManager skin()
        {
        return mySkinManager;
        }


    private ScreenBase mySharedBackground;

    private final SkinManager mySkinManager;

    private final AutohideSoftkeysScreen mySharedSoftkeys;
    }
