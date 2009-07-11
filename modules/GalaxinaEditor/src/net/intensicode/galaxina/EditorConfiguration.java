package net.intensicode.galaxina;

import javax.swing.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;

public final class EditorConfiguration
    {
    public EditorConfiguration( final EditorCoreAPI aCoreAPI ) throws IOException
        {
        myCoreAPI = aCoreAPI;
        myConfigurations = new HashMap<String, ActionConfiguration>();

        define( "Project" ).key( "VK_P" ).shortcut( "alt P" );
        define( "Path" ).key( "VK_A" ).shortcut( "alt A" );
        define( "Swarm" ).key( "VK_S" ).shortcut( "alt S" );
        define( "Game" ).key( "VK_G" ).shortcut( "alt G" );
        define( "Edit" ).key( "VK_E" ).shortcut( "alt E" );

        define( "OpenProject" ).set( "Open", "VK_O", "Open a project folder", "control O" ).
                icon( "actions/document-open" );
        define( "CloseProject" ).set( "Close", "VK_C", "Close the project", "control W" ).
                icon( "actions/edit-clear" );
        define( "SaveChanges" ).set( "Save", "VK_S", "Save changes to disk", "control S" ).
                icon( "actions/document-save" );
        define( "QuitApplication" ).set( "Quit", "VK_Q", "Quit the application", "control Q" ).
                icon( "actions/exit" );

        define_add_remove( "Enemy", "project" );

        define_up_down( "Path" );
        define_add_delete( "Path", "project" );
        define_clone( "Path" );
        define_set_reset( "Path" );

        define_up_down( "Swarm" );
        define_add_delete( "Swarm", "level" );
        define_clone( "Swarm" );
        define_set_reset( "Swarm" );

        define_add_remove( "PathNode", "selected path" );
        define_add_remove( "SwarmNode", "selected swarm" );

        define_add_delete( "SwarmPath", "swarm" );
        define_reassign( "SwarmPath", "swarm path" );
        define_add_delete( "SwarmEnemy", "swarm" );
        define_reassign( "SwarmEnemy", "swarm enemy" );

        define( "SwitchRepeatLevel" ).
                set( "Repeat?", "VK_R", "Toggle level repeated" );
        define( "CloneLevel" ).
                set( "Clone Level", "VK_C", "Clone current level as new level" );
        define( "DeleteLevel" ).
                set( "Delete Level", "VK_D", "Delete current level" );

        define( "SetIndependantMode" ).
                set( "Independant", "VK_I", "Independant from other enemies/pathes" );
        define( "SetZeroDelayMode" ).
                set( "Zero Delay", "VK_Z", "Zero delay between pathes" );
        define( "SetSyncByPath" ).
                set( "Sync By Path", "VK_P", "Sync enemies on different pathes" );
        define( "SetFollowRightSide" ).
                set( "Follow Right Side", "VK_R", "Sync enemies by flying side-by-side" );
        define( "SetFollowLeftSide" ).
                set( "Follow Left Side", "VK_L", "Sync enemies by flying side-by-side" );

        define( "ZoomExact" ).
                set( "Zoom 1:1", "VK_1", "Set zoom to exact pixel" ).
                shortcut( "control EQUALS" ).
                icon( "actions/zoomExact" );
        define( "ZoomToFit" ).
                set( "Zoom To Fit", "VK_F", "Set zoom to maximum visible" ).
                shortcut( "control 0" ).
                icon( "actions/zoomFit" );
        define( "ZoomIn" ).
                set( "Zoom In", "VK_I", "Zoom closer" ).
                shortcut( "control shift EQUALS" ).
                icon( "actions/zoomIn" );
        define( "ZoomOut" ).
                set( "Zoom Out", "VK_O", "Zoom away" ).
                shortcut( "control MINUS" ).
                icon( "actions/zoomOut" );

        define( "AlignToGrid" ).
                set( "Align To Grid", "VK_A", "Align positions to grid" ).
                shortcut( "control A" );
        define( "ShowGrid" ).
                set( "Show Grid", "VK_G", "Show grid" ).
                shortcut( "control G" );
        define( "ShowPathes" ).
                set( "Raw Pathes", "VK_P", "Show raw pathes" );
        define( "SmoothPathes" ).
                set( "Smooth Pathes", "VK_S", "Show smooth pathes" );

        define( "ZoomGameExact" ).
                set( "Zoom 1:1", "VK_1", "Set zoom to exact pixel", "control 1" ).
                icon( "actions/zoomExact" );
        define( "ZoomGameToFit" ).
                set( "Zoom To Fit", "VK_F", "Set zoom to maximum visible", "control 2" ).
                icon( "actions/zoomFit" );

        define( "ShrinkX" ).
                set( "Shrink X", null, "Shrink positions horizontally" ).
                icon( "actions/shrink-x" );
        define( "ShrinkY" ).
                set( "Shrink Y", null, "Shrink positions vertically" ).
                icon( "actions/shrink-y" );
        define( "MirrorX" ).
                set( "Mirror X", "VK_X", "Mirror horizontally" ).
                icon( "actions/mirror-x" );
        define( "MirrorY" ).
                set( "Mirror Y", "VK_Y", "Mirror vertically" ).
                icon( "actions/mirror-y" );

        define( "SetDefaultEnemies" ).
                set( "Set Default Enemies", "VK_D", "Set default enemies configuration" ).
                icon( "actions/view-refresh" );
        define( "GetEnemyFileName" ).
                set( "...", null, "Choose enemy image file" ).
                icon( "actions/document-open" );

        define( "StepGame" ).
                set( "Step", "VK_S", "Single step game", "control T" ).
                icon( "actions/go-next" );
        define( "PauseGame" ).
                set( "Pause", "VK_P", "Pause/resume game", "control P" ).
                icon( "actions/system-run" );
        define( "ReloadGame" ).
                set( "Reload", "VK_R", "Reload game", "control R" ).
                icon( "actions/view-refresh" );
        }

    public final ActionConfiguration define( final String aActionClassName )
        {
        final ActionConfiguration config = new ActionConfiguration( myCoreAPI, aActionClassName );
        myConfigurations.put( aActionClassName, config );
        return config;
        }

    public final void define_up_down( final String aBasename )
        {
        final String spellname = aBasename.toLowerCase();
        final String upID = String.format( "Move%sUp", aBasename );
        final String downID = String.format( "Move%sDown", aBasename );
        final String upDesc = String.format( "Move %s up", spellname );
        final String downDesc = String.format( "Move %s down", spellname );
        define( upID ).set( "Move Up", "VK_U", upDesc ).icon( "actions/go-up" );
        define( downID ).set( "Move Down", "VK_D", downDesc ).icon( "actions/go-down" );
        }

    public final void define_add_remove( final String aBasename, final String aWhere )
        {
        final String spellname = aBasename.toLowerCase();
        final String addID = String.format( "Add%s", aBasename );
        final String removeID = String.format( "Remove%s", aBasename );
        final String addName = String.format( "Add %s", aBasename );
        final String removeName = String.format( "Remove %s", aBasename );
        final String addWhere = String.format( "Add %s to %s", spellname, aWhere );
        final String removeWhere = String.format( "Remove %s from %s", spellname, aWhere );
        define( addID ).set( addName, "VK_A", addWhere ).icon( "actions/list-add" );
        define( removeID ).set( removeName, "VK_R", removeWhere ).icon( "actions/list-remove" );
        }

    public final void define_add_delete( final String aBasename, final String aWhere )
        {
        final String spellname = aBasename.toLowerCase();
        final String addID = String.format( "Add%s", aBasename );
        final String removeID = String.format( "Delete%s", aBasename );
        final String addName = String.format( "Add %s", aBasename );
        final String removeName = String.format( "Delete %s", aBasename );
        final String addWhere = String.format( "Add %s to %s", spellname, aWhere );
        final String removeWhere = String.format( "Delete %s from %s", spellname, aWhere );
        define( addID ).set( addName, "VK_A", addWhere ).icon( "actions/list-add" );
        define( removeID ).set( removeName, "VK_D", removeWhere ).icon( "actions/list-remove" );
        }

    public final void define_reassign( final String aBasename, final String aSpellname )
        {
        final String id = String.format( "Reassign%s", aBasename );
        final String desc = String.format( "Change selected %s", aSpellname );
        define( id ).set( "Reassign", "VK_R", desc ).icon( "actions/view-refresh" );
        }

    public final void define_clone( final String aBasename )
        {
        final String spellname = aBasename.toLowerCase();
        final String id = String.format( "Clone%s", aBasename );
        final String name = String.format( "Clone %s", aBasename );
        final String desc = String.format( "Clone selected %s", spellname );
        define( id ).set( name, "VK_C", desc ).icon( "actions/edit-copy" );
        }

    public final void define_set_reset( final String aBasename )
        {
        final String spellname = aBasename.toLowerCase();
        final String setID = String.format( "SetDefault%s", aBasename );
        final String setName = String.format( "Set Default %s", aBasename );
        final String setDesc = String.format( "Set %s to default configuration", spellname );
        final String resetID = String.format( "Reset%s", aBasename );
        final String resetName = String.format( "Reset %s", aBasename );
        final String resetDesc = String.format( "Reset %s to empty %s", spellname, spellname );
        define( setID ).set( setName, "VK_S", setDesc ).icon( "actions/view-refresh" );
        define( resetID ).set( resetName, "VK_R", resetDesc ).icon( "actions/edit-clear" );
        }

    public final String enemiesFileName()
        {
        return "enemies.dat";
        }

    public final String pathesFileName()
        {
        return "pathes.dat";
        }

    public final String levelsFileName()
        {
        return "levels.dat";
        }

    public final String string( final String aString, final Object... aArguments )
        {
        return MessageFormat.format( aString, aArguments );
        }

    public final void init( final Action aAction )
        {
        final String name = aAction.getClass().getSimpleName();
        aAction.putValue( Action.NAME, name );
        aAction.setEnabled( false );
        final ActionConfiguration config = myConfigurations.get( name );
        if ( config != null ) config.applyTo( aAction );
        }



    private final EditorCoreAPI myCoreAPI;

    private final HashMap<String, ActionConfiguration> myConfigurations;
    }
