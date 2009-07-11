package net.intensicode.galaxina;

import net.intensicode.galaxina.ui.EditorFrame;

import java.io.File;

public final class Editor
    {
    public static final void main( final String[] aArguments ) throws Exception
        {
        final EditorCore core = new EditorCore();
        final EditorFrame frame = new EditorFrame( core );
        frame.setVisible( true );

        if ( aArguments.length > 0 )
            {
            core.open( new File( aArguments[ 0 ] ) );
            }
        }
    }
