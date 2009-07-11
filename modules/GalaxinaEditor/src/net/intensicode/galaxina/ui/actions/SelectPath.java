package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.domain.Path;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class SelectPath extends BasicAction
    {
    public SelectPath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        }

    // From RunnableAction

    public final void actionPerformed( final ActionEvent aActionEvent )
        {
        final JComboBox comboBox = (JComboBox) aActionEvent.getSource();
        final Path path = (Path) comboBox.getModel().getSelectedItem();
        core().state().change( EditorState.SELECTED_PATH, path );
        }
    }
