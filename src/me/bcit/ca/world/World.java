package me.bcit.ca.world;

import javax.swing.*;
import java.util.List;

public interface World {

    void update();
    void display();
    void init();
    List<Cell> getNeighbouringCells(Cell cell);
    JPanel getWorldPanel();

}
