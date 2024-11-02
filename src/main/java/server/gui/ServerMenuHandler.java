package server.gui;

import lombok.Getter;

import javax.swing.*;

public class ServerMenuHandler {

    @Getter
    private JMenuItem newPollItem;

    @Getter
    private JMenuItem closePollItem;

    @Getter
    private JMenuItem exitItem;
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
        poolMenu.addSeparator();
        poolMenu.add(createExitItem());
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

    private JMenuItem createExitItem() {
        exitItem = new JMenuItem("Sair");
        exitItem.setActionCommand("Exit");
        return exitItem;
    }

}
