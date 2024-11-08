package server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import clientServer.gui.SystemInfo;
import server.PollServer;

public class ServerEventListener implements ActionListener {
    private final ServerMainWindow serverWindow;
    private final ServerMenuHandler menuHandler;
    private final PollServer server;

    ServerEventListener(ServerMainWindow mainWindow, ServerMenuHandler menuHandler, PollServer server) {
        this.serverWindow = mainWindow;
        this.menuHandler = menuHandler;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == menuHandler.getNewPollItem()) {
            serverWindow.setStatusMessage("Opção 'Nova Votação' selecionada!");
            showNewPollWindow();
        }

        if (event.getSource() == menuHandler.getClosePollItem()) {
            serverWindow.setStatusMessage("Opção 'Encerrar Votação' selecionada!");
            serverWindow.exitInterface();
        }
    }

    private void showNewPollWindow() {
        (new NewPollWindow(serverWindow, "Nova Votação - " + SystemInfo.getVersionName(), SystemInfo.getInstructionsToAddPool(), serverWindow, serverWindow,server)).setVisible(true);
    }

}
