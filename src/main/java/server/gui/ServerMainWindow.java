package server.gui;

import clientServer.gui.BaseWindow;
import lombok.Getter;
import server.PollServer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ServerMainWindow extends BaseWindow {
    private final PollServer server;
    @Getter
    private JPanel backgroundPanel;
    private final Map<String, JLabel> voteCountsLabels = new HashMap<>();

    public ServerMainWindow(PollServer votingServer) {
        super();

        this.server = votingServer;
        new Thread(() -> server.startServer()).start();

        initAddComponents();
    }

    public void initAddComponents() {
        initStatusPanel();
        initWindowListener();
        initMenu();
        initBackGround();
    }

    // resultados
    public void initBackGround() {
        backgroundPanel = createBackgroundPanel();
        addVoteCountLabels(backgroundPanel);

        JScrollPane scrollPane = createScrollPane(backgroundPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.revalidate();
        backgroundPanel.repaint();

        startVoteCountUpdater();
    }

    public void addVoteCountLabels(JPanel panel){
        panel.removeAll();

        if (server.getPollPackage() == null) { // Verifica se há votos disponíveis
            JLabel noVotesLabel = new JLabel("Nenhuma votação registrada ainda.");
            noVotesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(noVotesLabel);
        } else {
            for (String option : server.getPollPackage().getOptions()) {
                JLabel voteLabel = new JLabel(option + ": 0 votos");
                voteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                voteCountsLabels.put(option, voteLabel); // Associa a opção com seu respectivo JLabel
                panel.add(voteLabel);
            }
        }
    }

    //vai ficar chamando a função update a cada 1000 milissegundos
    private void startVoteCountUpdater(){
        Timer timer = new Timer(1000, e->updateVoteCounts());
        timer.start();
    }

    //atualização dos votos vai ocorrer aqui
    private void updateVoteCounts(){
        Map<String, Integer> voteCounts = server.getVoteCounts();

        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            String option = entry.getKey();
            int count = entry.getValue();

            JLabel voteLabel = voteCountsLabels.get(option);
            if (voteLabel != null) {
                voteLabel.setText(option + ": " + count + " votos");
            } else {
                System.out.println("Nenhum label encontrado para a opção: " + option); // Debug
            }
        }
    }

    private JScrollPane createScrollPane(JPanel backgroundPanel) {
        return new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JLabel createLabelExemplo() {
        JLabel exemploLabel = new JLabel("Label de exemplo");
        exemploLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return exemploLabel;
    }

    private void addComponentsToBackgroundPanel(JPanel panel, JLabel exemplo) {
        //panel.add(Box.createVerticalStrut(10)); esse createVerticalStruct dá um espaço entre um elementos pra eles não ficarem colados, fica a seu critério se quiser usar
        //.. adicionar o resto dos componentes do backgroundPanel
        panel.add(exemplo);
    }

    public void initMenu() {
        ServerMenuHandler menuHandler = new ServerMenuHandler(this);
        menuHandler.createAddToMenu();
        initEventListener(menuHandler);
    }

    public void initEventListener(ServerMenuHandler menuHandler) {
        ServerEventListener eventListener = new ServerEventListener(this, menuHandler, server);
        addMenuListeners(eventListener);
    }

    @Override
    public void exitInterface() {
        if(server.isServerRunning()) {
            server.closeServer();
        }
        super.exitInterface();
    }
}