package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UrlDialog extends ToolDialog {
    
    private UrlPanel panel;
    
    public UrlDialog() {
        super(true);
        setTitle("URL编解码工具");
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new UrlPanel();
        return panel;
    }
    
    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
}