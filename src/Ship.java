import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final int length;
    private final List<Point> coordinates;
    private int hitsSustained;

    public Ship(int length) {
        this.length = length;
        this.coordinates = new ArrayList<>();
        this.hitsSustained = 0;
    }

    public void addCoordinate(int row, int col) {
        coordinates.add(new Point(row, col));
    }

    public boolean occupiesLocation(int row, int col) {
        return coordinates.contains(new Point(row, col));
    }

    public boolean registerHit() {
        hitsSustained++;
        return isSunk();
    }

    public boolean isSunk() {
        return hitsSustained >= length;
    }

    public int getLength() { return length; }
}
