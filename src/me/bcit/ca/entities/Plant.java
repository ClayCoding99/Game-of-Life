package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Plant extends LifeForm implements Edible {

    private final int numPlantsToSeed;
    private final int numEmptyCellsToSeed;

    public Plant(Cell cell) {
        super(cell);
        this.lifeFormColor = Color.GREEN;
        this.numPlantsToSeed = 4;
        this.numEmptyCellsToSeed = 3;
    }

    @Override
    void eat(LifeForm lifeForm) {
        return;
    }

    @Override
    public Cell getMoveDestination(List<Cell> neighbouringCells) {
        List<Cell> emptyCells = getEmptyCells(neighbouringCells);
        int numberOfNeighbouringPlants = getNumberOfNeighbouringPlants(neighbouringCells);
        if (numberOfNeighbouringPlants >= numPlantsToSeed && emptyCells.size() >= numEmptyCellsToSeed) {
            return emptyCells.get(RandomGenerator.nextNumber(emptyCells.size()));
        }
        return null;
    }

    private int getNumberOfNeighbouringPlants(List<Cell> cells) {
        int plantCount = 0;
        for (Cell value : cells) {
            if (value.getLifeForm() instanceof Plant) {
                plantCount++;
            }
        }
        return plantCount;
    }

    private List<Cell> getEmptyCells(List<Cell> cells) {
        List<Cell> emptyCells = new ArrayList<>();
        for (Cell value : cells) {
            if (!value.hasLifeForm()) {
                emptyCells.add(value);
            }
        }
        return emptyCells;
    }

    @Override
    public void move(Cell destination) {
        LifeForm plant = new Plant(destination);
        plant.setCell(destination);
        destination.setLifeForm(plant);
    }

    @Override
    public String toString() {
        return "Plant";
    }

}