package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class Base64Panel extends BaseToolPanel {
    
    private JButton encodeButton;
    private JButton decodeButton;
    private JButton copyButton;
    
    public Base64Panel() {
        setName("Base64编解码");
    }
    
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        encodeButton = new JButton("编码");
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeBase64();
            }
        });
        
        decodeButton = new JButton("解码");
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeBase64();
            }
        });
        
        copyButton = new JButton("复制结果");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyOutput();
            }
        });
        
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(copyButton);
        
        return buttonPanel;
    }
    
    private void encodeBase64() {
        try {
            String input = getInputText();
            String encoded = Base64.getEncoder().encodeToString(input.getBytes());
            setOutputText(encoded);
        } catch (Exception ex) {
            Messages.showErrorDialog("Error encoding Base64: " + ex.getMessage(), "Base64 Encode Error");
        }
    }
    
    private void decodeBase64() {
        try {
            String input = getInputText();
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            String decoded = new String(decodedBytes);
            setOutputText(decoded);
        } catch (Exception ex) {
            Messages.showErrorDialog("Error decoding Base64: " + ex.getMessage(), "Base64 Decode Error");
        }
    }
    
    private void copyOutput() {
        try {
            String output = outputTextArea.getText();
            if (!output.isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(output), null);
                Messages.showMessageDialog("Output copied to clipboard", "Success", Messages.getInformationIcon());
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("Failed to copy to clipboard: " + ex.getMessage(), "Copy Error");
        }
    }
}