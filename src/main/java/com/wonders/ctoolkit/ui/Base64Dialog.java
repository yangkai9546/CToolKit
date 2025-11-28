package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Base64Dialog extends ToolDialog {
    
    private Base64Panel panel;
    
    public Base64Dialog() {
        super(true);
        setTitle("Base64编解码工具");
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new Base64Panel();
        return panel;
    }
    
    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
}