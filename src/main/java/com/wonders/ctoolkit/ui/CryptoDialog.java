package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CryptoDialog extends ToolDialog {
    
    private CryptoPanel panel;
    
    public CryptoDialog() {
        super(true);
        setTitle("加解密工具");
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new CryptoPanel();
        return panel;
    }
    
    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
}