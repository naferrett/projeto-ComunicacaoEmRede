/*
 * The class manages the voting server, allowing multiple clients to connect
 * to vote, validating their CPFs and recording the votes. Upon shutting down the server, it generates a
 * final report with the election results.
 */

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
    public final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clientSocketList = new LinkedList<>();
    @Setter
    @Getter
    private Poll pollPackage;
    @Getter
    private final Map<String, String> votes = new HashMap<>();
    @Getter
    private boolean serverRunning;

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT, 10, InetAddress.getByName("0.0.0.0"));
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
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            clientSocketList.add(clientSocket);

            new Thread(() -> clientVoteLoop(clientSocket)).start();
        }
    }

    private boolean verifyClientCPF(String cpf) {
        return !votes.containsKey(cpf);
    }

    private void clientVoteLoop(ClientSocket clientSocket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.socket().getInputStream());

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
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar cliente: " + e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    public Map<String,Integer> getVoteCounts(){
        Map<String, Integer> voteCounts = new HashMap<>();

        for(String vote : votes.values()){ // Gets every voters' choice
            if(voteCounts.containsKey(vote)){ // Verifies if the choice is already in the HashMap
                voteCounts.put(vote,voteCounts.get(vote)+1); // Adds one more vote for the choice
            } else{
                voteCounts.put(vote,1); // Inits counting for an option
            }
        }

        return voteCounts;
    }

    public void closeServer() {
        try {
            serverRunning = false;
            serverSocket.close();
            FinalReport report = new FinalReport();
            report.setPollServer(this);
            report.generateReport(pollPackage);
        } catch (IOException e) {
            System.out.println("Erro ao fechar o servidor: " + e.getMessage());
        }
    }
}
