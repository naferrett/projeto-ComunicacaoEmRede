/*
 * A classe ServerMenuHandler é responsável por criar e adicionar os menus à barra de menus da janela principal,
 * inserindo os menus de Arquivo, Configuração e Ajuda.
 */

package client.gui;

import clientServer.gui.BaseWindow;
import lombok.Getter;

import javax.swing.*;

public class ClientMenuHandler {
    @Getter
    private JMenuItem menuItemCredits;
    @Getter
    private JMenuItem menuItemHelp;
    private final ClientMainWindow mainWindow;

    public ClientMenuHandler(ClientMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void createAddToMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createHelpMenu());
        mainWindow.setJMenuBar(menuBar);
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Ajuda");

        helpMenu.add(createHelpItem());
        helpMenu.add(createCreditsItem());

        return helpMenu;
    }

    private JMenuItem createHelpItem() {
        menuItemHelp = new JMenuItem("Ajuda");
        return menuItemHelp;
    }

    private JMenuItem createCreditsItem() {
        menuItemCredits = new JMenuItem("Créditos");
        return menuItemCredits;
    }
}