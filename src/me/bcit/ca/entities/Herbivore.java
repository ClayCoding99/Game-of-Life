package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Herbivore extends LifeForm {

    private static final int MAX_MOVES_WITHOUT_EATING = 4;

    private int movesWithoutEating;

    public Herbivore(Cell cell) {
        super(cell);
        this.lifeFormColor = Color.YELLOW;
        this.movesWithoutEating = 0;
    }

    @Override
    public Cell getMoveDestination(List<Cell> neighbouringCells) {
        List<Cell> availableCells = this.getAvailableCells(neighbouringCells);
        return availableCells.get(RandomGenerator.nextNumber(availableCells.size()));
    }

    private List<Cell> getAvailableCells(List<Cell> cells) {
        List<Cell> availableCells = new ArrayList<>();
        for (Cell value : cells) {
            if (!value.hasLifeForm() || value.getLifeForm() instanceof Plant) {
                availableCells.add(value);
            }
        }
        return availableCells;
    }

    @Override
    public void eat(LifeForm lifeForm) {
        if (lifeForm instanceof Plant) {
            this.movesWithoutEating = 0;
        }
    }

    @Override
    void move(Cell destination) {
        // remove the life form from this cell
        remove();
        if (movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }
        // move the life form to the new cell
        destination.setLifeForm(this);
        this.setCell(destination);

        this.movesWithoutEating++;
    }

}