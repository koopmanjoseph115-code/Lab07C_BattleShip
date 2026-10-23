import javax.swing.SwingUtilities;

public class BattleShipRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BattleShipFrame().setVisible(true);
        });
    }
}
