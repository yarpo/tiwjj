/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.rmiserver.datasrc;

/**
 *
 * @author yarpo
 */
public class Main {


    public static void main(String args[])
    {
        IDataSource src = new MySQLSrc();

        boolean r = src.login("yarpo", "yarpo");

        if (r)
        {
            System.out.println("Dziala, zalogowany");
        }
        else
        {
            System.out.println("NIEzalogowany");
        }
    }
}
