package tiwjj.user;

/**
 *
 * @author asus
 */
public class UserBean {
    
    private String name;
    private String password;
    private String mail;
    private String hashedPassword;

    /** Creates a new instance of UserBean */
    public UserBean() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   
    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public void setHashedPassword(String p) {
        hashedPassword = p;
    }


    /**
     * Zwraca ciag gwiazdek o dlugosci takiej jak podane haslo
     */
    public String getHashedPassword() {
        int n = password.length();
        hashedPassword = "";

        for(int i=0; i< n; i++)
        {
            hashedPassword += "*";
        }

        return hashedPassword;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String saveData()
    {
        UserSaver uSaver = new UserSaver();
        int result = uSaver.save(name, password, mail);

        if (UserSaver.Result.OK == result)
        {
            return "success";
        }
        return "fail";
    }
}
