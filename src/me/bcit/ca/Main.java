package me.bcit.ca;

import me.bcit.ca.frame.GameFrame;
import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.World2D;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Driver class for the Game of Life program.
 */
public final class Main {

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;

    private static final Toolkit TOOLKIT;

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
    }

    private Main() {}

    /**
     * Entry point to the program. Here we initialize the world and set up the window
     * @param args not used
     */
    public static void main(final String[] args) {
        final GameFrame frame;
        final World2D world;
        RandomGenerator.reset();
        world = new World2D(WORLD_WIDTH, WORLD_HEIGHT);
        world.init();
        frame = new GameFrame(world);
        position(frame);
        frame.init();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void position(final GameFrame frame) {
        final Dimension size;
        size = calculateScreenArea(0.80f, 0.80f);
        frame.setSize(size);
        frame.setLocation(centreOnScreen(size));
    }

    public static Point centreOnScreen(final Dimension size) {
        final Dimension screenSize;

        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }

        screenSize = TOOLKIT.getScreenSize();

        return new Point((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
    }

    public static Dimension calculateScreenArea(final float widthPercent, final float heightPercent) {
        final Dimension screenSize;
        final Dimension area;
        final int width;
        final int height;
        final int size;

        if ((widthPercent <= 0.0f) || (widthPercent > 100.0f)) {
            throw new IllegalArgumentException("widthPercent cannot be " +
                    "<= 0 or > 100 - got: " +
                    widthPercent);
        }

        if ((heightPercent <= 0.0f) || (heightPercent > 100.0f)) {
            throw new IllegalArgumentException("heightPercent cannot be " +
                    "<= 0 or > 100 - got: " +
                    heightPercent);
        }

        screenSize = TOOLKIT.getScreenSize();
        width = (int) (screenSize.width * widthPercent);
        height = (int) (screenSize.height * heightPercent);
        size = Math.min(width, height);
        area = new Dimension(size, size);

        return area;
    }

}