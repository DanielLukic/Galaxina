package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public final class LevelRepeatUpdater implements ActionListener, FocusListener
    {
    public LevelRepeatUpdater( final EditorCoreAPI aCoreAPI, final Document aDocument )
        {
        myCoreAPI = aCoreAPI;
        myDocument = aDocument;
        }

    // From ActionListener

    public final void actionPerformed( final ActionEvent aEvent )
        {
        try
            {
            final String repeatInterval = myDocument.getText( 0, myDocument.getLength() );
            final int oldRepeatInterval = myCoreAPI.state().currentLevel().repeatInterval();
            final String oldValue = Integer.toString( oldRepeatInterval );
            if ( oldValue.equals( repeatInterval ) ) return;

            myCoreAPI.state().currentLevel().setRepeatInterval( Integer.parseInt( repeatInterval ) );
            }
        catch ( final BadLocationException e )
            {
            LOG.error( e );
            }
        }

    // From FocusListener

    public void focusGained( final FocusEvent aEvent )
        {
        }

    public void focusLost( final FocusEvent aEvent )
        {
        actionPerformed( null );
        }



    private final Document myDocument;

    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }