import javax.swing.JButton;
import java.awt.Font;

public class BoardCellButton extends JButton {
    private final int row;
    private final int col;

    public BoardCellButton(int row, int col) {
        this.row = row;
        this.col = col;
        setToBlank();
        setFont(new Font("Arial", Font.BOLD, 14));
    }

    public void setToBlank() {
        setText("~"); // Light blue wave character representation
        setEnabled(true);
    }

    public void setToHit() {
        setText("X"); // Red X Hit designator
        setEnabled(false);
    }

    public void setToMiss() {
        setText("M"); // Yellow M Miss designator
        setEnabled(false);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}