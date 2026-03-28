package com.wonders.ctoolkit.ui;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class QrCodeDialog extends ToolDialog {

    private QrCodePanel panel;

    public QrCodeDialog() {
        super(true);
        setTitle("二维码生成器");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new QrCodePanel();
        return panel;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
    }
}
