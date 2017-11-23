package ac.novel.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ac.novel.common.Game;
import ac.novel.common.Save;
import ac.novel.common.InputHandler;

/**
 * This is a callback object for updating the game state with a message from
 * the server.
 */
public class UpdateCallback extends UnicastRemoteObject implements ac.novel.common.UpdateCallback {
    transient private Game game;
    transient private InputHandler inputHandler;

    UpdateCallback(Game game) throws RemoteException {
        this.game = game;
        this.inputHandler = game.input;
    }

    @Override
    public void update(Save state) throws RemoteException {
        game.gameTime = state.gameTime;
        game.hasWon = state.hasWon;

        game.levels = state.levels;
        game.currentLevel = state.currentLevel;

        game.level = game.levels[game.currentLevel];
        game.player = state.player;
        game.player.game = game;
        game.player.input = inputHandler;
        System.out.println(game);
        System.out.println(inputHandler);
    }

    @Override
    public void hello() throws RemoteException {
        System.out.println("HELLLLLLLLLOOOOOOOOOOOOO");
    }
}