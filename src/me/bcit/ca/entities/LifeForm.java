package me.bcit.ca.entities;

import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.List;

/**
 * An abstract class used to construct different types of life forms.
 */
public abstract class LifeForm {

    private boolean moved;
    protected Cell cell;
    protected Color lifeFormColor;

    public LifeForm(Cell cell) {
        this.cell = cell;
    }

    /**
     * Used to update the life form. The eat, getMoveDestination, and eat methods are called using polymorphism.
     * Additionally. similar operations such as checking if the life form has moved has been conducted here to prevent
     * writing duplicate code in each life form class.
     */
    public final void update() {
        if (this.moved || this.cell == null) {
            return;
        }
        List<Cell> neighbouringCells = this.cell.getNeighbouringCellsFromWorld();

        giveBirth(neighbouringCells, createLifeForm());
        move(neighbouringCells);

        this.moved = true;
    }

    abstract void giveBirth(final List<Cell> moveLocations, LifeForm lifeForm);
    abstract LifeForm createLifeForm();
    abstract void eat(LifeForm lifeForm);
    abstract void move(final List<Cell> moveLocations);

    /**
     * Removes the life form from the cell and the cell from the life form.
     */
    protected void remove() {
        this.cell.setBackground(Cell.defaultColor);
        this.cell.setLifeForm(null);
        this.cell = null;
    }

    protected void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Color getLifeFormColor() {
        return this.lifeFormColor;
    }

    @Override
    public String toString() {
        return "Life form";
    }

}