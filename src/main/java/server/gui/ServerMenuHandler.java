/*
 * A classe ServerMenuHandler é responsável por criar e adicionar o menus de ajuda à barra de menus da janela principal do servidor.
 */

package server.gui;

import clientServer.gui.MenuHandler;
import lombok.Getter;

import javax.swing.*;

public class ServerMenuHandler extends MenuHandler {

    @Getter
    private JMenuItem newPollItem;
    @Getter
    private JMenuItem closePollItem;
    ServerMainWindow mainWindow;

    public ServerMenuHandler(ServerMainWindow mainWindow) {
        super(mainWindow);
        this.mainWindow = mainWindow;
    }

    public void createAddToMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createPollMenu());
        menuBar.add(super.createHelpMenu());
        mainWindow.setJMenuBar(menuBar);
    }

    private JMenu createPollMenu() {
        JMenu pollMenu = new JMenu("Votação");
        pollMenu.add(createNewPollItem());
        pollMenu.add(createClosePollItem());

        return pollMenu;
    }

    private JMenuItem createNewPollItem() {
        newPollItem = new JMenuItem("Nova Votação");
        return newPollItem;
    }

    private JMenuItem createClosePollItem() {
        closePollItem = new JMenuItem("Encerrar Votação");
        return closePollItem;
    }
}
