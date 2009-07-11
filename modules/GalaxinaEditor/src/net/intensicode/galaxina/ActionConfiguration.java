package net.intensicode.galaxina;

import net.intensicode.galaxina.util.Log;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;

public final class ActionConfiguration
    {
    public ActionConfiguration( final EditorCoreAPI aCoreAPI, final String aActionClassName )
        {
        myCoreAPI = aCoreAPI;
        myValues.put( Action.NAME, aActionClassName );
        }

    public final void applyTo( final Action aAction )
        {
        for ( final String key : myValues.keySet() )
            {
            aAction.putValue( key, myValues.get( key ) );
            }
        tryLoadIcon( aAction );
        setMnemonic( aAction );
        setAccelerator( aAction );
        }

    public final ActionConfiguration set( final String aName, final String aKeyCode, final String aDescription )
        {
        return set( aName, aKeyCode, aDescription, null );
        }

    public final ActionConfiguration set( final String aName, final String aKeyCode, final String aDescription, final String aKeySequence )
        {
        name( aName );
        key( aKeyCode );
        desc( aDescription );
        shortcut( aKeySequence );
        return this;
        }

    public final ActionConfiguration name( final String aName )
        {
        myValues.put( Action.NAME, aName );
        return this;
        }

    public final ActionConfiguration desc( final String aText )
        {
        myValues.put( Action.SHORT_DESCRIPTION, aText );
        return this;
        }

    public final ActionConfiguration icon( final String aFilename )
        {
        myValues.put( Action.SMALL_ICON, aFilename );
        return this;
        }

    public final ActionConfiguration key( final String aKeyCode )
        {
        myValues.put( Action.MNEMONIC_KEY, aKeyCode );
        return this;
        }

    public final ActionConfiguration shortcut( final String aKeySequence )
        {
        myValues.put( Action.ACCELERATOR_KEY, aKeySequence );
        return this;
        }

    public final void tryLoadIcon( final Action aAction )
        {
        final String filename = (String) aAction.getValue( Action.SMALL_ICON );
        if ( filename != null ) tryLoadIcon( aAction, filename );
        }

    public final void setMnemonic( final Action aAction )
        {
        final String keycode = (String) aAction.getValue( Action.MNEMONIC_KEY );
        if ( keycode != null ) setMnemonic( aAction, keycode );
        }

    public final void setAccelerator( final Action aAction )
        {
        final String sequence = (String) aAction.getValue( Action.ACCELERATOR_KEY );
        if ( sequence != null ) setAccelerator( aAction, sequence );
        }

    public final void tryLoadIcon( final Action aAction, final String aIconID )
        {
        try
            {
            final ImageIcon icon = myCoreAPI.ui().loadIcon( aIconID );
            if ( icon != null )
                {
                icon.setDescription( (String) aAction.getValue( Action.SHORT_DESCRIPTION ) );
                }
            aAction.putValue( Action.SMALL_ICON, icon );
            }
        catch ( final IllegalStateException e )
            {
            LOG.debug( "failed loading icon %s", aIconID );
            }
        }

    public final void setMnemonic( final Action aAction, final String aKeyCode )
        {
        try
            {
            final Field keyCodeField = KeyEvent.class.getField( aKeyCode );
            final Integer keyCodeValue = keyCodeField.getInt( null );
            aAction.putValue( Action.MNEMONIC_KEY, keyCodeValue );
            }
        catch ( final Exception e )
            {
            throw new RuntimeException( e );
            }
        }

    public final void setAccelerator( final Action aAction, final String aSequence )
        {
        aAction.putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke( aSequence ) );
        }



    private final HashMap<String, Object> myValues = new HashMap<String, Object>();

    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
