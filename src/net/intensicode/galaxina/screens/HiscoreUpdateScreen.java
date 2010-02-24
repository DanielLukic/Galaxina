package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.Hiscore;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.util.*;

import java.io.*;

public final class HiscoreUpdateScreen extends GalaxinaScreen implements NetworkCallback
    {
    public HiscoreUpdateScreen( final MainContext aMainLogic )
        {
        super( aMainLogic );
        myHiscore = aMainLogic.hiscore();
        myTitleFont = aMainLogic.visualContext().menuFont();
        myTextFont = aMainLogic.visualContext().textFont();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( visuals().sharedBackground() );
        addScreen( visuals().sharedSoftkeys() );

        myTitlePos.x = screen().width() / 2;
        myTitlePos.y = myTitleFont.charHeight();

        myErrorRect.x = myTextFont.charHeight();
        myErrorRect.y = screen().height() / 2;
        myErrorRect.width = screen().width() - myTextFont.charHeight() * 2;
        myErrorRect.height = screen().height() / 3;
        }

    public final void onInitEverytime() throws Exception
        {
        myState = STATE_START;
        visuals().sharedSoftkeys().setSoftkeys( "", I18n._( "CANCEL" ) );
        }

    public final void onControlTick() throws Exception
        {
        final KeysHandler keys = keys();
        if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            context().screens().showHiscore();
            }

        super.onControlTick();

        switch ( myState )
            {
            case STATE_START:
                onStateIdle();
                break;

            case STATE_UPDATING:
                Thread.yield();
                break;

            case STATE_COMPLETE:
                stack().popScreen( this );
                context().screens().showHiscore();
                context().saveHiscore();
                break;

            case STATE_ERROR:
                break;

            //#if DEBUG
            default:
                throw new IllegalStateException();
                //#endif
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();

        myBlitPos.setTo( myTitlePos );
        myTitleFont.blitString( gc, I18n._( "HISCORE" ), myBlitPos, FontGenerator.CENTER );

        myBlitPos.y += myTitleFont.charHeight();
        myTitleFont.blitString( gc, I18n._( "UPDATE" ), myBlitPos, FontGenerator.CENTER );

        myBlitPos.y += myTitleFont.charHeight() * 2;
        myTextFont.blitString( gc, I18n._( "STATUS" ), myBlitPos, FontGenerator.CENTER );

        myBlitPos.y += myTitleFont.charHeight();
        myTextFont.blitString( gc, getStatusMessage(), myBlitPos, FontGenerator.CENTER );

        if ( myError != null )
            {
            myBlitPos.y += myTitleFont.charHeight() * 2;
            myTextFont.blitText( gc, myError, myErrorRect );
            }
        }

    // From NetworkCallback

    public final void onReceived( final byte[] aBytes )
        {
        try
            {
            final ByteArrayInputStream stream = new ByteArrayInputStream( aBytes );
            myHiscore.loadFrom( new DataInputStream( stream ) );
            myState = STATE_COMPLETE;
            }
        catch ( final Throwable t )
            {
            onError( t );
            }
        }

    public final void onError( final Throwable aThrowable )
        {
        aThrowable.printStackTrace();

        myState = STATE_ERROR;
        myError = aThrowable.getMessage();
        }

    // Implementation

    private void onStateIdle() throws IOException
        {
        myState = STATE_UPDATING;

        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myHiscore.saveTo( new DataOutputStream( bytes ) );
        bytes.close();

        system().network.sendAndReceive( SERVER_URL, bytes.toByteArray(), this );
        }

    private String getStatusMessage()
        {
        switch ( myState )
            {
            case STATE_START:
                return I18n._( "IDLE" );
            case STATE_UPDATING:
                return I18n._( "UPDATING" );
            case STATE_COMPLETE:
                return I18n._( "COMPLETE" );
            case STATE_ERROR:
                return I18n._( "ERROR" );
            default:
                throw new IllegalStateException();
            }
        }


    private String myError;

    private int myState = STATE_START;

    private final Hiscore myHiscore;

    private final FontGenerator myTextFont;

    private final FontGenerator myTitleFont;

    private final Position myBlitPos = new Position();

    private final Position myTitlePos = new Position();

    private final Rectangle myErrorRect = new Rectangle();


    private static final int STATE_START = 0;

    private static final int STATE_UPDATING = 1;

    private static final int STATE_COMPLETE = 2;

    private static final int STATE_ERROR = 3;

    private static final String SERVER_URL = "http://www.intensicode.net/projects/DroidShock/hiscore/update.rb";
    }
