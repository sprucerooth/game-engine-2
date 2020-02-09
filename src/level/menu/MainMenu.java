package level.menu;

import input.KeyListenerImpl;
import level.Level;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

public class MainMenu implements Level {

    private Game game;
    private ArrayList<MenuItem> menuItems;
    private KeyListenerImpl keyListener;
    private int selected;

    public MainMenu(final Game game, final KeyListenerImpl keyListener) {
        this.game = game;
        this.keyListener = keyListener;
    }

    @Override
    public void generate() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Start Game", game::startGame));
        menuItems.add(new MenuItem("Quit", game::stop));
        selected = 0;

    }

    @Override
    public void update() {
        if (keyListener.down) {
            selected = (selected == menuItems.size() - 1) ? 0 : selected + 1;
        }
        if (keyListener.up) selected = (selected == 0) ? menuItems.size() - 1 : selected - 1;
        if (keyListener.space) menuItems.get(selected).getAction().go();

    }

    public void render(Graphics g) {
        g.setColor(new Color(0x0101010));
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        int y = 800;
        for (MenuItem item : menuItems) {
            if (item == menuItems.get(selected)) g.setColor(Color.GREEN);
            else g.setColor(Color.DARK_GRAY);
            g.drawString(item.getName(), 500, y);
            y += 20;
        }
    }

}
