/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.rmiserver.datasrc;


import java.sql.*;
/**
 *
 * @author yarpo
 */
public class MySQLConnector {

    private Connection connection;
    private Statement statement;

    public MySQLConnector(String url, String user, String pass)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection (url, user, pass);
            this.statement = this.connection.createStatement();
        }
        catch (Exception e)
        {
            System.out.println("Blad przy tworzeniu polaczenia z bd" + e);
            return;
        }
    }


    private ResultSet query(String q)
    {
        ResultSet result = null;
        try
        {
            result = this.statement.executeQuery(q);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet getUser(String name, String pass)
    {
        ResultSet result =
            this.query(
                "SELECT name, password " +
                "FROM " +
                "   users " +
                "WHERE " +
                "   name = '" + name + "'" +
                "AND " +
                "   password = '" + pass +"';");
        
        return result;
    }
}
