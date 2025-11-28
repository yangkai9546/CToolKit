package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlPanel extends BaseToolPanel {
    
    private JButton encodeButton;
    private JButton decodeButton;
    private JButton copyButton;
    
    public UrlPanel() {
        setName("URL编解码");
    }
    
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        encodeButton = new JButton("编码");
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeUrl();
            }
        });
        
        decodeButton = new JButton("解码");
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeUrl();
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
    
    private void encodeUrl() {
        try {
            String input = getInputText();
            String encoded = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
            setOutputText(encoded);
        } catch (Exception ex) {
            Messages.showErrorDialog("Error encoding URL: " + ex.getMessage(), "URL Encode Error");
        }
    }
    
    private void decodeUrl() {
        try {
            String input = getInputText();
            String decoded = URLDecoder.decode(input, StandardCharsets.UTF_8.toString());
            setOutputText(decoded);
        } catch (Exception ex) {
            Messages.showErrorDialog("Error decoding URL: " + ex.getMessage(), "URL Decode Error");
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