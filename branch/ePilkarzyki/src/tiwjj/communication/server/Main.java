/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.server;

/**
 *
 * @author yarpo
 */
public class Main {
    public static void main(String args[]) throws Exception
    {
        int port = 4444;
        int clients = 2;
        SecureServer Server = new  SecureServer(port);
        try
        {
            Server.listen(clients);
        }
        catch(Exception e)
        {
            System.out.println("Koniec zabawy");
        }
        
    }
}
