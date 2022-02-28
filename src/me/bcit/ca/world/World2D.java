package me.bcit.ca.world;

import me.bcit.ca.entities.*;
import me.bcit.ca.util.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World2D implements World {

    public static final int HERBIVORE_SPAWN = 85;
    public static final int PLANT_SPAWN = 65;
    public static final int GRID_GAP = 1;

    private final JPanel worldPanel; // panel for the world
    private final Cell[][] cells; // cell array holding the world information

    public World2D(int x, int y) {
        this.cells = new Cell[x][y];
        this.worldPanel = new JPanel();
        this.worldPanel.setLayout(new GridLayout(x, y, GRID_GAP, GRID_GAP));
        this.worldPanel.setBackground(Color.BLACK);
    }

    @Override
    public void init() {
        // initialize the cells of the world
        for (int x = 0; x < this.cells[0].length; x++) {
            for (int y = 0; y < this.cells.length; y++) {
                this.cells[y][x] = new Cell(this, x, y);
                LifeForm currentLifeForm = null;
                int random = RandomGenerator.nextNumber(100);
                if (random >= HERBIVORE_SPAWN) {
                    currentLifeForm = new Herbivore(this.cells[y][x]);
                } else if (random >= PLANT_SPAWN) {
                    currentLifeForm = new Plant(this.cells[y][x]);
                }
                this.cells[y][x].setLifeForm(currentLifeForm);
                this.worldPanel.add(this.cells[y][x]);
            }
        }
        display();
    }

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

    @Override
    public void display() {
        for (int x = 0; x < this.cells[0].length; x++) {
            for (Cell[] cell : this.cells) {
                cell[x].display();
            }
        }
    }

    @Override
    public List<Cell> getNeighbouringCells(Cell cell) {
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

    private boolean isValidLocation(int x, int y) {
        if (x >= 0 && x < this.cells[0].length) {
            return y >= 0 && y < this.cells.length;
        }
        return false;
    }

    private boolean isOwnCell(Cell cell, int x, int y) {
        return cell.getCellX() == x && cell.getCellY() == y;
    }

    @Override
    public JPanel getWorldPanel() {
        return this.worldPanel;
    }

}