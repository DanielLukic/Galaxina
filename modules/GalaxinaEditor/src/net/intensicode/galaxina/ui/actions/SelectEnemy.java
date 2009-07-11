package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.domain.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class SelectEnemy extends BasicAction
    {
    public SelectEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        }

    // From RunnableAction

    public final void actionPerformed( final ActionEvent aActionEvent )
        {
        final JComboBox comboBox = (JComboBox) aActionEvent.getSource();
        final Enemy enemy = (Enemy) comboBox.getModel().getSelectedItem();
        core().state().change( EditorState.SELECTED_ENEMY, enemy );
        }
    }
