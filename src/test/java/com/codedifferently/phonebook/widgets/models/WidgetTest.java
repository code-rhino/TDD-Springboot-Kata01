package com.codedifferently.phonebook.widgets.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WidgetTest {
    private Widget widget1;
    private Widget widget2;

    private Widget emptyWidget1;
    private Widget emptyWidget2;
    
    @BeforeEach
    public void setUp(){
        emptyWidget1 = new Widget();
        emptyWidget2 = new Widget();
        
        List<WidgetPart> parts = new ArrayList<>();
        parts.add(new WidgetPart("Part 1"));
        parts.add(new WidgetPart("Part 2"));

        widget1 = new Widget("Test Widget 01", parts);
        widget1.setId(1);

        widget2 = new Widget("Test Widget 02", parts);
        widget2.setId(2);
    }

    @Test
    public void testEmptyEquals() throws Exception {

        assertTrue(
                emptyWidget1.equals(emptyWidget2),
                "Both empty Widget instances should be equal");
    }

    @Test
    public void testContentEquals() throws Exception {

        assertTrue(
                widget1.equals(widget2),
                "Both non-empty Widget instances should be equal");
    }

    @Test
    public void testNotEquals() throws Exception {

        assertFalse(
                emptyWidget1.equals(widget2),
                "The Widget instances should not be equal");
    }

    @Test
    public void testEmptyHashCode() throws Exception {

        assertEquals(
                emptyWidget1.hashCode(),
                emptyWidget2.hashCode(),
                "Both empty Widget instances should have the same hashCode");
    }

    @Test
    public void testContentHashCode() throws Exception {

        assertEquals(
                widget1.hashCode(),
                widget2.hashCode(),
                "Both non-empty Widget instances should have the same hashCode");
    }

    @Test
    public void testHashCode() throws Exception {

        assertNotEquals(
                emptyWidget1.hashCode(),
                widget2.hashCode(),
                "The Widget instances should not have the same hashCode");
    }

    @Test
    public void testEmptyToString() throws Exception {

        assertEquals(
                emptyWidget1.toString(),
                emptyWidget2.toString(),
                "Both empty Widget instances should have the same toString");
    }

    @Test
    public void testContentToString() throws Exception {

        assertEquals(
                widget1.toString(),
                widget2.toString(),
                "Both non-empty Widget instances should have the same toString");
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyWidget1.toString(),
                widget2.toString(),
                "The Widget instances should not have the same toString");
    }
}
