package server;

import client.ClientSocket;
import clientServer.Poll;

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
    private Poll pollPackage;
    private final Map<String, String> votes = new HashMap<>(); // Armazena CPF e voto
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public VotingServer() {
        this.pollPackage = new Poll(
                "Qual é a sua linguagem de programação favorita?",
                List.of("Java", "Python", "JavaScript", "C++")
        );
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT); // Cria socket do servidor na porta 4000
            System.out.println("Servidor de Votação iniciado na porta " + PORT);
            clientConnectionLoop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());  // Aguarda conexões
            clientSocketList.add(clientSocket);

            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    private boolean verifyClientCPF(String cpf) {
        return !votes.containsKey(cpf); // Retorna true se o CPF ainda não foi usado
    }

    private void clientMessageLoop(ClientSocket clientSocket) {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream());

            // 1. Recebe o CPF do cliente para verificar
            String cpf = (String) inputStream.readObject();
            boolean isCpfValid = verifyClientCPF(cpf);

            // 2. Envia a resposta sobre a validade do CPF
            outputStream.writeObject(isCpfValid);
            outputStream.flush();

            if(isCpfValid) {
                // 3. Se o CPF for válido, envia o pacote de votação
                outputStream.writeObject(pollPackage);
                outputStream.flush();

                // 4. Recebe o voto do cliente e armazena no mapa de votos
                String vote = (String) inputStream.readObject();
                votes.put(cpf, vote);
                System.out.println("Voto registrado. CPF: " + cpf + " Votou: " + vote);
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar cliente: " + e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    public static void main(String[] args) {
        VotingServer server = new VotingServer();
        server.start();
    }
}
