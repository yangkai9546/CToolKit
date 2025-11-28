package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RandomStringDialog extends ToolDialog {
    
    private RandomStringPanel panel;
    
    public RandomStringDialog() {
        super(true);
        setTitle("随机字符串生成器");
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new RandomStringPanel();
        return panel;
    }
    
    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
}