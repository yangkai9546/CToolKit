package com.wonders.ctoolkit.ui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseToolPanel extends JPanel {

    protected JTextArea inputTextArea;
    protected JTextArea outputTextArea;

    public BaseToolPanel() {
        super(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        // Add any additional components from subclasses
        JComponent additionalComponents = createAdditionalComponents();
        if (additionalComponents != null) {
            add(additionalComponents, BorderLayout.NORTH);
        }

        // Create main input/output panel using BorderLayout for stability
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(createInputPanel(), BorderLayout.NORTH);
        centerPanel.add(createOutputPanel(), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

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

    protected JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入"));

        inputTextArea = new JTextArea(5, 40);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        return inputPanel;
    }

    protected JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("输出"));

        outputTextArea = new JTextArea(5, 40);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

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