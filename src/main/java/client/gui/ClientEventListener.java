/*
 * A classe ServerEventListener lida com os eventos de ação disparados pelos menus e itens de interface.
 * Essa classe implementa a interface ActionListener, respondendo a comandos como abrir/fechar arquivos, alterar padrões e cores de fundo,
 * mudar a velocidade de animação e exibir mensagens de ajuda ou sobre a aplicação.
 */


package client.gui;

import clientServer.gui.BaseWindow;
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
//        if (event.getSource() == menuHandler.getNewPollItem()) {
//            mainWindow.setStatusMessage("Opção 'Nova Votação' selecionada!");
//            showNewPollWindow();
//        }
//        if (event.getSource() == menuHandler.getClosePollItem()) {
//            mainWindow.setStatusMessage("Opção 'Encerrar Votação' selecionada!");
//            closePollWindow();
//        }
//        if (event.getSource() == menuHandler.getExitItem()) {
//            mainWindow.setStatusMessage("Opção 'Sair' selecionada!");
//            mainWindow.initInterface();
//        }
//        if (event.getSource() == menuHandler.getResultReportItem()) {
//            mainWindow.setStatusMessage("Opção 'Resultados' selecionada!");
//            showResultsReport();
//        }
//        if (event.getSource() == menuHandler.getVotersReportItem()) {
//            mainWindow.setStatusMessage("Opção 'Eleitores' selecionada!");
//            showVotersReport();
//        }
        if (event.getSource() == menuHandler.getMenuItemHelp()) {
            mainWindow.setStatusMessage("Opção 'Ajuda' selecionada!");
            showHelpMessage();
        }
        if (event.getSource() == menuHandler.getMenuItemCredits()) {
            mainWindow.setStatusMessage("Opção 'Créditos' selecionada!");
            showCreditsMessage();
        }
    }

//    private void showNewPollWindow() {
//        (new NewPollWindow(mainWindow, "Nova Votação - " + SystemInfo.getVersionName(), SystemInfo.getInstructionsToAddPool(), mainWindow)).setVisible(true);
//    }

    private void closePollWindow() {
    }

    private void showResultsReport() {
    }

    private void showVotersReport() {
    }

    private void showHelpMessage() {
        (new MessageWindow(mainWindow, "Ajuda - " + SystemInfo.getVersionName(), SystemInfo.getHelp())).setVisible(true);
    }

    private void showCreditsMessage() {
        (new MessageWindow(mainWindow, "Créditos - " + SystemInfo.getVersionName(), SystemInfo.getCredits())).setVisible(true);
    }

}