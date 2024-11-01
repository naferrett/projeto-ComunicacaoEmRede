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
            // 2. accept() bloqueia até um cliente se conectar
            //Socket clientSocket = serverSocket.accept();
            // 3. Quando um cliente conecta, cria um ClientSocket para gerenciar a comunicação
            //ClientSocket client = new ClientSocket(clientSocket);
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());  // Aguarda conexões
            clientSocketList.add(clientSocket);

            // Enviar pacote de votação ao cliente
            new Thread(() -> sendVotingPackage(clientSocket)).start();

            // Iniciar thread para escutar votos do cliente (e imprimir na tela)
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    private void sendVotingPackage(ClientSocket clientSocket) {
        try {
            // envia o objeto pela rede até o cliente
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            outputStream.writeObject(pollPackage);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar pacote de votação: " + e.getMessage());
        }
    }


//    private void clientMessageLoop(ClientSocket clientSocket) {
//        try {
//            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream());
//
//            while (true) {
//                String[] data = (String[]) inputStream.readObject();
//                String cpf = data[0];
//                String vote = data[1];
//
//                if (votes.containsKey(cpf)) {
//                    System.out.println("Voto rejeitado. CPF " + cpf + " já votou.");
//                    sendMessageToClient(clientSocket, "Voto rejeitado. CPF já registrado.");
//                } else {
//                    votes.put(cpf, vote);
//                    System.out.println("Voto registrado. CPF: " + cpf + " Votou: " + vote);
//                    sendMessageToClient(clientSocket, "Voto registrado com sucesso!");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Cliente desconectado: " + clientSocket.getSocket().getRemoteSocketAddress());
//        } catch (ClassNotFoundException e) {
//            System.out.println("Erro de classe não encontrada ao processar voto: " + e.getMessage());
//        } finally {
//            clientSocket.close();
//        }
//    }



    private void sendMessageToClient(ClientSocket clientSocket, String message) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem ao cliente: " + e.getMessage());
        }
    }

    // interface
    private boolean verifyClientCPF(String cpf) {
        return !votes.containsKey(cpf); // Retorna true se o CPF ainda não foi usado
    }


    private void clientMessageLoop(ClientSocket clientSocket) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            while (true) {
                String cpf = (String) inputStream.readObject();
                boolean isCpfValid = verifyClientCPF(cpf);

                outputStream.writeObject(isCpfValid);
                outputStream.flush();
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
