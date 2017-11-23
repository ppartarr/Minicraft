package ac.novel.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateCallback extends Remote {
    public void update(Save state) throws RemoteException;
}
