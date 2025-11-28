package com.wonders.ctoolkit.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.wonders.ctoolkit.ui.JsonFormatDialog;
import org.jetbrains.annotations.NotNull;

public class JsonFormatAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        String selectedText = null;
        
        if (editor != null) {
            selectedText = editor.getSelectionModel().getSelectedText();
        }
        
        JsonFormatDialog dialog = new JsonFormatDialog();
        // The dialog will be empty by default
        dialog.show();
    }
}