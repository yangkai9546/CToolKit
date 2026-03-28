package com.wonders.ctoolkit.ui;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TimestampDialog extends ToolDialog {

    private TimestampPanel panel;

    public TimestampDialog() {
        super(true);
        setTitle("时间戳转换工具");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new TimestampPanel();
        return panel;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
    }
}
