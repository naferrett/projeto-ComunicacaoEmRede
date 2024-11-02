package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.gui.ClientMainWindow;
import clientServer.Poll;
import server.VotingServer;

public class VotingClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private Socket socket;
    private Poll votingPackage;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void start() throws IOException {
        try {
            socket = new Socket(SERVER_ADDRESS, VotingServer.PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado ao servidor de votação.");
        } catch (IOException e) {
            System.out.println("Erro na conexão com o servidor: " + e.getMessage());
        }
    }

    public boolean sendCPFToVerification(String cpf) {
        try {
            outputStream.writeObject(cpf);  // Envia o CPF
            outputStream.flush();

            return (boolean) inputStream.readObject();  // Recebe a confirmação do servidor
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }


    public Poll receiveVotingPackage() {
        try {
            // Recebe o pacote de votação do servidor
            votingPackage = (Poll) inputStream.readObject();
            return votingPackage;
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
                System.out.println("Conexão com o servidor encerrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            (new ClientMainWindow(new VotingClient())).initInterface();
        } catch (Exception e) {
            e.printStackTrace();
        } //catch (HeadlessException ex) {
//            log.error("Exceção do tipo HeadLessException capturada: " + ex);
//        } catch (Exception ex) {
//            log.error("Exceção genérica capturada: " + ex);
//        }
    }
}
