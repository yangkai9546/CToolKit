package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

public class MainWindow extends DialogWrapper {

    public MainWindow() {
        super(true);
        setTitle("CToolKit - Developer Toolbox");
        setSize(1500, 1200);
        setModal(false);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel layoutPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JPanel firstRow = new JPanel(new GridLayout(1, 3, 10, 10));
        JPanel secondRow = new JPanel(new GridLayout(1, 3, 10, 10));
        JPanel thirdRow = new JPanel(new GridLayout(1, 3, 10, 10));

        JsonFormatPanel jsonPanel = new JsonFormatPanel();
        UrlPanel urlPanel = new UrlPanel();
        Base64Panel base64Panel = new Base64Panel();
        CryptoPanel cryptoPanel = new CryptoPanel();
        RandomStringPanel randomStringPanel = new RandomStringPanel();
        JwtPanel jwtPanel = new JwtPanel();
        TimestampPanel timestampPanel = new TimestampPanel();
        QrCodePanel qrCodePanel = new QrCodePanel();

        firstRow.add(createStablePanel(jsonPanel, "JSON格式化"));
        firstRow.add(createStablePanel(urlPanel, "URL编解码"));
        firstRow.add(createStablePanel(base64Panel, "Base64编解码"));

        secondRow.add(createStablePanel(cryptoPanel, "加解密"));
        secondRow.add(createStablePanel(randomStringPanel, "随机字符串"));
        secondRow.add(createStablePanel(jwtPanel, "JWT解码"));

        thirdRow.add(createStablePanel(timestampPanel, "时间戳转换"));
        thirdRow.add(createStablePanel(qrCodePanel, "二维码生成器"));
        thirdRow.add(new JPanel());

        layoutPanel.add(firstRow);
        layoutPanel.add(secondRow);
        layoutPanel.add(thirdRow);

        mainPanel.add(layoutPanel, BorderLayout.CENTER);

        // 在窗口真正显示后延迟强制重新布局，解决 GridLayout 初始渲染不完整的问题
        mainPanel.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && mainPanel.isShowing()) {
                    mainPanel.removeHierarchyListener(this);
                    Timer timer = new Timer(200, evt -> {
                        layoutPanel.doLayout();
                        for (Component row : layoutPanel.getComponents()) {
                            if (row instanceof JPanel) {
                                ((JPanel) row).doLayout();
                                for (Component cell : ((JPanel) row).getComponents()) {
                                    cell.doLayout();
                                    cell.repaint();
                                }
                            }
                        }
                        layoutPanel.repaint();
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        });

        return mainPanel;
    }

    private JPanel createStablePanel(BaseToolPanel panel, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder(title));
        panel.setPreferredSize(null);
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }
}
