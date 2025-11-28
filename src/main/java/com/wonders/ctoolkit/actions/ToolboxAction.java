package com.wonders.ctoolkit.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wonders.ctoolkit.ui.ToolboxDialog;
import org.jetbrains.annotations.NotNull;

public class ToolboxAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ToolboxDialog dialog = new ToolboxDialog();
        dialog.show();
    }
}