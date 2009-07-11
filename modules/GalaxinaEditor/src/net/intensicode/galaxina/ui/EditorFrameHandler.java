package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorGlobalAPI;
import net.intensicode.galaxina.util.Log;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class EditorFrameHandler extends WindowAdapter
    {
    public EditorFrameHandler( final EditorCoreAPI aEditorCore )
        {
        myEditorGlobalAPI = aEditorCore.global();
        }

    // From WindowAdapter

    public final void windowClosing( final WindowEvent e )
        {
        LOG.debug( "{m} %s", e );
        }

    public final void windowIconified( final WindowEvent e )
        {
        myEditorGlobalAPI.pause();
        }

    public final void windowDeiconified( final WindowEvent e )
        {
        myEditorGlobalAPI.resume();
        }

    public final void windowActivated( final WindowEvent e )
        {
        myEditorGlobalAPI.resume();
        }

    public final void windowDeactivated( final WindowEvent e )
        {
        myEditorGlobalAPI.pause();
        }

    public final void windowStateChanged( final WindowEvent e )
        {
        LOG.debug( "{m} %s", e );
        }

    public final void windowGainedFocus( final WindowEvent e )
        {
        myEditorGlobalAPI.resume();
        }

    public final void windowLostFocus( final WindowEvent e )
        {
        myEditorGlobalAPI.pause();
        }



    private static final Log LOG = Log.get();

    private final EditorGlobalAPI myEditorGlobalAPI;
    }
