package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;

public interface LoadingCallback
    {
    void onLoadingDone( final Engine aEngine, final DirectScreen aScreen ) throws Exception;
    }
