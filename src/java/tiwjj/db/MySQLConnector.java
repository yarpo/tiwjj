/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.db;


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

    /**
     * Zabezpieczenie przed SQLinjection
     *
     * @param String q
     *
     * return String
     */
    private String escape(String q)
    {
        String cleanSQL = q.replace("'", "\\'");
        cleanSQL = q.replace("\"", "\\\"");

        return cleanSQL;
    }

    /**
     * Wykonuje zapytanie do bd
     *
     * @param String q
     *
     * @return ResultSet
     */
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

    /**
     * Dodaje nowego usera do bd
     *
     * @param name
     * @param password
     * @param mail
     *
     * @return boolean
     */
    public boolean addUser(String name, String password, String mail)
    {
        int result = 0;
        try
        {
            result = this.statement.executeUpdate(
                "INSERT INTO users VALUES(" +
                "'" + this.escape(name) + "', " +
                "'" + this.escape(password) + "')");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return (result > 0);
    }

    /**
     * Pobiera uzytkownika o zadanym loginie i hasle
     *
     * @param name
     * @param password
     *
     * @return ResutlSet
     */
    public ResultSet getUser(String name, String pass)
    {
        ResultSet result =
            this.query(
                "SELECT name, password " +
                "FROM " +
                "   users " +
                "WHERE " +
                "   name = '" + this.escape(name) + "'" +
                "AND " +
                "   password = '" + this.escape(pass) +"'");

        return result;
    }

    /**
     * Pobiera jednego usera o konretnym loginie
     *
     * @param name
     *
     * @return ResultSet
     */
    public ResultSet getUserByName(String name)
    {
        ResultSet result =
            this.query(
                "SELECT name, password " +
                "FROM " +
                "   users " +
                "WHERE " +
                "   name = '" + this.escape(name) + "'");

        return result;
    }
}
