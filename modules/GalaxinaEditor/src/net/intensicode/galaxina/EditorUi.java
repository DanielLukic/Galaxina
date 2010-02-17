package net.intensicode.galaxina;

import net.intensicode.galaxina.ui.EditorForm;
import net.intensicode.galaxina.util.Log;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;

public final class EditorUi implements EditorUiAPI
    {
    public EditorUi( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From EditorUiAPI

    public final Component frame()
        {
        return myFrame;
        }

    public final void register( final JFrame aFrame )
        {
        myFrame = aFrame;
        }

    public final Action action( final String aActionID )
        {
        if ( !myActions.containsKey( aActionID ) )
            {
            try
                {
                final Action action = createAction( aActionID );
                myActions.put( aActionID, action );
                }
            catch ( final Exception e )
                {
                LOG.error( "failed creating action: %s", e );
                throw new RuntimeException( e );
                }
            }
        return myActions.get( aActionID );
        }

    public final ImageIcon loadIcon( final String aIconID )
        {
        final ImageIcon icon = loadIcon( "icons", aIconID );
        if ( icon != null ) return icon;
        final ImageIcon small = loadIcon( "small", aIconID );
        if ( small != null ) return small;
        final ImageIcon image = loadIcon( ".", aIconID );
        if ( image != null ) return image;
        LOG.debug( "missing icon for %s", aIconID );
        return null;
        }

    public final void run( final Action aRunnableAction )
        {
        if ( aRunnableAction instanceof Runnable )
            {
            SwingUtilities.invokeLater( (Runnable) aRunnableAction );
            }
        else
            {
            SwingUtilities.invokeLater( new Runnable()
            {
            public final void run()
                {
                aRunnableAction.actionPerformed( null );
                }
            } );
            }
        }

    public final UserResponseHandler askUserAboutChanges()
        {
        final int choice = JOptionPane.showConfirmDialog( frame(),
                "Save changes?", "Galaxina Editor Question",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                loadIcon( "status/dialog-question" ) );
        return new UserResponseHandler( this, choice == JOptionPane.OK_OPTION );
        }

    public final UiConfiguration configuration()
        {
        return myUiConfiguration;
        }

    // Implementation

    private String getActionClassName( final String aActionID )
        {
        final String actionPackage = getClass().getPackage().getName();
        final StringBuilder className = new StringBuilder();
        className.append( actionPackage );
        className.append( ACTIONS_PACKAGE );
        className.append( aActionID );
        return className.toString();
        }

    private Action createAction( final String aActionID ) throws Exception
        {
        final String actionClassName = getActionClassName( aActionID );
        final Class actionClass = Class.forName( actionClassName );

        {
        final Action action = tryEmptyConstructor( actionClass );
        if ( action != null ) return action;
        }
        {
        final Action action = tryDefaultConstructor( actionClass );
        if ( action != null ) return action;
        }

        LOG.error( "action class not found or incompatible: %s", aActionID );
        throw new IllegalArgumentException( aActionID );
        }

    private Action tryEmptyConstructor( final Class aClass ) throws Exception
        {
        for ( final Constructor constructor : aClass.getConstructors() )
            {
            final Class[] types = constructor.getParameterTypes();
            if ( types == null || types.length == 0 )
                {
                return (Action) aClass.newInstance();
                }
            }
        return null;
        }

    private Action tryDefaultConstructor( final Class aClass ) throws Exception
        {
        final Constructor constructor = aClass.getConstructor( EditorCoreAPI.class );
        return (Action) constructor.newInstance( myCoreAPI );
        }

    private ImageIcon loadIcon( final String aFolder, final String aIconID )
        {
        final String resourceName = String.format( "/%s/%s.png", aFolder, aIconID );
        final URL resource = getClass().getResource( resourceName );
        if ( resource == null ) return null;
        return new ImageIcon( resource );
        }


    private JFrame myFrame;

    private EditorForm myEditorForm;

    private final EditorCoreAPI myCoreAPI;

    private final UiConfiguration myUiConfiguration = new UiConfiguration();

    private final HashMap<String, Action> myActions = new HashMap<String, Action>();


    private static final Log LOG = Log.get();

    private static final String ACTIONS_PACKAGE = ".ui.actions.";
    }
