/*
 * The PollWindow class creates a dialog window to display the voting process to the user after the CPF has been confirmed and sent to the server,
 * presenting options and allowing the selection of a choice.
 * */

package client.gui;

import client.VotingClient;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class PollWindow extends JDialog {
    private ButtonGroup buttonGroup;
    VotingClient votingClient;

    public PollWindow(JFrame parent, String title, List<String> options, VotingClient votingClient) {
        super(parent, title, true);
        windowConfig(parent);
        createAddComponents(title, options);

        this.votingClient = votingClient;
    }

    private void createAddComponents(String title, List<String> options) {
        JLabel titleLabel = createTitleLabel(title);
        JPanel optionsPanel = createOptionsPanel(options);
        JScrollPane scrollPane = createScrollPane(optionsPanel);
        JButton confirmButton = createConfirmButton();

        addComponents(titleLabel, scrollPane, confirmButton);
    }

    private void windowConfig(JFrame parent) {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(300, 300);
        this.setLocationRelativeTo(parent);
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel("Votação: " + title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
    }

    private JPanel createOptionsPanel(List<String> options) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonGroup.add(radioButton);
            optionsPanel.add(radioButton);
        }
        return optionsPanel;
    }

    private JScrollPane createScrollPane(JPanel optionsPanel) {
        return new JScrollPane(optionsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Confirmar Voto");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> handleVote());
        return confirmButton;
    }

    private void addComponents(JLabel titleLabel, JScrollPane scrollPane, JButton confirmButton) {
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(scrollPane);
        add(Box.createVerticalStrut(10));
        add(confirmButton);
        add(Box.createVerticalStrut(10));
    }

    private void handleVote() {
        String selectedOption = getSelectedOption();
        if (selectedOption != null) {
            votingClient.sendVote(selectedOption);
            JOptionPane.showMessageDialog(this, "Voto confirmado: " + selectedOption, "Confirmação", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma opção para votar.", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getSelectedOption() {
        // getElements returns an Enumeration object.
        // Collections.list converts the Enumeration into a List, allowing it to be iterated by forEach.

        List<AbstractButton> buttons = Collections.list(buttonGroup.getElements());
        for (AbstractButton button : buttons) {
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
