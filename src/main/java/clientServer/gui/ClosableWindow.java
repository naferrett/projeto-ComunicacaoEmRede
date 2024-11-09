/*
* Interface que define o método exitInterface, herdados pelas classes ClientMainWindow e ServerMainWindow da classe BaseWindow para implementar o comportamento específico
* de encerramento da interface. A interface permite que essas classes compartilhem uma implementação comum de gerenciamento de eventos de janela por meio da
* classe WindowListenerHandler.
*/

package clientServer.gui;

public interface ClosableWindow {
    void exitInterface();
}
