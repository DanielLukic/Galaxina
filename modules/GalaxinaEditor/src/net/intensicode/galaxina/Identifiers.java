package net.intensicode.galaxina;

public interface Identifiers
    {
    double MIN_ZOOM_FACTOR = 0.1;

    double MAX_ZOOM_FACTOR = 10;

    enum ZoomMode
        {
            ZOOM_EXACT,
            ZOOM_TO_FIT,
            ZOOM_FREE
        }

    String PROJECT = "PROJECT";

    String EDITOR_TAB = "EDITOR_TAB";

    String MIDLET_ZOOM = "MIDLET_ZOOM";

    String MIDLET_CONTAINER = "MIDLET_CONTAINER";

    String MIDLET_RUNNING = "MIDLET_RUNNING";

    String SELECTED_LEVEL = "SELECTED_LEVEL";

    String SELECTED_ENEMY = "SELECTED_ENEMY";

    String SELECTED_PATH = "SELECTED_PATH";

    String SELECTED_SWARM = "SELECTED_SWARM";

    String SELECTED_SWARM_PATH = "SELECTED_SWARM_PATH";

    String SELECTED_SWARM_ENEMY = "SELECTED_SWARM_ENEMY";

    String ZOOM_MODE = "ZOOM_MODE";

    String ZOOM_FACTOR = "ZOOM_FACTOR";

    String SHOW_GRID = "SHOW_GRID";

    String ALIGN_TO_GRID = "ALIGN_TO_GRID";

    String SHOW_PATHES = "SHOW_PATHES";

    String SMOOTH_PATHES = "SMOOTH_PATHES";

    String SELECTED_PATH_MARKER = "SELECTED_PATH_MARKER";

    String SELECTED_SWARM_MARKER = "SELECTED_SWARM_MARKER";

    String PATH_EDITOR_POSITION = "PATH_EDITOR_POSITION";

    String PATH_EDITOR_OFFSET = "PATH_EDITOR_OFFSET";
    }
