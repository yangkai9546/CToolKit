package com.wonders.ctoolkit.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wonders.ctoolkit.ui.RandomStringDialog;
import org.jetbrains.annotations.NotNull;

public class RandomStringAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        RandomStringDialog dialog = new RandomStringDialog();
        dialog.show();
    }
}