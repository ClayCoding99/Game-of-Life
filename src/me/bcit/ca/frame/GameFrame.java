package me.bcit.ca.frame;

import me.bcit.ca.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Used as a window for the Game of Life.
 */
public final class GameFrame extends JFrame {

    public static final int WIDTH = 875;
    public static final int HEIGHT = 875;

    private final World world;
    private final Color bgColor;

    public GameFrame(final World world) {
        this.world = world;
        this.bgColor = Color.WHITE;
    }

    /**
     * Initializes and add the panel made up of the world to the frame.
     */
    public void init() {
        this.setTitle("Game of Life");
        this.addMouseListener(new TurnListener());
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(world.getWorldPanel());
        this.setBackground(bgColor);
        this.setVisible(true);
    }

    /**
     * Updates the world and then re-renders it.
     */
    public void takeTurn() {
        world.update();
        world.display();
    }

    /**
     * This class is a mouse listener for the Game of Life. It is added to the window and will wait for the mouse
     * to be clicked. Upon clicking the mouse, the takeTurn method will be called.
     */
    private class TurnListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2) {
                takeTurn();
            }
        }

    }

}