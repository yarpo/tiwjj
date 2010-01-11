/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

/**
 *
 * @author yarpo
 */
public class Settings {

    public static final int     PORT = 4444;
    public static final String  HOST = "localhost";

    public static class SSL {
        public static final String  KEYWORD    = "keyword";
        public static final String  TRUSTWORD  = "keyword";
        public static final String  KEY_File   = ".keystore";
        public static final String  TRU_File   = ".truststore";
    }

}
