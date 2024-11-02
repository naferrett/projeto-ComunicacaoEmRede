package client.gui;

import client.VotingClient;
import clientServer.Poll;
import clientServer.SystemInfo;
import clientServer.WindowListenerHandler;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

@Log4j2
public class MainWindow extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private JLabel labelStatus;
    private final VotingClient client;
    private JTextField cpfField;

    public MainWindow(VotingClient votingClient) throws HeadlessException {
        super(SystemInfo.getVersionName());

        windowConfig();
        initAddComponents();

        this.client = votingClient;
        try {
            client.start(); // Conecta ao servidor e recebe o pacote de votação
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor de votação.", "Erro", JOptionPane.ERROR_MESSAGE);
            log.error(e);
        }
    }

    private void windowConfig() {
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.3), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.3));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setIcon();
    }

    private void setIcon() {
        try {
            this.setIconImage(ImageIO.read(this.getClass().getResource(SystemInfo.iconImage)));
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o ícone.", "Erro", JOptionPane.ERROR_MESSAGE);
            log.error(ex);
        }
    }

    public void setStatusMessage(String message) {
        this.labelStatus.setText(message);
    }

    private void initAddComponents() {
        initStatusPanel();
        initWindowListener();
        initMenu();
        initBackGround();

        setVisible(true);
    }

    private void initStatusPanel() {
        JPanel statusPanel = new JPanel();
        this.labelStatus = new JLabel();
        statusPanel.add(labelStatus);
        statusPanel.setBackground(Color.gray);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        this.add(statusPanel, BorderLayout.SOUTH);
    }

    public void initInterface() {
        this.setStatusMessage(SystemInfo.university);
        this.setVisible(true);
    }

    private void initWindowListener() {
        WindowListenerHandler windowEventListener = new WindowListenerHandler(this);
        this.addWindowListener(windowEventListener);
    }

    private void initMenu() {
        MenuHandler menuHandler = new MenuHandler(this);
        menuHandler.createAddToMenu();

        initEventListener(menuHandler);
    }

    private void initEventListener(MenuHandler menuHandler) {
        EventListener eventListener = new EventListener(this, menuHandler);
        this.addMenuListeners(eventListener);
    }

    public void initBackGround() {
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel instructionLabel = new JLabel("Para votar, digite o CPF (números) e confirme:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cpfField = new JTextField(15);
        cpfField.setMaximumSize(new Dimension(200, 25));
        cpfField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> {
            String cpf = cpfField.getText();

            if (cpf.isEmpty() || !cpf.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "Por favor, insira o CPF válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean validCPF = client.sendCPFToVerification(cpf);

                if(validCPF) {
                    Poll votingPackage = client.receiveVotingPackage();
                    if (votingPackage != null) {
                        //Exibe a janela de votação
                        PollWindow pollWindow = new PollWindow(this, votingPackage.getQuestion(), votingPackage.getOptions(), client);
                        pollWindow.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Falha ao receber pacote de votação.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Esse CPF já foi registrado para votação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionando componentes ao painel de fundo
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(Box.createVerticalStrut(30));
        backgroundPanel.add(cpfLabel);  // Adicionando o label "CPF"
        backgroundPanel.add(Box.createVerticalStrut(15));
        backgroundPanel.add(cpfField);
        backgroundPanel.add(Box.createVerticalStrut(15));
        backgroundPanel.add(confirmButton);

        // Adicionando o painel de fundo à janela principal
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    void addMenuItemListener(ActionListener listener, JMenu mainMenu) {
        for(Component target : mainMenu.getMenuComponents()) {
            if(target instanceof JMenuItem) {
                ((JMenuItem) target).addActionListener(listener);
            }
        }
    }

    void addMenuListeners(ActionListener listener) {
        for(Component menu : this.getJMenuBar().getComponents()) {
            if(menu instanceof JMenu) {
                addMenuItemListener(listener, (JMenu) menu);
            }
        }
    }

    public void exitInterface() {
        client.disconnect();
        System.exit(NORMAL);
    }
}
