/*
 * A classe MenuHandler é responsável por criar e adicionar o menus de ajuda à barra de menus da janela principal do cliente e do servidor.
 */

package clientServer.gui;

import lombok.Getter;
import javax.swing.*;

public class MenuHandler {
    @Getter
    private JMenuItem menuItemCredits;
    @Getter
    private JMenuItem menuItemHelp;
    private final BaseWindow mainWindow;

    public MenuHandler(BaseWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void createAddToMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createHelpMenu());
        mainWindow.setJMenuBar(menuBar);
    }

    public JMenu createHelpMenu() {
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