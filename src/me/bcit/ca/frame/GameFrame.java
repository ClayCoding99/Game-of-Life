package me.bcit.ca.frame;

import me.bcit.ca.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class GameFrame extends JFrame {

    public static final int WIDTH = 875;
    public static final int HEIGHT = 875;

    private final World world;
    private final Color bgColor;

    public GameFrame(World world) {
        this.world = world;
        this.bgColor = Color.WHITE;
    }

    public void init() {
        this.setTitle("Game of Life");
        this.addMouseListener(new TurnListener());
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(world.getWorldPanel());
        this.setBackground(bgColor);
        this.setVisible(true);
    }

    public void takeTurn() {
        world.update();
        world.display();
    }

    private class TurnListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2) {
                takeTurn();
            }
        }

    }

}