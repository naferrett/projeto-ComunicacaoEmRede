package server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import clientServer.gui.SystemInfo;

public class ServerEventListener implements ActionListener {
    private final ServerMainWindow mainWindow;
    private final ServerMenuHandler menuHandler;

    ServerEventListener(ServerMainWindow mainWindow, ServerMenuHandler menuHandler) {
        this.mainWindow = mainWindow;
        this.menuHandler = menuHandler;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == menuHandler.getNewPollItem()) {
            mainWindow.setStatusMessage("Opção 'Nova Votação' selecionada!");
            showNewPollWindow();
        }
        if (event.getSource() == menuHandler.getClosePollItem()) {
            mainWindow.setStatusMessage("Opção 'Encerrar Votação' selecionada!");
            closePollWindow();
        }
        if (event.getSource() == menuHandler.getExitItem()) {
            mainWindow.setStatusMessage("Opção 'Sair' selecionada!");
            mainWindow.exitInterface();
        }
    }

    private void showNewPollWindow() {
        (new NewPollWindow(mainWindow, "Nova Votação - " + SystemInfo.getVersionName(), SystemInfo.getInstructionsToAddPool(), mainWindow)).setVisible(true);
    }

    private void closePollWindow() {
    }
}
