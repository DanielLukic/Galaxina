//#condition CHEAT

package net.intensicode.galaxina;

import net.intensicode.galaxina.game.GameController;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class CheatHandler extends ScreenBase
    {
    public CheatHandler( final GameController aGameController )
        {
        myGameController = aGameController;

        register( new Cheat( 70, 'F', "CLEAR ALL" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 71, 'G', "GOD MODE" )
        {
        public void onPerform()
            {
            myGodMode = !myGodMode;
            }
        } );

        register( new Cheat( 72, 'H', "HELP" )
        {
        public void onPerform()
            {
            myShowHelp = !myShowHelp;
            }
        } );

        register( new Cheat( 73, 'I', "INC SCORE" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 74, 'J', "FILL IN LINE" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 75, 'K', "INC LEVEL" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 76, 'L', "LEVEL COMPLETE" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 77, 'M', "REMOVE LINE" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 78, 'N', "DUMP CONTAINER" )
        {
        public void onPerform()
            {
            }
        } );

        register( new Cheat( 79, 'O', "SLOW DOWN EMU" )
        {
        public void onPerform()
            {
            mySlowDownMode++;
            if ( mySlowDownMode == 5 ) mySlowDownMode = 0;
            }
        } );

        register( new Cheat( 80, 'P', "INC DETONATORS" )
        {
        public void onPerform()
            {
            }
        } );
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        myFontGen = myGameController.visualContext().textFont();
        }

    public final void onControlTick() throws Exception
        {
        //if ( keys().cheatCode != 0 )
        //    {
        //    for ( int idx = 0; idx < myCheats.size; idx++ )
        //        {
        //        final Cheat cheat = (Cheat) myCheats.get( idx );
        //        if ( keys().cheatCode == cheat.code )
        //            {
        //            cheat.onPerform();
        //            keys().cheatCode = 0;
        //            }
        //        }
        //    }

        //if ( myGodMode ) myMainLogic.gameModel().player.slowDown = 9999;
        }

    public final void onDrawFrame()
        {
        if ( mySlowDownMode > 0 ) simulateSlowDown();

        final FontGenerator font = myFontGen;

        if ( myGodMode )
            {
            final int xPos = ( screen().width() - font.stringWidth( "GOD" ) ) / 2;
            font.blitString( graphics(), "GOD", 0, 3, xPos, 0 );
            }

        //if ( keys().debugEnabled )
        //    {
        //    final int xPos = ( screen().width() - font.stringWidth( "DEBUG" ) ) / 2;
        //    font.blitString( graphics(), "DEBUG", 0, 5, xPos, screen().height() - font.charHeight() );
        //    }

        if ( myShowHelp )
            {
            final int charWidth = font.charWidth( '0' );
            final int charHeight = font.charHeight();

            final int x1 = charWidth;
            final int x2 = charWidth * 3;
            final int x3 = charWidth * 5;

            myBlitPos.x = 0;
            myBlitPos.y = charHeight;
            for ( int idx = 0; idx < myCheats.size; idx++ )
                {
                final Cheat cheat = (Cheat) myCheats.get( idx );
                myBlitPos.x = x1;
                font.blitNumber( graphics(), myBlitPos, idx, FontGenerator.TOP_LEFT );
                myBlitPos.x = x2;
                font.blitChar( graphics(), myBlitPos.x, myBlitPos.y, cheat.code );
                myBlitPos.x = x3;
                font.blitString( graphics(), cheat.name, myBlitPos, FontGenerator.LEFT );
                myBlitPos.y += charHeight;
                }
            }
        }

    // Implementation

    private void register( final Cheat aCheat )
        {
        myCheats.add( aCheat );
        }

    private void simulateSlowDown()
        {
        try
            {
            int delay = mySlowDownMode * 10;
            //delay += BitmapFontGenerator.buffered ? 0 : 10;
            Thread.sleep( delay );
            }
        catch ( InterruptedException e )
            {
            e.printStackTrace();
            }
        }



    private boolean myGodMode;

    private boolean myShowHelp;

    private int mySlowDownMode;

    private BitmapFontGenerator myFontGen;


    private final GameController myGameController;

    private final Position myBlitPos = new Position();

    private final DynamicArray myCheats = new DynamicArray();



    public abstract class Cheat
        {
        public final int code;

        public final char id;

        public final String name;

        public Cheat( final int aCode, final char aChar, final String aName )
            {
            code = aCode;
            id = aChar;
            name = aName;
            }

        public abstract void onPerform();
        }
    }
