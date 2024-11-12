/*
 * Main class that initializes the client graphical interface and the client for the voting system.
 * */

package client;

import client.gui.ClientMainWindow;

import java.awt.*;

public class ClientMain {
    public static void main(String[] args) {
        try {
            (new ClientMainWindow(new VotingClient())).initInterface();
        } catch (HeadlessException e) {
            System.out.println("Exceção do tipo HeadLessException capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exceção genérica capturada: " + e.getMessage());
        }
    }
}
