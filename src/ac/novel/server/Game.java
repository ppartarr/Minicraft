package ac.novel.server;

import ac.novel.common.InitInterface;
import ac.novel.common.InputHandler;
import ac.novel.common.InputHandlerInterface;
import ac.novel.common.SaveInterface;
import ac.novel.common.Save;
import ac.novel.common.UpdateCallback;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Game extends ac.novel.common.Game {
	protected static final long serialVersionUID = 2L;

    static final String NAME = "Minicraft Server";

    private ArrayList<UpdateCallback> callbacks = new ArrayList<UpdateCallback>();

    public static void main(String[] args) {
        Game game = new Game();
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            game.start();
//            SaveInterface saveObj = new Save();
//            InitInterface initObj = new Init();
            SaveInterface saveObj = new GetSave(game);

            int port = 1234;
            // Bind the remote object's stub in the registry
            // Naming.rebind("rmi://localhost:" + port + "/InputHandler", obj);


//            SaveInterface saveStub = (SaveInterface) UnicastRemoteObject.exportObject(saveObj, port);
//            InitInterface initStub = (InitInterface) UnicastRemoteObject.exportObject(initObj, port);
            SaveInterface saveStub = (SaveInterface) UnicastRemoteObject.exportObject(saveObj, port);
            InputHandlerInterface stub = (InputHandlerInterface) UnicastRemoteObject.exportObject(game.input, port);
            Registry reg = LocateRegistry.createRegistry(port);
            System.err.println("Server is ready from main");
            
            reg.rebind("InputHandler", stub);
//            reg.rebind("Save", saveStub);
//            reg.rebind("Init", initStub);
            reg.rebind("GetSave", saveStub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        super.tick();
        Save save = new Save(this);
        ArrayList<UpdateCallback> failedCallbacks = new ArrayList<UpdateCallback>();
        for (UpdateCallback callback : callbacks) {
            try {
                callback.update(save);
            } catch (RemoteException e) {
                failedCallbacks.add(callback);
            }
        }

        for (UpdateCallback callback : failedCallbacks) {
            callbacks.remove(callback);
        }
    }

    public void addCallback(UpdateCallback callback) {
        callbacks.add(callback);
    }
}
