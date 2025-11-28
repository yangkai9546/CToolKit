package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class RandomStringPanel extends BaseToolPanel {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final SecureRandom RANDOM = new SecureRandom();

    private JTextField lengthField;
    private JTextField countField;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox lowercaseCheckBox;
    private JCheckBox digitsCheckBox;
    private JCheckBox specialCharsCheckBox;
    private JButton generateButton;
    private JButton copyButton;

    public RandomStringPanel() {
        setName("随机字符串");
    }

    @Override
    protected JComponent createAdditionalComponents() {
        // Create input panel for options
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("生成参数"));

        // Length panel
        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lengthPanel.add(new JLabel("长度:"));
        lengthField = new JTextField("12", 10);
        lengthPanel.add(lengthField);
        inputPanel.add(lengthPanel);

        // Character options panel
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("字符选项"));
        uppercaseCheckBox = new JCheckBox("大写字母", true);
        lowercaseCheckBox = new JCheckBox("小写字母", true);
        digitsCheckBox = new JCheckBox("数字", true);
        specialCharsCheckBox = new JCheckBox("特殊符号", false);
        optionsPanel.add(uppercaseCheckBox);
        optionsPanel.add(lowercaseCheckBox);
        optionsPanel.add(digitsCheckBox);
        optionsPanel.add(specialCharsCheckBox);
        inputPanel.add(optionsPanel);

        // Count panel
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countPanel.add(new JLabel("生成数量 (最多10个):"));
        countField = new JTextField("5", 10);
        countPanel.add(countField);
        inputPanel.add(countPanel);

        return inputPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        generateButton = new JButton("生成");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomStrings();
            }
        });

        copyButton = new JButton("复制结果");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyOutput();
            }
        });

        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);

        return buttonPanel;
    }

    @Override
    protected JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Use the input panel from BaseToolPanel for the actual string input/output
        panel.add(createInputPanel());

        // Use the output panel from BaseToolPanel
        JPanel outputPanel = createOutputPanel();
        // Increase rows for output text area to accommodate multiple strings
        if (outputTextArea != null) {
            outputTextArea.setRows(15);
            outputTextArea.setColumns(50);
        }
        panel.add(outputPanel);

        return panel;
    }

    private void generateRandomStrings() {
        try {
            int length = Integer.parseInt(lengthField.getText().trim());
            int count = Integer.parseInt(countField.getText().trim());

            if (length <= 0) {
                Messages.showErrorDialog("请输入一个正整数作为字符串长度", "无效长度");
                return;
            }

            if (count <= 0 || count > 10) {
                Messages.showErrorDialog("生成数量必须是1到10之间的正整数", "无效数量");
                return;
            }

            // Build character set based on selected options
            StringBuilder charSetBuilder = new StringBuilder();
            if (uppercaseCheckBox.isSelected()) {
                charSetBuilder.append(UPPERCASE);
            }
            if (lowercaseCheckBox.isSelected()) {
                charSetBuilder.append(LOWERCASE);
            }
            if (digitsCheckBox.isSelected()) {
                charSetBuilder.append(DIGITS);
            }
            if (specialCharsCheckBox.isSelected()) {
                charSetBuilder.append(SPECIAL_CHARS);
            }

            String charSet = charSetBuilder.toString();
            if (charSet.isEmpty()) {
                Messages.showErrorDialog("请至少选择一种字符类型", "无字符类型");
                return;
            }

            // Generate strings
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < count; i++) {
                StringBuilder sb = new StringBuilder(length);
                for (int j = 0; j < length; j++) {
                    sb.append(charSet.charAt(RANDOM.nextInt(charSet.length())));
                }
                result.append(sb.toString());
                if (i < count - 1) {
                    result.append("\n");
                }
            }

            setOutputText(result.toString());
        } catch (NumberFormatException ex) {
            Messages.showErrorDialog("请输入有效的整数", "无效数字");
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
