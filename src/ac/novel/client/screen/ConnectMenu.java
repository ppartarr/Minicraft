package ac.novel.client.screen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ac.novel.common.gfx.Color;
import ac.novel.common.gfx.Font;
import ac.novel.common.screen.Menu;
import ac.novel.common.gfx.Screen;
import ac.novel.common.sound.Sound;

public class ConnectMenu extends Menu implements KeyListener {
    private String hostname = "";

    public ConnectMenu() {
    }

    @Override
    public void render(Screen screen) {
        screen.clear(0);

        int x = calculateX();
        int y = 60;
        int color = Color.get(-1, 000, 000, 555);

        Font.draw("Enter the address of", screen, 0, y - 20, color);
        Font.draw("the server:", screen, 36, y - 10, color);

        Font.draw("< " + hostname + " >", screen, x, y, color);

        Font.draw("Press <Enter>", screen, 28, y + 30, color);
        Font.draw("when done", screen, 44, y + 40, color);
    }

    private int calculateX() {
       if (hostname.length() < 16) {
           return (64 - 4 * hostname.length());
       } else {
           return (-8 * (hostname.length() - 16));
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 8) { // BACKSPACE
            if (hostname.isEmpty()) {
                return;
            }

            hostname = hostname.substring(0, hostname.length() - 1);
        } else if (c == 10) { // ENTER
            if (game instanceof ac.novel.client.Game) {
                ac.novel.client.Game clientGame = (ac.novel.client.Game) game;
                clientGame.startController(hostname);
            }
        } else {
            hostname += c;
        }
    }
}
