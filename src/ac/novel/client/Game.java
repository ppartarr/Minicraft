package ac.novel.client;

import ac.novel.common.InputHandlerInterface;
import ac.novel.common.InputHandler;
import ac.novel.common.screen.TitleMenu;
import ac.novel.client.screen.ConnectMenu;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Game extends ac.novel.common.Game {
    
    static final String NAME = "Novelcraft";
    
    public void start(InputHandlerInterface inputHandlerServerInterface) {
        running = true;
        input = new InputHandlerClient(inputHandlerServerInterface);
        this.addKeyListener(input);
        new Thread(this).start();
    }

    public void startTest() {
        running = true;
        new Thread(this).start();
    }

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

        // RMI
        System.err.println("Client before RMI");
        String reg_host = "localhost";
        int reg_port = 1234;

        game.startTest();
        System.err.println("Game Started");
    }

    public void startController(String reg_host) {
        int reg_port = 1234;
        try {
            Registry registry = LocateRegistry.getRegistry(reg_host,reg_port);
            InputHandlerInterface stub = (InputHandlerInterface) registry.lookup("InputHandler");
            System.err.println("Client got remote InputHandler");

            input = new InputHandlerClient(stub);
            addKeyListener(input);

            setMenu(new TitleMenu());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        super.init();
        ConnectMenu menu = new ConnectMenu();
        setMenu(menu);
        addKeyListener(menu);
    }

    @Override
    public void tick() {
        if (!hasFocus()) {
            input.releaseAll();
        }
        input.updateClients();
        super.tick();
    }
}
