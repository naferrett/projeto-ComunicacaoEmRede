package server;

import client.VotingClient;
import client.gui.ClientMainWindow;
import clientServer.ClientSocket;
import clientServer.Poll;
import lombok.Getter;
import lombok.Setter;
import server.gui.ServerMainWindow;
import server.reports.FinalReport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class VotingServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clientSocketList = new LinkedList<>();

    @Getter
    private Poll pollPackage;
    private final Map<String, String> votes = new HashMap<>(); // Armazena CPF e voto
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    @Setter
    private boolean serverRunning;

//    public VotingServer() {
//        List<String> options = List.of("Java", "Python", "JavaScript", "C++");
//
//        if (duplicateOptions(options)) {
//            throw new IllegalArgumentException("A lista de opções de votação contém duplicatas.");
//        }
//
//        this.pollPackage = new Poll("Qual é a sua linguagem de programação favorita?", options);
//    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT); // Cria socket do servidor na porta 4000
            System.out.println("Servidor de Votação iniciado na porta " + PORT);
            serverRunning = true;

            while (serverRunning) {
                try {
                    clientConnection();
                } catch (SocketException e) {
                    if (serverRunning) {
                        throw new RuntimeException("Erro ao aceitar conexão", e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clientConnection() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());  // Aguarda conexões
            clientSocketList.add(clientSocket);

            new Thread(() -> clientVoteLoop(clientSocket)).start();
        }
    }

    private boolean verifyClientCPF(String cpf) {
        return !votes.containsKey(cpf);
    }

    private void clientVoteLoop(ClientSocket clientSocket) {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream());

            while (serverRunning) {
                String message = (String) inputStream.readObject();

                if ("Client disconnected.".equals(message)) {
                    break;
                }

                boolean validCpf = verifyClientCPF(message);

                outputStream.writeObject(validCpf);
                outputStream.flush();

                if(validCpf) {
                    outputStream.writeObject(pollPackage);
                    outputStream.flush();

                    String vote = (String) inputStream.readObject();
                    votes.put(message, vote);
                    System.out.println("Voto registrado. CPF: " + message + " Votou: " + vote);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar cliente: " + e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    public void closeServer() {
        try {
            serverRunning = false;
            serverSocket.close();
            (new FinalReport()).generateReport(pollPackage);  // Salva o relatório final
        } catch (IOException e) {
            System.out.println("Erro ao fechar o servidor: " + e.getMessage());
        }
    }

    public boolean getServerRunning() {
        return serverRunning;
    }

    public void setPollPackage(Poll newPoll) {
        this.pollPackage = newPoll;
    }


    public static void main(String[] args) {
        try {
            (new ServerMainWindow(new VotingServer())).initInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
