package guiApp;

import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MainWindow extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private JLabel labelStatus;
    private JLabel poolTitle;
    private JTextField cpfField;
    private JButton confirmButton;
    private List<String> poolOptions;
    private JScrollPane scrollPane;

    MainWindow() throws HeadlessException {
        super(SystemInfo.getVersionName());

        poolOptions = new ArrayList<>();

        windowConfig();
        initAddComponents();

        MenuHandler menuHandler = new MenuHandler(this);
        menuHandler.createAddToMenu();

        EventListener eventListener = new EventListener(this, menuHandler);
        this.addMenuListeners(eventListener);
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

    public void setPoolTitle(String message) {
        this.poolTitle.setText(message);
    }

    public void setBackGround() {
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Mensagem de instrução
        JLabel instructionLabel = new JLabel("Para votar, digite o CPF (números) e confirme:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label "CPF"
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo de texto para CPF
        cpfField = new JTextField(15);
        cpfField.setMaximumSize(new Dimension(200, 25));
        cpfField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de confirmação
        confirmButton = new JButton("Confirmar");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> {
            String cpf = cpfField.getText();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira o CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                // Exibe a PoolWindow com as opções
                PoolWindow poolWindow = new PoolWindow(this, "Título da Votação", poolOptions);
                poolWindow.setVisible(true);
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


    public List<String> getPoolOptions() {
        return poolOptions;
    }

    private void initAddComponents() {
        initStatusPanel();
        setBackGround();
        initWindowListener();

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

    void initInterface() {
        this.setStatusMessage(SystemInfo.university);
        this.setVisible(true);
    }

    private void initWindowListener() {
        WindowListenerHandler windowEventListener = new WindowListenerHandler(this);
        this.addWindowListener(windowEventListener);
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

    void exitInterface() {
        System.exit(NORMAL);
    }
}
