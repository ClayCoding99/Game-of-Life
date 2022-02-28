package me.bcit.ca.entities;

import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.List;

public abstract class LifeForm {

    private boolean moved;
    protected Cell cell;
    protected Color lifeFormColor;

    public LifeForm(Cell cell) {
        this.cell = cell;
    }

    public final void update() {
        if (this.moved || this.cell == null) {
            return;
        }
        List<Cell> neighbouringCells = this.cell.getNeighbouringCellsFromWorld();
        Cell destination = getMoveDestination(neighbouringCells);
        if (isADestination(destination)) {
            if (destination.getLifeForm() instanceof Edible) {
                eat(destination.getLifeForm());
            }
            move(destination);
            this.moved = true;
        }
    }

    private boolean isADestination(Cell cell) {
        return cell != null;
    }

    abstract void eat(LifeForm lifeForm);
    abstract void move(Cell destination);
    abstract Cell getMoveDestination(List<Cell> neighbouringCells);

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