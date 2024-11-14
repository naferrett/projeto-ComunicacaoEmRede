/*
 * The ServerEventListener class handles action events triggered by the menus of the server interface.
 */

package server.gui;

import java.awt.event.ActionEvent;
import clientServer.gui.EventListener;
import clientServer.gui.SystemInfo;
import server.PollServer;

public class ServerEventListener extends EventListener {
    private final ServerMainWindow mainWindow;
    private final ServerMenuHandler menuHandler;
    private final PollServer server;

    ServerEventListener(ServerMainWindow mainWindow, ServerMenuHandler menuHandler, PollServer server) {
        super(mainWindow, menuHandler);

        this.mainWindow = mainWindow;
        this.menuHandler = menuHandler;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if (event.getSource() == menuHandler.getNewPollItem()) {
            mainWindow.setStatusMessage("Opção 'Nova Votação' selecionada!");
            showNewPollWindow();
        }

        if (event.getSource() == menuHandler.getClosePollItem()) {
            mainWindow.setStatusMessage("Opção 'Encerrar Votação' selecionada!");
            mainWindow.exitInterface();
        }
    }

    private void showNewPollWindow() {
        (new NewPollWindow("Nova Votação - " + SystemInfo.getVersionName(), SystemInfo.getInstructionsToAddPool(), mainWindow, server)).setVisible(true);
    }

}
