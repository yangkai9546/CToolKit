package com.wonders.ctoolkit.ui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseToolPanel extends JPanel {
    
    protected JTextArea inputTextArea;
    protected JTextArea outputTextArea;
    
    public BaseToolPanel() {
        super(new BorderLayout());
        // Set preferred size to ensure consistent sizing across panels
        setPreferredSize(new Dimension(800, 600));
        // Allow flexible sizing by not setting minimum and maximum sizes
        initializeUI();
    }
    
    private void initializeUI() {
        // Add any additional components from subclasses
        JComponent additionalComponents = createAdditionalComponents();
        if (additionalComponents != null) {
            add(additionalComponents, BorderLayout.NORTH);
        }
        
        // Create main input/output panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        if (buttonPanel != null) {
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    
    /**
     * Override this method to provide additional components that should be added
     * to the NORTH of the panel (e.g., key input fields for crypto tools)
     * @return A JComponent to add to the NORTH, or null if none
     */
    protected JComponent createAdditionalComponents() {
        return null;
    }
    
    protected JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Set alignment to prevent layout issues
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(createInputPanel());
        panel.add(createOutputPanel());
        
        return panel;
    }
    
    protected JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入"));
        // Set alignment to prevent layout issues
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        inputTextArea = new JTextArea(10, 50);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        // Ensure consistent sizing
        inputTextArea.setMinimumSize(new Dimension(300, 100));
        inputTextArea.setPreferredSize(new Dimension(780, 150));
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        // Ensure scroll pane has consistent sizing
        inputScrollPane.setMinimumSize(new Dimension(300, 100));
        inputScrollPane.setPreferredSize(new Dimension(780, 150));
        inputPanel.add(inputScrollPane);
        
        return inputPanel;
    }
    
    protected JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.setBorder(BorderFactory.createTitledBorder("输出"));
        // Set alignment to prevent layout issues
        outputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        outputTextArea = new JTextArea(10, 50);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        // Ensure consistent sizing
        outputTextArea.setMinimumSize(new Dimension(300, 100));
        outputTextArea.setPreferredSize(new Dimension(780, 150));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        // Ensure scroll pane has consistent sizing
        outputScrollPane.setMinimumSize(new Dimension(300, 100));
        outputScrollPane.setPreferredSize(new Dimension(780, 150));
        outputPanel.add(outputScrollPane);
        
        return outputPanel;
    }
    
    protected abstract JPanel createButtonPanel();
    
    public void setInputText(String text) {
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
}