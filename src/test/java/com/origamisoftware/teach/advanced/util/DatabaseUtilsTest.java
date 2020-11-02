package com.origamisoftware.teach.advanced.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *  Tests for the DatabaseUtils class
 */
public class DatabaseUtilsTest {

    /**
     * Initializes connection using the initializationFile path provided in DatabaseUtils
     * @throws Exception
     */
    @Test
    public void testGoodInitFile() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    /**
     * Initializes connection using an invalid initializationFile
     * @throws Exception
     */
    @Test(expected = DatabaseInitializationException.class)
    public void testBadInitFile() throws Exception {
        DatabaseUtils.initializeDatabase("badInitFile");
    }

    @Test
    public void testGetConnection() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        assertNotNull("verify that we can get a connection ok",connection);
    }

    @Test
    public void testGetConnectionWorks() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from quotes");
        assertTrue("verify that we can execute a statement",execute);
    }


}
