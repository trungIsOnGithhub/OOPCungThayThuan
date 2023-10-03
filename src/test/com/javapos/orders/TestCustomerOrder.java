package com.javapos.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestCustomerOrder {
  @BeforeClass
  public void beforeTest() {
    System.out.println("Default date format for this test: " + CustomerOrder.dateFormat);
  }

  @Test
  public void testAllArgsConstructorWithFalsyInput() {
    var testObject = new CustomerOrder("wrong date format", "3213", "93210809");
    var defaultObject = new CustomerOrder();

    assertEquals(testObject.toString(), defaultObject.toString());
  }

  @Test
  public void testUtilsIsValiDateWithFalsyInput() {
    assertFalse(CustomerOrder.isValidDate("1/1/2002"));
  }
}
