public class GameEngine {
    private Board board;
    private int consecutiveMisses = 0;
    private int strikes = 0;
    private int totalMisses = 0;
    private int totalHits = 0;

    public GameEngine() {
        resetGame();
    }

    public void resetGame() {
        board = new Board();
        consecutiveMisses = 0;
        strikes = 0;
        totalMisses = 0;
        totalHits = 0;
    }

    // Return codes: -1 = Miss, 1 = Hit, 2 = Hit and Sunk, 3 = Hit and Game Won
    public int fireMissile(int row, int col) {
        Ship ship = board.getShipAt(row, col);

        if (ship != null) {
            totalHits++;
            consecutiveMisses = 0; // Reset continuous miss streak
            boolean sunk = ship.registerHit();

            if (board.isAllFleetSunk()) return 3;
            if (sunk) return 2;
            return 1;
        } else {
            totalMisses++;
            consecutiveMisses++;
            if (consecutiveMisses >= 5) {
                strikes++;
                consecutiveMisses = 0;
            }
            return -1;
        }
    }

    public boolean isGameOverLoss() { return strikes >= 3; }
    public int getConsecutiveMisses() { return consecutiveMisses; }
    public int getStrikes() { return strikes; }
    public int getTotalMisses() { return totalMisses; }
    public int getTotalHits() { return totalHits; }
}