package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import clientServer.Poll;

public class VotingClient {
    private final String serverAddress = "172.29.240.1"; //This is the computer's IP address. It should be changed depending on the IP of the computer hosting the server.
    public final int port = 4000;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void start() {
        try {
            socket = new Socket(serverAddress, port);
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
}
