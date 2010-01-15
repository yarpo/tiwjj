/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.rmiserver.datasrc.db.mysql;

import java.sql.*;
import tiwjj.communication.rmiserver.datasrc.IDataSource;
/**
 *
 * @author yarpo
 */
public class MySQLSrc implements IDataSource {

    private MySQLConnector db = new MySQLConnector(Settings.host, 
                                                   Settings.user,
                                                   Settings.pass);

    public MySQLSrc() { }

    public boolean login(String user, String password)
    {
        ResultSet rows = db.getUser(user, password);

        boolean result = false;
        try
        {
            result = rows.first();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

}
