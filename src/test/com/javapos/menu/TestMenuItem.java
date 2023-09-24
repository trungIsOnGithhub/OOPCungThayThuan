package com.javapos.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestMenuItem {
    @BeforeClass
    public void beforeTest() {
        System.out.println("Testing Class 4 Constructors and toString() method");
    }

    @Test
    public void testConstructor1ToString() {
        var object = new MenuItem();

        Assertions.assertEquals(object.toString(), "undefined 0 VND");
    }

    @Test
    public void testConstructor2ToString() {
        var object = new MenuItem("Name 1234", "696886");

        Assertions.assertEquals(object.toString(), "Name 1234 696886 VND");
    }

    @Test
    public void testConstructor3and4ToString() {
        System.out.println("Invalid Input and 3rd Constructor fallback in this test.");

        var object1 = new MenuItem();
        var object2 = new MenuItem("Name 1234", "696886909090");
        var object3 = new MenuItem(object1)

        Assertions.assertEquals(object2.toString(), object3.toString());
    }
}