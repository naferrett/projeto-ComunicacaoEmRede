package client.gui;

import client.VotingClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

public class PollWindow extends JDialog {
    private final ButtonGroup buttonGroup;
    VotingClient votingClient;
    public PollWindow(JFrame parent, String title, List<String> options, VotingClient votingClient) {
        super(parent, title, true);

        this.votingClient = votingClient;

        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(300, 300);
        this.setLocationRelativeTo(parent);

        // Título da Votação
        JLabel titleLabel = new JLabel("Votação: " + title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel para as opções de votação
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        // Adicionando as opções de votação
        buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonGroup.add(radioButton);
            optionsPanel.add(radioButton);
        }

        // JScrollPane para as opções
        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setPreferredSize(new Dimension(250, 150));  // Define o tamanho máximo visível para as opções
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Botão de confirmar voto
        JButton confirmButton = new JButton("Confirmar Voto");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSelectedOption() != null) {
                    votingClient.sendVote(getSelectedOption());
                    JOptionPane.showMessageDialog(PollWindow.this, "Voto confirmado: " + getSelectedOption(), "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Fecha a janela após confirmar o voto
                } else {
                    JOptionPane.showMessageDialog(PollWindow.this, "Selecione uma opção para votar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Adicionando componentes ao PollWindow
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(scrollPane);  // Adiciona o scrollPane com as opções
        add(Box.createVerticalStrut(10));
        add(confirmButton);
        add(Box.createVerticalStrut(10));
    }

    // Método para obter a opção selecionada
    public String getSelectedOption() {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
