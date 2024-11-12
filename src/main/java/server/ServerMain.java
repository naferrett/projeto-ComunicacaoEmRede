/*
 * Start the voting server and the server's graphical interface, handling any exceptions that may occur during execution.
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
