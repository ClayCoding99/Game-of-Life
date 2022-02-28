package me.bcit.ca.world;

import me.bcit.ca.entities.LifeForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Cell extends JPanel implements WorldCell {

    public static final Color defaultColor = Color.WHITE;

    private LifeForm lifeForm;
    private final World world;
    private final int cellX;
    private final int cellY;
    private List<Cell> neighbouringCells;

    public Cell(World world, int cellX, int cellY) {
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
    public void setLifeForm(LifeForm lifeForm) {
        this.lifeForm = lifeForm;
    }

    @Override
    public boolean hasLifeForm() {
        return this.lifeForm != null;
    }

    @Override
    public void update() {
        if (this.hasLifeForm()) {
            this.lifeForm.update();
        } else {
            setBackground(defaultColor);
        }
    }

    @Override
    public void display() {
        if (this.hasLifeForm()) {
            this.setBackground(this.lifeForm.getLifeFormColor());
        }
    }

    // gets the neighbouring cells from the world. Initially, the cells will be stored in the neighbouring cells
    // instance variable, so we only have to grab them once from the world for optimization purposes.
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