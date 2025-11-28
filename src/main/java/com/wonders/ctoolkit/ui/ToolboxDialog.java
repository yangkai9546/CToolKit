package com.wonders.ctoolkit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolboxDialog extends DialogWrapper {
    
    public ToolboxDialog() {
        super(true);
        setTitle("CToolKit Toolbox");
        setSize(400, 300);
        setModal(true);
        init();
    }
    
    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton jsonButton = new JButton("JSON格式化");
        jsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                JsonFormatDialog dialog = new JsonFormatDialog();
                dialog.show();
            }
        });
        
        JButton urlButton = new JButton("URL编解码");
        urlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                UrlDialog dialog = new UrlDialog();
                dialog.show();
            }
        });
        
        JButton base64Button = new JButton("Base64编解码");
        base64Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                Base64Dialog dialog = new Base64Dialog();
                dialog.show();
            }
        });
        
        JButton cryptoButton = new JButton("加解密");
        cryptoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                CryptoDialog dialog = new CryptoDialog();
                dialog.show();
            }
        });
        
        JButton randomButton = new JButton("随机字符串");
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                RandomStringDialog dialog = new RandomStringDialog();
                dialog.show();
            }
        });
        
        JButton jwtButton = new JButton("JWT解码");
        jwtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(DialogWrapper.OK_EXIT_CODE);
                JwtDialog dialog = new JwtDialog();
                dialog.show();
            }
        });
        
        panel.add(jsonButton);
        panel.add(urlButton);
        panel.add(base64Button);
        panel.add(cryptoButton);
        panel.add(randomButton);
        panel.add(jwtButton);
        
        return panel;
    }
}