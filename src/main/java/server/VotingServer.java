package server;

import teste.ClientSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VotingServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clientSocketList = new LinkedList<>();
    private VotingPackage votingPackage;
    private final Map<String, String> votes = new HashMap<>(); // Armazena CPF e voto

    public VotingServer() {
        this.votingPackage = new VotingPackage(
                "Qual é a sua linguagem de programação favorita?",
                List.of("Java", "Python", "JavaScript", "C++")
        );
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor de Votação iniciado na porta " + PORT);
            clientConnectionLoop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            clientSocketList.add(clientSocket);

            // Enviar pacote de votação ao cliente
            new Thread(() -> sendVotingPackage(clientSocket)).start();

            // Iniciar thread para escutar votos do cliente
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    private void sendVotingPackage(ClientSocket clientSocket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            outputStream.writeObject(votingPackage);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar pacote de votação: " + e.getMessage());
        }
    }

    private void clientMessageLoop(ClientSocket clientSocket) {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream())) {
            while (true) {
                String[] data = (String[]) inputStream.readObject();
                String cpf = data[0];
                String vote = data[1];

                if (votes.containsKey(cpf)) {
                    System.out.println("Voto rejeitado. CPF " + cpf + " já votou.");
                    sendMessageToClient(clientSocket, "Voto rejeitado. CPF já registrado.");
                } else {
                    votes.put(cpf, vote);
                    System.out.println("Voto registrado. CPF: " + cpf + " Votou: " + vote);
                    sendMessageToClient(clientSocket, "Voto registrado com sucesso!");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar voto do cliente: " + e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    private void sendMessageToClient(ClientSocket clientSocket, String message) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem ao cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        VotingServer server = new VotingServer();
        server.start();
    }
}
