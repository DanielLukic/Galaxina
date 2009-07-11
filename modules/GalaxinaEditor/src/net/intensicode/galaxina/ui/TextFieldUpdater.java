package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public abstract class TextFieldUpdater implements ActionListener, FocusListener
    {
    public TextFieldUpdater( final EditorCoreAPI aCoreAPI, final JTextField aTextField )
        {
        myCoreAPI = aCoreAPI;
        myTextField = aTextField;
        myDocument = aTextField.getDocument();
        aTextField.addActionListener( this );
        aTextField.addFocusListener( this );
        }

    // From ActionListener

    public final void actionPerformed( final ActionEvent aEvent )
        {
        try
            {
            final String value = myDocument.getText( 0, myDocument.getLength() );
            final String old = getOldValue();
            if ( old == null || old.equals( value ) ) return;
            setNewValue( value );
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

    // Protected Interface

    protected final JTextField textField()
        {
        return myTextField;
        }

    protected final Document document()
        {
        return myDocument;
        }

    protected final EditorCoreAPI coreAPI()
        {
        return myCoreAPI;
        }

    protected abstract String getOldValue();

    protected abstract void setNewValue( final String aNewValue );



    private final Document myDocument;

    private final JTextField myTextField;

    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
