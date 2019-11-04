import entity.Entity;
import entity.Player;
import input.KeyboardListener;
import level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private static final String GAME_TITLE = "NatturaFighting";
    private boolean running = false;
    private int ups, fps = 0;

    private Thread thread;
    private JFrame frame;
    private KeyboardListener input;
    private Player player;

    private Game() {
        frame = new JFrame();
        input = new KeyboardListener();

        player = new Player(input, 0, 0, 50, 50, Color.RED, null);
        new Level(player).generateLevel();

        addKeyListener(input);
    }

    private synchronized void start() {
        thread = new Thread(this, "Display");
        thread.start();
        running = true;
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta = 0;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                ups = updates;
                fps = frames;
                updates = 0;
                frames = 0;
            }
        }
    }

    private void update() {
        checkInput();
        Entity.allEntities.forEach(Entity::update);
    }

    private void render() {
        if (getBufferStrategy() == null) {
            createBufferStrategy(3);
        }

        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        fillBackground(g);
        Entity.allEntities.forEach(e -> e.draw(g));
        drawStrings(g);

        g.dispose();
        bs.show();
    }

    private void fillBackground(Graphics g) {
        g.setColor(new Color(0x0101010));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawStrings(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Pos x: " + player.x, 50, 50);
        g.drawString("Pos y: " + player.y, 50, 70);
        g.drawString(GAME_TITLE + " | " + ups + "ups, " + fps + "fps", 800, 50);
    }

    private void checkInput() {
        if (input.esc) {
            frame.dispose();
            System.exit(0);
        }
        input.update();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.add(game);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.frame.setUndecorated(true);
        game.frame.setVisible(true);
        game.frame.requestFocus();

        game.start();
    }
}
