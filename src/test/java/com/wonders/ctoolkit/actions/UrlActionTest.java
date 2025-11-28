package com.wonders.ctoolkit.actions;

import org.junit.Test;
import static org.junit.Assert.*;

public class UrlActionTest {

    @Test
    public void testUrlEncodeDecode() throws Exception {
        String originalText = "Hello World! https://example.com";
        
        // Test URL encoding
        String encoded = java.net.URLEncoder.encode(originalText, "UTF-8");
        assertEquals("Encoded URL should match expected", 
            "Hello+World%21+https%3A%2F%2Fexample.com", encoded);
        
        // Test URL decoding
        String decoded = java.net.URLDecoder.decode(encoded, "UTF-8");
        assertEquals("Decoded URL should match original", originalText, decoded);
    }
}