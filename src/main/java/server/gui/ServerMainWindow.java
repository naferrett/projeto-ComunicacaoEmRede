package server.gui;

import clientServer.gui.BaseWindow;
import server.PollServer;

import javax.swing.*;
import java.awt.*;

public class ServerMainWindow extends BaseWindow {
    private final PollServer server;

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

    public void initBackGround() {
        JPanel backgroundPanel = createBackgroundPanel();
        //criar os outros métodos para adicionar no background e adicionar aqui..
        // ex:
        JLabel exemplo = createLabelExemplo();
        addComponentsToBackgroundPanel(backgroundPanel, exemplo);

        //o scrollPane é aquela barrinha de rolagem na lateral, é um container externo que envolve o JPanel, por isso ele fica por último
        JScrollPane scrollPane = createScrollPane(backgroundPanel);
        this.add(scrollPane, BorderLayout.CENTER);
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