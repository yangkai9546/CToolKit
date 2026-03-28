package com.wonders.ctoolkit.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampPanel extends BaseToolPanel {

    private static final long MILLIS_THRESHOLD = 10_000_000_000L;

    private static final String[][] TIMEZONE_OPTIONS = {
            {"Asia/Shanghai", "中国标准时间 (UTC+8)"},
            {"UTC", "UTC"},
            {"America/New_York", "美国东部 (UTC-5/-4)"},
            {"America/Los_Angeles", "美国西部 (UTC-8/-7)"},
            {"Europe/London", "英国 (UTC+0/+1)"},
            {"Europe/Berlin", "德国 (UTC+1/+2)"},
            {"Asia/Tokyo", "日本 (UTC+9)"},
            {"Australia/Sydney", "澳大利亚 (UTC+10/+11)"},
            {"Asia/Kolkata", "印度 (UTC+5:30)"},
            {"Asia/Singapore", "新加坡 (UTC+8)"}
    };

    private JComboBox<String> timezoneCombo;

    public TimestampPanel() {
        setName("时间戳转换");
    }

    @Override
    protected JComponent createAdditionalComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("时区："));

        String[] displayNames = new String[TIMEZONE_OPTIONS.length];
        for (int i = 0; i < TIMEZONE_OPTIONS.length; i++) {
            displayNames[i] = TIMEZONE_OPTIONS[i][1];
        }
        timezoneCombo = new JComboBox<>(displayNames);
        timezoneCombo.setSelectedIndex(0);
        timezoneCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoConvert();
            }
        });
        topPanel.add(timezoneCombo);

        return topPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton currentTimestampButton = new JButton("当前时间戳");
        currentTimestampButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCurrentTimestamp();
            }
        });

        buttonPanel.add(currentTimestampButton);
        return buttonPanel;
    }

    @Override
    protected JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("输入"));

        inputTextArea = new JTextArea(5, 40) {
            private final String placeholder = "请输入时间戳，支持秒级（如 1743142932）或毫秒级（如 1743142932123），自动识别";

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(UIManager.getColor("textInactiveText"));
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int y = fm.getAscent() + fm.getLeading();
                    for (String line : placeholder.split("，")) {
                        g2d.drawString(line, getInsets().left + 2, y);
                        y += fm.getHeight();
                    }
                }
            }
        };
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);

        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                autoConvert();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                autoConvert();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                autoConvert();
            }
        });
        return panel;
    }

    private void autoConvert() {
        String input = inputTextArea.getText().trim();
        if (input.isEmpty()) {
            setOutputText("");
            return;
        }
        try {
            long value = Long.parseLong(input);
            long millis;
            long seconds;
            String type;
            if (value >= MILLIS_THRESHOLD) {
                millis = value;
                seconds = value / 1000;
                type = "毫秒";
            } else {
                seconds = value;
                millis = value * 1000;
                type = "秒";
            }
            Instant instant = Instant.ofEpochMilli(millis);
            ZonedDateTime zdt = instant.atZone(ZoneId.of(getSelectedZoneId()));
            setOutputText(formatOutput(zdt, seconds, millis, type));
        } catch (NumberFormatException ex) {
            setOutputText("请输入有效的时间戳数字");
        }
    }

    private void generateCurrentTimestamp() {
        long millis = System.currentTimeMillis();
        long seconds = millis / 1000;
        Instant instant = Instant.ofEpochMilli(millis);
        ZonedDateTime zdt = instant.atZone(ZoneId.of(getSelectedZoneId()));
        setOutputText(formatOutput(zdt, seconds, millis, ""));
    }

    private String getSelectedZoneId() {
        int index = timezoneCombo.getSelectedIndex();
        return TIMEZONE_OPTIONS[index][0];
    }

    private String formatOutput(ZonedDateTime zdt, long seconds, long millis, String detectedType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterWithZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss (z)");

        StringBuilder sb = new StringBuilder();
        if (!detectedType.isEmpty()) {
            sb.append("识别为：").append(detectedType).append("级时间戳\n");
        }
        sb.append("日期时间：").append(zdt.format(formatter)).append("\n");
        sb.append("带时区：").append(zdt.format(formatterWithZone)).append("\n");
        sb.append("秒级时间戳：").append(seconds).append("\n");
        sb.append("毫秒时间戳：").append(millis);
        return sb.toString();
    }
}
