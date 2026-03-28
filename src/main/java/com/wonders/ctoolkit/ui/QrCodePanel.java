package com.wonders.ctoolkit.ui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class QrCodePanel extends BaseToolPanel {

    private JLabel imageLabel;
    private static final int QR_SIZE = 250;

    public QrCodePanel() {
        setName("二维码生成器");
    }

    @Override
    protected JComponent createAdditionalComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("尺寸："));

        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(QR_SIZE, 100, 600, 50));
        topPanel.add(sizeSpinner);

        JButton generateBtn = new JButton("生成");
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateQrCode((Integer) sizeSpinner.getValue());
            }
        });
        topPanel.add(generateBtn);

        JButton saveBtn = new JButton("保存图片");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
        topPanel.add(saveBtn);

        return topPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        // 不需要底部按钮，操作按钮放在顶部
        return null;
    }

    @Override
    protected JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("输入"));

        inputTextArea = new JTextArea(3, 40) {
            private final String placeholder = "请输入链接或文字内容，如 https://example.com";

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

        return panel;
    }

    @Override
    protected JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("二维码"));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }

    private void generateQrCode(int size) {
        String text = getInputText().trim();
        if (text.isEmpty()) {
            Messages.showErrorDialog("请输入链接或文字内容", "输入为空");
            return;
        }
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText(null);
        } catch (WriterException ex) {
            Messages.showErrorDialog("生成二维码失败: " + ex.getMessage(), "错误");
        }
    }

    private void saveImage() {
        if (imageLabel.getIcon() == null) {
            Messages.showErrorDialog("请先生成二维码", "无内容");
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("qrcode.png"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG 图片", "png"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage image = (BufferedImage) ((ImageIcon) imageLabel.getIcon()).getImage();
                javax.imageio.ImageIO.write(image, "png", fileChooser.getSelectedFile());
                Messages.showMessageDialog("二维码已保存", "成功", Messages.getInformationIcon());
            } catch (Exception ex) {
                Messages.showErrorDialog("保存失败: " + ex.getMessage(), "错误");
            }
        }
    }
}
