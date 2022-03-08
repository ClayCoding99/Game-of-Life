package me.bcit.ca.world;

import me.bcit.ca.entities.LifeForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Used to represent a cell in the world. It extends JPanel, so it can be added as a component to the world border
 * layout.
 */
public class Cell extends JPanel implements WorldCell {

    public static final Color defaultColor;

    static {
        defaultColor = Color.WHITE;
    }

    private LifeForm lifeForm;
    private final World world;
    private final int cellX;
    private final int cellY;
    private List<Cell> neighbouringCells;

    public Cell(final World world, final int cellX, final int cellY) {
        this.setBackground(defaultColor);
        this.world = world;
        this.lifeForm = null;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public LifeForm getLifeForm() {
        return this.lifeForm;
    }

    @Override
    public void setLifeForm(final LifeForm lifeForm) {
        this.lifeForm = lifeForm;
    }

    @Override
    public boolean hasLifeForm() {
        return this.lifeForm != null;
    }

    /**
     * Updates the life form in the cell if it has one.
     */
    @Override
    public void update() {
        if (this.hasLifeForm()) {
            this.lifeForm.update();
        } else {
            setBackground(defaultColor);
        }
    }

    /**
     * Displays the life form in the cell if it has one.
     */
    @Override
    public void display() {
        if (this.hasLifeForm()) {
            this.setBackground(this.lifeForm.getLifeFormColor());
        }
    }

    /**
     * Used to get the neighbouring cells surrounding this one. To optimize the program and not have to fetch the
     * neighbouring cells from the world every single turn, they are only gathered from the world the first time
     * and stored in an instance variable.
     *
     * @return neighbouring cells
     */
    @Override
    public List<Cell> getNeighbouringCellsFromWorld() {
        if (this.neighbouringCells == null) {
            this.neighbouringCells = world.getNeighbouringCells(this);
        }
        return this.neighbouringCells;
    }

    @Override
    public int getCellX() {
        return this.cellX;
    }

    @Override
    public int getCellY() {
        return this.cellY;
    }

    @Override
    public String toString() {
        return "X: " + this.cellX + ", Y: " + this.cellY + ", life form: " + lifeForm;
    }

}