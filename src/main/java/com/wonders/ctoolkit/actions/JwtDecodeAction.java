package com.wonders.ctoolkit.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.wonders.ctoolkit.ui.JwtDialog;
import org.jetbrains.annotations.NotNull;

public class JwtDecodeAction extends AnAction implements DumbAware {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get editor and selected text
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        String selectedText = editor != null ? editor.getSelectionModel().getSelectedText() : null;

        // Create and show dialog
        JwtDialog dialog = new JwtDialog();
        if (selectedText != null && !selectedText.isEmpty()) {
            dialog.setInputText(selectedText);
        }
        dialog.show();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // Enable action only if there's an editor
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setEnabledAndVisible(editor != null);
    }
}