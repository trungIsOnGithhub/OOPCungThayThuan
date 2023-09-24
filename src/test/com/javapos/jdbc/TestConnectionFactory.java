package com.javapos.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class TestConnectionFactory {
    @BeforeClass
    public void beforeTest() {
        System.out.println("Testing JDBC connection!!, 1 of 2 will fail in both case JDBC available or not!");
    }

    @Test
    public void testSQLExceptionThrown() {
        SQLException exception = Assertions.assertThrows(SQLException.class, ConnectionFactory.getConnection());
        assertTrue(e instanceof SQLException);
    }

    @Test
    public void testSuccessfulConnect() {
        Assertions.assertNotNull(ConnectionFactory.getConnection());
    }
}