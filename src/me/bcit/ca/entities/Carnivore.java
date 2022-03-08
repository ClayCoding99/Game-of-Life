package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Carnivore extends LifeForm implements OmiEdible {

    private static final int MAX_MOVES_WITHOUT_EATING = 5;
    private static final int CARNIVORE_CELLS_TO_GIVE_BIRTH = 1;
    private static final int EMPTY_CELLS_TO_GIVE_BIRTH = 3;
    private static final int FOOD_CELLS_TO_GIVE_BIRTH = 2;

    private int movesWithoutEating;

    public Carnivore(Cell cell) {
        super(cell);
        this.movesWithoutEating = 0;
        this.lifeFormColor = Color.RED;
    }

    @Override
    LifeForm createLifeForm() {
        return new Carnivore(this.cell);
    }

    @Override
    void giveBirth(final List<Cell> moveLocations, LifeForm lifeForm) {
        if (movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }
        int carnivoreCells = 0;
        int emptyCells = 0;
        int foodCells = 0;
        List<Cell> emptyCellsList = new ArrayList<>();
        for (Cell moveLocation : moveLocations) {
            if (!moveLocation.hasLifeForm()) {
                emptyCells++;
                emptyCellsList.add(moveLocation);
            } else if (moveLocation.getLifeForm() instanceof CarnEdible) {
                foodCells++;
            } else if (moveLocation.getLifeForm() instanceof Carnivore) {
                carnivoreCells++;
            }
        }
        // spawn in a newly breed life form (set moved to true so they cannot move this turn)
        if (carnivoreCells >= CARNIVORE_CELLS_TO_GIVE_BIRTH && emptyCells >= EMPTY_CELLS_TO_GIVE_BIRTH && foodCells >= FOOD_CELLS_TO_GIVE_BIRTH) {
            Cell breedCell = emptyCellsList.get(RandomGenerator.nextNumber(emptyCellsList.size()));
            lifeForm.setCell(breedCell);
            breedCell.setLifeForm(lifeForm);
            breedCell.getLifeForm().setMoved(true);
        }
    }

    @Override
    void eat(LifeForm lifeForm) {
        if (lifeForm instanceof CarnEdible) {
            this.movesWithoutEating = 0;
        }
    }

    @Override
    void move(final List<Cell> moveLocations) {
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

    // TODO: put this method in the abstract class and pass in a class type to prevent duplicate code if possible
    Cell getMoveDestination(final List<Cell> neighbouringCells) {
        List<Cell> availableCells = new ArrayList<>();
        for (Cell value : neighbouringCells) {
            if (!value.hasLifeForm() || value.getLifeForm() instanceof CarnEdible) {
                availableCells.add(value);
            }
        }
        return availableCells.size() == 0 ? null : availableCells.get(RandomGenerator.nextNumber(availableCells.size()));
    }

}
