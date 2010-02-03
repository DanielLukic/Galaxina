package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.EmbeddedGalaxina;
import net.intensicode.runme.*;

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class GamePanel extends JPanel implements DisplayBuffer, EditorStateListener, Identifiers
    {
    public GamePanel( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;

        aCoreAPI.state().add( PROJECT, this );
        aCoreAPI.state().add( EDITOR_TAB, this );
        aCoreAPI.state().add( MIDLET_ZOOM, this );
        aCoreAPI.state().add( SELECTED_LEVEL, this );

        final int width = width();
        final int height = height();
        setMinimumSize( new Dimension( width, height ) );

        myImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
        myGraphics = new javax.microedition.lcdui.Graphics( myImage );

        addMouseListener( new MouseBasedFocusHandler( this ) );

        setFocusable( true );
        }

    // From DisplayBuffer

    public final int width()
        {
        return myCoreAPI.gameScreenWidth();
        }

    public final int height()
        {
        return myCoreAPI.gameScreenHeight();
        }

    public final javax.microedition.lcdui.Graphics beginFrame()
        {
        return myGraphics;
        }

    public final void endFrame()
        {
        repaint();
        }

    public final void renderInto( final java.awt.Graphics aGraphics, final int aX, final int aY, final int aWidth, final int aHeight )
        {
        throw new RuntimeException( "nyi" );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == SELECTED_LEVEL && aNewValue != null )
            {
            final Action action = myCoreAPI.ui().action( "ReloadGame" );
            if ( action.isEnabled() ) action.actionPerformed( null );
            }

        if ( aEventID == Identifiers.PROJECT )
            {
            if ( aNewValue != null )
                {
                myGalaxina = myCoreAPI.project().galaxina();
                myDisplay = new MIDletDisplay( myGalaxina, this );
                myContainer = new MIDletContainer( myGalaxina );
                myKeyHandler = new MIDletKeyHandler( myGalaxina );
                addKeyListener( myKeyHandler );
                myCoreAPI.state().change( MIDLET_DISPLAY, myDisplay );
                myCoreAPI.state().change( MIDLET_CONTAINER, myContainer );
                }
            else
                {
                myCoreAPI.state().change( MIDLET_CONTAINER, null );
                myCoreAPI.state().change( MIDLET_DISPLAY, null );
                if ( myKeyHandler != null ) removeKeyListener( myKeyHandler );
                myKeyHandler = null;
                if ( myContainer != null ) myContainer.destroy();
                myContainer = null;
                if ( myDisplay != null ) myDisplay.unregister( myGalaxina );
                myDisplay = null;
                if ( myGalaxina != null ) myGalaxina.destroyApp( true );
                myGalaxina = null;
                }
            }

        if ( aEventID == Identifiers.EDITOR_TAB )
            {
            final MIDletContainer container = (MIDletContainer) myCoreAPI.state().get( MIDLET_CONTAINER );

            if ( aNewValue == EditorIdentifiers.GAME_VIEW )
                {
                final RunnableChain chain = new RunnableChain();
                chain.append( new SaveChanges() );
                chain.append( new StartMidlet() );
                chain.append( new ReloadGame() );
                chain.append( new RequestFocus() );
                chain.start();
                }
            else
                {
                if ( container != null )
                    {
                    myCoreAPI.state().change( MIDLET_RUNNING, false );
                    container.pause();
                    }
                }
            }

        updateUI();
        }

    private final class SaveChanges implements Runnable
        {
        public final void run()
            {
            final Action save = myCoreAPI.ui().action( "SaveChanges" );
            if ( save.isEnabled() ) save.actionPerformed( null );
            }
        }

    private final class StartMidlet implements Runnable
        {
        public final void run()
            {
            final MIDletContainer container = myCoreAPI.state().container();
            if ( container == null ) return;

            container.start();
            myCoreAPI.state().change( MIDLET_RUNNING, true );
            }
        }

    private final class ReloadGame implements Runnable
        {
        public final void run()
            {
            final Action reload = myCoreAPI.ui().action( "ReloadGame" );
            if ( reload.isEnabled() ) reload.actionPerformed( null );
            }
        }

    private final class RequestFocus implements Runnable
        {
        public final void run()
            {
            requestFocus();
            }
        }

    // From JComponent

    protected final void paintComponent( final java.awt.Graphics g )
        {
        super.paintComponent( g );

        final int width = getBlitWidth();
        final int height = getBlitHeigh();
        final int xOffset = ( getWidth() - width ) / 2;
        final int yOffset = ( getHeight() - height ) / 2;

        if ( hasFocus() )
            {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor( Color.RED );
            g2d.setStroke( new BasicStroke( 3f ) );
            g2d.drawRect( xOffset - 2, yOffset - 2, width + 3, height + 3 );
            }

        g.drawImage( myImage, xOffset, yOffset, width, height, null );
        }

    // Implementation

    private int getBlitWidth()
        {
        if ( myCoreAPI.state().get( MIDLET_ZOOM ) == ZoomMode.ZOOM_EXACT )
            {
            return myImage.getWidth();
            }
        final int xMult = getWidth() / myImage.getWidth();
        final int yMult = getHeight() / myImage.getHeight();
        final int mult = Math.min( xMult, yMult );
        return myImage.getWidth() * mult;
        }

    private int getBlitHeigh()
        {
        if ( myCoreAPI.state().get( MIDLET_ZOOM ) == ZoomMode.ZOOM_EXACT )
            {
            return myImage.getHeight();
            }
        final int xMult = getWidth() / myImage.getWidth();
        final int yMult = getHeight() / myImage.getHeight();
        final int mult = Math.min( xMult, yMult );
        return myImage.getHeight() * mult;
        }



    private MIDletDisplay myDisplay;

    private MIDletContainer myContainer;

    private MIDletKeyHandler myKeyHandler;

    private EmbeddedGalaxina myGalaxina;


    private final BufferedImage myImage;

    private final EditorCoreAPI myCoreAPI;

    private final javax.microedition.lcdui.Graphics myGraphics;
    }
