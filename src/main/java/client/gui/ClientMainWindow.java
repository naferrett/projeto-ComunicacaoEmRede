package client.gui;

import client.VotingClient;
import clientServer.gui.BaseWindow;
import clientServer.Poll;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientMainWindow extends BaseWindow {
    private final VotingClient client;

    public ClientMainWindow(VotingClient votingClient) throws IOException {
        super();

        this.client = votingClient;
        client.start();

        initAddComponents();
    }

    public void initAddComponents() {
        initStatusPanel();
        initWindowListener();
        initMenu();
        initBackGround();
    }

    public void initBackGround() {
        JPanel backgroundPanel = createBackgroundPanel();

        JLabel instructionLabel = createInstructionLabel();
        JLabel cpfLabel = createCpfLabel();
        JTextField cpfField = createCpfField();
        JButton confirmButton = createConfirmButton(cpfField);

        addComponentsToBackgroundPanel(backgroundPanel, instructionLabel, cpfLabel, cpfField, confirmButton);

        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JLabel createInstructionLabel() {
        JLabel label = new JLabel("Para votar, digite o CPF (números) e confirme:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createCpfLabel() {
        JLabel label = new JLabel("CPF:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createCpfField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(200, 25));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }

    private JButton createConfirmButton(JTextField cpfField) {
        JButton button = new JButton("Confirmar");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> processCPF(cpfField));
        return button;
    }

    private void addComponentsToBackgroundPanel(JPanel panel, JLabel instructionLabel, JLabel cpfLabel, JTextField cpfField, JButton confirmButton) {
        panel.add(Box.createVerticalStrut(10));
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(cpfLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(cpfField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(confirmButton);
    }

    private void processCPF(JTextField cpfField) {
        String cpf = cpfField.getText();

        if (cpf.isEmpty() || !cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean validCPF = client.sendCPFToVerification(cpf);

            if (validCPF) {
                Poll votingPackage = client.receiveVotingPackage();
                if (votingPackage != null) {
                    PollWindow pollWindow = new PollWindow(this, votingPackage.getTitle(), votingPackage.getOptions(), client);
                    pollWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao receber pacote de votação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Esse CPF já foi registrado para votação.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void initMenu() {
        ClientMenuHandler menuHandler = new ClientMenuHandler(this);
        menuHandler.createAddToMenu();
        initEventListener(menuHandler);
    }

    public void initEventListener(ClientMenuHandler menuHandler) {
        ClientEventListener eventListener = new ClientEventListener(this, menuHandler);
        addMenuListeners(eventListener);
    }

    @Override
    public void exitInterface() {
        client.disconnect();
        super.exitInterface();
    }
}
