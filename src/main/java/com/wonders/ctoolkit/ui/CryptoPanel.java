package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.Messages;
import com.wonders.ctoolkit.utils.CryptoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;

public class CryptoPanel extends BaseToolPanel {

    private JComboBox<String> algorithmComboBox;
    private JComboBox<String> aesModeComboBox;
    private JTextField keyField;
    private JTextField ivField;
    private JTextField publicKeyField;
    private JTextField privateKeyField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton copyButton;
    private JButton generateKeyButton;
    private JButton generateRSAKeyPairButton;
    private JPanel keyPanel;
    private JPanel ivPanel;
    private JPanel rsaKeyPanel;
    private JPanel outputFormatPanel;
    private JPanel aesModePanel;
    private JRadioButton base64RadioButton;
    private JRadioButton hexRadioButton;

    public CryptoPanel() {
        setName("加解密");
    }

    @Override
    protected JComponent createAdditionalComponents() {
        // Main container panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create algorithm selection panel
        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmPanel.setBorder(BorderFactory.createTitledBorder("加密算法"));
        algorithmPanel.add(new JLabel("算法:"));
        algorithmComboBox = new JComboBox<>(new String[]{"AES", "RSA"});
        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUIBasedOnAlgorithm();
            }
        });
        algorithmPanel.add(algorithmComboBox);
        mainPanel.add(algorithmPanel);

        // Create AES key panel for symmetric encryption
        keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.setBorder(BorderFactory.createTitledBorder("AES密钥"));
        keyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        keyPanel.add(new JLabel("密钥:"));
        keyField = new JTextField(30);
        keyPanel.add(keyField);

        generateKeyButton = new JButton("生成密钥");
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSymmetricKey();
            }
        });
        keyPanel.add(generateKeyButton);
        mainPanel.add(keyPanel);

        // AES mode and padding selection panel
        aesModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aesModePanel.setBorder(BorderFactory.createTitledBorder("AES模式"));
        aesModePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        aesModePanel.add(new JLabel("模式:"));

        aesModeComboBox = new JComboBox<>(new String[]{
                "CBC/PKCS5Padding",
                "CBC/NoPadding",
            "ECB/PKCS5Padding",
            "ECB/NoPadding"

        });
        aesModeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIVPanelVisibility();
            }
        });
        aesModePanel.add(aesModeComboBox);
        mainPanel.add(aesModePanel);

        // IV panel for AES CBC mode - separate panel
        ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ivPanel.setBorder(BorderFactory.createTitledBorder("偏移量 (IV) - 可选"));
        ivPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ivPanel.add(new JLabel("IV:"));
        ivField = new JTextField(30);
        ivPanel.add(ivField);
        mainPanel.add(ivPanel);

        // Output format selection panel
        outputFormatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputFormatPanel.setBorder(BorderFactory.createTitledBorder("输出格式"));
        outputFormatPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        outputFormatPanel.add(new JLabel("编码:"));

        base64RadioButton = new JRadioButton("Base64", true);
        hexRadioButton = new JRadioButton("Hex (十六进制)");
        ButtonGroup formatGroup = new ButtonGroup();
        formatGroup.add(base64RadioButton);
        formatGroup.add(hexRadioButton);

        outputFormatPanel.add(base64RadioButton);
        outputFormatPanel.add(hexRadioButton);
        mainPanel.add(outputFormatPanel);

        // Create key panel for asymmetric encryption (RSA)
        rsaKeyPanel = new JPanel();
        rsaKeyPanel.setLayout(new BoxLayout(rsaKeyPanel, BoxLayout.Y_AXIS));
        rsaKeyPanel.setBorder(BorderFactory.createTitledBorder("RSA密钥"));
        rsaKeyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Public key panel
        JPanel publicKeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        publicKeyPanel.add(new JLabel("公钥:"));
        publicKeyField = new JTextField(30);
        publicKeyPanel.add(publicKeyField);
        rsaKeyPanel.add(publicKeyPanel);

        // Private key panel
        JPanel privateKeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        privateKeyPanel.add(new JLabel("私钥:"));
        privateKeyField = new JTextField(30);
        privateKeyPanel.add(privateKeyField);
        rsaKeyPanel.add(privateKeyPanel);

        generateRSAKeyPairButton = new JButton("生成RSA密钥对");
        generateRSAKeyPairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRSAKeyPair();
            }
        });
        rsaKeyPanel.add(generateRSAKeyPairButton);
        mainPanel.add(rsaKeyPanel);

        // Initially show only AES components
        updateUIBasedOnAlgorithm();

        return mainPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        encryptButton = new JButton("加密");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptText();
            }
        });

        decryptButton = new JButton("解密");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptText();
            }
        });

        copyButton = new JButton("复制结果");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyOutput();
            }
        });

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(copyButton);

        return buttonPanel;
    }

    private void updateUIBasedOnAlgorithm() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        if ("AES".equals(selectedAlgorithm)) {
            keyPanel.setVisible(true);
            aesModePanel.setVisible(true);
            updateIVPanelVisibility();
            rsaKeyPanel.setVisible(false);
        } else if ("RSA".equals(selectedAlgorithm)) {
            keyPanel.setVisible(false);
            aesModePanel.setVisible(false);
            ivPanel.setVisible(false);
            rsaKeyPanel.setVisible(true);
        }
        // Revalidate and repaint to reflect changes
        if (keyPanel.getParent() != null) {
            keyPanel.getParent().revalidate();
            keyPanel.getParent().repaint();
        }
    }

    private void updateIVPanelVisibility() {
        String selectedMode = (String) aesModeComboBox.getSelectedItem();
        // Show IV panel only for CBC modes
        boolean needsIV = selectedMode != null && selectedMode.startsWith("CBC");
        ivPanel.setVisible(needsIV);
        // Revalidate and repaint to reflect changes
        if (ivPanel.getParent() != null) {
            ivPanel.getParent().revalidate();
            ivPanel.getParent().repaint();
        }
    }

    private void encryptText() {
        try {
            String input = getInputText();
            if (input.isEmpty()) {
                Messages.showErrorDialog("请输入要加密的文本", "输入错误");
                return;
            }

            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            boolean useHex = hexRadioButton.isSelected();

            if ("AES".equals(selectedAlgorithm)) {
                String key = keyField.getText();
                if (key.isEmpty()) {
                    Messages.showErrorDialog("请输入加密密钥", "密钥错误");
                    return;
                }

                String iv = ivField.getText();
                String mode = (String) aesModeComboBox.getSelectedItem();
                String encrypted;
                if (useHex) {
                    encrypted = CryptoUtils.encryptAESHex(input, key, iv, mode);
                } else {
                    encrypted = CryptoUtils.encryptAES(input, key, iv, mode);
                }
                setOutputText(encrypted);
            } else if ("RSA".equals(selectedAlgorithm)) {
                String publicKey = publicKeyField.getText();
                if (publicKey.isEmpty()) {
                    Messages.showErrorDialog("请输入公钥", "公钥错误");
                    return;
                }

                String encrypted;
                if (useHex) {
                    encrypted = CryptoUtils.encryptRSAHex(input, publicKey);
                } else {
                    encrypted = CryptoUtils.encryptRSA(input, publicKey);
                }
                setOutputText(encrypted);
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("加密过程中出错: " + ex.getMessage(), "加密错误");
        }
    }

    private void decryptText() {
        try {
            String input = getInputText();
            if (input.isEmpty()) {
                Messages.showErrorDialog("请输入要解密的文本", "输入错误");
                return;
            }

            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            boolean useHex = hexRadioButton.isSelected();

            if ("AES".equals(selectedAlgorithm)) {
                String key = keyField.getText();
                if (key.isEmpty()) {
                    Messages.showErrorDialog("请输入解密密钥", "密钥错误");
                    return;
                }

                String iv = ivField.getText();
                String mode = (String) aesModeComboBox.getSelectedItem();
                String decrypted;
                if (useHex) {
                    decrypted = CryptoUtils.decryptAESHex(input, key, iv, mode);
                } else {
                    decrypted = CryptoUtils.decryptAES(input, key, iv, mode);
                }
                setOutputText(decrypted);
            } else if ("RSA".equals(selectedAlgorithm)) {
                String privateKey = privateKeyField.getText();
                if (privateKey.isEmpty()) {
                    Messages.showErrorDialog("请输入私钥", "私钥错误");
                    return;
                }

                String decrypted;
                if (useHex) {
                    decrypted = CryptoUtils.decryptRSAHex(input, privateKey);
                } else {
                    decrypted = CryptoUtils.decryptRSA(input, privateKey);
                }
                setOutputText(decrypted);
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("解密过程中出错: " + ex.getMessage(), "解密错误");
        }
    }

    private void generateSymmetricKey() {
        try {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            if ("AES".equals(selectedAlgorithm)) {
                String key = CryptoUtils.generateAESKey();
                keyField.setText(key);
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("生成密钥时出错: " + ex.getMessage(), "密钥生成错误");
        }
    }

    private void generateRSAKeyPair() {
        try {
            KeyPair keyPair = CryptoUtils.generateRSAKeyPair();
            String publicKey = CryptoUtils.getPublicKeyString(keyPair.getPublic());
            String privateKey = CryptoUtils.getPrivateKeyString(keyPair.getPrivate());

            publicKeyField.setText(publicKey);
            privateKeyField.setText(privateKey);
        } catch (Exception ex) {
            Messages.showErrorDialog("生成RSA密钥对时出错: " + ex.getMessage(), "密钥对生成错误");
        }
    }

    private void copyOutput() {
        try {
            String output = outputTextArea.getText();
            if (!output.isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(output), null);
                Messages.showMessageDialog("结果已复制到剪贴板", "成功", Messages.getInformationIcon());
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("复制到剪贴板失败: " + ex.getMessage(), "复制错误");
        }
    }
}
