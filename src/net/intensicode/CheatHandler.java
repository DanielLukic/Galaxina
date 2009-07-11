//#condition CHEAT

package net.intensicode;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.GameController;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

public final class CheatHandler extends AbstractScreen
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

    // From AbstractScreen

    public void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myFontGen = myGameController.visualContext().textFont();
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        if ( aEngine.keys.cheatCode != 0 )
            {
            for ( int idx = 0; idx < myCheats.size; idx++ )
                {
                final Cheat cheat = (Cheat) myCheats.get( idx );
                if ( aEngine.keys.cheatCode == cheat.code )
                    {
                    cheat.onPerform();
                    aEngine.keys.cheatCode = 0;
                    }
                }
            }

        //if ( myGodMode ) myMainLogic.gameModel().player.slowDown = 9999;
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        if ( mySlowDownMode > 0 ) simulateSlowDown();

        final FontGen font = myFontGen;

        if ( myGodMode )
            {
            final int xPos = ( aScreen.width() - font.stringWidth( "GOD" ) ) / 2;
            font.blitString( aScreen.graphics(), "GOD", 0, 3, xPos, 0 );
            }

        if ( engine().keys.debugEnabled )
            {
            final int xPos = ( aScreen.width() - font.stringWidth( "DEBUG" ) ) / 2;
            font.blitString( aScreen.graphics(), "DEBUG", 0, 5, xPos, aScreen.height() - font.charHeight() );
            }

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
                font.blitNumber( aScreen.graphics(), myBlitPos, idx, FontGen.TOP_LEFT );
                myBlitPos.x = x2;
                font.blitChar( aScreen.graphics(), myBlitPos.x, myBlitPos.y, cheat.code );
                myBlitPos.x = x3;
                font.blitString( aScreen.graphics(), cheat.name, myBlitPos, FontGen.LEFT );
                myBlitPos.y += charHeight;
                }
            }
        }

    // Implementation

    private final void register( final Cheat aCheat )
        {
        myCheats.add( aCheat );
        }

    private final void simulateSlowDown()
        {
        try
            {
            int delay = mySlowDownMode * 10;
            delay += BitmapFontGen.buffered ? 0 : 10;
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

    private BitmapFontGen myFontGen;


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
