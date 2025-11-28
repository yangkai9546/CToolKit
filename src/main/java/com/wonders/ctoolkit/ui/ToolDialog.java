package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ToolDialog extends DialogWrapper {
    
    protected JTextArea inputTextArea;
    protected JTextArea outputTextArea;
    
    public ToolDialog(boolean canBeParent) {
        super(canBeParent);
        // Set dialog size
        setSize(800, 600);
        // Make dialog resizable
        setResizable(true);
        init();
    }
    
    protected JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.add(createInputPanel());
        panel.add(createOutputPanel());
        
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入"));
        
        inputTextArea = new JTextArea(10, 50);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(inputScrollPane);
        
        return inputPanel;
    }
    
    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.setBorder(BorderFactory.createTitledBorder("输出"));
        
        outputTextArea = new JTextArea(10, 50);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(outputScrollPane);
        
        return outputPanel;
    }
    
    protected void setInputText(String text) {
        if (inputTextArea != null) {
            inputTextArea.setText(text);
        }
    }
    
    protected void setOutputText(String text) {
        if (outputTextArea != null) {
            outputTextArea.setText(text);
        }
    }
    
    protected String getInputText() {
        return inputTextArea != null ? inputTextArea.getText() : "";
    }
    
    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (getInputText().trim().isEmpty()) {
            return new ValidationInfo("Input cannot be empty", inputTextArea);
        }
        return null;
    }
}