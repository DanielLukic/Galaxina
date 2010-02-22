package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.*;
import net.intensicode.graphics.*;
import net.intensicode.screens.*;

public final class ResetScreen extends GalaxinaScreen
    {
    public ResetScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( visuals().sharedBackground() );
        addScreen( visuals().sharedSoftkeys() );

        final FontGenerator myTitleFont = visuals().menuFont();
        final FontGenerator myTextFont = visuals().textFont();

        int x = screen().width() / 2;
        int y = myTitleFont.charHeight() * 2;
        addScreen( new AlignedTextScreen( myTitleFont, I18n._( "RESET" ), x, y, FontGenerator.CENTER ) );

        y += myTitleFont.charHeight() * 4;
        addScreen( new AlignedTextScreen( myTextFont, I18n._( "SET OPTIONS" ), x, y, FontGenerator.CENTER ) );
        y += myTextFont.charHeight() * 2;
        addScreen( new AlignedTextScreen( myTextFont, I18n._( "AND CONTROLS" ), x, y, FontGenerator.CENTER ) );
        y += myTextFont.charHeight() * 2;
        addScreen( new AlignedTextScreen( myTextFont, I18n._( "TO DEFAULTS" ), x, y, FontGenerator.CENTER ) );
        y += myTextFont.charHeight() * 4;
        addScreen( new AlignedTextScreen( myTextFont, I18n._( "ARE YOU SURE?" ), x, y, FontGenerator.CENTER ) );
        }

    public final void onInitEverytime() throws Exception
        {
        visuals().sharedSoftkeys().setSoftkeys( I18n._( "YES" ), I18n._( "NO" ) );
        }

    public final void onControlTick() throws Exception
        {
        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() )
            {
            executeReset();
            stack().popScreen( this );
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            }

        super.onControlTick();
        }

    // Implementation

    private void executeReset() throws Exception
        {
        keys().resetCodes();

        EngineStats.show = false;
        BitmapFontGenerator.buffered = false;

        final VisualConfiguration config = visuals().configuration();
        config.showStars = true;

        context().controls().initFrom( keys() );

        storage().erase( context().controls() );
        storage().erase( context().options() );
        }
    }
