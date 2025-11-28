package com.wonders.ctoolkit.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JsonFormatPanel extends BaseToolPanel {
    
    private JButton formatButton;
    private JButton compressButton;
    private JButton copyButton;
    
    public JsonFormatPanel() {
        setName("JSON格式化");
    }
    
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        formatButton = new JButton("格式化");
        formatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatJson();
            }
        });
        
        compressButton = new JButton("压缩");
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compressJson();
            }
        });
        
        copyButton = new JButton("复制结果");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyOutput();
            }
        });
        
        buttonPanel.add(formatButton);
        buttonPanel.add(compressButton);
        buttonPanel.add(copyButton);
        
        return buttonPanel;
    }
    
    private void formatJson() {
        try {
            String input = getInputText();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(input);
            String formattedJson = gson.toJson(jsonElement);
            setOutputText(formattedJson);
        } catch (Exception ex) {
            Messages.showErrorDialog("Invalid JSON format: " + ex.getMessage(), "JSON Format Error");
        }
    }
    
    private void compressJson() {
        try {
            String input = getInputText();
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(input);
            String compressedJson = gson.toJson(jsonElement);
            setOutputText(compressedJson);
        } catch (Exception ex) {
            Messages.showErrorDialog("Invalid JSON format: " + ex.getMessage(), "JSON Compress Error");
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