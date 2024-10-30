package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;
import server.VotingPackage;
import server.VotingServer;

public class VotingClient implements Runnable {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private Socket socket;
    private ObjectOutputStream outputStream;

    public void start() throws IOException {
        try {
            socket = new Socket(SERVER_ADDRESS, VotingServer.PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Conectado ao servidor de votação.");

            // Receber o pacote de votação e exibir as opções ao usuário
            VotingPackage votingPackage = receiveVotingPackage();
            if (votingPackage == null) {
                System.out.println("Falha ao receber o pacote de votação. Encerrando conexão.");
                return;
            }

            // Solicitar CPF ao usuário com validação
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite seu CPF (somente números): ");
            String cpf = scanner.nextLine();
            while (!isValidCPF(cpf)) {
                System.out.println("CPF inválido. Tente novamente.");
                System.out.print("Digite seu CPF (somente números): ");
                cpf = scanner.nextLine();
            }

            // Exibir opções de voto e permitir que o usuário selecione uma
            System.out.println("Pergunta: " + votingPackage.getQuestion());
            for (int i = 0; i < votingPackage.getOptions().size(); i++) {
                System.out.println((i + 1) + ". " + votingPackage.getOptions().get(i));
            }

            System.out.print("Digite o número da sua opção de voto: ");
            int option;
            while (true) {
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    if (option >= 1 && option <= votingPackage.getOptions().size()) {
                        break;
                    } else {
                        System.out.print("Opção inválida. Tente novamente: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Entrada inválida. Digite o número da sua opção de voto: ");
                }
            }

            // Enviar o CPF e o voto ao servidor
            sendVote(cpf, votingPackage.getOptions().get(option - 1));

            // Receber a resposta do servidor
            String serverResponse = receiveServerResponse();
            if (serverResponse != null) {
                System.out.println("Resposta do servidor: " + serverResponse);
            }

        } finally {
            // Desconectar-se do servidor
            disconnect();
        }
    }

    private VotingPackage receiveVotingPackage() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (VotingPackage) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao receber pacote de votação: " + e.getMessage());
            return null;
        }
    }

    private boolean isValidCPF(String cpf) {
        return Pattern.matches("\\d{11}", cpf); // Verifica se o CPF tem exatamente 11 dígitos
    }

    private void sendVote(String cpf, String vote) {
        try {
            outputStream.writeObject(new String[] { cpf, vote });
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar voto: " + e.getMessage());
        }
    }

    private String receiveServerResponse() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao receber resposta do servidor: " + e.getMessage());
            return null;
        }
    }

    private void disconnect() {
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
            new VotingClient().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Receber mensagens do servidor (caso necessário, pode ser implementado)
    }
}
