package server.gui;

import clientServer.gui.BaseWindow;
import server.PollServer;

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