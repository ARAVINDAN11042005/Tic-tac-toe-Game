import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TicTacToe implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton[] buttons = new JButton[9];
    private boolean playerTurn = true; // true: Human's turn (X), false: Computer (O)

    public TicTacToe() {
        frame = new JFrame("Tic-Tac-Toe: You vs Computer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!playerTurn)
            return; // Ignore clicks when it's computer's turn

        JButton button = (JButton) e.getSource();
        if (button.getText().equals("")) {
            button.setText("X");
            button.setEnabled(false);
            playerTurn = false;
            checkForWinner();

            // Let computer play after short delay
            Timer timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    computerMove();
                    checkForWinner();
                    playerTurn = true;
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void computerMove() {
        // Simple logic: Pick first available spot
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled()) {
                buttons[i].setText("O");
                buttons[i].setEnabled(false);
                break;
            }
        }
    }

    public void checkForWinner() {
        String[][] combos = {
                { "0", "1", "2" },
                { "3", "4", "5" },
                { "6", "7", "8" },
                { "0", "3", "6" },
                { "1", "4", "7" },
                { "2", "5", "8" },
                { "0", "4", "8" },
                { "2", "4", "6" },
        };

        for (String[] combo : combos) {
            int a = Integer.parseInt(combo[0]);
            int b = Integer.parseInt(combo[1]);
            int c = Integer.parseInt(combo[2]);

            String valA = buttons[a].getText();
            String valB = buttons[b].getText();
            String valC = buttons[c].getText();

            if (!valA.equals("") && valA.equals(valB) && valB.equals(valC)) {
                showWinner(valA);
                return;
            }
        }

        // Check for tie
        boolean tie = true;
        for (JButton b : buttons) {
            if (b.isEnabled()) {
                tie = false;
                break;
            }
        }
        if (tie) {
            JOptionPane.showMessageDialog(frame, "It's a Tie!");
            resetGame();
        }
    }

    public void showWinner(String winner) {
        JOptionPane.showMessageDialog(frame, winner + " wins!");
        resetGame();
    }

    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        playerTurn = true;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
