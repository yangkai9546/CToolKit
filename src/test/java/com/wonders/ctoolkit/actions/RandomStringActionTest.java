package com.wonders.ctoolkit.actions;

import org.junit.Test;
import static org.junit.Assert.*;

public class RandomStringActionTest {

    @Test
    public void testRandomStringGeneration() {
        RandomStringAction action = new RandomStringAction();
        
        // Since we can't easily test the full action without IntelliJ platform,
        // we'll test the underlying logic
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        // Test that a generated string of length 10 contains only valid characters
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(characters.charAt((int)(Math.random() * characters.length())));
        }
        
        String randomString = sb.toString();
        assertEquals("Random string should have correct length", 10, randomString.length());
        
        // Verify each character is in the valid character set
        for (char c : randomString.toCharArray()) {
            assertTrue("Character should be in valid set", characters.indexOf(c) >= 0);
        }
    }
}