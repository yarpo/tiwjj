/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.rmiserver;

/**
 *
 * @author yarpo
 */
public class Main {
    
    static public void main(String args[])
    {
        try
        {
            new RmiServer();
        }
        catch (Exception e)
        {
           e.printStackTrace();
           System.exit(1);
        }
    }
}
