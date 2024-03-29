package me.bcit.ca.entities;

import me.bcit.ca.util.RandomGenerator;
import me.bcit.ca.world.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent a Herbivore in the Game of Life.
 */
public class Herbivore extends LifeForm implements OmiEdible, CarnEdible {

    private static final int MAX_MOVES_WITHOUT_EATING = 5;
    private static final int HERB_CELLS_TO_GIVE_BIRTH = 1;
    private static final int EMPTY_CELLS_TO_GIVE_BIRTH = 2;
    private static final int FOOD_CELLS_TO_GIVE_BIRTH = 2;

    private int movesWithoutEating;

    public Herbivore(Cell cell) {
        super(cell);
        this.lifeFormColor = Color.YELLOW;
        this.movesWithoutEating = 0;
    }

    @Override
    LifeForm createLifeForm() {
        return new Herbivore(this.cell);
    }

    @Override
    void giveBirth(final List<Cell> moveLocations, LifeForm lifeForm) {
        if (movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }
        int herbCells = 0;
        int emptyCells = 0;
        int foodCells = 0;
        List<Cell> emptyCellsList = new ArrayList<>();
        for (Cell moveLocation : moveLocations) {
            if (!moveLocation.hasLifeForm()) {
                emptyCells++;
                emptyCellsList.add(moveLocation);
            } else if (moveLocation.getLifeForm() instanceof HerbEdible) {
                foodCells++;
            } else if (moveLocation.getLifeForm() instanceof Herbivore) {
                herbCells++;
            }
        }
        // spawn in a newly breed life form (set moved to true so they cannot move this turn)
        if (herbCells >= HERB_CELLS_TO_GIVE_BIRTH && emptyCells >= EMPTY_CELLS_TO_GIVE_BIRTH && foodCells >= FOOD_CELLS_TO_GIVE_BIRTH) {
            Cell breedCell = emptyCellsList.get(RandomGenerator.nextNumber(emptyCellsList.size()));
            lifeForm.setCell(breedCell);
            breedCell.setLifeForm(lifeForm);
            breedCell.getLifeForm().setMoved(true);
        }
    }

    /**
     * Sets the moves without eating back to 0.
     */
    @Override
    void eat(LifeForm lifeForm) {
        if (lifeForm instanceof HerbEdible) {
            this.movesWithoutEating = 0;
        }
    }

    /**
     * Moves this to the destination cell.
     *
     * @param moveLocations move to one
     */
    @Override
    void move(final List<Cell> moveLocations) {
        // remove this from this cell
        remove();
        if (movesWithoutEating == MAX_MOVES_WITHOUT_EATING) {
            return;
        }

        // get destination
        Cell destination = getMoveDestination(moveLocations);
        if (destination == null) {
            this.movesWithoutEating++;
            return;
        }

        // eat the life form at the destination
        eat(destination.getLifeForm());

        // put the life form on its destination cell if it is not eaten
        destination.setLifeForm(this);
        this.setCell(destination);
        this.movesWithoutEating++;
    }

    /**
     * Returns the cell (if any) that the herbivore can move to. The herbivore can move to one of
     * the cells that doesn't contain another herbivore.
     *
     * @param neighbouringCells used to get the cell to move to
     * @return a cell not containing another herbivore
     */
    private Cell getMoveDestination(final List<Cell> neighbouringCells) {
        List<Cell> availableCells = new ArrayList<>();
        for (Cell value : neighbouringCells) {
            if (!value.hasLifeForm() || value.getLifeForm() instanceof HerbEdible) {
                availableCells.add(value);
            }
        }
        return availableCells.size() == 0 ? null : availableCells.get(RandomGenerator.nextNumber(availableCells.size()));
    }

    @Override
    public String toString() {
        return "Herbivore";
    }

}