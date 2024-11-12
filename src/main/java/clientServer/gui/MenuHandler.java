/*
 * The MenuHandler class is responsible for creating and adding help menus to the main window's menu bar of the client and server.
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