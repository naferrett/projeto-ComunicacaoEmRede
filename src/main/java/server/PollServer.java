package server;

import clientServer.Poll;
import lombok.Getter;
import lombok.Setter;
import server.gui.ServerMainWindow;
import server.reports.FinalReport;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.*;
import java.util.List;

public class PollServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clientSocketList = new LinkedList<>();
    @Setter
    @Getter
    private Poll pollPackage;
    @Getter
    private final Map<String, String> votes = new HashMap<>(); //{(cpf, voto), (cpf, voto)}
    @Getter
    private boolean serverRunning;


    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("0.0.0.0"));
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

    //contagem dos votos vai ocorrer aqui
    public Map<String,Integer> getVoteCounts(){
        Map<String, Integer> voteCounts = new HashMap<>();

        for(String vote : votes.values()){//pega as escolhas de cada cpf dos votos
            if(voteCounts.containsKey(vote)){//verifica se a escolha ja esta no novo map
                voteCounts.put(vote,voteCounts.get(vote)+1);//incrementa a contagem dos votos na escolha caso ja esteja no map
            } else{
                voteCounts.put(vote,1);//inicia a contagem dos votos com 1
            }
        }
        return voteCounts;//retorna o map
    }

    private boolean verifyClientCPF(String cpf) {
        return !votes.containsKey(cpf);
    }

    private void clientVoteLoop(ClientSocket clientSocket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getSocket().getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getSocket().getInputStream());

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
            (new FinalReport()).generateReport(pollPackage);
        } catch (IOException e) {
            System.out.println("Erro ao fechar o servidor: " + e.getMessage());
        }
    }

    //public boolean getServerRunning() {
//        return serverRunning;
//    }

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
