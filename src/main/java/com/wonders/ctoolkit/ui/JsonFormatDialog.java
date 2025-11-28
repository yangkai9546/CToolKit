package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JsonFormatDialog extends ToolDialog {
    
    private JsonFormatPanel panel;
    
    public JsonFormatDialog() {
        super(true);
        setTitle("JSON格式化工具");
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new JsonFormatPanel();
        return panel;
    }
    
    @Override
    protected void doOKAction() {
        // Handle OK action if needed
        super.doOKAction();
    }
}