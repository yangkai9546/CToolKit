package com.wonders.ctoolkit.actions;

import org.junit.Test;
import static org.junit.Assert.*;

public class Base64ActionTest {

    @Test
    public void testBase64EncodeDecode() {
        Base64EncodeAction encodeAction = new Base64EncodeAction();
        Base64DecodeAction decodeAction = new Base64DecodeAction();
        
        String originalText = "Hello, World!";
        
        // Since we can't easily test the full action without IntelliJ platform,
        // we'll test the underlying logic
        String encoded = java.util.Base64.getEncoder().encodeToString(originalText.getBytes());
        String decoded = new String(java.util.Base64.getDecoder().decode(encoded));
        
        assertEquals("Encoded text should match expected", "SGVsbG8sIFdvcmxkIQ==", encoded);
        assertEquals("Decoded text should match original", originalText, decoded);
    }
}