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
import java.util.Base64;

public class JwtPanel extends BaseToolPanel {

    private JButton decodeButton;
    private JButton copyHeaderButton;
    private JButton copyPayloadButton;
    private JButton copySignatureButton;
    private JTextArea headerTextArea;
    private JTextArea payloadTextArea;
    private JTextArea signatureTextArea;

    public JwtPanel() {
        setName("JWT解码");
    }

    @Override
    protected JComponent createAdditionalComponents() {
        // Create a panel to hold the JWT-specific output areas
        JPanel jwtOutputPanel = new JPanel();
        jwtOutputPanel.setLayout(new BoxLayout(jwtOutputPanel, BoxLayout.Y_AXIS));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createTitledBorder("Header"));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        headerTextArea = new JTextArea(5, 40);
        headerTextArea.setLineWrap(true);
        headerTextArea.setWrapStyleWord(true);
        headerTextArea.setEditable(false);
        JScrollPane headerScrollPane = new JScrollPane(headerTextArea);
        headerPanel.add(headerScrollPane, BorderLayout.CENTER);

        // Payload panel
        JPanel payloadPanel = new JPanel(new BorderLayout());
        payloadPanel.setBorder(BorderFactory.createTitledBorder("Payload"));
        payloadPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        payloadTextArea = new JTextArea(5, 40);
        payloadTextArea.setLineWrap(true);
        payloadTextArea.setWrapStyleWord(true);
        payloadTextArea.setEditable(false);
        JScrollPane payloadScrollPane = new JScrollPane(payloadTextArea);
        payloadPanel.add(payloadScrollPane, BorderLayout.CENTER);

        // Signature panel
        JPanel signaturePanel = new JPanel(new BorderLayout());
        signaturePanel.setBorder(BorderFactory.createTitledBorder("Signature"));
        signaturePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        signatureTextArea = new JTextArea(3, 40);
        signatureTextArea.setLineWrap(true);
        signatureTextArea.setWrapStyleWord(true);
        signatureTextArea.setEditable(false);
        JScrollPane signatureScrollPane = new JScrollPane(signatureTextArea);
        signaturePanel.add(signatureScrollPane, BorderLayout.CENTER);

        jwtOutputPanel.add(headerPanel);
        jwtOutputPanel.add(payloadPanel);
        jwtOutputPanel.add(signaturePanel);

        return jwtOutputPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        decodeButton = new JButton("解码");
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeJwt();
            }
        });

        copyHeaderButton = new JButton("复制Header");
        copyHeaderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(headerTextArea.getText(), "Header");
            }
        });

        copyPayloadButton = new JButton("复制Payload");
        copyPayloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(payloadTextArea.getText(), "Payload");
            }
        });

        copySignatureButton = new JButton("复制Signature");
        copySignatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(signatureTextArea.getText(), "Signature");
            }
        });

        buttonPanel.add(decodeButton);
        buttonPanel.add(copyHeaderButton);
        buttonPanel.add(copyPayloadButton);
        buttonPanel.add(copySignatureButton);

        return buttonPanel;
    }

    private void decodeJwt() {
        try {
            String jwt = getInputText().trim();

            if (jwt.isEmpty()) {
                Messages.showErrorDialog("请输入JWT Token", "输入错误");
                return;
            }

            // Check if it's a valid JWT format (three parts separated by dots)
            String[] parts = jwt.split("\\.");
            if (parts.length != 3) {
                Messages.showErrorDialog("无效的JWT格式。JWT应包含三个由点分隔的部分。", "JWT格式错误");
                return;
            }

            // Decode header
            String headerJson = decodeBase64ToJson(parts[0]);
            headerTextArea.setText(headerJson);

            // Decode payload
            String payloadJson = decodeBase64ToJson(parts[1]);
            payloadTextArea.setText(payloadJson);

            // Display signature
            signatureTextArea.setText(parts[2]);

        } catch (Exception ex) {
            Messages.showErrorDialog("解码JWT时出错: " + ex.getMessage(), "解码错误");
        }
    }

    private String decodeBase64ToJson(String base64String) throws Exception {
        // Add padding if necessary
        int padding = 4 - (base64String.length() % 4);
        if (padding != 4) {
            StringBuilder padded = new StringBuilder(base64String);
            for (int i = 0; i < padding; i++) {
                padded.append("=");
            }
            base64String = padded.toString();
        }

        // Replace URL-safe characters
        base64String = base64String.replace('-', '+').replace('_', '/');

        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        String jsonString = new String(decodedBytes);

        // Format JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        return gson.toJson(jsonElement);
    }

    private void copyToClipboard(String text, String partName) {
        try {
            if (!text.isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(text), null);
                Messages.showMessageDialog(partName + "已复制到剪贴板", "成功", Messages.getInformationIcon());
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("复制到剪贴板失败: " + ex.getMessage(), "复制错误");
        }
    }
}