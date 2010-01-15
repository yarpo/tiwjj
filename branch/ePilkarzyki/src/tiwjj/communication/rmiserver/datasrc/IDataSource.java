package tiwjj.communication.rmiserver.datasrc;

/**
 *
 * @author yarpo
 */
public interface IDataSource {
    public boolean login(String user, String password);
}
