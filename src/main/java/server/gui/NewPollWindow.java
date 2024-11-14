/*
 * The NewPollWindow class represents a window for creating a new poll by the server.
 * This class allows the user to enter the poll title and options, validating if there are duplicates.
 * Upon confirming the poll, the new poll is sent to the server, and the voting options are displayed in the server's interface.
 */

package server.gui;

import clientServer.Poll;
import server.PollServer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class NewPollWindow extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    private JPanel pollPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JButton sendPollButton;
    private JButton confirmPollOptionButton;
    private JTextField pollTitle;
    private JTextField pollOption;
    private final List<String> pollOptions = new ArrayList<>();
    private JTextArea textArea;
    private final PollServer server;
    private final ServerMainWindow serverWindow;

    NewPollWindow(String title, String text, ServerMainWindow serverWindow, PollServer server) throws HeadlessException {
        super(serverWindow, title);
        windowConfig();

        this.serverWindow = serverWindow;
        this.server = server;

        createAddComponents(text);
    }

    public void windowConfig() {
        this.setSize(600, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(serverWindow);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    private JPanel createPoolPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBorder(new TitledBorder(new LineBorder(Color.gray), "Nova Votação"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = createScrollPane(textArea);
        panel.add(scrollPane);

        panel.add(inputPanel);

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

    private void createTextPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = createBasePanel();

        JPanel titlePanel = createTitlePanel();
        JPanel optionPanel = createOptionPanel();
        JPanel optionButtonPanel = createOptionButtonPanel();

        inputPanel.add(titlePanel);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(optionPanel);
        inputPanel.add(optionButtonPanel);

        return inputPanel;
    }

    private JPanel createBasePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel insertTitle = new JLabel("Insira o título da votação:");
        insertTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pollTitle = new JTextField(20);

        titlePanel.add(insertTitle, BorderLayout.NORTH);
        titlePanel.add(pollTitle, BorderLayout.CENTER);

        return titlePanel;
    }

    private JPanel createOptionPanel() {
        JPanel optionPanel = new JPanel(new BorderLayout());
        JLabel insertOptions = new JLabel("Insira as opções da votação:");
        insertOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
        pollOption = new JTextField(20);

        optionPanel.add(insertOptions, BorderLayout.NORTH);
        optionPanel.add(pollOption, BorderLayout.CENTER);

        return optionPanel;
    }

    private JPanel createOptionButtonPanel() {
        JPanel optionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmPollOptionButton = new JButton("Enviar Opção");
        confirmPollOptionButton.addActionListener(this);
        optionButtonPanel.add(confirmPollOptionButton);

        return optionButtonPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        cancelButton = new JButton("Cancelar");
        sendPollButton = new JButton("Confirmar");

        cancelButton.addActionListener(this);
        sendPollButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(sendPollButton);

        return buttonPanel;
    }

    private void createAddComponents(String text) {
        textArea = createTextArea(text);
        createTextPanel(createScrollPane(textArea));

        inputPanel = createInputPanel();
        pollPanel = createPoolPanel();
        buttonPanel = createButtonPanel();

        formatTextArea();
        setLayout(new BorderLayout());

        add(pollPanel, BorderLayout.CENTER);
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

    private boolean findDuplicateOptions(List<String> options) {
        Set<String> findDuplicate = new HashSet<>();
        for (String option : options) {
            if (!findDuplicate.add(option)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmPollOptionButton) {
            pollOptions.add(pollOption.getText());

            if(findDuplicateOptions(pollOptions)) {
                JOptionPane.showMessageDialog(serverWindow, "A lista de opções de votação não aceita duplicatas.", "Erro", JOptionPane.ERROR_MESSAGE);
                pollOptions.remove(pollOption.getText());
            }
            pollOption.setText("");
        }

        if (event.getSource() == sendPollButton) {
            Poll newPoll = new Poll(pollTitle.getText(), pollOptions);
            this.server.setPollPackage(newPoll);

            JPanel backgroundPanel = serverWindow.getBackgroundPanel();
            serverWindow.addVoteCountLabels(backgroundPanel);

            backgroundPanel.revalidate();
            backgroundPanel.repaint();

            setVisible(false);
        }

        if (event.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }
}
