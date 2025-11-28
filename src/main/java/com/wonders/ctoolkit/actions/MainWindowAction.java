package com.wonders.ctoolkit.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wonders.ctoolkit.ui.MainWindow;
import org.jetbrains.annotations.NotNull;

public class MainWindowAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.show();
    }
}