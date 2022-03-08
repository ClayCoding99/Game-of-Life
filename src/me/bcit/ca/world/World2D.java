package me.bcit.ca.world;

import me.bcit.ca.entities.*;
import me.bcit.ca.util.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used hold and conduct the world logic.
 */
public class World2D implements World {

    public static final int HERBIVORE_SPAWN = 80;
    public static final int PLANT_SPAWN = 60;
    public static final int OMNIVORE_SPAWN = 45;
    public static final int CARNIVORE_SPAWN = 50;
    public static final int GRID_GAP = 1;

    private final JPanel worldPanel; // panel for the world
    private final Cell[][] cells; // cell array holding the world information

    public World2D(final int x, final int y) {
        this.cells = new Cell[x][y];
        this.worldPanel = new JPanel();
        this.worldPanel.setLayout(new GridLayout(x, y, GRID_GAP, GRID_GAP));
        this.worldPanel.setBackground(Color.BLACK);
    }

    /**
     * Initializes the world by setting all the cells in the world and giving them their corresponding life forms.
     */
    @Override
    public void init() {
        final int randomNumberMaxBound = 100;
        RandomGenerator.reset();
        for (int x = 0; x < this.cells[0].length; x++) {
            for (int y = 0; y < this.cells.length; y++) {
                this.cells[y][x] = new Cell(this, x, y);
                LifeForm currentLifeForm = null;
                int random = RandomGenerator.nextNumber(randomNumberMaxBound);
                if (random >= HERBIVORE_SPAWN) {
                    currentLifeForm = new Herbivore(this.cells[y][x]);
                } else if (random >= PLANT_SPAWN) {
                    currentLifeForm = new Plant(this.cells[y][x]);
                } else if (random >= CARNIVORE_SPAWN) {
                    currentLifeForm = new Carnivore(this.cells[y][x]);
                } else if (random >= OMNIVORE_SPAWN) {
                    currentLifeForm = new Omnivore(this.cells[y][x]);
                }
                this.cells[y][x].setLifeForm(currentLifeForm);
                this.worldPanel.add(this.cells[y][x]);
            }
        }
        display();
    }

    /**
     * Tells all the cells that the world is made up of to update. After the cells have updated, the flag indicating
     * that the life form has moved or not are all reset to false in preparation for the next turn.
     */
    @Override
    public void update() {
        // update the life form in each cell
        for (int x = 0; x < this.cells[0].length; x++) {
            for (Cell[] cell : this.cells) {
                cell[x].update();
            }
        }
        // set all life forms movement to false after all life forms have moved
        for (int x = 0; x < this.cells[0].length; x++) {
            for (Cell[] cell : this.cells) {
                LifeForm currentCellLifeForm = cell[x].getLifeForm();
                if (currentCellLifeForm != null) {
                    currentCellLifeForm.setMoved(false);
                }
            }
        }
    }

    /**
     * Tells all the cells that the world is made up of to be displayed (called after an update).
     */
    @Override
    public void display() {
        for (int x = 0; x < this.cells[0].length; x++) {
            for (Cell[] cell : this.cells) {
                cell[x].display();
            }
        }
    }

    /**
     * This returns all the cells that surround the cell passed in as a parameter.
     * @param cell used as the cell we want to get the neighbours of
     * @return neighbouring cells that surround the cell passed in
     */
    @Override
    public List<Cell> getNeighbouringCells(final Cell cell) {
        List<Cell> neighbouringCells = new ArrayList<>();
        for (int x = cell.getCellX() - 1; x <= cell.getCellX() + 1; x++) {
            for (int y = cell.getCellY() - 1; y <= cell.getCellY() + 1; y++) {
                if (isValidLocation(x, y) && !isOwnCell(cell, x, y)) {
                    neighbouringCells.add(this.cells[y][x]);
                }
            }
        }
        return neighbouringCells;
    }

    /**
     * Checks to see if the location is within the bounds of the cell array
     * @param x as horizontal location
     * @param y as vertical location
     * @return true if valid location, false otherwise
     */
    private boolean isValidLocation(final int x, final int y) {
        if (x >= 0 && x < this.cells[0].length) {
            return y >= 0 && y < this.cells.length;
        }
        return false;
    }

    /**
     * Checks to see if the cell at the current position is the cell passed in from the getNeighbouringCells method.
     * @param cell checked
     * @param x as horizontal location
     * @param y as vertical location
     * @return true if the cell is the same as the one at the position, false otherwise
     */
    private boolean isOwnCell(final Cell cell, final int x, final int y) {
        return cell.getCellX() == x && cell.getCellY() == y;
    }

    /**
     * Returns the panel that holds the world.
     * @return worldPanel
     */
    @Override
    public JPanel getWorldPanel() {
        return this.worldPanel;
    }

}