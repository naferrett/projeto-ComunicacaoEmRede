/*
* Classe principal que inicializa a interface gráfica do cliente e o cliente para o sistema de votação,
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
