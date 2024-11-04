package client;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.gui.ClientMainWindow;
import clientServer.Poll;
import server.PollServer;

public class VotingClient {
    private static final String SERVER_ADDRESS = "172.29.240.1"; // 127.0.0.1
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, PollServer.PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Erro na conexão com o servidor: " + e.getMessage());
        }
    }

    public boolean sendCPFToVerification(String cpf) {
        try {
            outputStream.writeObject(cpf);
            outputStream.flush();

            return (boolean) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }


    public Poll receiveVotingPackage() {
        try {
            return (Poll) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao receber pacote de votação: " + e.getMessage());
            return null;
        }
    }

    public void sendVote(String vote) {
        try {
            outputStream.writeObject(vote);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar voto: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (outputStream != null) {
                outputStream.writeObject("Client disconnected.");
                outputStream.flush();
            }

            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

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
