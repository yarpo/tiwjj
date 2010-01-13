/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication.rmiclient;

import java.util.Random;

/**
 *
 * @author yarpo
 */
public class Debug {


   public static void main (String args[] )
    {
        RmiClient rmi = new RmiClient("localhost", 3232);
        int team = rmi.joinGame();

        if (-1 == team)
        {
            System.out.println("Nie możesz się dołączyc");
            return;
        }

        while(true)
        {
            if (rmi.isMyTurn())
            {
                System.out.println("To moja tura!");
                System.out.print("\tRuszylem ");
                if (rmi.myMove())
                {
                    System.out.print("to byl dobry ruch\n");
                }
                else
                {
                    System.out.print("nieprawidlowy ruch\n");
                }
            }
            else
            {
                    System.out.println("Nie moj ruch");
            }

            try
            {
                Random r = new Random();

                Thread.sleep(r.nextInt(2300));
            }
            catch(Exception e) {}
            

        }
    }
}
