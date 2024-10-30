package server.gui;

import client.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class NewPollWindow extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JPanel poolPanel;
    private final JPanel textPanel;
    private final JPanel inputPanel;
    private final JPanel buttonPanel;
    private JButton cancelButton;
    private JButton sendPoolButton;
    private JButton confirmPoolTitleButton;
    private JButton confirmPoolOptionButton;
    private JTextField poolTitle;
    private JTextField poolOption;
    private final JTextArea textArea;
    private client.gui.MainWindow mainWindow;

    NewPollWindow(JFrame window, String title, String text, MainWindow mainWindow) throws HeadlessException {
        super(window, title);

        this.mainWindow = mainWindow;

        setSize(600, 300);
        setResizable(false);
        setLocationRelativeTo(mainWindow);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);

        textArea = createTextArea(text);
        formatTextArea();

        JScrollPane scrollPane = createScrollPane(textArea);
        textPanel = createTextPanel(scrollPane);

        inputPanel = createInputPanel();

        poolPanel = createPoolPanel();
        buttonPanel = createButtonPanel();

        addComponents();
    }

    private JPanel createPoolPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBorder(new TitledBorder(new LineBorder(Color.gray), "Nova Votação"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Usando BoxLayout para empilhar componentes verticalmente

        // Adicionando o JScrollPane com a JTextArea ao poolPanel
        JScrollPane scrollPane = createScrollPane(textArea); // Crie o scrollPane com a textArea
        panel.add(scrollPane); // Adiciona o scrollPane ao poolPanel

        // Adicionando o inputPanel ao poolPanel
        panel.add(inputPanel); // Adiciona o inputPanel abaixo do scrollPane

        return panel;
    }

    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        return textArea;
    }

    private JScrollPane createScrollPane(JTextArea textArea) {
        return new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createTextPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        //panel.setBorder(new TitledBorder(new LineBorder(Color.gray), SystemInfo.name));
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para título da votação
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel insertTitle = new JLabel("Insira o título da votação:");
        insertTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        poolTitle = new JTextField(20);
        titlePanel.add(insertTitle, BorderLayout.NORTH);
        titlePanel.add(poolTitle, BorderLayout.CENTER);

        // Painel para botão de título
        JPanel titleButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmPoolTitleButton = new JButton("Enviar Título da Votação");
        confirmPoolTitleButton.addActionListener(this);
        titleButtonPanel.add(confirmPoolTitleButton);

        // Painel para opções da votação
        JPanel optionPanel = new JPanel(new BorderLayout());
        JLabel insertOptions = new JLabel("Insira as opções da votação:");
        insertOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
        poolOption = new JTextField(20);
        optionPanel.add(insertOptions, BorderLayout.NORTH);
        optionPanel.add(poolOption, BorderLayout.CENTER);

        // Painel para botão de opção
        JPanel optionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmPoolOptionButton = new JButton("Enviar Opção");
        confirmPoolOptionButton.addActionListener(this);
        optionButtonPanel.add(confirmPoolOptionButton);

        // Adicionando os painéis ao painel principal de entrada
        inputPanel.add(titlePanel);
        inputPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre título e botão
        inputPanel.add(titleButtonPanel);
        //inputPanel.add(Box.createVerticalStrut(15)); // Espaçamento entre título e opção
        inputPanel.add(optionPanel);
        //inputPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre opção e botão
        inputPanel.add(optionButtonPanel);

        return inputPanel;
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        cancelButton = new JButton("Cancelar");
        sendPoolButton = new JButton("Confirmar");

        cancelButton.addActionListener(this);
        sendPoolButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(sendPoolButton);

        return buttonPanel;
    }



    private void addComponents() {
        setLayout(new BorderLayout());
        add(poolPanel, BorderLayout.CENTER); // Alterado para CENTER se quiser centralizar todo o conteúdo
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void formatTextArea() {
        textArea.setForeground(Color.black);
        textArea.setBackground(Color.white);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.BOLD, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmPoolTitleButton) {
            mainWindow.setPoolTitle(poolTitle.getText());
        }

        if (event.getSource() == confirmPoolOptionButton) {
            mainWindow.getPoolOptions().add(poolOption.getText());
            poolOption.setText("");
        }

        if (event.getSource() == sendPoolButton) {
            // to-do
        }

        if (event.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }
}
