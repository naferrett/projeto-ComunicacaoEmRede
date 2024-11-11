/*
 * A classe ServerEventListener lida com os eventos de ação disparados pelos menus da interface do servidor.
 */

package server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clientServer.gui.EventListener;
import clientServer.gui.SystemInfo;
import server.PollServer;

public class ServerEventListener extends EventListener implements ActionListener {
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
        (new NewPollWindow("Nova Votação - " + SystemInfo.getVersionName(), SystemInfo.getInstructionsToAddPool(), mainWindow, mainWindow,server)).setVisible(true);
    }

}
