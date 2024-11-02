package server.gui;

import client.VotingClient;
import client.gui.ClientEventListener;
import client.gui.ClientMenuHandler;
import clientServer.gui.BaseWindow;
import clientServer.gui.SystemInfo;
import lombok.extern.log4j.Log4j2;
import server.VotingServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;

@Log4j2
public class ServerMainWindow extends BaseWindow {
    private final VotingServer server;

    public ServerMainWindow(VotingServer votingServer) {
        super();

        this.server = votingServer;
        server.startServer();
    }

    public void initInterface() {
        initStatusPanel();
        initWindowListener();
        initMenu();

        setVisible(true);
    }

    public void initMenu() {
        ServerMenuHandler menuHandler = new ServerMenuHandler(this);
        menuHandler.createAddToMenu();
        initEventListener(menuHandler);
    }

    public void initEventListener(ServerMenuHandler menuHandler) {
        ServerEventListener eventListener = new ServerEventListener(this, menuHandler);
        addMenuListeners(eventListener);
    }

    @Override
    public void exitInterface() {
        server.closeServer();
        super.exitInterface();
    }
}