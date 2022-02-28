Major Changes Made to UML diagram:

1) KeyListener Changed To MouseListener
2) Added init method to World
3) moved getNeighbouringCells method from cell to world (makes more sense for the world class to take care of neighbours rather than the cell itself)
4) Removed fixed width and height from the Cell class. It only needs to hold an x and y, java swing could handle the cell size
5) Added abstract methods getLocationToMoveTo and eat to LifeForm. Also added an update method because there were some common setup
    things in the move method that wasn't necessary. The update method calls the abstract move method instead now.
6) removed the seed method in Plants because it could be replaced with the move method obtained from extending LifeForm
7) Made an interface for the Cell with most of its methods to make the code more flexible. If we wanted to make a different kind of cell, we could
    do so by implementing the cell interface and not have to adjust the entire program.