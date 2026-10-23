import javax.swing.*;
import java.awt.*;

public class BattleShipFrame extends JFrame {
    private final GameEngine engine = new GameEngine();
    private final BoardCellButton[][] cellMatrix = new BoardCellButton[10][10];

    private JLabel lblMissStreak, lblStrikes, lblTotalMisses, lblTotalHits;
    private JButton btnPlayAgain, btnQuit;

    public BattleShipFrame() {
        setTitle("Single Player Battleship Camp");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initHeader();
        initGameBoard();
        initDashboardPanel();
        updateDashboardDisplay();
    }

    private void initHeader() {
        JLabel title = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        title.setFont(new Font("Impact", Font.PLAIN, 28));
        add(title, BorderLayout.NORTH);
    }

    private void initGameBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(10, 10, 2, 2));
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                BoardCellButton cell = new BoardCellButton(r, c);
                cell.addActionListener(e -> processCellClick(cell));
                cellMatrix[r][c] = cell;
                boardPanel.add(cell);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initDashboardPanel() {
        JPanel sidePanel = new JPanel(new GridLayout(6, 1, 5, 5));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblMissStreak = new JLabel();
        lblStrikes = new JLabel();
        lblTotalMisses = new JLabel();
        lblTotalHits = new JLabel();

        btnPlayAgain = new JButton("Play Again");
        btnQuit = new JButton("Quit");

        btnQuit.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) System.exit(0);
        });

        btnPlayAgain.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to restart?", "New Game", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) triggerRestartSequence();
        });

        sidePanel.add(lblMissStreak);
        sidePanel.add(lblStrikes);
        sidePanel.add(lblTotalMisses);
        sidePanel.add(lblTotalHits);
        sidePanel.add(btnPlayAgain);
        sidePanel.add(btnQuit);

        add(sidePanel, BorderLayout.EAST);
    }

    private void processCellClick(BoardCellButton cell) {
        int outcome = engine.fireMissile(cell.getRow(), cell.getCol());

        if (outcome == -1) {
            cell.setToMiss();
        } else {
            cell.setToHit();
        }

        updateDashboardDisplay();

        if (outcome == 2) {
            JOptionPane.showMessageDialog(this, "Boom! You sunk a ship!");
        } else if (outcome == 3) {
            JOptionPane.showMessageDialog(this, "Victory! You have completely eliminated the enemy fleet!");
            promptReplay();
        }

        if (engine.isGameOverLoss()) {
            JOptionPane.showMessageDialog(this, "Defeat! Too many tactical errors. The operation has failed.");
            promptReplay();
        }
    }

    private void updateDashboardDisplay() {
        lblMissStreak.setText("Current Miss Streak: " + engine.getConsecutiveMisses() + " / 5");
        lblStrikes.setText("Strikes Incurred: " + engine.getStrikes() + " / 3");
        lblTotalMisses.setText("Total Missed Shells: " + engine.getTotalMisses());
        lblTotalHits.setText("Total Verified Hits: " + engine.getTotalHits() + " / 17");
    }

    private void promptReplay() {
        int res = JOptionPane.showConfirmDialog(this, "Would you like to deploy for another match?", "Play Again?", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            triggerRestartSequence();
        } else {
            System.exit(0);
        }
    }

    private void triggerRestartSequence() {
        engine.resetGame();
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                cellMatrix[r][c].setToBlank();
            }
        }
        updateDashboardDisplay();
    }
}