package main;

import entity.Entity;
import entity.Player;
import input.KeyListenerImpl;
import input.MouseListenerImpl;
import level.Level;
import level.Level1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static final int width = (int) (1200 * 1.2);
    public static final int height = (int) (700 * 1.2);


    private static final String TITLE = "NatturaFighting";
    private static final double UPS = 60.0;
    private boolean running = false;
    private int ups, fps = 0;

    private Thread thread;
    private JFrame frame;
    private KeyListenerImpl keyInput;
    private MouseListenerImpl mouseInput;
    private Level currentLevel;

    private Game() {
        frame = new JFrame();

        setPreferredSize(new Dimension(width, height));

        keyInput = new KeyListenerImpl();
        mouseInput = new MouseListenerImpl();
        addKeyListener(keyInput);
        addMouseListener(mouseInput);

        currentLevel = /*new MainMenu(this, keyInput);*/ new Level1(this, new Player(keyInput, mouseInput));
        currentLevel.generate();
    }

    // Game loop
    public void run() {
        long last = System.nanoTime();
        long lastMillis = System.currentTimeMillis();
        final double oneSecInNanoSec = 1000000000.0;
        final double nanoSecPerUpdate = oneSecInNanoSec / UPS;
        double delta = 0;

        int updates = 0;
        int frames = 0;

        requestFocusInWindow();

        while (running) {
            long now = System.nanoTime();
            delta += now - last;
            last = now;

            if (delta >= nanoSecPerUpdate) {
                update();
                updates++;
                delta = 0;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - lastMillis > 1000) {
                lastMillis += 1000;
                ups = updates;
                fps = frames;
                updates = 0;
                frames = 0;
            }
        }
    }

    public void startGame() {
        Level newLevel = new Level1(this, new Player(keyInput, mouseInput));
        newLevel.generate();
        currentLevel = newLevel;
    }


    private void update() {
        checkInput();
        currentLevel.update();
        Entity.allEntities.forEach(Entity::update);
    }

    private void render() {
        if (getBufferStrategy() == null) {
            createBufferStrategy(3);
        }

        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        currentLevel.render(g);
        Entity.allEntities.forEach(e -> e.draw(g));
        drawStrings(g);

        g.dispose();
        bs.show();
    }

    private void drawStrings(Graphics g) {
        g.setColor(Color.white);
        g.drawString(TITLE + " | " + ups + "ups, " + fps + "fps", 800, 50);
    }

    private void checkInput() {
        if (keyInput.esc) {
            frame.dispose();
            System.exit(0);
        }

        keyInput.update();
    }

    private synchronized void start() {
        thread = new Thread(this, "Display");
        thread.start();
        running = true;
    }

    public void stop() {
        running = false;
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setUndecorated(true);
        game.frame.getContentPane().setBackground(Color.BLACK);
        game.frame.getContentPane().setLayout(new GridBagLayout());
        game.frame.getContentPane().add(game, new GridBagConstraints());
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.frame.setVisible(true);
        game.frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) {
        }, new Point(1, 1), "NoCursor"));

        game.start();
    }
}