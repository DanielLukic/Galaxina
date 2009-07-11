package net.intensicode.galaxina.util;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log
    {
    public static final Log get()
        {
        final String loggerName = getLoggerName( getStackTraceElement() );
        if ( !myLogs.containsKey( loggerName ) )
            {
            myLogs.put( loggerName, new Log( loggerName ) );
            }
        return myLogs.get( loggerName );
        }

    public final void debug( final String aMessage, final Object... aVarArgs )
        {
        final String message = String.format( aMessage, aVarArgs );
        doLog( DEBUG, message );
        }

    public final void info( final String aMessage, final Object... aVarArgs )
        {
        final String message = String.format( aMessage, aVarArgs );
        doLog( INFO, message );
        }

    public final void warn( final String aMessage, final Object... aVarArgs )
        {
        final String message = String.format( aMessage, aVarArgs );
        doLog( WARN, message );
        }

    public final void error( final String aMessage, final Object... aVarArgs )
        {
        final String message = String.format( aMessage, aVarArgs );
        doLog( ERROR, message );
        }

    public final void error( final Throwable aThrowable )
        {
        error( "{c}#{m} %s", aThrowable );
        }

    private Log( final String aName )
        {
        myLogger = Logger.getLogger( aName );
        }

    private final void doLog( final Level aLevel, final String aMessage )
        {
        final StackTraceElement stackTraceElement = getStackTraceElement();
        final String message = replaceMacros( aMessage, stackTraceElement );
        myLogger.logp( aLevel, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), message );
        }

    private final String replaceMacros( final String aMessage, final StackTraceElement aStackTraceElement )
        {
        final String a = aMessage.replace( "{m}", aStackTraceElement.getMethodName() );
        final String b = a.replace( "{c}", aStackTraceElement.getClassName() );
        final String c = b.replace( "{f}", aStackTraceElement.getFileName() );
        final String d = c.replace( "{l}", Integer.toString( aStackTraceElement.getLineNumber() ) );
        return d;
        }

    private static final StackTraceElement getStackTraceElement()
        {
        final String myPackageName = Log.class.getPackage().getName();
        final StackTraceElement[] stack = new RuntimeException().getStackTrace();
        for ( int idx = 0; idx < stack.length; idx++ )
            {
            if ( stack[ idx ].getClassName().startsWith( myPackageName ) ) continue;
            return stack[ idx ];
            }
        throw new IllegalStateException();
        }

    private static String getLoggerName( final StackTraceElement aStackElement )
        {
        return aStackElement.getClassName();
        }



    private final Logger myLogger;

    private static final Level DEBUG = Level.FINE;

    private static final Level INFO = Level.INFO;

    private static final Level WARN = Level.WARNING;

    private static final Level ERROR = Level.SEVERE;

    private static final HashMap<String, Log> myLogs = new HashMap<String, Log>();
    }
