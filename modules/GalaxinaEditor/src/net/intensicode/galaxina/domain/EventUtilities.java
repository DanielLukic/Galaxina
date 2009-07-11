package net.intensicode.galaxina.domain;

import java.lang.reflect.Method;
import java.util.ArrayList;

public final class EventUtilities<T>
    {
    public final void add( final T aListener )
        {
        if ( myListeners.contains( aListener ) ) return;
        myListeners.add( aListener );
        }

    public final void remove( final T aListener )
        {
        while ( myListeners.remove( aListener ) ) ;
        }

    public final void fire( final String aEventID, final Object... aArguments )
        {
        fire( myListeners, aEventID, aArguments );
        }

    public final void fire( final ArrayList aListeners, final String aEventID, final Object... aArguments )
        {
        if ( aListeners.isEmpty() ) return;

        for ( final Object listener : aListeners )
            {
            try
                {
                final Method method = findEventMethod( listener, aEventID, aArguments );
                method.invoke( listener, aArguments );
                }
            catch ( final Exception e )
                {
                throw new RuntimeException( e );
                }
            }
        }

    // Implementation

    private final Method findEventMethod( final Object aListener, final String aName, final Object[] aArguments )
        {
        final Method[] listenerMethods = aListener.getClass().getMethods();

        for ( final Method method : listenerMethods )
            {
            if ( method.getName().equals( aName ) == false ) continue;
            final Class[] types = method.getParameterTypes();
            if ( types.length != aArguments.length ) continue;
            if ( signatureMatches( types, aArguments ) == false ) continue;
            return method;
            }

        for ( final Method method : listenerMethods )
            {
            if ( method.getName().equals( aName ) == false ) continue;
            if ( method.isVarArgs() == false ) continue;
            return method;
            }

        for ( final Method method : listenerMethods )
            {
            if ( method.getName().equals( aName ) == false ) continue;
            return method;
            }

        final StringBuilder signature = new StringBuilder();
        for ( final Object arg : aArguments )
            {
            signature.append( arg );
            signature.append( ',' );
            }
        signature.deleteCharAt( signature.length() - 1 );

        final String msg = String.format( "Missing %s(%s) in %s", aName, signature, aListener.getClass() );
        throw new IllegalArgumentException( msg );
        }

    private final boolean signatureMatches( final Class[] aTypes, final Object[] aArguments )
        {
        for ( int idx = 0; idx < aTypes.length; idx++ )
            {
            if ( aTypes[ idx ].isInstance( aArguments[ idx ] ) == false ) return false;
            }
        return true;
        }



    private final ArrayList<T> myListeners = new ArrayList<T>();
    }
