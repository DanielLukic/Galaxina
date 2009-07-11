package net.intensicode.galaxina;

import javax.swing.*;
import java.awt.*;

public interface EditorUiAPI
    {
    Component frame();

    void register( JFrame aFrame );

    Action action( String aActionID );

    ImageIcon loadIcon( String aIconID );

    void run( Action aRunnableAction );

    UserResponseHandler askUserAboutChanges();

    UiConfiguration configuration();
    }
