package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JwtDialog extends ToolDialog {

    private JwtPanel panel;

    public JwtDialog() {
        super(true);
        setTitle("JWT解码工具");
        // Use consistent size with other dialogs
        setSize(800, 600);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new JwtPanel();
        return panel;
    }

    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
    
    // Public wrapper method for protected setInputText method
    public void setInputText(String text) {
        super.setInputText(text);
    }
}