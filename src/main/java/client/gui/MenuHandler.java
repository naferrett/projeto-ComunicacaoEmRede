/*
 * A classe MenuHandler é responsável por criar e adicionar os menus à barra de menus da janela principal,
 * inserindo os menus de Arquivo, Configuração e Ajuda.
 */

package client.gui;

import lombok.Getter;

import javax.swing.*;

public class MenuHandler {
    @Getter
    private JMenuItem newPollItem;

    @Getter
    private JMenuItem closePollItem;

    @Getter
    private JMenuItem exitItem;

    @Getter
    private JMenuItem votersReportItem;

    @Getter
    private JMenuItem resultReportItem;

    @Getter
    private JMenuItem menuItemCredits;

    @Getter
    private JMenuItem menuItemHelp;

    private final MainWindow mainWindow;

    public MenuHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void createAddToMenu() {
        JMenuBar menuBar = new JMenuBar();

        //menuBar.add(createPoolMenu());
        //menuBar.add(createReportsMenu());
        menuBar.add(createHelpMenu());

        mainWindow.setJMenuBar(menuBar);
    }

    private JMenu createPoolMenu() {
        JMenu poolMenu = new JMenu("Votação");
        poolMenu.add(createNewPoolItem());
        poolMenu.add(createClosePoolItem());
        poolMenu.addSeparator();
        poolMenu.add(createExitItem());
        return poolMenu;
    }

    private JMenuItem createNewPoolItem() {
        newPollItem = new JMenuItem("Nova Votação");
        newPollItem.setActionCommand("New Pool");
        return newPollItem;
    }

    private JMenuItem createClosePoolItem() {
        closePollItem = new JMenuItem("Encerrar Votação");
        closePollItem.setActionCommand("Close Pool");
        return closePollItem;
    }

    private JMenuItem createExitItem() {
        exitItem = new JMenuItem("Sair");
        exitItem.setActionCommand("Exit");
        return exitItem;
    }

    private JMenu createReportsMenu() {
        JMenu reportsMenu = new JMenu("Relatórios");

        reportsMenu.add(createVotersReportItem());
        reportsMenu.add(createResultReportItem());

        return reportsMenu;
    }

    private JMenuItem createVotersReportItem() {
        votersReportItem = new JMenuItem("Eleitores");
        votersReportItem.setActionCommand("Voters Report");
        return votersReportItem;
    }

    private JMenuItem createResultReportItem() {
        resultReportItem = new JMenuItem("Resultados");
        resultReportItem.setActionCommand("Results Report");
        return resultReportItem;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Ajuda");

        helpMenu.add(createHelpItem());
        helpMenu.add(createCreditsItem());

        return helpMenu;
    }

    private JMenuItem createHelpItem() {
        menuItemHelp = new JMenuItem("Ajuda");
        menuItemHelp.setActionCommand("Help Message");
        return menuItemHelp;
    }

    private JMenuItem createCreditsItem() {
        menuItemCredits = new JMenuItem("Créditos");
        menuItemCredits.setActionCommand("Credits Message");
        return menuItemCredits;
    }
}