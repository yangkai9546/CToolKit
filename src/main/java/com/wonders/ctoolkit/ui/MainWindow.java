package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends DialogWrapper {

    public MainWindow() {
        super(true);
        setTitle("CToolKit - Developer Toolbox");
        setSize(1500, 800); // Size for balanced 3x2 grid
        setModal(false);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the main layout panel with GridLayout for a clean two-row layout
        JPanel layoutPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column with gaps

        // First row panel (3 panels: JSON, URL, Base64)
        JPanel firstRow = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns

        // Second row panel (3 panels: Crypto, Random String, JWT)
        JPanel secondRow = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns

        // Create panels
        JsonFormatPanel jsonPanel = new JsonFormatPanel();
        UrlPanel urlPanel = new UrlPanel();
        Base64Panel base64Panel = new Base64Panel();
        CryptoPanel cryptoPanel = new CryptoPanel();
        RandomStringPanel randomStringPanel = new RandomStringPanel();
        JwtPanel jwtPanel = new JwtPanel();

        // Add panels to first row
        firstRow.add(createStablePanel(jsonPanel, "JSON格式化"));
        firstRow.add(createStablePanel(urlPanel, "URL编解码"));
        firstRow.add(createStablePanel(base64Panel, "Base64编解码"));

        // Add panels to second row
        secondRow.add(createStablePanel(cryptoPanel, "加解密"));
        secondRow.add(createStablePanel(randomStringPanel, "随机字符串"));
        secondRow.add(createStablePanel(jwtPanel, "JWT解码"));

        // Add rows to main layout
        layoutPanel.add(firstRow);
        layoutPanel.add(secondRow);

        // Add the layout panel to the main panel
        mainPanel.add(layoutPanel, BorderLayout.CENTER);

        // Force validation and repaint to ensure proper initial rendering
        SwingUtilities.invokeLater(() -> {
            layoutPanel.validate();
            layoutPanel.repaint();
        });

        return mainPanel;
    }

    /**
     * Wraps the panel in a container that ensures stable layout
     */
    private JPanel createStablePanel(BaseToolPanel panel, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder(title));
        // Remove fixed preferred size to allow flexible sizing within the grid
        panel.setPreferredSize(null);
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }
}
