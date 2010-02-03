package net.intensicode.galaxina;

import net.intensicode.galaxina.ui.EditorFrame;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Font;
import java.io.File;
import java.awt.*;

public final class Editor
    {
    public static void main( final String[] aArguments ) throws Exception
        {
        final Dimension gameScreenSize = new Dimension( 240, 320 );

        final EditorCore core = new EditorCore( gameScreenSize );
        final EditorFrame frame = new EditorFrame( core );

        Displayable.displaySize = gameScreenSize;
        Font.displaySize = gameScreenSize;
        Image.theGraphicsContext = frame;

        frame.setVisible( true );

        if ( aArguments.length > 0 ) core.open( new File( aArguments[ 0 ] ) );
        }
    }
