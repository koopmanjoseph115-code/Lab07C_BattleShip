import java.util.*;

public class Board {
    private final int[][] grid = new int[10][10]; // 0=Empty, 1=Has Ship
    private final List<Ship> deployedShips = new ArrayList<>();

    public Board() {
        clearBoard();
        deployFleetRandomly();
    }

    public void clearBoard() {
        for (int r = 0; r < 10; r++) {
            Arrays.fill(grid[r], 0);
        }
        deployedShips.clear();
    }

    private void deployFleetRandomly() {
        // Correct fleet composition specified in prompt: {5, 4, 3, 3, 2}
        int[] shipSizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);

                if (canPlaceShip(row, col, size, horizontal)) {
                    Ship ship = new Ship(size);
                    for (int i = 0; i < size; i++) {
                        int r = horizontal ? row : row + i;
                        int c = horizontal ? col + i : col;
                        grid[r][c] = 1;
                        ship.addCoordinate(r, c);
                    }
                    deployedShips.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && (col + size > 10)) return false;
        if (!horizontal && (row + size > 10)) return false;

        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            if (grid[r][c] != 0) return false; // Overlap detected
        }
        return true;
    }

    public Ship getShipAt(int row, int col) {
        for (Ship s : deployedShips) {
            if (s.occupiesLocation(row, col)) return s;
        }
        return null;
    }

    public boolean isAllFleetSunk() {
        for (Ship s : deployedShips) {
            if (!s.isSunk()) return false;
        }
        return true;
    }
}
