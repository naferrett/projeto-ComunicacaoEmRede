/*
 * The ServerMainWindow class represents the main interface of the voting server.
 * It manages the display of the list of voting options and the real-time vote count for each option.
 * It also handles user interaction, such as adding new polls and shutting down the server.
 */

package server.gui;

import clientServer.gui.BaseWindow;
import lombok.Getter;
import server.PollServer;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class ServerMainWindow extends BaseWindow {
    private final PollServer server;
    @Getter
    private JPanel backgroundPanel;
    private final Map<String, JLabel> voteCountsLabels = new HashMap<>();

    public ServerMainWindow(PollServer pollServer) {
        super();

        this.server = pollServer;
        new Thread(() -> server.startServer()).start();

        initAddComponents();
    }

    public void initAddComponents() {
        initStatusPanel();
        initWindowListener();
        initServerMenu();
        initBackGround();
    }

    public void initBackGround() {
        backgroundPanel = createBackgroundPanel();
        addVoteCountLabels(backgroundPanel);

        JScrollPane scrollPane = createScrollPane(backgroundPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        startVoteCountUpdater();
    }

    public void addVoteCountLabels(JPanel panel){
        panel.removeAll();

        if (server.getPollPackage() == null) {
            JLabel noVotesLabel = new JLabel("Nenhuma votação registrada ainda.");
            noVotesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(noVotesLabel);
        } else {
            for (String option : server.getPollPackage().options()) {
                JLabel voteLabel = new JLabel(option + ": 0 votos");
                voteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                voteCountsLabels.put(option, voteLabel);
                panel.add(voteLabel);
            }
        }
    }

    private void startVoteCountUpdater(){
        Timer timer = new Timer(1000, e->updateVoteCounts());
        timer.start();
    }

    private void updateVoteCounts(){
        Map<String, Integer> voteCounts = server.getVoteCounts();

        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            String option = entry.getKey();
            int count = entry.getValue();

            JLabel voteLabel = voteCountsLabels.get(option);
            if (voteLabel != null) {
                voteLabel.setText(option + ": " + count + " votos");
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

    public void initServerMenu() {
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