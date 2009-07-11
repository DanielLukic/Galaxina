package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorUiAPI;

import javax.swing.*;
import java.awt.*;

public final class EditorFrame extends JFrame
    {
    public EditorFrame( final EditorCoreAPI aCoreAPI )
        {
        super( "Galaxina Editor" );

        myCoreAPI = aCoreAPI;

        setJMenuBar( createMenuBar() );

        final Container content = getContentPane();
        content.setLayout( new BoxLayout( content, BoxLayout.Y_AXIS ) );

        final EditorForm editorForm = new EditorForm( aCoreAPI );
        content.add( editorForm.getComponent() );

        final KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( new GlobalKeyEventDispatcher( myCoreAPI, editorForm ) );

        pack();

        final int minWidth = getSize().width;
        final int minHeight = getSize().height;

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int targetWidth = screenSize.width * 2 / 4;
        final int targetHeight = screenSize.height * 3 / 4;
        final int width = Math.max( minWidth, targetWidth );
        final int height = Math.max( minHeight, targetHeight );
        final int x = ( screenSize.width - width ) / 2;
        final int y = ( screenSize.height - height ) / 2;
        setBounds( x, y, width, height );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        addWindowListener( new EditorFrameHandler( myCoreAPI ) );

        ui().register( this );
        }

    private final JMenuBar createMenuBar()
        {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add( createProjectMenu() );
        menuBar.add( createPathMenu() );
        menuBar.add( createSwarmMenu() );
        menuBar.add( createGameMenu() );
        menuBar.add( createEditMenu() );
        return menuBar;
        }

    private final JMenu createProjectMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Project" ) );
        menu.add( new JMenuItem( ui().action( "OpenProject" ) ) );
        menu.add( new JMenuItem( ui().action( "SaveChanges" ) ) );
        menu.add( new JMenuItem( ui().action( "CloseProject" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "QuitApplication" ) ) );
        return menu;
        }

    private final JMenu createPathMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Path" ) );
        menu.add( new JMenuItem( ui().action( "AddPath" ) ) );
        menu.add( new JMenuItem( ui().action( "DeletePath" ) ) );
        menu.add( new JMenuItem( ui().action( "ClonePath" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "MovePathUp" ) ) );
        menu.add( new JMenuItem( ui().action( "MovePathDown" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.AddPathNode" ) ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.RemovePathNode" ) ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.SetDefaultPath" ) ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.ResetPath" ) ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.MirrorX" ) ) );
        menu.add( new JMenuItem( ui().action( "pathEditor.MirrorY" ) ) );
        return menu;
        }

    private final JMenu createSwarmMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Swarm" ) );
        menu.add( new JMenuItem( ui().action( "AddSwarm" ) ) );
        menu.add( new JMenuItem( ui().action( "DeleteSwarm" ) ) );
        menu.add( new JMenuItem( ui().action( "CloneSwarm" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "MoveSwarmUp" ) ) );
        menu.add( new JMenuItem( ui().action( "MoveSwarmDown" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.AddSwarmNode" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.RemoveSwarmNode" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.SetDefaultSwarm" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.ResetSwarm" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.MirrorX" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.MirrorY" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.ShrinkX" ) ) );
        menu.add( new JMenuItem( ui().action( "swarmEditor.ShrinkY" ) ) );
        return menu;
        }

    private final JMenu createGameMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Game" ) );
        menu.add( new JMenuItem( ui().action( "StepGame" ) ) );
        menu.add( new JMenuItem( ui().action( "PauseGame" ) ) );
        menu.add( new JMenuItem( ui().action( "ReloadGame" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "ZoomGameExact" ) ) );
        menu.add( new JMenuItem( ui().action( "ZoomGameToFit" ) ) );
        return menu;
        }

    private final JMenu createEditMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Edit" ) );
        menu.add( new JMenuItem( ui().action( "editors.AlignToGrid" ) ) );
        menu.add( new JMenuItem( ui().action( "editors.ShowGrid" ) ) );
        //menu.add( new JMenuItem( ui().action( "editors.ShowPathes" ) ) );
        //menu.add( new JMenuItem( ui().action( "editors.SmoothPathes" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "editors.ZoomExact" ) ) );
        menu.add( new JMenuItem( ui().action( "editors.ZoomToFit" ) ) );
        menu.add( new JMenuItem( ui().action( "editors.ZoomIn" ) ) );
        menu.add( new JMenuItem( ui().action( "editors.ZoomOut" ) ) );
        return menu;
        }

    private final EditorUiAPI ui()
        {
        return myCoreAPI.ui();
        }



    private final EditorCoreAPI myCoreAPI;
    }
