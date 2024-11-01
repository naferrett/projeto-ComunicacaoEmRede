package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

import client.gui.MainWindow;
import clientServer.Poll;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.VotingServer;

public class VotingClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private Socket socket;
    @Getter
    private Poll votingPackage;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void start() throws IOException {
        try {
            socket = new Socket(SERVER_ADDRESS, VotingServer.PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado ao servidor de votação.");

            votingPackage = receiveVotingPackage();
            if (votingPackage == null) {
                System.out.println("Falha ao receber o pacote de votação. Encerrando conexão.");
                return;
            }

            System.out.println("Pacote recebido.");


        } catch (IOException e) {
            System.out.println("Erro na conexão com o servidor: " + e.getMessage());
        } //finally {
            //disconnect();
        //}
    }

    private Poll receiveVotingPackage() {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            return (Poll) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao receber pacote de votação: " + e.getMessage());
            return null;
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
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Conexão com o servidor encerrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            //new VotingClient().start();
            (new MainWindow(new VotingClient())).initInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
