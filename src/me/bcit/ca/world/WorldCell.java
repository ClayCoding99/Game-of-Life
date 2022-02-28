package me.bcit.ca.world;

import me.bcit.ca.entities.LifeForm;

import java.util.List;

public interface WorldCell {

    LifeForm getLifeForm();
    void setLifeForm(LifeForm lifeForm);
    boolean hasLifeForm();
    void update();
    void display();
    int getCellX();
    int getCellY();
    List<Cell> getNeighbouringCellsFromWorld();

}
