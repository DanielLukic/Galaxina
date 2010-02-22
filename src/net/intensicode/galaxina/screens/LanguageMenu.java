package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;
import net.intensicode.util.*;

import java.io.IOException;

public final class LanguageMenu extends MenuBase
    {
    public LanguageMenu( final FontGenerator aMenuFont ) throws IOException
        {
        super( aMenuFont );
        }

    public final LanguageMenu shareSoftkeys( final SoftkeysScreen aSoftkeys )
        {
        mySoftkeys = aSoftkeys;
        return this;
        }

    // From MenuBase

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        if ( aSelectedEntry.id >= 0 )
            {
            I18n.setTo( (Configuration) myLanguages.get( aSelectedEntry.id ) );
            }
        else
            {
            I18n.setTo( new Configuration() );
            }

        stack().popScreen( this );
        }

    protected final void afterInitEverytime() throws Exception
        {
        mySoftkeys.setSoftkeys( I18n._( "SELECT" ), I18n._( "EXIT" ) );
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        super.onInitOnce();

        loadLanguages();

        if ( !hasEnglish() ) addMenuEntry( -1, "ENGLISH" );

        for ( int idx = 0; idx < myLanguages.size; idx++ )
            {
            final Configuration language = (Configuration) myLanguages.get( idx );
            final String languageName = language.readString( "TARGET_LANGUAGE", null );
            if ( languageName == null ) continue;
            addMenuEntry( idx, languageName );
            }

        //#if false
        updateSelectedEntry( 1 );
        //#else
        //# updateSelectedEntry( 0 );
        //#endif

        addScreen( mySoftkeys );
        }

    // Implementation

    private boolean hasEnglish()
        {
        for ( int idx = 0; idx < myLanguages.size; idx++ )
            {
            final Configuration config = (Configuration) myLanguages.get( idx );
            if ( config.readString( "TARGET_LANGUAGE", "" ).equals( "ENGLISH" ) ) return true;
            }
        return false;
        }

    private void loadLanguages() throws Exception
        {
        final ResourcesManager loader = resources();
        final Configuration config = loader.loadConfiguration( "languages.config" );
        final String[] languages = config.readList( "languages", "", "," );
        for ( int idx = 0; idx < languages.length; idx++ )
            {
            loadLanguage( loader, languages, idx );
            }
        }

    private void loadLanguage( final ResourcesManager aLoader, final String[] aLanguages, final int aIdx )
        {
        final String resourceName = "strings_" + aLanguages[ aIdx ] + ".config";
        try
            {
            myLanguages.add( aLoader.loadConfiguration( resourceName ) );
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            Log.debug( "Failed loading language: {}", resourceName );
            //#endif
            }
        }


    private SoftkeysScreen mySoftkeys;

    private final DynamicArray myLanguages = new DynamicArray();
    }
