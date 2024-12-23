/*
 * The EventListener class handles action events triggered by the menus of the client and server interface.
 */

package clientServer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventListener implements ActionListener {
    private final BaseWindow mainWindow;
    private final MenuHandler menuHandler;

    public EventListener(BaseWindow mainwindow, MenuHandler menuHandler) {
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