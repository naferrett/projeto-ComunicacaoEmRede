package client.gui;

import client.VotingClient;
import clientServer.gui.BaseWindow;
import clientServer.Poll;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Log4j2
public class ClientMainWindow extends BaseWindow {
    private final VotingClient client;

    public ClientMainWindow(VotingClient votingClient) throws IOException {
        super();  // Chama o construtor de BaseWindow

        this.client = votingClient;
        client.start();  // Conecta ao servidor

        initInterface();
    }

    public void initInterface() {
        initStatusPanel();
        initWindowListener();
        initMenu();
        initBackGround();  // Configura o painel de votação
        setVisible(true);
    }

    public void initBackGround() {
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel instructionLabel = new JLabel("Para votar, digite o CPF (números) e confirme:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField cpfField = new JTextField();
        cpfField.setMaximumSize(new Dimension(200, 25));
        cpfField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> processCPFEntry(cpfField));

        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(Box.createVerticalStrut(30));
        backgroundPanel.add(cpfLabel);
        backgroundPanel.add(Box.createVerticalStrut(15));
        backgroundPanel.add(cpfField);
        backgroundPanel.add(Box.createVerticalStrut(15));
        backgroundPanel.add(confirmButton);

        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    private void processCPFEntry(JTextField cpfField) {
        String cpf = cpfField.getText();

        if (cpf.isEmpty() || !cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean validCPF = client.sendCPFToVerification(cpf);

            if (validCPF) {
                Poll votingPackage = client.receiveVotingPackage();
                if (votingPackage != null) {
                    PollWindow pollWindow = new PollWindow(this, votingPackage.getQuestion(), votingPackage.getOptions(), client);
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
