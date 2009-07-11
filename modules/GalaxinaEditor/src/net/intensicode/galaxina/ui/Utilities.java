package net.intensicode.galaxina.ui;

import java.awt.*;

public final class Utilities
    {
    public static final void setEnabled( final Container aContainer, final boolean aEnabledFlag )
        {
        for ( final Component component : aContainer.getComponents() )
            {
            component.setEnabled( aEnabledFlag );
            if ( component instanceof Container ) setEnabled( (Container) component, aEnabledFlag );
            }
        }
    }
