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
    
    // Keep a reference to the tabbedPane so we can access it in ensureProperRendering
    private JTabbedPane tabbedPane;
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // Create the main panel for the tool window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Create tabbed pane with all tools
        tabbedPane = new JTabbedPane() {
            // Override to prevent recursive layout calls
            @Override
            public void doLayout() {
                try {
                    super.doLayout();
                } catch (StackOverflowError e) {
                    // Prevent stack overflow by skipping layout in problematic cases
                    System.err.println("Prevented StackOverflowError in tabbed pane layout");
                }
            }
        };
        
        // Set tab layout policy to wrap to prevent layout issues
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        // Additional measures to prevent recursive layout updates
        tabbedPane.putClientProperty("JTabbedPane.disableAutoListener", Boolean.TRUE);
        tabbedPane.putClientProperty("validationStrategy", "manual");
        // Set a fixed font to prevent font metrics related issues
        tabbedPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        
        // Create panels first
        JsonFormatPanel jsonPanel = new JsonFormatPanel();
        UrlPanel urlPanel = new UrlPanel();
        Base64Panel base64Panel = new Base64Panel();
        CryptoPanel cryptoPanel = new CryptoPanel();
        RandomStringPanel randomStringPanel = new RandomStringPanel();
        JwtPanel jwtPanel = new JwtPanel();
        
        // Add tabs for each tool with Chinese titles
        tabbedPane.addTab("JSON格式化", createStablePanel(jsonPanel));
        tabbedPane.addTab("URL编解码", createStablePanel(urlPanel));
        tabbedPane.addTab("Base64编解码", createStablePanel(base64Panel));
        tabbedPane.addTab("加解密", createStablePanel(cryptoPanel));
        tabbedPane.addTab("随机字符串", createStablePanel(randomStringPanel));
        tabbedPane.addTab("JWT解码", createStablePanel(jwtPanel));
        
        // Ensure the first tab is properly selected
        tabbedPane.setSelectedIndex(0);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create content and add it to the tool window
        Content content = ContentFactory.getInstance().createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
        
        // Schedule the rendering fix
        SwingUtilities.invokeLater(this::ensureProperRendering);
    }
    
    /**
     * Ensures proper rendering of the tabbed pane and its components
     */
    private void ensureProperRendering() {
        if (tabbedPane != null) {
            // Small delay to ensure UI is ready
            SwingUtilities.invokeLater(() -> {
                // Revalidate and repaint the entire tabbed pane
                tabbedPane.revalidate();
                tabbedPane.repaint();
                
                // Specifically ensure the first tab is properly rendered
                if (tabbedPane.getTabCount() > 0) {
                    // Get the component of the first tab
                    Component firstTabComponent = tabbedPane.getComponentAt(0);
                    if (firstTabComponent != null) {
                        firstTabComponent.revalidate();
                        firstTabComponent.repaint();
                    }
                    
                    // Also validate the selected tab
                    Component selectedTabComponent = tabbedPane.getSelectedComponent();
                    if (selectedTabComponent != null && selectedTabComponent != firstTabComponent) {
                        selectedTabComponent.revalidate();
                        selectedTabComponent.repaint();
                    }
                }
                
                // Force the tabbed pane to recalculate its layout
                tabbedPane.doLayout();
            });
        }
    }
    
    /**
     * Wraps the panel in a container that ensures stable layout when switching tabs
     */
    private JPanel createStablePanel(BaseToolPanel panel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(800, 600));
        // Ensure the panel can resize properly within the wrapper
        panel.setPreferredSize(null); // Remove fixed preferred size for tabbed interface
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }
}