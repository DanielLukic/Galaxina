package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.*;
import net.intensicode.runme.GraphicsContext;

import javax.swing.*;
import java.awt.*;

public final class EditorFrame extends JFrame implements GraphicsContext
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

        centerAndResizeTo();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        addWindowListener( new EditorFrameHandler( myCoreAPI ) );

        ui().register( this );
        }

    public final void centerAndResizeTo()
        {
        final GraphicsDevice device = getGraphicsDevice();
        final DisplayMode mode = device.getDisplayMode();
        final int availableWidth = mode.getWidth() * 80 / 100;
        final int availableHeight = mode.getHeight() * 80 / 100;
        final Insets insets = getInsets();
        final int width = availableWidth + insets.left + insets.right;
        final int height = availableHeight + insets.top + insets.bottom;
        final int x = ( mode.getWidth() - width ) / 2;
        final int y = ( mode.getHeight() - height ) / 2;
        setBounds( x, y, width, height );
        }

    // From GraphicsContext

    public final GraphicsDevice getGraphicsDevice()
        {
        return getGraphicsConfiguration().getDevice();
        }

    // Implementation

    private JMenuBar createMenuBar()
        {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add( createProjectMenu() );
        menuBar.add( createPathMenu() );
        menuBar.add( createSwarmMenu() );
        menuBar.add( createGameMenu() );
        menuBar.add( createEditMenu() );
        return menuBar;
        }

    private JMenu createProjectMenu()
        {
        final JMenu menu = new JMenu( ui().action( "Project" ) );
        menu.add( new JMenuItem( ui().action( "OpenProject" ) ) );
        menu.add( new JMenuItem( ui().action( "SaveChanges" ) ) );
        menu.add( new JMenuItem( ui().action( "CloseProject" ) ) );
        menu.add( new JSeparator( JSeparator.HORIZONTAL ) );
        menu.add( new JMenuItem( ui().action( "QuitApplication" ) ) );
        return menu;
        }

    private JMenu createPathMenu()
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

    private JMenu createSwarmMenu()
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

    private JMenu createGameMenu()
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

    private JMenu createEditMenu()
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

    private EditorUiAPI ui()
        {
        return myCoreAPI.ui();
        }



    private final EditorCoreAPI myCoreAPI;
    }
