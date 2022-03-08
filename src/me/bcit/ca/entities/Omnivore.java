package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Omnivore extends LifeForm implements CarnEdible {

    private static final int MAX_MOVES_WITHOUT_EATING = 5;
    private static final int EMPTY_CELLS_TO_GIVE_BIRTH = 3;
    private static final int FOOD_CELLS_TO_GIVE_BIRTH = 1;
    private static final int OMNIVORE_CELLS_TO_GIVE_BIRTH = 1;

    private int movesWithoutEating;

    public Omnivore(Cell cell) {
        super(cell);
        this.lifeFormColor = Color.BLUE;
        this.movesWithoutEating = 0;
    }

    @Override
    void eat(LifeForm lifeForm) {
        if (lifeForm instanceof OmiEdible) {
            this.movesWithoutEating = 0;
        }
    }

    @Override
    LifeForm createLifeForm() {
        return new Omnivore(this.cell);
    }

    @Override
    void giveBirth(final List<Cell> moveLocations, LifeForm lifeForm) {
        if (movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }
        int omnivoreCells = 0;
        int emptyCells = 0;
        int foodCells = 0;
        List<Cell> emptyCellsList = new ArrayList<>();
        for (Cell moveLocation : moveLocations) {
            if (!moveLocation.hasLifeForm()) {
                emptyCells++;
                emptyCellsList.add(moveLocation);
            } else if (moveLocation.getLifeForm() instanceof OmiEdible) {
                foodCells++;
            } else if (moveLocation.getLifeForm() instanceof Omnivore) {
                omnivoreCells++;
            }
        }
        // spawn in a newly breed life form (set moved to true so they cannot move this turn)
        if (omnivoreCells >= OMNIVORE_CELLS_TO_GIVE_BIRTH && emptyCells >= EMPTY_CELLS_TO_GIVE_BIRTH && foodCells >= FOOD_CELLS_TO_GIVE_BIRTH) {
            Cell breedCell = emptyCellsList.get(RandomGenerator.nextNumber(emptyCellsList.size()));
            lifeForm.setCell(breedCell);
            lifeForm.setMoved(true);
            breedCell.setLifeForm(lifeForm);
        }
    }

    @Override
    void move(List<Cell> moveLocations) {
        remove();
        if (this.movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }

        Cell destination = getMoveDestination(moveLocations);
        if (destination == null) {
            this.movesWithoutEating++;
            return;
        }

        eat(destination.getLifeForm());

        destination.setLifeForm(this);
        this.setCell(destination);
        this.movesWithoutEating++;
    }

    private Cell getMoveDestination(final List<Cell> neighbouringCells) {
        List<Cell> availableCells = new ArrayList<>();
        for (Cell value : neighbouringCells) {
            if (!value.hasLifeForm() || value.getLifeForm() instanceof OmiEdible) {
                availableCells.add(value);
            }
        }
        return availableCells.size() == 0 ? null : availableCells.get(RandomGenerator.nextNumber(availableCells.size()));
    }

}
