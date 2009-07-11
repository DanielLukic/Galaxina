package net.intensicode.galaxina;

import net.intensicode.galaxina.domain.Project;

import java.io.File;

public interface EditorCoreAPI
    {
    void open( final File aFile ) throws Exception;

    // Core Objects

    Project project();

    // Sub APIs

    EditorUiAPI ui();

    EditorGlobalAPI global();

    EditorConfiguration configuration();

    EditorState state();
    }
