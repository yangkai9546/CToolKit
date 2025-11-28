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
    private JPanel rsaKeyPanel;
    
    public CryptoPanel() {
        setName("加解密");
    }
    
    @Override
    protected JComponent createAdditionalComponents() {
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
        
        // Create key panel for symmetric encryption
        keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.setBorder(BorderFactory.createTitledBorder("密钥"));
        keyPanel.add(new JLabel("密钥:"));
        keyField = new JTextField(30);
        keyPanel.add(keyField);
        
        // IV panel for AES CBC mode
        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ivPanel.setBorder(BorderFactory.createTitledBorder("偏移量 (IV) - 可选"));
        ivPanel.add(new JLabel("IV:"));
        ivField = new JTextField(30);
        ivPanel.add(ivField);
        
        generateKeyButton = new JButton("生成密钥");
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSymmetricKey();
            }
        });
        keyPanel.add(generateKeyButton);
        keyPanel.add(ivPanel);
        
        // Create key panel for asymmetric encryption (RSA)
        rsaKeyPanel = new JPanel();
        rsaKeyPanel.setLayout(new BoxLayout(rsaKeyPanel, BoxLayout.Y_AXIS));
        rsaKeyPanel.setBorder(BorderFactory.createTitledBorder("RSA密钥"));
        
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
        
        // Main panel containing all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(algorithmPanel);
        mainPanel.add(keyPanel);
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
            rsaKeyPanel.setVisible(false);
        } else if ("RSA".equals(selectedAlgorithm)) {
            keyPanel.setVisible(false);
            rsaKeyPanel.setVisible(true);
        }
        // Revalidate and repaint to reflect changes
        if (keyPanel.getParent() != null) {
            keyPanel.getParent().revalidate();
            keyPanel.getParent().repaint();
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
            
            if ("AES".equals(selectedAlgorithm)) {
                String key = keyField.getText();
                if (key.isEmpty()) {
                    Messages.showErrorDialog("请输入加密密钥", "密钥错误");
                    return;
                }
                
                String iv = ivField.getText();
                String encrypted = CryptoUtils.encryptAES(input, key, iv);
                setOutputText(encrypted);
            } else if ("RSA".equals(selectedAlgorithm)) {
                String publicKey = publicKeyField.getText();
                if (publicKey.isEmpty()) {
                    Messages.showErrorDialog("请输入公钥", "公钥错误");
                    return;
                }
                
                String encrypted = CryptoUtils.encryptRSA(input, publicKey);
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
            
            if ("AES".equals(selectedAlgorithm)) {
                String key = keyField.getText();
                if (key.isEmpty()) {
                    Messages.showErrorDialog("请输入解密密钥", "密钥错误");
                    return;
                }
                
                String iv = ivField.getText();
                String decrypted = CryptoUtils.decryptAES(input, key, iv);
                setOutputText(decrypted);
            } else if ("RSA".equals(selectedAlgorithm)) {
                String privateKey = privateKeyField.getText();
                if (privateKey.isEmpty()) {
                    Messages.showErrorDialog("请输入私钥", "私钥错误");
                    return;
                }
                
                String decrypted = CryptoUtils.decryptRSA(input, privateKey);
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