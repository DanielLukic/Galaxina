package net.intensicode.galaxina.ui.logic;

import java.awt.event.MouseEvent;

public interface InputHandler
    {
    boolean isActive();
    
    boolean mouseClicked( MouseEvent aEvent );

    boolean mousePressed( MouseEvent aEvent );

    boolean mouseReleased( MouseEvent aEvent );

    boolean mouseDragged( MouseEvent aEvent );
    }
