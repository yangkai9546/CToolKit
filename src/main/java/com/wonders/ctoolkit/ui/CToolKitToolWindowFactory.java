package com.wonders.ctoolkit.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

// Tool window factory for CToolKit plugin
public class CToolKitToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // Create the main panel for the tool window
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create tabbed pane with SCROLL layout to prevent tab wrapping and jumping
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        // Create panels
        JsonFormatPanel jsonPanel = new JsonFormatPanel();
        UrlPanel urlPanel = new UrlPanel();
        Base64Panel base64Panel = new Base64Panel();
        CryptoPanel cryptoPanel = new CryptoPanel();
        RandomStringPanel randomStringPanel = new RandomStringPanel();
        JwtPanel jwtPanel = new JwtPanel();
        TimestampPanel timestampPanel = new TimestampPanel();
        QrCodePanel qrCodePanel = new QrCodePanel();

        // Add tabs for each tool with Chinese titles
        tabbedPane.addTab("JSON格式化", jsonPanel);
        tabbedPane.addTab("URL编解码", urlPanel);
        tabbedPane.addTab("Base64编解码", base64Panel);
        tabbedPane.addTab("加解密", cryptoPanel);
        tabbedPane.addTab("随机字符串", randomStringPanel);
        tabbedPane.addTab("JWT解码", jwtPanel);
        tabbedPane.addTab("时间戳转换", timestampPanel);
        tabbedPane.addTab("二维码生成器", qrCodePanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Create content and add it to the tool window
        Content content = ContentFactory.getInstance().createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}