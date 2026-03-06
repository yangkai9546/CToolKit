package com.wonders.ctoolkit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utility class to generate plugin icons.
 */
public class IconGenerator {

    private static final int ICON_40_SIZE = 40;
    private static final int ICON_80_SIZE = 80;

    public static void main(String[] args) {
        try {
            // Get the resources directory path
            String resourcesPath = "src/main/resources/META-INF/icons";
            File iconsDir = new File(resourcesPath);
            if (!iconsDir.exists()) {
                iconsDir.mkdirs();
            }

            // Generate icons
            generateIcon(new File(iconsDir, "ctoolkit13x13.png"), 13, false);
            generateIcon(new File(iconsDir, "ctoolkit40x40.png"), 40, false);
            generateIcon(new File(iconsDir, "ctoolkit80x80.png"), 80, false);

            // Generate plugin icon at root level (13x13 for Dark Theme)
            generateIcon(new File(iconsDir, "pluginIcon_dark.png"), 13, true);
            generateIcon(new File(iconsDir, "pluginIcon.png"), 13, false);

            System.out.println("Icons generated successfully!");
            System.out.println("Location: " + iconsDir.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a rounded square icon with "CT" text.
     *
     * @param file      Output file
     * @param size      Icon size in pixels
     * @param isDark    Whether this is a dark theme icon (white text)
     */
    private static void generateIcon(File file, int size, boolean isDark) throws IOException {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background - gradient from blue to darker blue
        int padding = size / 10;
        int arcSize = size / 5;

        GradientPaint gradient = new GradientPaint(
            0, 0,
            new Color(66, 165, 245),  // Light blue
            size, size,
            new Color(33, 150, 243)   // Darker blue
        );

        g2d.setPaint(gradient);
        g2d.fill(new RoundRectangle2D.Double(0, 0, size, size, arcSize, arcSize));

        // Inner border
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.setStroke(new BasicStroke(size / 40f));
        g2d.draw(new RoundRectangle2D.Double(padding, padding, size - 2 * padding, size - 2 * padding, arcSize, arcSize));

        // Draw "CT" text
        String text = "CT";
        Font font = new Font("Arial", Font.BOLD, (int) (size * 0.45));
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics();

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int textX = (size - textWidth) / 2;
        int textY = (size - textHeight) / 2 + fm.getAscent();

        g2d.drawString(text, textX, textY);

        // Add a small highlight at the top
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Double(
            padding * 1.5, padding * 1.5,
            size - 3 * padding, size / 3,
            arcSize, arcSize
        ));

        g2d.dispose();
        ImageIO.write(image, "PNG", file);
    }
}
