package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent a Plant in the Game of Life.
 */
public class Plant extends LifeForm implements HerbEdible, OmiEdible {

    private static final int numPlantsToSeed = 2;
    private static final int numEmptyCellsToSeed = 3;

    public Plant(Cell cell) {
        super(cell);
        this.lifeFormColor = Color.GREEN;
    }

    @Override
    void giveBirth(List<Cell> moveLocations, LifeForm lifeForm) {
        Cell destination = getMoveDestination(moveLocations);
        if (destination == null) {
            return;
        }
        LifeForm plant = new Plant(destination);
        plant.setCell(destination);
        destination.setLifeForm(plant);
    }

    @Override
    LifeForm createLifeForm() {
        return new Plant(this.cell);
    }

    /**
     * Returns the cell (if any) that the plant can move to. The neighbouring cells must at least contain
     * 'numPlantsToSeed' and 'numEmptyCellsToSeed'.
     *
     * @param neighbouringCells used to find the location to move to
     * @return move destination cell
     */
    private Cell getMoveDestination(final List<Cell> neighbouringCells) {
        List<Cell> emptyCells = getEmptyCells(neighbouringCells);
        int numberOfNeighbouringPlants = getNumberOfNeighbouringPlants(neighbouringCells);
        if (numberOfNeighbouringPlants >= numPlantsToSeed && emptyCells.size() >= numEmptyCellsToSeed) {
            return emptyCells.get(RandomGenerator.nextNumber(emptyCells.size()));
        }
        return null;
    }

    /**
     * Plants don't need to eat so we just leave the method body blank in this case.
     */
    @Override
    void eat(LifeForm lifeForm) {
    }

    /**
     * Returns the number of plants in the cells passed in.
     *
     * @param cells used to get the number of neighbouring plants
     * @return number of plants in cells
     */
    private int getNumberOfNeighbouringPlants(final List<Cell> cells) {
        int plantCount = 0;
        for (Cell value : cells) {
            if (value.getLifeForm() instanceof Plant) {
                plantCount++;
            }
        }
        return plantCount;
    }

    /**
     * Returns the amount of empty cells passed in.
     *
     * @param cells used to get the empty cells
     * @return amount of cells with no life forms
     */
    private List<Cell> getEmptyCells(final List<Cell> cells) {
        List<Cell> emptyCells = new ArrayList<>();
        for (Cell value : cells) {
            if (!value.hasLifeForm()) {
                emptyCells.add(value);
            }
        }
        return emptyCells;
    }

    /**
     * Moves this to the destination cell.
     *
     * @param moveLocations moved to one
     */
    @Override
    void move(final List<Cell> moveLocations) {
        // spawn a new plant at the destination
        giveBirth(moveLocations, createLifeForm());
    }

    @Override
    public String toString() {
        return "Plant";
    }

}