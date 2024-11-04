package server.gui;

import lombok.Getter;

import javax.swing.*;

public class ServerMenuHandler {

    @Getter
    private JMenuItem newPollItem;
    @Getter
    private JMenuItem closePollItem;
    ServerMainWindow mainWindow;

    public ServerMenuHandler(ServerMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void createAddToMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createPoolMenu());
        mainWindow.setJMenuBar(menuBar);
    }

    private JMenu createPoolMenu() {
        JMenu poolMenu = new JMenu("Votação");
        poolMenu.add(createNewPollItem());
        poolMenu.add(createClosePollItem());

        return poolMenu;
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
