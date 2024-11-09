/*
 * Iniciar o servidor de votação e a interface gráfica do servidor, lidando com exceções que possam ocorrer durante a execução.
*/


package server;

import server.gui.ServerMainWindow;

import java.awt.*;

public class ServerMain {
    public static void main(String[] args) {
        try {
            (new ServerMainWindow(new PollServer())).initInterface();
        } catch (HeadlessException e) {
            System.out.println("Exceção do tipo HeadLessException capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exceção genérica capturada: " + e.getMessage());
        }
    }
}
