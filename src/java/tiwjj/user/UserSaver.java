package tiwjj.user;

import java.sql.ResultSet;
import tiwjj.db.MySQLConnector;
import tiwjj.db.Settings;

/**
 *
 * @author Patryk Jar
 */
public class UserSaver {

    /**
     * Statyczna klasa ze stalymi - wyniki zwracane przez funkcje
     */
    public static final class Result {
        /**
         * Wszystko poszlo dobrze
         */
        public static final int OK = 1;

        /**
         * Cos poszlo nie tak [blad bazy danych, itp.]
         */
        public static final int FAIL = -1;

        /**
         * Email zduplikowany - nie dodalo
         */
        public static final int DUPLICATED = 0;
    }

    /**
     * Polaczenie z bd
     */
    private MySQLConnector db = new MySQLConnector(Settings.host,
                                                   Settings.user,
                                                   Settings.pass);
    /**
     * Zapisuje uzytkownika do bazym, jesli jest to mozliwe
     *
     * @param UserBean user
     *
     * @return int
     */
    public int save(String name, String password, String mail)
    {
        System.out.println("UserSaver::save");

        if (null == this.db)
        {
            System.out.println("UserSaver::FAIL");
            return Result.FAIL;
        }

        if (this.exists(name))
        {
            System.out.println("UserSaver::DUPLICATED");
            return Result.DUPLICATED;
        }

        return (this.db.addUser(name, password, mail)) ? Result.OK : Result.FAIL;
    }


    /**
     * Sprawdza czy w bd istnieje
     *
     * @param String name
     *
     * @return boolean
     */
    public boolean exists(String name)
    {
        ResultSet rows = db.getUserByName(name);

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
