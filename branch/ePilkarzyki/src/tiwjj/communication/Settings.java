package tiwjj.communication;

/**
 * Statyczna klasa Settings
 * Przechowuje konfiguracje polaczenia
  * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
public class Settings { 

    /**
     * Port na ktorym pracuje serwer
     */
    public static final int     PORT = 4444;


    /**
     * Adres serwera
     */
    public static final String  HOST = "localhost";


    public static final String CODEBASE = "file:/D:/netbeans/ePilkarzyki/build/classes/";


    /**
     * Statyczna klasa zawierajaca dane do szyfrowanego polaczenia SSL
     */
    public static class SSL {


        /**
         * Slowo kluczowe w kluczu prywatnym
         */
        public static final String  KEYWORD    = "keyword";


        /**
         * Slowo kluczowe w kluczu publicznym
         */
        public static final String  TRUSTWORD  = "keyword";


        /**
         * Sciezka do pliku z kluczem publicznym
         */
        public static final String  KEY_File   = ".keystore";


        /**
         * Sciezka do pliku z kluczem prywatnym
         */
        public static final String  TRU_File   = ".truststore";
    }

}
