package teste;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;

public class ChatClient implements Runnable {
    private static final String SERVER_ADRESS = "127.0.0.1"; // local, na mesma máquina (localhost)
    private ClientSocket clientSocket;
    private Scanner sc;

    public ChatClient() {
        sc = new Scanner(System.in);
    }

    public void start() throws IOException {
        try {
            clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, ChatServer.PORT));
            out.println("Cliente conectado " + SERVER_ADRESS);
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }
    }

    @Override
    public void run() {
        String msg;

        while ((msg = clientSocket.getMessage()) != null) {
            System.out.println("Mensagem recebida do servidor: " + clientSocket.getMessage());
        }
    }

    private void messageLoop() { // loop de envio de mensagen, nao é infinito
        String msg;
        do {
            out.println("Digite: ");
            msg = sc.nextLine();
            clientSocket.sendMsg(msg);
        } while(!msg.equalsIgnoreCase("sair"));
    }
    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
