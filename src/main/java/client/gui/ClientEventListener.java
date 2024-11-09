/*
 * A classe ClientEventListener lida com os eventos de ação disparados pelos menus da interface do cliente.
 */

package client.gui;

import clientServer.gui.SystemInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientEventListener implements ActionListener {
    private final ClientMainWindow mainWindow;
    private final ClientMenuHandler menuHandler;

    ClientEventListener(ClientMainWindow mainwindow, ClientMenuHandler menuHandler) {
        this.mainWindow = mainwindow;
        this.menuHandler = menuHandler;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == menuHandler.getMenuItemHelp()) {
            mainWindow.setStatusMessage("Opção 'Ajuda' selecionada!");
            showHelpMessage();
        }
        if (event.getSource() == menuHandler.getMenuItemCredits()) {
            mainWindow.setStatusMessage("Opção 'Créditos' selecionada!");
            showCreditsMessage();
        }
    }

    private void showHelpMessage() {
        (new MessageWindow(mainWindow, "Ajuda - " + SystemInfo.getVersionName(), SystemInfo.getHelp())).setVisible(true);
    }

    private void showCreditsMessage() {
        (new MessageWindow(mainWindow, "Créditos - " + SystemInfo.getVersionName(), SystemInfo.getCredits())).setVisible(true);
    }

}