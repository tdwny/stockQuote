package com.origamisoftware.teach.advanced.util;

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that contains database related utility methods.
 */
public class DatabaseUtils {

    /**
     * Used to create the quotes table
     */
    public static final String initializationFile = "./src/main/sql/stocks_db_initialization.sql" ;

    // in a real program these values would be a configurable property and not hard coded.
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks?characterEncoding=utf8";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "password";

    /**
     * Establishes a connection to the database used the JDBC driver name, database url and credentials defined in the class variables
     * @return
     * @throws DatabaseConnectionException
     */
    public static Connection getConnection() throws DatabaseConnectionException{
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection =   DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException  | SQLException e)  {
           throw new  DatabaseConnectionException("Could not connection to database." + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * A utility method that runs a db initialize script.
     * @param initializationScript    full path to the script to run to create the schema
     * @throws DatabaseInitializationException
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new  FileInputStream(initializationScript);

            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {
           throw new DatabaseInitializationException("Could not initialize db because of:"
                   + e.getMessage(),e);
        }


    }
}
